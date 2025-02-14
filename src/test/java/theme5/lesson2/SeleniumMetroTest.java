package theme5.lesson2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Assert;

public class SeleniumMetroTest {

    private WebDriver driver;
    private MetroHomePage metroPage;

    private static final String CITY_SAINTP = "Санкт-Петербург";
    private static final String STATION_SPORTIVNAYA = "Спортивная";
    private static final String STATION_LUBYANKA = "Лубянка";
    private static final String STATION_KRASNOGVARD = "Красногвардейская";


    @Before
    public void setUp() {
        // открыли браузер
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        // перешли на страницу тестового приложения
        driver.get("https://qa-metro.stand-2.praktikum-services.ru/");

        // создали объект класса страницы стенда
        metroPage = new MetroHomePage(driver);
        // дождались загрузки страницы
        metroPage.waitForLoadHomePage();

    }

    // проверили выбор города
    @Test
    public void checkChooseCityDropdown() {
        // выбрали Санкт-Петербург в списке городов
        metroPage.chooseCity(CITY_SAINTP);
        // проверили, что станция метро «Спортивная» видна
        metroPage.waitForStationVisibility(STATION_SPORTIVNAYA);
    }

    // проверяем отображение времени маршрута
    @Test
    public void checkRouteApproxTimeIsDisplayed() {
        // построили маршрут от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверили, что у первого маршрута списка отображается примерное время поездки
        Assert.assertEquals("≈ 36 мин.", metroPage.getApproximateRouteTime(0));
    }

    // проверь отображение станции «Откуда» в карточке маршрута
    @Test
    public void checkRouteStationFromIsCorrect() {
        // построй маршрут от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверь, что отображается корректное название станции начала маршрута
        Assert.assertEquals(STATION_LUBYANKA, metroPage.getRouteStationFrom());
    }

    // проверь отображение станции «Куда» в карточке маршрута
    @Test
    public void checkRouteStationToIsCorrect() {
        // построй маршрут от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверь, что отображается корректное название станции конца маршрута
        Assert.assertEquals(STATION_KRASNOGVARD, metroPage.getRouteStationTo());
    }

    @After
    public void tearDown() {
        // закрыли браузер
        driver.quit();
    }
}
