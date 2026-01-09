package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Первая часть формы

    @FindBy(xpath = "//input[@placeholder='* Имя']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@placeholder='* Фамилия']")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[@placeholder='* Адрес: куда привезти заказ']")
    private WebElement addressField;

    @FindBy(xpath = "//input[@placeholder='* Станция метро']")
    private WebElement metroStationField;

    @FindBy(xpath = "//input[@placeholder='* Телефон: на него позвонит курьер']")
    private WebElement phoneField;

    @FindBy(xpath = "//button[text()='Далее']")
    private WebElement nextButton;

    // Вторая часть формы

    @FindBy(xpath = "//input[@placeholder='* Когда привезти самокат']")
    private WebElement deliveryDateField;

    @FindBy(xpath = "//div[@class='Dropdown-placeholder']")
    private WebElement rentalPeriodField;

    @FindBy(xpath = "//label[@for='black']")
    private WebElement blackColorCheckbox;

    @FindBy(xpath = "//label[@for='grey']")
    private WebElement greyColorCheckbox;

    @FindBy(xpath = "//input[@placeholder='Комментарий для курьера']")
    private WebElement commentField;

    @FindBy(xpath = "//button[text()='Заказать' and @class='Button_Button__ra12g Button_Middle__1CSJM']")
    private WebElement orderButton;

    // Модальное окно подтверждения

    @FindBy(xpath = "//button[text()='Да']")
    private WebElement confirmYesButton;

    @FindBy(xpath = "//div[contains(@class, 'Order_ModalHeader__')]")
    private WebElement successModal;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Увеличили время ожидания
        PageFactory.initElements(driver, this);
    }

    // Методы для первой части формы

    public void setName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameField));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void setLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void setAddress(String address) {
        addressField.clear();
        addressField.sendKeys(address);
    }

    public void setMetroStation(String station) {
        // Ждем пока поле станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(metroStationField));

        // Кликаем на поле
        metroStationField.click();

        // Вводим название станции посимвольно (иногда помогает)
        metroStationField.sendKeys(station);

        // Ждем появления выпадающего списка
        // Используем более надежный локатор для выпадающего списка
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'select-search__select') or contains(@class, 'select-search__options')]")));

        // Теперь выбираем станцию из выпадающего списка
        // В большинстве реализаций это кнопка или div с классом содержащим 'option'
        // Попробуем кликнуть на элемент списка станций метро

        // Вариант 1: Клик по первому элементу в списке (обычно это искомая станция)
        try {
            WebElement stationElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class, 'select-search__option') or contains(@class, 'Dropdown-option')])[1]")
            ));
            stationElement.click();
        } catch (Exception e) {
            // Вариант 2: Используем стрелку вниз и Enter
            metroStationField.sendKeys(Keys.ARROW_DOWN);
            metroStationField.sendKeys(Keys.ENTER);
        }

        // Убедимся, что поле заполнилось
        wait.until(driver -> {
            String value = metroStationField.getAttribute("value");
            return value != null && !value.isEmpty();
        });
    }

    public void setPhone(String phone) {
        wait.until(ExpectedConditions.visibilityOf(phoneField));
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    public void clickNextButton() {
        // Проверяем, что кнопка активна (все поля заполнены)
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();

        // Ждем загрузки второй части формы
        wait.until(ExpectedConditions.visibilityOf(deliveryDateField));
    }

    // Методы для второй части формы

    public void setDeliveryDate(String date) {
        // Очищаем поле и вводим дату
        deliveryDateField.clear();
        deliveryDateField.sendKeys(date);
        deliveryDateField.sendKeys(Keys.ENTER);

        // Кликаем вне поля, чтобы закрыть календарь
        commentField.click();
    }

    public void setRentalPeriod(String period) {
        // Кликаем на поле выбора срока
        rentalPeriodField.click();

        // Ждем появления списка вариантов
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='Dropdown-menu']")));

        // Ищем нужный вариант по точному тексту
        By periodOption = By.xpath("//div[@class='Dropdown-option' and text()='" + period + "']");
        wait.until(ExpectedConditions.elementToBeClickable(periodOption));
        driver.findElement(periodOption).click();
    }

    public void selectColor(String color) {
        if ("black".equalsIgnoreCase(color)) {
            wait.until(ExpectedConditions.elementToBeClickable(blackColorCheckbox));
            blackColorCheckbox.click();
        } else if ("grey".equalsIgnoreCase(color)) {
            wait.until(ExpectedConditions.elementToBeClickable(greyColorCheckbox));
            greyColorCheckbox.click();
        }
    }

    public void setComment(String comment) {
        commentField.clear();
        commentField.sendKeys(comment);
    }

    public void clickOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        orderButton.click();
    }

    // Методы для модального окна

    public void clickConfirmYesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        confirmYesButton.click();
    }

    public boolean isSuccessModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successModal));
            return successModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessModalText() {
        if (isSuccessModalDisplayed()) {
            return successModal.getText();
        }
        return "";
    }
}