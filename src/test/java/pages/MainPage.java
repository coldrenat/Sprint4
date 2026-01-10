package pages;

import org.openqa.selenium.By;
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
    private final WebDriverWait wait;

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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        orderButtonTop.click();
    }

    public void clickOrderButtonBottom() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        orderButtonBottom.click();
    }

    public void clickYandexLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(yandexLogo));
        yandexLogo.click();
    }

    public void clickScooterLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(scooterLogo));
        scooterLogo.click();
    }

    public void clickOrderStatusButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderStatusButton));
        orderStatusButton.click();
    }

    public void setOrderNumber(String orderNumber) {
        wait.until(ExpectedConditions.visibilityOf(orderNumberInput));
        orderNumberInput.clear();
        orderNumberInput.sendKeys(orderNumber);
    }

    public void clickGoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(goButton));
        goButton.click();
    }

    public void clickFaqQuestion(int index) {
        if (index >= 0 && index < faqQuestions.size()) {
            WebElement question = faqQuestions.get(index);
            wait.until(ExpectedConditions.elementToBeClickable(question));
            question.click();
        }
    }

    public String getFaqAnswerText(int index) {
        if (index >= 0 && index < faqAnswers.size()) {
            wait.until(ExpectedConditions.visibilityOf(faqAnswers.get(index)));
            return faqAnswers.get(index).getText();
        }
        return "";
    }

    public boolean isFaqAnswerDisplayed(int index) {
        if (index >= 0 && index < faqAnswers.size()) {
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
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieConfirmButton));
            cookieConfirmButton.click();
        } catch (Exception e) {
            // Если кнопка куки не появилась, продолжаем
        }
    }
    //
    public int getFaqQuestionsCount() {
        return faqQuestions.size();
    }
}