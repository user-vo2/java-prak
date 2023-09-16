package ru.msu.cmc.javaprak.DAO.impl;

import ru.msu.cmc.javaprak.DAO.WorkingPlaceDAO;
import ru.msu.cmc.javaprak.models.Vacancy;
import ru.msu.cmc.javaprak.models.WorkingPlace;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkingPlaceDAOImpl extends CommonDAOImpl<WorkingPlace, Long> implements WorkingPlaceDAO {

    public WorkingPlaceDAOImpl() {
        super(WorkingPlace.class);
    }

    @Override
    public List<WorkingPlace> getAllWorkingPlacesByPersonId(Long personId) {
        if (personId == null) {
            return (List<WorkingPlace>) getAll();
        }

        try (Session session = sessionFactory.openSession()) {
            Query<WorkingPlace> query = session.createQuery("FROM WorkingPlace WHERE person.id = :queryName", WorkingPlace.class)
                    .setParameter("queryName", personId);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

}
