package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import pages.HomePage;
import pages.TodoPage;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class firstTests {
    private WebDriver driver;
    private HomePage homePage;
    private TodoPage todoPage;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.edgedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new EdgeDriver();
        homePage = new HomePage(driver);
        todoPage = new TodoPage(driver);
    }

    private static Stream<Arguments> provideTodoParameters() {
        return Stream.of(
                arguments("Backbone.js", List.of("Buy sugar", "Buy water"), List.of("Buy sugar"), 1),
                arguments("Backbone.js New", List.of("Buy sugar", "Buy water"), List.of("Buy sugar"), 2),
                arguments("React New", List.of("Buy sugar", "Buy water"), List.of("Buy sugar"), 2),
                arguments("React New", List.of("Buy milk", "Buy bread"), List.of("Buy bread", "Buy milk"), 0)
        );
    }

    @ParameterizedTest(name = "Test {index} - {0}")
    @MethodSource("provideTodoParameters")
    @Link(name = "Website", url = "https://todomvc.com/")
    @Owner("User")
    @Description("Questo test prova a creare degli item in una ToDo list, spuntarne alcuni e assicurarsi che siano stati creati con successo")
    @Epic("TodoMVC")
    @Feature("ToDo List")
    @Story("Utente crea e spunta task in una lista")
    @Severity(SeverityLevel.CRITICAL)
    public void toDoListTest(
            String technology,
            List<String> todoItems,
            List<String> todoItemsToCheck,
            Integer expectedLeftItems
    ) throws InterruptedException {
        // Navigate to page
        driver.get("https://todomvc.com/");

        Allure.parameter("technology", technology);
        Allure.parameter("todoItems", todoItems);
        Allure.parameter("todoItemsToCheck", todoItemsToCheck);
        Allure.parameter("expectedLeftItems", expectedLeftItems);

        Allure.step("click on " + technology);

        // Click on the link
        homePage.clickLink(technology);

        // Add todo items
        Allure.step("Aggiungo delle task alla lista", step -> {
        for (String todoItem : todoItems) {
            Allure.step("Aggiungo l'item " + todoItem);
            todoPage.addNewItem(todoItem);
        }
        });

        // Check todo items
        Allure.step("Spunto delle task nella lista", step -> {

            for (String todoItemToCheck : todoItemsToCheck) {
                Allure.step("Spunto l'item " + todoItemToCheck);

                todoPage.clickCheckbox(todoItemToCheck);
        }
        });

        Allure.step("Verificare il numero di item rimasti");


        // Check items left
        todoPage.assertItemsLeft(expectedLeftItems);
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
