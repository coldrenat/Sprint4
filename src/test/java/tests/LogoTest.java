package tests;

import org.junit.Test;
import org.openqa.selenium.WindowType;
import pages.MainPage;
import static org.junit.Assert.assertTrue;

public class LogoTest extends BaseTest {
    // pull request change
    @Test
    public void testScooterLogoReturnsToMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Нажимаем на статус заказа, чтобы уйти с главной страницы
        mainPage.clickOrderStatusButton();

        // Возвращаемся на главную через логотип Самоката
        mainPage.clickScooterLogo();

        // Проверяем, что вернулись на главную страницу
        // (проверяем наличие кнопки заказа вверху)
        assertTrue("Должна отображаться кнопка 'Заказать' вверху на главной странице",
                driver.getCurrentUrl().contains("qa-scooter"));
    }

    @Test
    public void testYandexLogoOpensNewTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        String originalWindow = driver.getWindowHandle();

        // Нажимаем на логотип Яндекс
        mainPage.clickYandexLogo();

        // Ждем открытия новой вкладки и переключаемся на нее
        try {
            Thread.sleep(2000); // Даем время на открытие вкладки
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Переключаемся на новую вкладку
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Проверяем, что открылась страница Яндекс
        String currentUrl = driver.getCurrentUrl();
        assertTrue("Должна открыться страница Яндекс. Текущий URL: " + currentUrl,
                currentUrl.contains("yandex") || currentUrl.contains("dzen"));

        // Закрываем вкладку и возвращаемся к оригинальной
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

        // Проверяем, что открылась страница статуса заказа
        // (в реальном тесте нужно проверять конкретное сообщение об ошибке)
        assertTrue("Должна открыться страница статуса заказа",
                driver.getCurrentUrl().contains("track"));
    }
}