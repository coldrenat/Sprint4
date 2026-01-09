package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private final String testCaseName;
    private final String orderButtonType;
    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public OrderTest(String testCaseName, String orderButtonType, String name, String lastName,
                     String address, String metroStation, String phone,
                     int daysToAdd, String rentalPeriod,
                     String color, String comment) {
        this.testCaseName = testCaseName;
        this.orderButtonType = orderButtonType;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = LocalDate.now().plusDays(daysToAdd).format(dateFormatter);
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {
                        "Заказ через верхнюю кнопку - Иван Иванов",
                        "top",
                        "Иван",
                        "Иванов",
                        "ул. Ленина, д. 1",
                        "Сокольники",
                        "+79991234567",
                        1,      // завтра
                        "сутки",
                        "black",
                        "Позвонить за час"
                },
                {
                        "Заказ через нижнюю кнопку - Мария Петрова",
                        "bottom",
                        "Мария",
                        "Петрова",
                        "пр. Мира, д. 10",
                        "Черкизовская",
                        "+79998765432",
                        3,      // через 3 дня
                        "двое суток",
                        "grey",
                        "Оставить у двери"
                }
        };
    }

    @Test
    public void testOrderScooter() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Выбираем кнопку для начала заказа
        if ("top".equals(orderButtonType)) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);

        // Заполняем первую часть формы
        orderPage.setName(name);
        orderPage.setLastName(lastName);
        orderPage.setAddress(address);
        orderPage.setMetroStation(metroStation);
        orderPage.setPhone(phone);
        orderPage.clickNextButton();

        // Заполняем вторую часть формы
        orderPage.setDeliveryDate(deliveryDate);
        orderPage.setRentalPeriod(rentalPeriod);
        orderPage.selectColor(color);
        orderPage.setComment(comment);
        orderPage.clickOrderButton();

        // Подтверждаем заказ
        orderPage.clickConfirmYesButton();

        // Проверяем успешное оформление
        assertTrue(testCaseName + ": должно отображаться модальное окно об успешном заказе",
                orderPage.isSuccessModalDisplayed());
    }
}