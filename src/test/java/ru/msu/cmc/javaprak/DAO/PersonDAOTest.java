package ru.msu.cmc.javaprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.javaprak.models.Person;
import ru.msu.cmc.javaprak.models.WorkingPlace;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static ru.msu.cmc.javaprak.models.Person.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class PersonDAOTest {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private WorkingPlaceDAO workingPlaceDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void addPeople() {

        List<Person> peopleList = new ArrayList<>();
        peopleList.add(new Person(1L, "Иван Иванович", "89990001122", "ivan@mail.ru", "среднее профессиональное", 4L, true, "Менеджер", 80000L));
        peopleList.add(new Person(2L, "Александр Смирнов", "89990101122", "alex@mail.ru", "высшее", 3L, true, "Администратор", 100000L));
        peopleList.add(new Person(3L, "Михаил Кузнецов", "89990201122", "michael@mail.ru", "высшее", 0L, true, "Кондитер", 60000L));
        peopleList.add(new Person(4L, "Максим Попов", "89990301122", "max@mail.ru", "высшее", 6L, true, "Оператор", 120000L));
        peopleList.add(new Person(5L, "Артём Васильев", "89990401122", "artem@mail.ru", "среднее профессиональное", 4L, true, "Водитель", 110000L));
        peopleList.add(new Person(6L, "Артем Васильев", "89990401122", "artem@mail.ru", "среднее профессиональное", 4L, false, null, null));
        personDAO.saveCollection(peopleList);

//        List<WorkingPlace> workingPlaceList = new ArrayList<>();
//        workingPlaceList.add(new WorkingPlace(1L,"Русснефть", Date.valueOf("2022-04-30"),Date.valueOf("2023-02-20"), "Менеджер"));
//        workingPlaceList.add(new WorkingPlace(2L,"Трансмашхолдинг",Date.valueOf("2023-05-10"),Date.valueOf("2023-07-21"), "Оператор"));
//        workingPlaceList.add(new WorkingPlace(3L,"Металлоинвест",Date.valueOf("2021-06-20"),Date.valueOf("2023-04-22"), "Администратор"));
//        workingPlaceList.add(new WorkingPlace(4L,"Металлоинвест",Date.valueOf("2020-07-30"),Date.valueOf("2023-05-23"), "Водитель"));
//        workingPlaceList.add(new WorkingPlace(5L,"Карусель",Date.valueOf("2019-08-22"),Date.valueOf("2022-04-24"), "Менеджер"));
//        workingPlaceDAO.saveCollection(workingPlaceList);
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
        List<Person> peopleList = (List<Person>) personDAO.getAll();
        assertEquals(6, peopleList.size());
    }

    @Test
    void testGetMultiple() {
        List<Person> peopleList = personDAO.getAllPersonByName("Васильев");
        assertEquals(2, peopleList.size());
        peopleList = personDAO.getAllPersonByEducation("высшее");
        assertEquals(3, peopleList.size());
        peopleList = personDAO.getAllPersonBySalary(80000L);
        assertEquals(2, peopleList.size());

    }

    @Test
    void testEducation() {
        List<Person> personList = personDAO.getAllPersonByEducation("высшее");
        assertEquals(3, personList.size());
    }

    @Test
    void testMinSalary() {
        List<Person> personList = personDAO.getAllPersonBySalary(80000L);
        assertEquals(2, personList.size());
    }

    @Test
    void testUpdate() {
        Person person = personDAO.getSinglePersonByName("Иван");
        person.setEmail("ivan2344@gmail.com");
        personDAO.update(person);
        assertEquals("ivan2344@gmail.com", personDAO.getById(1L).getEmail());
    }

    @Test
    void testDelete() {
        List<Person> people = personDAO.getAllPersonByName("Артём Васильев");
        personDAO.delete(people.get(0));
        assertNull(personDAO.getAllPersonByName("Артём Васильев"));
    }

    @Test
    void testDeleteById() {
        personDAO.deleteById(3L);
        assertNull(personDAO.getById(3L));
    }
}