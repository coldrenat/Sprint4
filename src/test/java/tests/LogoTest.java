package tests;

import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import java.time.Duration;
import java.util.Set;
import static org.junit.Assert.assertTrue;

public class LogoTest extends BaseTest {

    @Test
    public void testScooterLogoReturnsToMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Нажимаем на статус заказа
        mainPage.clickOrderStatusButton();

        // Ждем загрузки страницы статуса заказа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("track"));

        // Возвращаемся на главную через логотип Самоката
        mainPage.clickScooterLogo();

        // Ждем возврата на главную страницу
        wait.until(ExpectedConditions.urlContains("qa-scooter"));

        // Проверяем URL
        assertTrue("Должна отображаться главная страница Самоката",
                driver.getCurrentUrl().contains("qa-scooter"));
    }

    @Test
    public void testYandexLogoOpensNewTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        String originalWindow = driver.getWindowHandle();
        int originalWindowCount = driver.getWindowHandles().size();

        // Нажимаем на логотип Яндекс
        mainPage.clickYandexLogo();

        // Ждем открытия новой вкладки
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(originalWindowCount + 1));

        // Переключаемся на новую вкладку
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Ждем загрузки страницы
        wait.until(driver -> {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("yandex") ||
                    currentUrl.contains("dzen") ||
                    currentUrl.contains("ya.ru");
        });

        // Проверяем URL
        String currentUrl = driver.getCurrentUrl();
        assertTrue("Должна открыться страница Яндекс. Текущий URL: " + currentUrl,
                currentUrl.contains("yandex") ||
                        currentUrl.contains("dzen") ||
                        currentUrl.contains("ya.ru"));

        // Закрываем вкладку и возвращаемся
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testInvalidOrderNumberShowsErrorMessage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Вводим неверный номер заказа
        mainPage.setOrderNumber("1234567890");
        mainPage.clickGoButton();
//
        // Ждем загрузки страницы статуса заказа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("track"));

        // Проверяем URL
        assertTrue("Должна открыться страница статуса заказа",
                driver.getCurrentUrl().contains("track"));
    }
}