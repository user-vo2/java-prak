package ru.msu.cmc.javaprak.DAO.impl;

import ru.msu.cmc.javaprak.DAO.VacancyDAO;
import ru.msu.cmc.javaprak.models.Person;
import ru.msu.cmc.javaprak.models.Vacancy;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VacancyDAOImpl extends CommonDAOImpl<Vacancy, Long> implements VacancyDAO {

    public VacancyDAOImpl() {
        super(Vacancy.class);
    }

    @Override
    public List<Vacancy> getAllVacanciesByCompanyId  (Long companyId) {
        if (companyId == null) {
            return (List<Vacancy>) getAll();
        }

        try (Session session = sessionFactory.openSession()) {
            Query<Vacancy> query = session.createQuery("FROM Vacancy WHERE company.id = :queryName", Vacancy.class)
                    .setParameter("queryName", companyId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}
