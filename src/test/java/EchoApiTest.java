import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.LogParser;
import org.example.service.TelegramBot;
import org.example.service.TestFailureHandler;
import org.example.service.TestReporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Postman Echo API")
@Feature("Request Methods")
public class EchoApiTest {
    private static final Logger LOGGER = LogManager.getLogger(EchoApiTest.class);
    private static final String BASE_URI = "https://postman-echo.com";
    private static final String COMMON_BODY = "This is expected to be sent back as part of response body.";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
    private static final int STATUS_OK = 200;
    private static final String PORT = "443";
    private TelegramBot bot;
    private final TestFailureHandler failureHandler = new TestFailureHandler(bot);
    private long startTime;
    private long endTime;

    @BeforeMethod
    public void initStartTime() {
        bot = new TelegramBot();
        Allure.step("Запуск теста и инициализация таймера");
        startTime = System.currentTimeMillis();
    }

    @Step("Лог ответа: {response}")
    private void logResponse(Response response) {
        String formatted = response.prettyPrint();
        LOGGER.info("Ответ от API:\n{}", formatted);
    }

    private void sendSuccessLogToTelegram() {
        endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String logs = LogParser.readLastLogLines(LogParser.getLatestLogFile("logs/"), 20);
        String message = TestReporter.generateSuccessMessage(startTime, endTime, duration, logs);
        bot.sendMessageToTelegram(message);
    }

    @Test
    @Story("GET method validation")
    @Description("Проверка, что GET-запрос возвращает правильные заголовки и URL")
    public void testGetRequest() {
        try {
            Allure.step("Отправка GET-запроса");
            Response response = given()
                    .baseUri(BASE_URI)
                    .log().all()
                    .when()
                    .get("/get")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(STATUS_OK)
                    .body("headers.host", equalTo("postman-echo.com"))
                    .body("headers.x-forwarded-proto", equalTo("https"))
                    .body("headers.x-forwarded-port", equalTo(PORT))
                    .body("url", equalTo(BASE_URI + "/get"))
                    .extract().response();
            logResponse(response);
            sendSuccessLogToTelegram();
        } catch (AssertionError | Exception e) {
            failureHandler.handleTestFailure("GET-запрос завершился ошибкой", e);
            throw e;
        }
    }

    @Test
    @Story("POST method validation")
    @Description("Проверка, что POST-запрос возвращает тело запроса в ответе")
    public void testPostRequest() {
        try {
            Allure.step("Отправка POST-запроса с телом");
            Response response = given()
                    .baseUri(BASE_URI)
                    .log().all()
                    .header(HEADER_CONTENT_TYPE, CONTENT_TYPE_TEXT_PLAIN)
                    .body(COMMON_BODY)
                    .when()
                    .post("/post")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(STATUS_OK)
                    .body("data", containsString(COMMON_BODY))
                    .body("headers.host", containsString("postman-echo.com"))
                    .body("headers.content-type", containsString(CONTENT_TYPE_TEXT_PLAIN))
                    .body("url", containsString(BASE_URI + "/post"))
                    .extract().response();
            logResponse(response);
            sendSuccessLogToTelegram();
        } catch (AssertionError | Exception e) {
            failureHandler.handleTestFailure("POST-запрос завершился ошибкой", e);
            throw e;
        }
    }

    @Test
    @Story("PATCH method validation")
    @Description("Проверка, что PATCH-запрос возвращает тело запроса в ответе")
    public void testPatchRequest() {
        try {
            Allure.step("Отправка PATCH-запроса с телом");
            Response response = given()
                    .baseUri(BASE_URI)
                    .log().all()
                    .header(HEADER_CONTENT_TYPE, CONTENT_TYPE_TEXT_PLAIN)
                    .body(COMMON_BODY)
                    .when()
                    .patch("/patch")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(STATUS_OK)
                    .body("data", containsString(COMMON_BODY))
                    .body("headers.host", containsString("postman-echo.com"))
                    .body("headers.content-type", containsString(CONTENT_TYPE_TEXT_PLAIN))
                    .body("url", containsString(BASE_URI + "/patch"))
                    .extract().response();
            logResponse(response);
            sendSuccessLogToTelegram();
        } catch (AssertionError | Exception e) {
            failureHandler.handleTestFailure("PATCH-запрос завершился ошибкой", e);
            throw e;
        }
    }

    @Test
    @Story("PUT method validation")
    @Description("Проверка, что PUT-запрос возвращает тело запроса и заголовки")
    public void testPutRequest() {
        try {
            Allure.step("Отправка PUT-запроса с телом");
            Response response = given()
                    .baseUri(BASE_URI)
                    .log().all()
                    .header(HEADER_CONTENT_TYPE, CONTENT_TYPE_TEXT_PLAIN)
                    .body(COMMON_BODY)
                    .when()
                    .put("/put")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(STATUS_OK)
                    .body("data", equalTo(COMMON_BODY))
                    .body("headers.host", equalTo("postman-echo.com"))
                    .body("headers.x-forwarded-proto", equalTo("https"))
                    .body("headers.x-forwarded-port", equalTo(PORT))
                    .body("headers.'content-type'", containsString(CONTENT_TYPE_TEXT_PLAIN))
                    .body("json", nullValue())
                    .body("url", equalTo(BASE_URI + "/put"))
                    .extract().response();
            logResponse(response);
            sendSuccessLogToTelegram();
        } catch (AssertionError | Exception e) {
            failureHandler.handleTestFailure("PUT-запрос завершился ошибкой", e);
            throw e;
        }
    }

    @Test
    @Story("DELETE method validation")
    @Description("Проверка, что DELETE-запрос возвращает тело запроса и заголовки")
    public void testDeleteRequest() {
        try {
            Allure.step("Отправка DELETE-запроса с телом");
            Response response = given()
                    .baseUri(BASE_URI)
                    .log().all()
                    .header(HEADER_CONTENT_TYPE, CONTENT_TYPE_TEXT_PLAIN)
                    .body(COMMON_BODY)
                    .when()
                    .delete("/delete")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(STATUS_OK)
                    .body("data", containsString(COMMON_BODY))
                    .body("headers.host", containsString("postman-echo.com"))
                    .body("headers.content-type", containsString(CONTENT_TYPE_TEXT_PLAIN))
                    .body("url", containsString(BASE_URI + "/delete"))
                    .extract().response();
            logResponse(response);
            sendSuccessLogToTelegram();
        } catch (AssertionError | Exception e) {
            failureHandler.handleTestFailure("DELETE-запрос завершился ошибкой", e);
            throw e;
        }
    }
}
