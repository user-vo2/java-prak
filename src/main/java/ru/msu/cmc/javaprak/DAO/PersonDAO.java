package ru.msu.cmc.javaprak.DAO;

        import lombok.Builder;
        import lombok.Getter;
        import ru.msu.cmc.javaprak.models.Person;

        import java.util.List;

public interface PersonDAO extends CommonDAO<Person, Long> {

    List<Person> getAllPersonByName(String personName);
    Person getSinglePersonByName(String personName);
    List<Person> getAllPersonByEducation(String edu);
    List<Person> getAllPersonBySalary(Long salary);
}