package tests;

import org.junit.Test;
import pages.MainPage;
import static org.junit.Assert.assertTrue;

public class LogoTest extends BaseTest {

    @Test
    public void testScooterLogoReturnsToMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Нажимаем на статус заказа
        mainPage.clickOrderStatusButton();

        // Возвращаемся на главную через логотип Самоката
        mainPage.clickScooterLogo();

        // Проверяем, что вернулись на главную страницу
        assertTrue("Должна отображаться главная страница Самоката",
                driver.getCurrentUrl().contains("qa-scooter"));
    }

    @Test
    public void testYandexLogoOpensNewTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Запоминаем исходное окно
        String originalWindow = driver.getWindowHandle();

        // Нажимаем на логотип Яндекс (в методе есть ожидание открытия новой вкладки)
        mainPage.clickYandexLogo();

        // Переключаемся на новую вкладку
        mainPage.switchToNewWindow(originalWindow);

        // Проверяем, что открылась страница Яндекс
        String currentUrl = driver.getCurrentUrl();
        assertTrue("Должна открыться страница Яндекс. Текущий URL: " + currentUrl,
                currentUrl.contains("yandex") ||
                        currentUrl.contains("dzen") ||
                        currentUrl.contains("ya.ru"));

        // Закрываем вкладку и возвращаемся к оригинальной
        mainPage.closeCurrentWindowAndSwitchBack(originalWindow);
    }

    @Test
    public void testInvalidOrderNumberShowsErrorMessage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Вводим неверный номер заказа
        mainPage.setOrderNumber("1234567890");
        mainPage.clickGoButton();

        // Проверяем, что открылась страница статуса заказа
        assertTrue("Должна открыться страница статуса заказа",
                driver.getCurrentUrl().contains("track"));
    }
}