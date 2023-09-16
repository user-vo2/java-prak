package ru.msu.cmc.javaprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.javaprak.DAO.impl.CompanyDAOImpl;
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
public class VacancyDAOTest {

    @Autowired
    private VacancyDAO vacancyDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private SessionFactory sessionFactory;


    @BeforeEach
    void addVacancies() {

        List<Company> companies = new ArrayList<>();

        companies.add(new Company(1L, "Testcompany", "testspec", "88005553535", "test@test.test"));
        companies.add(new Company(2L, "Testcompany2", "testspec2", "88005553525", "test2@test.test"));
        companyDAO.saveCollection(companies);

        List<Vacancy> vacancies = new ArrayList<>();

        vacancies.add(new Vacancy(1L, companies.get(0), "testpos", 1234L, "testedu", 1L, "testother"));
        vacancies.add(new Vacancy(2L, companies.get(0), "testpos2", 2234L, "testedu2", 3L, "testother2"));
        vacancies.add(new Vacancy(3L, companies.get(1), "testpos2", 3234L, "testedu2", 4L, "testother2"));
        vacancies.add(new Vacancy(4L, companies.get(1), "testpos", 1234L, "testedu", 1L, "testother"));
        vacancyDAO.saveCollection(vacancies);


    }

    @AfterEach
    void eraseCompanies() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE companies RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testGetAll() {
        List<Vacancy> vacancies = (List<Vacancy>) vacancyDAO.getAll();
        assertEquals(4, vacancies.size());
    }

    @Test
    void testGetMultiple() {
        List<Vacancy> vacancies = vacancyDAO.getAllVacanciesByCompanyId(1L);
        assertEquals(2, vacancies.size());
    }
    @Test
    void testUpdate() {
        Vacancy vacancy = vacancyDAO.getById(1L);
        vacancy.setPosition("Testposnew");
        vacancyDAO.update(vacancy);
        assertEquals("Testposnew", vacancyDAO.getById(1L).getPosition());
    }

    @Test
    void testDelete() {
        companyDAO.deleteById(2L);
        assertNull(vacancyDAO.getAllVacanciesByCompanyId(2L));
        vacancyDAO.deleteById(3L);
        assertEquals(1,vacancyDAO.getAllVacanciesByCompanyId(1L).size());

    }

    @Test
    void testDeleteById() {
        vacancyDAO.deleteById(3L);
        assertNull(vacancyDAO.getById(3L));
    }
}