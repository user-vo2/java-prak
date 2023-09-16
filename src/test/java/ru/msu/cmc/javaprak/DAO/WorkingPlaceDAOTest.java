package ru.msu.cmc.javaprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.javaprak.DAO.impl.CompanyDAOImpl;
import ru.msu.cmc.javaprak.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.javaprak.DAO.impl.VacancyDAOImpl;
import ru.msu.cmc.javaprak.models.Person;
import ru.msu.cmc.javaprak.models.Vacancy;
import ru.msu.cmc.javaprak.models.Company;
import ru.msu.cmc.javaprak.models.WorkingPlace;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static ru.msu.cmc.javaprak.models.Vacancy.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class WorkingPlaceDAOTest {

    @Autowired
    private VacancyDAO vacancyDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private WorkingPlaceDAO workingPlaceDAO;
    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private SessionFactory sessionFactory;


    @BeforeEach
    void addVacancies() {
        List<Person> peopleList = new ArrayList<>();
        peopleList.add(new Person(1L, "Иван Иванович", "89990001122", "ivan@mail.ru", "среднее профессиональное", 4L, true, "Менеджер", 80000L));
        peopleList.add(new Person(2L, "Александр Смирнов", "89990101122", "alex@mail.ru", "высшее", 3L, true, "Администратор", 100000L));
        personDAO.saveCollection(peopleList);
        List<WorkingPlace> workingPlaces = new ArrayList<>();

        workingPlaces.add(new WorkingPlace(1L, peopleList.get(0), "testcompany", Date.valueOf("2022-04-30"), Date.valueOf("2022-04-30"), "testpos"));
        workingPlaces.add(new WorkingPlace(2L, peopleList.get(0), "testcompany", Date.valueOf("2022-04-30"), Date.valueOf("2022-04-30"), "testpos2"));
        workingPlaces.add(new WorkingPlace(3L, peopleList.get(1), "testcompany", Date.valueOf("2022-04-30"), Date.valueOf("2022-04-30"), "testpos2"));
        workingPlaces.add(new WorkingPlace(4L, peopleList.get(1), "testcompany", Date.valueOf("2022-04-30"), Date.valueOf("2022-04-30"), "testpos"));
        workingPlaceDAO.saveCollection(workingPlaces);


    }

    @AfterEach
    void erasePeople() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE people RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testGetAll() {
        List<WorkingPlace> workingPlaces = (List<WorkingPlace>) workingPlaceDAO.getAll();
        assertEquals(4, workingPlaces.size());
    }

    @Test
    void testGetMultiple() {
        List<WorkingPlace> workingPlaces = workingPlaceDAO.getAllWorkingPlacesByPersonId(1L);
        assertEquals(2, workingPlaces.size());
    }
    @Test
    void testUpdate() {
        WorkingPlace workingPlace = workingPlaceDAO.getById(1L);
        workingPlace.setPosition("Testposnew");
        workingPlaceDAO.update(workingPlace);
        assertEquals("Testposnew", workingPlaceDAO.getById(1L).getPosition());
    }

    @Test
    void testDelete() {
        List<WorkingPlace> workingPlaces = workingPlaceDAO.getAllWorkingPlacesByPersonId(2L);
        personDAO.deleteById(2L);
        assertNull(vacancyDAO.getAllVacanciesByCompanyId(2L));
        workingPlaceDAO.deleteById(1L);
        assertEquals(1, workingPlaceDAO.getAllWorkingPlacesByPersonId(1L).size());

    }

    @Test
    void testDeleteById() {
        workingPlaceDAO.deleteById(3L);
        assertNull(workingPlaceDAO.getById(3L));
    }
}