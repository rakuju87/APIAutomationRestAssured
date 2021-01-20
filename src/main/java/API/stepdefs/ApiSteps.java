package API.stepdefs;

import API.actions.ApiActions;
import API.models.Weather;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class ApiSteps {
  private final Logger logger = LogManager.getLogger();
  ApiActions apiActions = new ApiActions();

  @Given("I like to surf in (.*) of Sydney with (.*)")
  public void givenIwantToExecuteService(String city, String postalCode){
    String path = "forecast/daily";
    apiActions.getWeatherDetails(postalCode,path);
    logger.info("Success response got from weather API");
  }

  @Then("I check temp on (.*) and (.*) is between (.*) to (.*)")
  public void checkTempRangeOnDay(String day1,String day2, double minTemp, double maxTemp){
    apiActions.getAllForecastOnTwoDay(day1,day2);
    Assert.assertTrue(apiActions.tempCheck(minTemp,maxTemp),"No thursday and friday has temp between "+ minTemp +" to " + maxTemp);
  }
  @And("I check to see if UV index is less than or equal (.*)")
  public void checkUV(double uv){
    Assert.assertTrue(apiActions.uvCheck(uv),"UV is higher than " + uv);
  }
}
