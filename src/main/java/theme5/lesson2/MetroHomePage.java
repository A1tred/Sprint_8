package theme5.lesson2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MetroHomePage {

    private final WebDriver driver;

    // локатор кнопки выпадающего списка городов по имени класса
    private final By selectCityButton = By.className("select_metro__button");

    // локатор поля ввода «Откуда» по XPATH, поиск по плейсхолдеру
    private final By fieldFrom = By.xpath(".//input[@placeholder='Откуда']");

    // локатор поля ввода «Куда» по XPATH, поиск по плейсхолдеру
    private final By fieldTo = By.xpath(".//input[@placeholder='Куда']");

    // локатор коллекций станций «Откуда» и «Куда» маршрута по имени класса
    private final By routeStationFromTo = By.className("route-details-block__terminal-station");

    public MetroHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // метод ожидания загрузки страницы: проверили видимость станции «Театральная»
    public void waitForLoadHomePage() {
        // ждем 8 секунд, пока появится веб-элемент с нужным текстом
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text() = 'Театральная']")));
    }

    // метод выбора города по названию
    public void chooseCity(String cityName) {
        // клик по выпадающему списку городов
        driver.findElement(selectCityButton).click();
        // выбор города
        driver.findElement(By.xpath(String.format("//*[text()='%s']", cityName))).click();
    }

    // метод ввода названия станции в поле «Откуда»
    public void setStationFrom(String stationFrom) {
        // ввели название станции в поле ввода, а затем с помощью клавиш «Вниз» и Enter выбери его в выпадающем списке саджеста
        driver.findElement(fieldFrom).sendKeys(stationFrom, Keys.DOWN, Keys.ENTER);
    }

    // метод ввода названия станции в поле «Куда»
    public void setStationTo(String stationTo) {
        // вводим название станции в поле ввода, а затем с помощью клавиш «Вниз» и Enter выбираем его в выпадающем списке саджеста
        driver.findElement(fieldTo).sendKeys(stationTo, Keys.DOWN, Keys.ENTER);
    }

    // метод ожидания построения маршрута: проверяем видимость кнопки «Получить ссылку на маршрут»
    public void waitForLoadRoute() {
        // ждем видимости элемента с нужным текстом в течение 3 секунд
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text() = 'Получить ссылку на маршрут']")));
    }

    // метод построения маршрута
    public void buildRoute(String stationFrom, String stationTo) {
        // указываем станцию «Откуда»
        setStationFrom(stationFrom);
        // указываем станцию «Куда»
        setStationTo(stationTo);
        // ждём построения маршрута
        waitForLoadRoute();
    }

    // метод получения имени станции «Откуда» для построенного маршрута
    public String getRouteStationFrom() {
        // вернули текст первого элемента коллекции
        return driver.findElements(routeStationFromTo).get(0).getText();
    }

    // метод получения имени станции «Куда» построенного маршрута
    public String getRouteStationTo() {
        // вернули текст второго элемента коллекции
        return driver.findElements(routeStationFromTo).get(1).getText();
    }

    // метод получения примерного времени маршрута
    public String getApproximateRouteTime(int routeNumber) {
        // вернули текст в требуемом элементе из коллекции времен всех маршрутов
        return driver.findElements(By.className("route-list-item__time")).get(routeNumber).getText();
    }

    // метод проверки с ожиданием видимости станции метро
    public void waitForStationVisibility(String stationName) {
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//*[text()='%s']", stationName))));
    }
}
