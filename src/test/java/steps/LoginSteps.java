package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;

public class LoginSteps {

    WebDriver driver = Driver.getDriver();
    LoginPage loginPage = new LoginPage();
    LoginSteps loginSteps = new LoginSteps();

    @Given("user is on the webpage")
    public void user_is_on_the_webpage() {
        driver.get(Config.getProperty("sauceDemo"));
    }
    @When("user enters username in the username field")
    public void user_enters_username_in_the_username_field() {
        loginPage.username.sendKeys(Config.getProperty("username"));
    }
    @When("user enters password in the password field")
    public void user_enters_password_in_the_password_field() {
        loginPage.password.sendKeys(Config.getProperty("password"));
    }
    @Then("user clicks on Login button")
    public void user_clicks_on_login_button() {
        loginPage.loginButton.click();
    }
    @Then("verify user is logged in")
    public void verify_user_is_logged_in() {
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(expectedUrl, Driver.getDriver().getCurrentUrl());
    }
}
