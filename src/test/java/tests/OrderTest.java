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
                        "Заказ через верхнюю кнопку",
                        "top",
                        "Иван",
                        "Иванов",
                        "ул. Ленина, д. 1",
                        "Сокольники",
                        "+79991234567",
                        1,
                        "сутки",
                        "black",
                        "Позвонить за час"
                },
                {
                        "Заказ через нижнюю кнопку",
                        "bottom",
                        "Мария",
                        "Петрова",
                        "пр. Мира, д. 10",
                        "Черкизовская",
                        "+79998765432",
                        3,
                        "двое суток",
                        "grey",
                        "Оставить у двери"
                }
        };
    }

    @Test
    public void testOrderScooter() {
        System.out.println("Начало теста: " + testCaseName);

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Выбираем кнопку для начала заказа
        if ("top".equals(orderButtonType)) {
            System.out.println("Кликаем на верхнюю кнопку заказа");
            mainPage.clickOrderButtonTop();
        } else {
            System.out.println("Кликаем на нижнюю кнопку заказа");
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);

        System.out.println("Заполняем имя: " + name);
        orderPage.setName(name);

        System.out.println("Заполняем фамилию: " + lastName);
        orderPage.setLastName(lastName);

        System.out.println("Заполняем адрес: " + address);
        orderPage.setAddress(address);

        System.out.println("Выбираем станцию метро: " + metroStation);
        orderPage.setMetroStation(metroStation);

        System.out.println("Заполняем телефон: " + phone);
        orderPage.setPhone(phone);

        System.out.println("Нажимаем кнопку 'Далее'");
        orderPage.clickNextButton();

        System.out.println("Устанавливаем дату доставки: " + deliveryDate);
        orderPage.setDeliveryDate(deliveryDate);

        System.out.println("Выбираем срок аренды: " + rentalPeriod);
        orderPage.setRentalPeriod(rentalPeriod);

        System.out.println("Выбираем цвет: " + color);
        orderPage.selectColor(color);

        System.out.println("Добавляем комментарий: " + comment);
        orderPage.setComment(comment);

        System.out.println("Нажимаем кнопку 'Заказать'");
        orderPage.clickOrderButton();

        System.out.println("Подтверждаем заказ");
        orderPage.clickConfirmYesButton();

        // Проверяем успешное оформление
        boolean isSuccess = orderPage.isSuccessModalDisplayed();
        System.out.println("Модальное окно об успехе отображено: " + isSuccess);

        assertTrue(testCaseName + ": должно отображаться модальное окно об успешном заказе",
                isSuccess);
//
        System.out.println("Тест завершен успешно: " + testCaseName);
    }
}