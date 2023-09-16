package ru.msu.cmc.javaprak.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.javaprak.models.Person;
import ru.msu.cmc.javaprak.models.WorkingPlace;

import java.util.List;

public interface WorkingPlaceDAO extends CommonDAO<WorkingPlace, Long> {

    List<WorkingPlace> getAllWorkingPlacesByPersonId(Long personId);
}