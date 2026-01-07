package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private final String orderButtonType; // "top" или "bottom"
    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    // pull request change
    public OrderTest(String orderButtonType, String name, String lastName, String address,
                     String metroStation, String phone, String deliveryDate,
                     String rentalPeriod, String color, String comment) {
        this.orderButtonType = orderButtonType;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Заказ через кнопку {0}, клиент: {1} {2}")
    public static Object[][] getTestData() {
        return new Object[][] {
                // Первый набор данных
                {
                        "top",          // кнопка вверху
                        "Иван",         // имя
                        "Иванов",       // фамилия
                        "ул. Ленина, д. 1", // адрес
                        "Сокольники",   // станция метро
                        "+79991234567", // телефон
                        "01.12.2024",   // дата доставки
                        "сутки",        // срок аренды
                        "black",        // цвет
                        "Позвонить за час" // комментарий
                },
                // Второй набор данных
                {
                        "bottom",       // кнопка внизу
                        "Мария",        // имя
                        "Петрова",      // фамилия
                        "пр. Мира, д. 10", // адрес
                        "Черкизовская", // станция метро
                        "+79998765432", // телефон
                        "05.12.2024",   // дата доставки
                        "двое суток",   // срок аренды
                        "grey",         // цвет
                        "Оставить у двери" // комментарий
                }
        };
    }

    @Test
    public void testOrderScooter() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Нажимаем на кнопку "Заказать" в зависимости от параметра
        if ("top".equals(orderButtonType)) {
            mainPage.clickOrderButtonTop();
        } else {
            // Прокручиваем до нижней кнопки
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);

        // Заполняем первую часть формы
        orderPage.fillFirstPartOfForm(name, lastName, address, metroStation, phone);

        // Заполняем вторую часть формы
        orderPage.fillSecondPartOfForm(deliveryDate, rentalPeriod, color, comment);

        // Подтверждаем заказ
        orderPage.clickConfirmYesButton();

        // Проверяем, что появилось модальное окно об успешном заказе
        assertTrue("Должно отображаться модальное окно об успешном заказе",
                orderPage.isSuccessModalDisplayed());
    }
}