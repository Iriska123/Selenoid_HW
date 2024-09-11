package org.example.tests;

import com.codeborne.selenide.WebDriverRunner;
import org.example.pom.LoginPage;
import org.example.pom.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.module.Configuration;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Пример использования самых базовых методов библиотеки Selenium.
 */
public class GeekBrainsStandTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private MainPage mainPage;

    private static String USERNAME;
    private static String PASSWORD;
    private static String SELENIOD_URL = "http://localhost:4444/wd/hub";

    @BeforeAll
    public static void setupClass() {
        // Помещаем в переменные окружения путь до драйвера
        //System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        // mvn clean test -Dgeekbrains_username=USER -Dgeekbrains_password=PASS
//        USERNAME = System.getProperty("geekbrains_username", System.getenv("geekbrains_username"));
//        PASSWORD = System.getProperty("geekbrains_password", System.getenv("geekbrains_password"));
         USERNAME = "GB202308987ff12";
         PASSWORD = "385c6c5703";


    }

    @BeforeEach
    public void setupTest() {
        ChromeOptions options = new ChromeOptions();
        try {
            driver = new RemoteWebDriver(new URL(SELENIOD_URL), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));

        // Создаём экземпляр драйвера
        //driver = new ChromeDriver();
        //wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Растягиваем окно браузера на весь экран
        driver.manage().window().maximize();
        // Навигация на https://test-stand.gb.ru/login
        driver.get("https://test-stand.gb.ru/login");
        // Объект созданного Page Object
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testLoginWithEmptyFields() {
        // Клик на кнопку LOGIN без ввода данных в поля
        loginPage.clickLoginButton();
        // Проверка, что появился блок с ожидаемой ошибкой
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());

    }
    @Test
    public void testGeekBrainsStandLogin() {
        loginPage.login(USERNAME, PASSWORD);
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
    }



    //@Test
//    void testArchiveGroupOnMainPage() {
//        // Обычный логин + создание группы
//        loginPage.login(USERNAME, PASSWORD);
//        mainPage = new MainPage(driver, wait);
//        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
//        String groupTestName = "New Test Group " + System.currentTimeMillis();
//        mainPage.createGroup(groupTestName);
//        // Требуется закрыть модальное окно
//        mainPage.closeCreateGroupModalWindow();
//        // Изменение созданной группы с проверками
//        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
//        mainPage.clickTrashIconOnGroupWithTitle(groupTestName);
//        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(groupTestName));
//        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(groupTestName);
//        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
//    }

    //@Test
//    void testBlockingStudentInTableOnMainPage() throws InterruptedException {
//        // Обычный логин + создание группы
//        loginPage.login(USERNAME, PASSWORD);
//        mainPage = new MainPage(driver, wait);
//        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
//        String groupTestName = "New Test Group " + System.currentTimeMillis();
//        mainPage.createGroup(groupTestName);
//        // Требуется закрыть модальное окно
//        mainPage.closeCreateGroupModalWindow();
//        // Добавление студентов
//        mainPage.clickAddStudentsIconOnGroupWithTitle(groupTestName);
//        mainPage.typeAmountOfStudentsInCreateStudentsForm(3);
//        mainPage.clickSaveButtonOnCreateStudentsForm();
//        mainPage.closeCreateStudentsModalWindow();
//        mainPage.clickZoomInIconOnGroupWithTitle(groupTestName);
//        // Проверка переходов статуса первого студента из таблицы
//        String firstGeneratedStudentName = mainPage.getFirstGeneratedStudentName();
//        assertEquals("active", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
//        mainPage.clickTrashIconOnStudentWithName(firstGeneratedStudentName);
//        assertEquals("block", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
//        mainPage.clickRestoreFromTrashIconOnStudentWithName(firstGeneratedStudentName);
//        assertEquals("active", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
//    }

    @AfterEach
    public void teardown() {
        // Закрываем все окна брайзера и процесс драйвера
        driver.quit();

    }

}

