package ru.msu.cmc.javaprak.DAO.impl;

import ru.msu.cmc.javaprak.DAO.PersonDAO;
import ru.msu.cmc.javaprak.models.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAOImpl extends CommonDAOImpl<Person, Long> implements PersonDAO {

    public PersonDAOImpl() {
        super(Person.class);
    }

    @Override
    public Person getSinglePersonByName(String personName) {
        List<Person> people = this.getAllPersonByName(personName);
        return people == null ? null :
                people.size() == 1 ? people.get(0) : null;
    }


    @Override
    public List<Person> getAllPersonByName(String personName) {
        if (personName == null || personName.isBlank()) {
            return (List<Person>) getAll();
        }

        try (Session session = sessionFactory.openSession()) {
            Query<Person> query = session.createQuery("FROM Person WHERE name LIKE :queryName", Person.class)
                    .setParameter("queryName", likeTemplate(personName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Person> getAllPersonByEducation(String edu) {
        if (edu == null || edu.isBlank()) {
            return (List<Person>) getAll();
        }

        try (Session session = sessionFactory.openSession()) {
            Query<Person> query = session.createQuery("FROM Person WHERE edu LIKE :queryName", Person.class)
                    .setParameter("queryName", likeTemplate(edu));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Person> getAllPersonBySalary(Long salary) {
        if (salary == null) {
            return (List<Person>) getAll();
        }

        try (Session session = sessionFactory.openSession()) {
            Query<Person> query = session.createQuery("FROM Person WHERE wanted_salary <= :queryName", Person.class)
                    .setParameter("queryName", salary);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeTemplate(String s) {
        return "%" + s + "%";
    }
}
