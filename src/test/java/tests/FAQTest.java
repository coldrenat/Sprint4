package tests;
// изменения для PR
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import static org.junit.Assert.assertTrue;
// изменения для PR
@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {
    // изменения для PR
    private final int questionIndex;
    private final String expectedAnswerContains;
    // изменения для PR
    public FAQTest(int questionIndex, String expectedAnswerContains) {
        this.questionIndex = questionIndex;
        this.expectedAnswerContains = expectedAnswerContains;
    }
    // изменения для PR
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей"},
                {1, "один заказ — один самокат"},
                {2, "Допустим, вы оформляете заказ на 8 мая"},
                {3, "Только начиная с завтрашнего дня"},
                {4, "Пока что нет"},
                {5, "Самокат приезжает к вам с полной зарядкой"},
                {6, "Да, пока самокат не привезли"},
                {7, "Да, обязательно"}
        };
    }
    // изменения для PR
    @Test
    public void testFaqQuestions() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        // изменения для PR
        // Прокручиваем до FAQ
        mainPage.clickFaqQuestion(questionIndex);
// изменения для PR
        // Проверяем, что ответ отображается и содержит ожидаемый текст
        assertTrue("Ответ на вопрос " + questionIndex + " должен отображаться",
                mainPage.isFaqAnswerDisplayed(questionIndex));
// изменения для PR
        String answerText = mainPage.getFaqAnswerText(questionIndex);
        assertTrue("Ответ должен содержать текст: " + expectedAnswerContains +
                        ", но содержит: " + answerText,
                answerText.contains(expectedAnswerContains));
    }
}