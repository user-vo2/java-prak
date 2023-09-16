package ru.msu.cmc.javaprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.javaprak.DAO.PersonDAO;
import ru.msu.cmc.javaprak.DAO.WorkingPlaceDAO;
import ru.msu.cmc.javaprak.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.javaprak.DAO.impl.WorkingPlaceDAOImpl;
import ru.msu.cmc.javaprak.models.WorkingPlace;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkingPlaceController {

    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final WorkingPlaceDAO workingPlaceDAO = new WorkingPlaceDAOImpl();

//    @Autowired
//    private final People2WorkingplacesDAO people2WorkingplacesDAO = new People2WorkingplacesDAOImpl();

    @GetMapping("/workingPlaces")
    public String workingPlacesListPage(@RequestParam(name = "personId") Long personId, Model model) {
        List<WorkingPlace> workingPlaces = workingPlaceDAO.getAllWorkingPlacesByPersonId(personId);
        if (workingPlaces == null) {
            workingPlaces = new ArrayList<>();
        }
        model.addAttribute("workingPlaces", workingPlaces);
        model.addAttribute("workingPlaceService", workingPlaceDAO);
        model.addAttribute("personService", personDAO);
        model.addAttribute("personId", personId);
        return "workingPlaces";
    }

    @GetMapping("/workingPlace")
    public String workingPlacePage(@RequestParam(name = "workingPlaceId") Long workingPlaceId,
                                   @RequestParam(name = "personId", required = false) Long personId,Model model) {
        WorkingPlace workingPlace = workingPlaceDAO.getById(workingPlaceId);

        if (workingPlace == null) {
            model.addAttribute("error_msg", "В базе нет места с ID = " + workingPlaceId);
            return "errorPage";
        }

        model.addAttribute("workingPlace", workingPlace);
        model.addAttribute("workingPlaceService", workingPlaceDAO);
        model.addAttribute("personService", personDAO);
        model.addAttribute("personId", personId);
        return "workingPlace";
    }

    @GetMapping("/editWorkingPlace")
    public String editWorkingPlacePage(@RequestParam(name = "workingPlaceId", required = false) Long workingPlaceId,
                                       @RequestParam(name = "personId", required = false) Long personId,Model model) {
        WorkingPlace workingPlace = new WorkingPlace();
        if (workingPlaceId != null) {
            workingPlace = workingPlaceDAO.getById(workingPlaceId);
        }

        model.addAttribute("workingPlace", workingPlace);
        model.addAttribute("personId", personId);
        model.addAttribute("personService", personDAO);
        model.addAttribute("workingPlaceService", workingPlaceDAO);
        return "editWorkingPlace";
    }

    @PostMapping("/saveWorkingPlace")
    public String saveWorkingPlacePage(@RequestParam(name = "workingPlaceId", required = false) Long workingPlaceId,
                                       @RequestParam(name = "personId") Long personId,
                                 @RequestParam(name = "company_name") String company_name,
                                 @RequestParam(name = "startofperiod") String startofperiod,
                                 @RequestParam(name = "endofperiod") String endofperiod,
                                 @RequestParam(name = "position") String position,
                                 Model model) {
        boolean changeIsSuccessful = false;
        WorkingPlace workingPlace;
        if (workingPlaceId != null) {
            workingPlace = workingPlaceDAO.getById(workingPlaceId);
            workingPlace.setPerson(personDAO.getById(personId));
            workingPlace.setCompany_name(company_name);
            workingPlace.setStart(Date.valueOf(startofperiod));
            workingPlace.setEnd(Date.valueOf(endofperiod));
            workingPlace.setPosition(position);
            workingPlaceDAO.update(workingPlace);
        } else {
            workingPlace = new WorkingPlace(null, personDAO.getById(personId), company_name, Date.valueOf(startofperiod),Date.valueOf(endofperiod), position);
            workingPlaceDAO.save(workingPlace);
        }

        return "redirect:/workingPlace?workingPlaceId=" + workingPlace.getId();
    }

    @PostMapping("/removeWorkingPlace")
    public String removeWorkingPlacePage(@RequestParam(name = "workingPlaceId") Long workingPlaceId) {
        Long personId = workingPlaceDAO.getById(workingPlaceId).getPerson().getId();
        workingPlaceDAO.deleteById(workingPlaceId);
        return "redirect:/workingPlaces?personId=" + personId;
    }
}