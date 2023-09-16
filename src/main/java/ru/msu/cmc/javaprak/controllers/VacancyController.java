package ru.msu.cmc.javaprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.javaprak.DAO.VacancyDAO;
import ru.msu.cmc.javaprak.DAO.CompanyDAO;
import ru.msu.cmc.javaprak.DAO.impl.VacancyDAOImpl;
import ru.msu.cmc.javaprak.DAO.impl.CompanyDAOImpl;
import ru.msu.cmc.javaprak.models.Company;
import ru.msu.cmc.javaprak.models.Person;
import ru.msu.cmc.javaprak.models.Vacancy;

import java.util.ArrayList;
import java.util.List;
@Controller
public class VacancyController {

    @Autowired
    private final VacancyDAO vacancyDAO = new VacancyDAOImpl();

    @Autowired
    private final CompanyDAO companyDAO = new CompanyDAOImpl();


    @GetMapping("/vacancies")
    public String vacanciesListPage(@RequestParam(name = "companyId", required = false) Long companyId,
                                    @RequestParam(name = "educationVacancy", required = false) String education,
                                    @RequestParam(name = "experienceVacancy", required = false) Long experience,
                                    @RequestParam(name = "positionVacancy", required = false) String wanted_position,
                                    @RequestParam(name = "salaryVacancy", required = false) Long wanted_salary,
                                    Model model) {
        List<Vacancy> vacancies = new ArrayList<>();
        if (companyId != null) {
            vacancies = (List<Vacancy>) vacancyDAO.getAllVacanciesByCompanyId(companyId);
        } else {
            if ((education != null || experience != null || wanted_salary != null || wanted_position != null)) {
                List<Vacancy> vacanciesSearch = (List<Vacancy>) vacancyDAO.getAll();
                for (Vacancy vacancy : vacanciesSearch) {
                    if (    ( education.isEmpty() || education.equals(vacancy.getEducation()))
                            && (experience == null || experience <= vacancy.getExperience())
                            && (wanted_position.isEmpty() || wanted_position.equals(vacancy.getPosition()))
                            && (wanted_salary == null || wanted_salary >= vacancy.getSalary())) {
                        vacancies.add(vacancy);
                    }
                }
            } else {
                vacancies = (List<Vacancy>) vacancyDAO.getAll();
            }
        }
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("companyId", companyId);
        model.addAttribute("vacancyService", vacancyDAO);
        model.addAttribute("companyService", companyDAO);
        return "vacancies";
    }

    @GetMapping("/vacancy")
    public String vacancyPage(@RequestParam(name = "vacancyId") Long vacancyId,
                              @RequestParam(name = "companyId", required = false) Long companyId, Model model) {
        Vacancy vacancy = vacancyDAO.getById(vacancyId);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("vacancyService", vacancyDAO);
        model.addAttribute("companyId", companyId);
        model.addAttribute("companyService", companyDAO);
        return "vacancy";
    }

    @GetMapping("/editVacancy")
    public String editVacancyPage(@RequestParam(name = "vacancyId", required = false) Long vacancyId,
                                  @RequestParam(name = "companyId", required = false) Long companyId,Model model) {
        if (vacancyId == null) {
            model.addAttribute("vacancy", new Vacancy());
            model.addAttribute("companyId",companyId);
            model.addAttribute("vacancyService", vacancyDAO);
            return "editVacancy";
        }

        Vacancy vacancy = vacancyDAO.getById(vacancyId);

        model.addAttribute("vacancy", vacancy);
        model.addAttribute("companyId",companyId);
        model.addAttribute("vacancyService", vacancyDAO);
        model.addAttribute("companyService", companyDAO);
        return "editVacancy";
    }

    @PostMapping("/saveVacancy")
    public String saveVacancyPage(@RequestParam(name = "vacancyId", required = false) Long vacancyId,
                                 @RequestParam(name = "companyId") Long companyId,
                                 @RequestParam(name = "position") String position,
                                 @RequestParam(name = "salary") Long salary,
                                 @RequestParam(name = "education", required = false) String education,
                                 @RequestParam(name = "experience", required = false) Long experience,
                                 @RequestParam(name = "other", required = false) String other,
                                 Model model) {
        boolean changeIsSuccessful = false;
        Vacancy vacancy;
        if (vacancyId != null) {

            vacancy = vacancyDAO.getById(vacancyId);
            vacancy.setCompany(companyDAO.getById(companyId));
            vacancy.setPosition(position);
            vacancy.setSalary(salary);
            vacancy.setEducation(education);
            vacancy.setExperience(experience);
            vacancy.setOther(other);
            vacancyDAO.update(vacancy);
        } else {

            vacancy = new Vacancy(null, companyDAO.getById(companyId), position,salary, education, experience, other);
            vacancyDAO.save(vacancy);
        }

        return "redirect:/vacancy?vacancyId=" + vacancy.getId();
    }

    @PostMapping("/removeVacancy")
    public String removeVacancyPage(@RequestParam(name = "vacancyId") Long vacancyId) {
        Long companyId = vacancyDAO.getById(vacancyId).getCompany().getId();
        vacancyDAO.deleteById(vacancyId);
        return "redirect:/vacancies?companyId=" + companyId;
    }
}