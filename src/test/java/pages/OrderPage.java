package pages;
// изменения для PR
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
// изменения для PR
public class OrderPage {

    private final WebDriver driver;

    // Локаторы для формы заказа

    // Поле "Имя"
    @FindBy(xpath = "//input[@placeholder='* Имя']")
    private WebElement nameField;
    // изменения для PR
    // Поле "Фамилия"
    @FindBy(xpath = "//input[@placeholder='* Фамилия']")
    private WebElement lastNameField;
    // изменения для PR
    // Поле "Адрес"
    @FindBy(xpath = "//input[@placeholder='* Адрес: куда привезти заказ']")
    private WebElement addressField;
    // изменения для PR
    // Поле "Станция метро"
    @FindBy(xpath = "//input[@placeholder='* Станция метро']")
    private WebElement metroStationField;
    // изменения для PR
    // Список станций метро
    @FindBy(xpath = "//div[@class='Order_Text__2broi']")
    private WebElement metroStationOption;
    // изменения для PR
    // Поле "Телефон"
    @FindBy(xpath = "//input[@placeholder='* Телефон: на него позвонит курьер']")
    private WebElement phoneField;
    // изменения для PR
    // Кнопка "Далее"
    @FindBy(xpath = "//button[text()='Далее']")
    private WebElement nextButton;
    // изменения для PR
    // Поле "Когда привезти самокат"
    @FindBy(xpath = "//input[@placeholder='* Когда привезти самокат']")
    private WebElement deliveryDateField;
    // изменения для PR
    // Календарь (выбор даты)
    @FindBy(xpath = "//div[contains(@class, 'react-datepicker__day--selected')]")
    private WebElement datePickerSelected;
    // изменения для PR
    // Срок аренды
    @FindBy(xpath = "//div[@class='Dropdown-placeholder']")
    private WebElement rentalPeriodField;
    // изменения для PR
    // Опции срока аренды
    @FindBy(xpath = "//div[@class='Dropdown-option' and text()='сутки']")
    private WebElement rentalPeriodOption;
    // изменения для PR
    // Чекбокс "Черный жемчуг"
    @FindBy(xpath = "//input[@id='black']")
    private WebElement blackPearlCheckbox;
    // изменения для PR
    // Чекбокс "Серая безысходность"
    @FindBy(xpath = "//input[@id='grey']")
    private WebElement greyHopelessnessCheckbox;
    // изменения для PR
    // Поле "Комментарий для курьера"
    @FindBy(xpath = "//input[@placeholder='Комментарий для курьера']")
    private WebElement commentField;
    // изменения для PR
    // Кнопка "Заказать"
    @FindBy(xpath = "//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']")
    private WebElement orderButton;
    // изменения для PR
    // Кнопка "Да" в модальном окне подтверждения
    @FindBy(xpath = "//button[text()='Да']")
    private WebElement confirmYesButton;
    // изменения для PR
    // Модальное окно с сообщением об успешном заказе
    @FindBy(xpath = "//div[contains(@class, 'Order_ModalHeader__3FDaJ')]")
    private WebElement successModal;
    // изменения для PR
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Методы для заполнения формы
// изменения для PR
    public void setName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }
    // изменения для PR
    public void setLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }
    // изменения для PR
    public void setAddress(String address) {
        addressField.clear();
        addressField.sendKeys(address);
    }
    // изменения для PR
    public void setMetroStation(String station) {
        metroStationField.clear();
        metroStationField.sendKeys(station);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(metroStationOption));
        metroStationOption.click();
    }
    // изменения для PR
    public void setPhone(String phone) {
        phoneField.clear();
        phoneField.sendKeys(phone);
    }
    // изменения для PR
    public void clickNextButton() {
        nextButton.click();
    }

    public void setDeliveryDate(String date) {
        deliveryDateField.clear();
        deliveryDateField.sendKeys(date);
        // Закрываем календарь кликом по другому элементу
        nameField.click();
    }
    // изменения для PR
    public void setRentalPeriod(String period) {
        rentalPeriodField.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOption));
        rentalPeriodOption.click();
    }
    // изменения для PR
    public void selectColor(String color) {
        if ("black".equalsIgnoreCase(color)) {
            blackPearlCheckbox.click();
        } else if ("grey".equalsIgnoreCase(color)) {
            greyHopelessnessCheckbox.click();
        }
    }
    // изменения для PR
    public void setComment(String comment) {
        commentField.clear();
        commentField.sendKeys(comment);
    }
    // изменения для PR
    public void clickOrderButton() {
        orderButton.click();
    }
    // изменения для PR
    public void clickConfirmYesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        confirmYesButton.click();
    }
    // изменения для PR
    public boolean isSuccessModalDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(successModal));
            return successModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    // изменения для PR
    public String getSuccessModalText() {
        if (isSuccessModalDisplayed()) {
            return successModal.getText();
        }
        return "";
    }
    // изменения для PR
    // Комплексный метод для заполнения первой части формы
    public void fillFirstPartOfForm(String name, String lastName, String address, String metroStation, String phone) {
        setName(name);
        setLastName(lastName);
        setAddress(address);
        setMetroStation(metroStation);
        setPhone(phone);
        clickNextButton();
    }
    // изменения для PR
    // Комплексный метод для заполнения второй части формы
    public void fillSecondPartOfForm(String date, String period, String color, String comment) {
        setDeliveryDate(date);
        setRentalPeriod(period);
        selectColor(color);
        setComment(comment);
        clickOrderButton();
    }
}