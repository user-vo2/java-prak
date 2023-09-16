package ru.msu.cmc.javaprak.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.javaprak.models.Vacancy;

import java.util.List;

public interface VacancyDAO extends CommonDAO<Vacancy, Long> {

    List<Vacancy> getAllVacanciesByCompanyId(Long companyId);


}