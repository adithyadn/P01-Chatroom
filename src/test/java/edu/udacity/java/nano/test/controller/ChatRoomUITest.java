package edu.udacity.java.nano.test.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class ChatRoomUITest {


    private WebDriver driver;
    @Before
    public void setUp() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(getClass().getClassLoader().getResource("chromedriver").getFile())).build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.merge(capabilities);
        this.driver = new ChromeDriver(service, options);
    }


    @Test
    public void testCanary() {
        assertTrue(true);
    }

    @Test
    public void testLogin() {
        this.driver.get("http://localhost:8080");
        WebElement inputElement = this.driver.findElement(By.id("username"));
        WebElement submitElement = this.driver.findElement(By.className("submit"));

        inputElement.sendKeys("Adithya");
        submitElement.click();

        assertEquals(this.driver.getCurrentUrl(), "http://localhost:8080/chat/Adithya");
        assertEquals(this.driver.findElement(By.id("username")).getText(), "Adithya");

    }

    @Test
    public void testJoin() {
        this.driver.get("http://localhost:8080/chat/Adithya");
        Assert.assertEquals(this.driver.findElement(By.id("username")).getText(), "Adithya");
        Assert.assertEquals(this.driver.findElement(By.id("welcomeTxt")).getText(), "Welcome：");

    }

    @Test
    public void chat() {
        this.driver.get("http://localhost:8080/chat/Adithya");

        this.driver.findElement(By.className("mdui-textfield-input")).sendKeys("hello!");
        this.driver.findElement(By.className("mdui-color-pink-accent")).click();
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("message-content")));

        assertEquals(driver.findElement(By.className("message-content")).getText().contains("hello!"), true);

    }

    @Test
    public void leave() {
        this.driver.get("http://localhost:8080/chat/Adithya");

        this.driver.findElement(By.className("mdui-btn-icon")).click();

        assertEquals(this.driver.getCurrentUrl(), "http://localhost:8080/");

    }

}
