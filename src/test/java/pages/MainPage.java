package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
// pull request change
    // Локаторы для главной страницы

    // Кнопка "Заказать" вверху страницы
    @FindBy(xpath = "//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']")
    private WebElement orderButtonTop;

    // Кнопка "Заказать" внизу страницы
    @FindBy(xpath = "//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']")
    private WebElement orderButtonBottom;

    // Логотип Яндекс
    @FindBy(xpath = "//a[@class='Header_LogoYandex__3TSOI']/img")
    private WebElement yandexLogo;

    // Логотип Самоката
    @FindBy(xpath = "//a[@class='Header_LogoScooter__3lsAR']/img")
    private WebElement scooterLogo;

    // Кнопка "Статус заказа"
    @FindBy(xpath = "//button[text()='Статус заказа']")
    private WebElement orderStatusButton;

    // Поле ввода номера заказа
    @FindBy(xpath = "//input[@placeholder='Введите номер заказа']")
    private WebElement orderNumberInput;

    // Кнопка "Go!" для поиска заказа
    @FindBy(xpath = "//button[text()='Go!']")
    private WebElement goButton;

    // Вопросы о важном (FAQ) - список вопросов
    @FindBy(xpath = "//div[@data-accordion-component='AccordionItemButton']")
    private List<WebElement> faqQuestions;

    // Ответы на вопросы (скрытые элементы)
    @FindBy(xpath = "//div[@data-accordion-component='AccordionItemPanel']")
    private List<WebElement> faqAnswers;

    // Кнопка принятия куки
    @FindBy(id = "rcc-confirm-button")
    private WebElement cookieConfirmButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Методы для работы с элементами

    public void clickOrderButtonTop() {
        orderButtonTop.click();
    }

    public void clickOrderButtonBottom() {
        orderButtonBottom.click();
    }

    public void clickYandexLogo() {
        yandexLogo.click();
    }

    public void clickScooterLogo() {
        scooterLogo.click();
    }

    public void clickOrderStatusButton() {
        orderStatusButton.click();
    }

    public void setOrderNumber(String orderNumber) {
        orderNumberInput.clear();
        orderNumberInput.sendKeys(orderNumber);
    }

    public void clickGoButton() {
        goButton.click();
    }

    // Методы для работы с FAQ
    public void clickFaqQuestion(int index) {
        if (index >= 0 && index < faqQuestions.size()) {
            faqQuestions.get(index).click();
        }
    }

    public String getFaqAnswerText(int index) {
        if (index >= 0 && index < faqAnswers.size()) {
            return faqAnswers.get(index).getText();
        }
        return "";
    }

    public boolean isFaqAnswerDisplayed(int index) {
        if (index >= 0 && index < faqAnswers.size()) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                wait.until(ExpectedConditions.visibilityOf(faqAnswers.get(index)));
                return faqAnswers.get(index).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public void acceptCookies() {
        if (cookieConfirmButton.isDisplayed()) {
            cookieConfirmButton.click();
        }
    }

    public int getFaqQuestionsCount() {
        return faqQuestions.size();
    }
}