package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import org.testng.annotations.BeforeSuite;
import utils.RequestOptions;
import utils.SettingsValue;
import utils.UserDataValue;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BaseTest {

    protected Logger log = AqualityServices.getLogger();
    protected static final ISettingsFile SETTINGS_FILE = new JsonSettingsFile("settings.json");
    protected static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    protected static final String URL = SETTINGS_FILE.getValue("/remoteConnectionUrl").toString();

    @BeforeMethod
    protected void beforeTest() {
        log.info(String.format("Go to the %s", URL));
        getBrowser().goTo(URL);
        getBrowser().maximize();
        getBrowser().waitForPageToLoad();
    }

    @BeforeSuite
    protected void beforeSuite() {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri(TEST_DATA.getValue(SettingsValue.BASE_REQUEST.getValue()).toString())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .addQueryParam(RequestOptions.ACCESS_TOKEN.getValue(), TEST_DATA.getValue(UserDataValue.TOKEN.getValue()))
                .addQueryParam(RequestOptions.V.getValue(), TEST_DATA.getValue(SettingsValue.VERSION_API.getValue()))
                .build();
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}