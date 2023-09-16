package ru.msu.cmc.javaprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.javaprak.DAO.PersonDAO;
import ru.msu.cmc.javaprak.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.javaprak.models.Person;
import ru.msu.cmc.javaprak.DAO.WorkingPlaceDAO;
import ru.msu.cmc.javaprak.DAO.impl.WorkingPlaceDAOImpl;
import ru.msu.cmc.javaprak.models.Vacancy;
import ru.msu.cmc.javaprak.models.WorkingPlace;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PersonController {

    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final WorkingPlaceDAO workingPlaceDAO = new WorkingPlaceDAOImpl();


    @GetMapping("/people")
    public String peopleListPage(@RequestParam(name = "education", required = false) String education,
                                 @RequestParam(name = "experience", required = false) Long experience,
                                 @RequestParam(name = "wanted_position", required = false) String wanted_position,
                                 @RequestParam(name = "wanted_salary", required = false) Long wanted_salary,
                                 Model model) {

        List<Person> people = new ArrayList<>();
        if ((education != null || experience != null || wanted_salary != null || wanted_position != null)) {
            List<Person> peopleSearch = (List<Person>) personDAO.getAll();
            for (Person person : peopleSearch) {
                if (person.getStatus() &&
                        ( education.isEmpty() || education.equals(person.getEdu()))
                        && (experience == null || experience <= person.getExperience())
                        && (wanted_position.isEmpty() || wanted_position.equals(person.getWanted_position()))
                        && (wanted_salary == null || wanted_salary >= person.getWanted_salary())) {
                    people.add(person);
                }
            }
        } else {
            people = (List<Person>) personDAO.getAll();
        }
        model.addAttribute("people", people);
        model.addAttribute("personService", personDAO);
        model.addAttribute("workingPlaceService", workingPlaceDAO);
        return "people";
    }

    @GetMapping("/person")
    public String personPage(@RequestParam(name = "personId") Long personId, Model model) {
        Person person = personDAO.getById(personId);

        if (person == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + personId);
            return "errorPage";
        }

        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        model.addAttribute("workingPlaceService", workingPlaceDAO);
        return "person";
    }

    @GetMapping("/editPerson")
    public String editPersonPage(@RequestParam(name = "personId", required = false) Long personId, Model model) {
        if (personId == null) {
            model.addAttribute("person", new Person());
            model.addAttribute("personService", personDAO);
            return "editPerson";
        }

        Person person = personDAO.getById(personId);

        if (person == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + personId);
            return "errorPage";
        }

        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        return "editPerson";
    }

    @PostMapping("/savePerson")
    public String savePersonPage(@RequestParam(name = "personId", required = false) Long personId,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "phone_num") String phone_num,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "education", required = false) String education,
                                 @RequestParam(name = "experience", required = false) Long experience,
                                 @RequestParam(name = "status") Boolean status,
                                 @RequestParam(name = "wanted_position", required = false) String position,
                                 @RequestParam(name = "wanted_salary", required = false) Long salary,
                                 Model model) {
        Person person;
        if (personId != null) {

            person = personDAO.getById(personId);
            person.setName(name);
            person.setPhone(phone_num);
            person.setEmail(email);
            person.setEdu(education);
            person.setExperience(experience);
            person.setStatus(status);
            person.setWanted_position(position);
            person.setWanted_salary(salary);
            personDAO.update(person);
        } else {
            person = new Person(null, name, phone_num,email, education, experience, status, position, salary);
            personDAO.save(person);
        }



        return "redirect:/person?personId=" + person.getId();
    }

    @PostMapping("/removePerson")
    public String removePersonPage(@RequestParam(name = "personId") Long personId) {
        List<WorkingPlace> workingPlaces = workingPlaceDAO.getAllWorkingPlacesByPersonId(personId);
        if (workingPlaces != null) {
            for (WorkingPlace workingPlace : workingPlaces) {
                workingPlaceDAO.delete(workingPlace);
            }
        }
        personDAO.deleteById(personId);
        return "redirect:/people";
    }
}