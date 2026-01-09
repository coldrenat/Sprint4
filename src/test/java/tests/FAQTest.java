package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {

    private final int questionIndex;
    private final String expectedAnswerContains;

    public FAQTest(int questionIndex, String expectedAnswerContains) {
        this.questionIndex = questionIndex;
        this.expectedAnswerContains = expectedAnswerContains;
    }

    @Parameterized.Parameters(name = "FAQ #{0}: проверка ответа содержит \"{1}\"")
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

    @Test
    public void testFaqQuestions() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Кликаем на вопрос
        mainPage.clickFaqQuestion(questionIndex);

        // Проверяем, что ответ отображается
        assertTrue("Ответ на вопрос #" + questionIndex + " должен отображаться",
                mainPage.isFaqAnswerDisplayed(questionIndex));

        // Проверяем, что ответ содержит ожидаемый текст
        String answerText = mainPage.getFaqAnswerText(questionIndex);
        assertTrue("Ответ должен содержать текст: '" + expectedAnswerContains +
                        "', но содержит: " + answerText,
                answerText.contains(expectedAnswerContains));
    }
}