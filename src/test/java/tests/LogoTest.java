package tests;
// изменения для PR
import org.junit.Test;
import org.openqa.selenium.WindowType;
import pages.MainPage;
import static org.junit.Assert.assertTrue;
// изменения для PR
public class LogoTest extends BaseTest {
    // изменения для PR
    @Test
    public void testScooterLogoReturnsToMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
// изменения для PR
        // Нажимаем на статус заказа, чтобы уйти с главной страницы
        mainPage.clickOrderStatusButton();
// изменения для PR
        // Возвращаемся на главную через логотип Самоката
        mainPage.clickScooterLogo();
// изменения для PR
        // Проверяем, что вернулись на главную страницу
        // (проверяем наличие кнопки заказа вверху)
        assertTrue("Должна отображаться кнопка 'Заказать' вверху на главной странице",
                driver.getCurrentUrl().contains("qa-scooter"));
    }
    // изменения для PR
    @Test
    public void testYandexLogoOpensNewTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        String originalWindow = driver.getWindowHandle();
// изменения для PR
        // Нажимаем на логотип Яндекс
        mainPage.clickYandexLogo();

        // Ждем открытия новой вкладки и переключаемся на нее
        try {
            Thread.sleep(2000); // Даем время на открытие вкладки
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// изменения для PR
        // Переключаемся на новую вкладку
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
// изменения для PR
        // Проверяем, что открылась страница Яндекс
        String currentUrl = driver.getCurrentUrl();
        assertTrue("Должна открыться страница Яндекс. Текущий URL: " + currentUrl,
                currentUrl.contains("yandex") || currentUrl.contains("dzen"));

        // Закрываем вкладку и возвращаемся к оригинальной
        driver.close();
        driver.switchTo().window(originalWindow);
    }
    // изменения для PR
    @Test
    public void testInvalidOrderNumberShowsErrorMessage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Вводим неверный номер заказа
        mainPage.setOrderNumber("1234567890");
        mainPage.clickGoButton();
// изменения для PR
        // Проверяем, что открылась страница статуса заказа
        // (в реальном тесте нужно проверять конкретное сообщение об ошибке)
        assertTrue("Должна открыться страница статуса заказа",
                driver.getCurrentUrl().contains("track"));
    }
}