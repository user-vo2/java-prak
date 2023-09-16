package ru.msu.cmc.javaprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.javaprak.DAO.CompanyDAO;
import ru.msu.cmc.javaprak.DAO.VacancyDAO;
import ru.msu.cmc.javaprak.DAO.impl.CompanyDAOImpl;
import ru.msu.cmc.javaprak.DAO.impl.VacancyDAOImpl;
import ru.msu.cmc.javaprak.models.Company;
import ru.msu.cmc.javaprak.models.Vacancy;

import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private final CompanyDAO companyDAO = new CompanyDAOImpl();

    @Autowired
    private final VacancyDAO vacancyDAO = new VacancyDAOImpl();
    @GetMapping("/companies")
    public String companiesListPage(Model model) {
        List<Company> companies = (List<Company>) companyDAO.getAll();
        model.addAttribute("companies", companies);
        model.addAttribute("companyService", companyDAO);
        model.addAttribute("vacancyService", vacancyDAO);
        return "companies";
    }

    @GetMapping("/company")
    public String companyPage(@RequestParam(name = "companyId") Long companyId, Model model) {
        Company company = companyDAO.getById(companyId);

        if (company == null) {
            model.addAttribute("error_msg", "В базе нет компании с ID = " + companyId);
            return "errorPage";
        }

        model.addAttribute("company", company);
        model.addAttribute("companyService", companyDAO);
        model.addAttribute("vacancyService", vacancyDAO);
        return "company";
    }

    @GetMapping("/editCompany")
    public String editCompanyPage(@RequestParam(name = "companyId", required = false) Long companyId, Model model) {
        if (companyId == null) {
            model.addAttribute("company", new Company());
            model.addAttribute("companyService", companyDAO);
            model.addAttribute("vacancyService", vacancyDAO);
            return "editCompany";
        }

        Company company = companyDAO.getById(companyId);

        if (company == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + companyId);
            return "errorPage";
        }

        model.addAttribute("company", company);
        model.addAttribute("companyService", companyDAO);
        model.addAttribute("vacancyService", vacancyDAO);
        return "editCompany";
    }

    @PostMapping("/saveCompany")
    public String saveCompanyPage(@RequestParam(name = "companyId", required = false) Long companyId,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "specialization") String specialization,
                                 @RequestParam(name = "phone_num") String phone_num,
                                 @RequestParam(name = "email") String email,
                                 Model model) {

        Company company;
        if (companyId != null) {
            company = companyDAO.getById(companyId);
            company.setName(name);
            company.setSpec(specialization);
            company.setPhone(phone_num);
            company.setEmail(email);
            companyDAO.update(company);
        } else {
            company = new Company(null, name, specialization,phone_num,email);
            companyDAO.save(company);
        }

        return "redirect:/company?companyId=" + company.getId();
    }

    @PostMapping("/removeCompany")
    public String removeCompanyPage(@RequestParam(name = "companyId") Long companyId) {
        List<Vacancy> vacancies = vacancyDAO.getAllVacanciesByCompanyId(companyId);
        if (vacancies != null) {
            for (Vacancy vacancy : vacancies) {
                vacancyDAO.delete(vacancy);
            }
        }
        companyDAO.deleteById(companyId);
        return "redirect:/companies";
    }
}