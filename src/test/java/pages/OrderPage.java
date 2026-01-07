package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    // pull request change
    private final WebDriver driver;

    // Локаторы для формы заказа

    // Поле "Имя"
    @FindBy(xpath = "//input[@placeholder='* Имя']")
    private WebElement nameField;

    // Поле "Фамилия"
    @FindBy(xpath = "//input[@placeholder='* Фамилия']")
    private WebElement lastNameField;

    // Поле "Адрес"
    @FindBy(xpath = "//input[@placeholder='* Адрес: куда привезти заказ']")
    private WebElement addressField;

    // Поле "Станция метро"
    @FindBy(xpath = "//input[@placeholder='* Станция метро']")
    private WebElement metroStationField;

    // Список станций метро
    @FindBy(xpath = "//div[@class='Order_Text__2broi']")
    private WebElement metroStationOption;

    // Поле "Телефон"
    @FindBy(xpath = "//input[@placeholder='* Телефон: на него позвонит курьер']")
    private WebElement phoneField;

    // Кнопка "Далее"
    @FindBy(xpath = "//button[text()='Далее']")
    private WebElement nextButton;

    // Поле "Когда привезти самокат"
    @FindBy(xpath = "//input[@placeholder='* Когда привезти самокат']")
    private WebElement deliveryDateField;

    // Календарь (выбор даты)
    @FindBy(xpath = "//div[contains(@class, 'react-datepicker__day--selected')]")
    private WebElement datePickerSelected;

    // Срок аренды
    @FindBy(xpath = "//div[@class='Dropdown-placeholder']")
    private WebElement rentalPeriodField;

    // Опции срока аренды
    @FindBy(xpath = "//div[@class='Dropdown-option' and text()='сутки']")
    private WebElement rentalPeriodOption;

    // Чекбокс "Черный жемчуг"
    @FindBy(xpath = "//input[@id='black']")
    private WebElement blackPearlCheckbox;

    // Чекбокс "Серая безысходность"
    @FindBy(xpath = "//input[@id='grey']")
    private WebElement greyHopelessnessCheckbox;

    // Поле "Комментарий для курьера"
    @FindBy(xpath = "//input[@placeholder='Комментарий для курьера']")
    private WebElement commentField;

    // Кнопка "Заказать"
    @FindBy(xpath = "//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']")
    private WebElement orderButton;

    // Кнопка "Да" в модальном окне подтверждения
    @FindBy(xpath = "//button[text()='Да']")
    private WebElement confirmYesButton;

    // Модальное окно с сообщением об успешном заказе
    @FindBy(xpath = "//div[contains(@class, 'Order_ModalHeader__3FDaJ')]")
    private WebElement successModal;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Методы для заполнения формы

    public void setName(String name) {
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
        metroStationField.clear();
        metroStationField.sendKeys(station);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(metroStationOption));
        metroStationOption.click();
    }

    public void setPhone(String phone) {
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void setDeliveryDate(String date) {
        deliveryDateField.clear();
        deliveryDateField.sendKeys(date);
        // Закрываем календарь кликом по другому элементу
        nameField.click();
    }

    public void setRentalPeriod(String period) {
        rentalPeriodField.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOption));
        rentalPeriodOption.click();
    }

    public void selectColor(String color) {
        if ("black".equalsIgnoreCase(color)) {
            blackPearlCheckbox.click();
        } else if ("grey".equalsIgnoreCase(color)) {
            greyHopelessnessCheckbox.click();
        }
    }

    public void setComment(String comment) {
        commentField.clear();
        commentField.sendKeys(comment);
    }

    public void clickOrderButton() {
        orderButton.click();
    }

    public void clickConfirmYesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        confirmYesButton.click();
    }

    public boolean isSuccessModalDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    // Комплексный метод для заполнения первой части формы
    public void fillFirstPartOfForm(String name, String lastName, String address, String metroStation, String phone) {
        setName(name);
        setLastName(lastName);
        setAddress(address);
        setMetroStation(metroStation);
        setPhone(phone);
        clickNextButton();
    }

    // Комплексный метод для заполнения второй части формы
    public void fillSecondPartOfForm(String date, String period, String color, String comment) {
        setDeliveryDate(date);
        setRentalPeriod(period);
        selectColor(color);
        setComment(comment);
        clickOrderButton();
    }
}