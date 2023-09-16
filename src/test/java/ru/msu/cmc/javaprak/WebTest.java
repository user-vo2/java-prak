package ru.msu.cmc.javaprak;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class WebTest {

    private final String rootTitle = "Главная страница";
    private final String peopleTitle = "Люди";
    private final String companiesTitle = "Компании";
    private final String personTitle = "Информация о человеке";
    private final String companyTitle = "Информация о компании";
    @Test
    void MainPage() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        driver.quit();
    }

    @Test
    void HeaderTest() {
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1024,768));
        driver.get("http://localhost:8080/");

        WebElement peopleButton = driver.findElement(By.id("peopleListLink"));
        peopleButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(peopleTitle, driver.getTitle());

        WebElement rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(rootTitle, driver.getTitle());

        WebElement placesButton = driver.findElement(By.id("companiesListLink"));
        placesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(companiesTitle, driver.getTitle());

        rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(rootTitle, driver.getTitle());

        driver.quit();
    }
    @Test
    void mainPage() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        driver.quit();
    }

    @Test
    void addupdatedeletePerson() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/people");
        assertEquals(peopleTitle, driver.getTitle());
        WebElement addPerson = driver.findElement(By.id("addPersonButton"));
        addPerson.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editPersonTitle = "Редактировать данные";
        assertEquals(editPersonTitle, driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Ivan Ivanovich");
        driver.findElement(By.id("phone_num")).sendKeys("88005553535");
        driver.findElement(By.id("email")).sendKeys("ivan@gmail.com");
        driver.findElement(By.id("education")).sendKeys("University");
        driver.findElement(By.id("experience")).sendKeys("6");
        driver.findElement(By.id("statust")).click();
        driver.findElement(By.id("wanted_position")).sendKeys("manager");
        driver.findElement(By.id("wanted_salary")).sendKeys("100000");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(personTitle, driver.getTitle());
        WebElement personInfo = driver.findElement(By.id("personInfo"));
        List<WebElement> cells = personInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Имя: Ivan Ivanovich");
        assertEquals(cells.get(1).getText(), "Телефон: 88005553535");
        assertEquals(cells.get(2).getText(), "email: ivan@gmail.com");
        assertEquals(cells.get(3).getText(), "Образование: University");
        assertEquals(cells.get(4).getText(), "Опыт: 6");
        assertEquals(cells.get(5).getText(), "Статус: Ищет работу");
        assertEquals(cells.get(6).getText(), "Должность, на которую претендует: manager");
        assertEquals(cells.get(7).getText(), "Зарлата, которую хочет: 100000");

        driver.findElement(By.id("editButton")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("phone_num")).clear();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("education")).clear();
        driver.findElement(By.id("experience")).clear();
        driver.findElement(By.id("wanted_position")).clear();
        driver.findElement(By.id("wanted_salary")).clear();
        driver.findElement(By.id("name")).sendKeys("Ivan Ivanovich2");
        driver.findElement(By.id("phone_num")).sendKeys("88005553532");
        driver.findElement(By.id("email")).sendKeys("ivan2@gmail.com");
        driver.findElement(By.id("education")).sendKeys("University2");
        driver.findElement(By.id("experience")).sendKeys("2");
        driver.findElement(By.id("statust")).click();
        driver.findElement(By.id("wanted_position")).sendKeys("manager2");
        driver.findElement(By.id("wanted_salary")).sendKeys("100002");
        driver.findElement(By.id("submitButton")).click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(personTitle, driver.getTitle());
        personInfo = driver.findElement(By.id("personInfo"));
        cells = personInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Имя: Ivan Ivanovich2");
        assertEquals(cells.get(1).getText(), "Телефон: 88005553532");
        assertEquals(cells.get(2).getText(), "email: ivan2@gmail.com");
        assertEquals(cells.get(3).getText(), "Образование: University2");
        assertEquals(cells.get(4).getText(), "Опыт: 2");
        assertEquals(cells.get(5).getText(), "Статус: Ищет работу");
        assertEquals(cells.get(6).getText(), "Должность, на которую претендует: manager2");
        assertEquals(cells.get(7).getText(), "Зарлата, которую хочет: 100002");

        WebElement deletePerson = driver.findElement(By.id("deleteButton"));
        deletePerson.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(peopleTitle, driver.getTitle());
        personInfo = driver.findElement(By.id("empty"));
        cells = personInfo.findElements(By.tagName("td"));
        assertEquals(cells.get(0).getText(), "В базе пока нет ни одного человека.");

        driver.quit();
    }

    @Test
    void addupdatedeleteCompany() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/companies");
        assertEquals(companiesTitle, driver.getTitle());
        WebElement addCompany = driver.findElement(By.id("addCompanyButton"));
        addCompany.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editCompanyTitle = "Редактировать данные";
        assertEquals(editCompanyTitle, driver.getTitle());


        driver.findElement(By.id("name")).sendKeys("Gasprom");
        driver.findElement(By.id("specialization")).sendKeys("Oil and gas");
        driver.findElement(By.id("phone_num")).sendKeys("88005553533");
        driver.findElement(By.id("email")).sendKeys("gasprom@yandex.ru");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(companyTitle, driver.getTitle());
        WebElement companyInfo = driver.findElement(By.id("companyInfo"));
        List<WebElement> cells = companyInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Название: Gasprom");
        assertEquals(cells.get(1).getText(), "Специализация: Oil and gas");
        assertEquals(cells.get(2).getText(), "Телефон: 88005553533");
        assertEquals(cells.get(3).getText(), "email: gasprom@yandex.ru");

        driver.findElement(By.id("editButton")).click();

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("specialization")).clear();
        driver.findElement(By.id("phone_num")).clear();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("name")).sendKeys("Gasprom2");
        driver.findElement(By.id("specialization")).sendKeys("Oil and gas2");
        driver.findElement(By.id("phone_num")).sendKeys("88005553532");
        driver.findElement(By.id("email")).sendKeys("gasprom2@yandex.ru");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(companyTitle, driver.getTitle());
        companyInfo = driver.findElement(By.id("companyInfo"));
        cells = companyInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Название: Gasprom2");
        assertEquals(cells.get(1).getText(), "Специализация: Oil and gas2");
        assertEquals(cells.get(2).getText(), "Телефон: 88005553532");
        assertEquals(cells.get(3).getText(), "email: gasprom2@yandex.ru");

        WebElement deleteCompany = driver.findElement(By.id("deleteButton"));
        deleteCompany.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(companiesTitle, driver.getTitle());
        companyInfo = driver.findElement(By.id("empty"));
        cells = companyInfo.findElements(By.tagName("td"));
        assertEquals(cells.get(0).getText(), "В базе пока нет ни одной компании.");

        driver.quit();
    }

    @Test
    void addupdatedeleteWorkingPlace() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/people");
        assertEquals(peopleTitle, driver.getTitle());
        WebElement addPerson = driver.findElement(By.id("addPersonButton"));
        addPerson.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editPersonTitle = "Редактировать данные";
        assertEquals(editPersonTitle, driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Ivan Ivanovich");
        driver.findElement(By.id("phone_num")).sendKeys("88005553535");
        driver.findElement(By.id("email")).sendKeys("ivan@gmail.com");
        driver.findElement(By.id("education")).sendKeys("University");
        driver.findElement(By.id("experience")).sendKeys("6");
        driver.findElement(By.id("statust")).click();
        driver.findElement(By.id("wanted_position")).sendKeys("manager");
        driver.findElement(By.id("wanted_salary")).sendKeys("100000");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        WebElement workingPlaces = driver.findElement(By.id("personId"));
        workingPlaces.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String workingPlacesTitle = "Места работы";
        assertEquals(workingPlacesTitle, driver.getTitle());

        WebElement addworkingPlace = driver.findElement(By.id("addWorkingPlaceButton"));
        addworkingPlace.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(editPersonTitle, driver.getTitle());

        driver.findElement(By.id("company_name")).sendKeys("Testcompany");
        driver.findElement(By.id("position")).sendKeys("testposition");
        driver.findElement(By.id("startofperiod")).sendKeys("6202020");
        driver.findElement(By.id("endofperiod")).sendKeys("6202023");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        String workingPlaceTitle = "Информация о месте";
        assertEquals(workingPlaceTitle, driver.getTitle());
        WebElement workingPlaceInfo = driver.findElement(By.id("workingPlaceInfo"));
        List<WebElement> cells = workingPlaceInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Компания: Testcompany");
        assertEquals(cells.get(1).getText(), "Должность: testposition");
        assertEquals(cells.get(2).getText(), "Начало работы: 2020-06-20");
        assertEquals(cells.get(3).getText(), "Конец работы: 2023-06-20");

        driver.findElement(By.id("editButton")).click();

        driver.findElement(By.id("company_name")).clear();
        driver.findElement(By.id("position")).clear();
        driver.findElement(By.id("startofperiod")).clear();
        driver.findElement(By.id("endofperiod")).clear();
        driver.findElement(By.id("company_name")).sendKeys("Testcompany2");
        driver.findElement(By.id("position")).sendKeys("testposition2");
        driver.findElement(By.id("startofperiod")).sendKeys("6202022");
        driver.findElement(By.id("endofperiod")).sendKeys("6202024");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(workingPlaceTitle, driver.getTitle());
        workingPlaceInfo = driver.findElement(By.id("workingPlaceInfo"));
        cells = workingPlaceInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Компания: Testcompany2");
        assertEquals(cells.get(1).getText(), "Должность: testposition2");
        assertEquals(cells.get(2).getText(), "Начало работы: 2022-06-20");
        assertEquals(cells.get(3).getText(), "Конец работы: 2024-06-20");

        driver.findElement(By.id("deleteButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(workingPlacesTitle, driver.getTitle());
        workingPlaceInfo = driver.findElement(By.id("empty"));
        cells = workingPlaceInfo.findElements(By.tagName("td"));
        assertEquals(cells.get(0).getText(), "В базе пока нет ни одного места работы.");

        driver.get("http://localhost:8080/people");
        WebElement person = driver.findElement(By.id("people")).findElement(By.tagName("a"));
        person.click();
        assertEquals(personTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();
        assertEquals(peopleTitle, driver.getTitle());

        driver.quit();

    }

    @Test
    void addupdatedeleteVacancy() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/companies");
        assertEquals(companiesTitle, driver.getTitle());
        WebElement addCompany = driver.findElement(By.id("addCompanyButton"));
        addCompany.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editCompanyTitle = "Редактировать данные";
        assertEquals(editCompanyTitle, driver.getTitle());


        driver.findElement(By.id("name")).sendKeys("Gasprom");
        driver.findElement(By.id("specialization")).sendKeys("Oil and gas");
        driver.findElement(By.id("phone_num")).sendKeys("88005553533");
        driver.findElement(By.id("email")).sendKeys("gasprom@yandex.ru");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(companyTitle, driver.getTitle());

        WebElement vacancies = driver.findElement(By.id("companyId"));
        vacancies.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String vacanciesTitle = "Вакансии";
        assertEquals(vacanciesTitle, driver.getTitle());

        WebElement addVacancy = driver.findElement(By.id("addVacancyButton"));
        addVacancy.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(editCompanyTitle, driver.getTitle());

        driver.findElement(By.id("position")).sendKeys("testposition");
        driver.findElement(By.id("salary")).sendKeys("111111");
        driver.findElement(By.id("education")).sendKeys("testEducation");
        driver.findElement(By.id("experience")).sendKeys("1");
        driver.findElement(By.id("other")).sendKeys("testOther");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        String vacancyTitle = "Информация о вакансии";
        assertEquals(vacancyTitle, driver.getTitle());
        WebElement vacancyInfo = driver.findElement(By.id("vacancyInfo"));
        List<WebElement> cells = vacancyInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Должность: testposition");
        assertEquals(cells.get(1).getText(), "Зарплата: 111111");
        assertEquals(cells.get(2).getText(), "Образование: testEducation");
        assertEquals(cells.get(3).getText(), "Опыт: 1");
        assertEquals(cells.get(4).getText(), "Другое: testOther");

        driver.findElement(By.id("editButton")).click();

        driver.findElement(By.id("position")).clear();
        driver.findElement(By.id("salary")).clear();
        driver.findElement(By.id("education")).clear();
        driver.findElement(By.id("experience")).clear();
        driver.findElement(By.id("other")).clear();
        driver.findElement(By.id("position")).sendKeys("testposition2");
        driver.findElement(By.id("salary")).sendKeys("1111112");
        driver.findElement(By.id("education")).sendKeys("testEducation2");
        driver.findElement(By.id("experience")).sendKeys("12");
        driver.findElement(By.id("other")).sendKeys("testOther2");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(vacancyTitle, driver.getTitle());
        vacancyInfo = driver.findElement(By.id("vacancyInfo"));
        cells = vacancyInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Должность: testposition2");
        assertEquals(cells.get(1).getText(), "Зарплата: 1111112");
        assertEquals(cells.get(2).getText(), "Образование: testEducation2");
        assertEquals(cells.get(3).getText(), "Опыт: 12");
        assertEquals(cells.get(4).getText(), "Другое: testOther2");

        driver.findElement(By.id("deleteButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(vacanciesTitle, driver.getTitle());
        vacancyInfo = driver.findElement(By.id("empty"));
        cells = vacancyInfo.findElements(By.tagName("td"));
        assertEquals(cells.get(0).getText(), "В базе пока нет ни одной вакансии.");

        driver.get("http://localhost:8080/companies");
        driver.findElement(By.id("companies")).findElement(By.tagName("a")).click();
        assertEquals(companyTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();
        assertEquals(companiesTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void findVacancies() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/companies");
        assertEquals(companiesTitle, driver.getTitle());
        WebElement addCompany = driver.findElement(By.id("addCompanyButton"));
        addCompany.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editCompanyTitle = "Редактировать данные";
        assertEquals(editCompanyTitle, driver.getTitle());


        driver.findElement(By.id("name")).sendKeys("Gasprom");
        driver.findElement(By.id("specialization")).sendKeys("Oil and gas");
        driver.findElement(By.id("phone_num")).sendKeys("88005553533");
        driver.findElement(By.id("email")).sendKeys("gasprom@yandex.ru");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(companyTitle, driver.getTitle());

        driver.findElement(By.id("companyId")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String vacanciesTitle = "Вакансии";
        assertEquals(vacanciesTitle, driver.getTitle());
        driver.findElement(By.id("addVacancyButton")).click();
        driver.findElement(By.id("position")).sendKeys("testposition");
        driver.findElement(By.id("salary")).sendKeys("111111");
        driver.findElement(By.id("education")).sendKeys("testEducation");
        driver.findElement(By.id("experience")).sendKeys("1");
        driver.findElement(By.id("other")).sendKeys("testOther");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        String vacancyTitle = "Информация о вакансии";
        assertEquals(vacancyTitle, driver.getTitle());

        driver.get("http://localhost:8080/companies");
        assertEquals(companiesTitle, driver.getTitle());
        driver.findElement(By.id("addCompanyButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(editCompanyTitle, driver.getTitle());


        driver.findElement(By.id("name")).sendKeys("Gasprom2");
        driver.findElement(By.id("specialization")).sendKeys("Oil and gas2");
        driver.findElement(By.id("phone_num")).sendKeys("88005553534");
        driver.findElement(By.id("email")).sendKeys("gasprom2@yandex.ru");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(companyTitle, driver.getTitle());

        driver.findElement(By.id("companyId")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(vacanciesTitle, driver.getTitle());
        driver.findElement(By.id("addVacancyButton")).click();
        driver.findElement(By.id("position")).sendKeys("testposition2");
        driver.findElement(By.id("salary")).sendKeys("211111");
        driver.findElement(By.id("education")).sendKeys("testEducation2");
        driver.findElement(By.id("experience")).sendKeys("2");
        driver.findElement(By.id("other")).sendKeys("testOther2");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(vacancyTitle, driver.getTitle());

        driver.get("http://localhost:8080/companies");
        assertEquals(companiesTitle, driver.getTitle());

        driver.findElement(By.id("companies")).click();
        assertEquals(companyTitle, driver.getTitle());

        driver.findElement(By.id("companyId")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(vacanciesTitle, driver.getTitle());
        driver.findElement(By.id("addVacancyButton")).click();
        driver.findElement(By.id("position")).sendKeys("testposition2");
        driver.findElement(By.id("salary")).sendKeys("151111");
        driver.findElement(By.id("education")).sendKeys("testEducation2");
        driver.findElement(By.id("experience")).sendKeys("1");
        driver.findElement(By.id("other")).sendKeys("testOther2");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(vacancyTitle, driver.getTitle());

        driver.get("http://localhost:8080");

        driver.findElement(By.id("positionVacancy")).sendKeys("testposition2");
        driver.findElement(By.id("salaryVacancy")).sendKeys("220000");
        driver.findElement(By.id("educationVacancy")).sendKeys("testEducation2");
        driver.findElement(By.id("experienceVacancy")).sendKeys("0");

        driver.findElement(By.id("findVacanciesButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(vacanciesTitle, driver.getTitle());
        assertEquals(2, driver.findElement(By.id("vacancies")).findElements(By.tagName("tr")).size());

        driver.get("http://localhost:8080/companies");
        driver.findElement(By.id("companiesall")).findElement(By.tagName("a")).click();
        assertEquals(companyTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();
        driver.findElement(By.id("companiesall")).findElement(By.tagName("a")).click();
        assertEquals(companyTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();

        driver.quit();
    }


    @Test
    void findPeople() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/people");
        assertEquals(peopleTitle, driver.getTitle());
        WebElement addPerson = driver.findElement(By.id("addPersonButton"));
        addPerson.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editPersonTitle = "Редактировать данные";
        assertEquals(editPersonTitle, driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Ivan Ivanovich");
        driver.findElement(By.id("phone_num")).sendKeys("88005553535");
        driver.findElement(By.id("email")).sendKeys("ivan@gmail.com");
        driver.findElement(By.id("education")).sendKeys("University");
        driver.findElement(By.id("experience")).sendKeys("6");
        driver.findElement(By.id("statust")).click();
        driver.findElement(By.id("wanted_position")).sendKeys("manager");
        driver.findElement(By.id("wanted_salary")).sendKeys("100000");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(personTitle, driver.getTitle());
        driver.get("http://localhost:8080/people");
        assertEquals(peopleTitle, driver.getTitle());
        driver.findElement(By.id("addPersonButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(editPersonTitle, driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Ivan Ivanovich2");
        driver.findElement(By.id("phone_num")).sendKeys("88005553532");
        driver.findElement(By.id("email")).sendKeys("ivan2@gmail.com");
        driver.findElement(By.id("education")).sendKeys("High School");
        driver.findElement(By.id("experience")).sendKeys("3");
        driver.findElement(By.id("statust")).click();
        driver.findElement(By.id("wanted_position")).sendKeys("manager2");
        driver.findElement(By.id("wanted_salary")).sendKeys("50000");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(personTitle, driver.getTitle());

        driver.get("http://localhost:8080/people");
        assertEquals(peopleTitle, driver.getTitle());
        driver.findElement(By.id("addPersonButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(editPersonTitle, driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Ivan Ivanovich3");
        driver.findElement(By.id("phone_num")).sendKeys("88005553536");
        driver.findElement(By.id("email")).sendKeys("ivan3@gmail.com");
        driver.findElement(By.id("education")).sendKeys("High School");
        driver.findElement(By.id("experience")).sendKeys("2");
        driver.findElement(By.id("statust")).click();
        driver.findElement(By.id("wanted_position")).sendKeys("manager2");
        driver.findElement(By.id("wanted_salary")).sendKeys("60000");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(personTitle, driver.getTitle());

        driver.get("http://localhost:8080");

        driver.findElement(By.id("wanted_position")).sendKeys("manager2");
        driver.findElement(By.id("wanted_salary")).sendKeys("75000");
        driver.findElement(By.id("education")).sendKeys("High School");
        driver.findElement(By.id("experience")).sendKeys("2");

        driver.findElement(By.id("findPeopleButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(peopleTitle, driver.getTitle());


        assertEquals(2, driver.findElement(By.id("people")).findElements(By.tagName("tr")).size());

        driver.findElement(By.id("people")).findElement(By.tagName("a")).click();
        assertEquals(personTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();
        driver.findElement(By.id("people")).findElement(By.tagName("a")).click();
        assertEquals(personTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();
        driver.findElement(By.id("people")).findElement(By.tagName("a")).click();
        assertEquals(personTitle, driver.getTitle());
        driver.findElement(By.id("deleteButton")).click();

        driver.quit();
    }

}


