package API.actions;

import API.config.ApiConfiguration;
import API.models.Weather;
import API.rest.ResponseParser;
import API.rest.RestConsumer;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class ApiActions {
  private final Logger logger = LogManager.getLogger();
  Response response;
  List<Weather> weatherList;
  ApiConfiguration apiConfig = new ApiConfiguration();
  public void getWeatherDetails(String postalCode, String path){
    response = RestConsumer.executeApi(Method.GET,createRequestSpec(postalCode, path));
    logger.info("Response - {}",response.getBody().asString());
  }

  private RequestSpecification createRequestSpec(String postalCode, String path) {
    return RestConsumer.fetchBaseRequest(apiConfig.getWeatherApiUrl())
        .basePath(path)
        .queryParam("postal_code",postalCode)
        .queryParam("country","AU")
        .queryParam("key",apiConfig.getKey());
  }

  public boolean validateFieldValue(String fieldName, String expectedVal){
    String actualValue = ResponseParser.getFieldValue(response,fieldName);
    logger.info("returned value for {} - {}",fieldName,actualValue);
    if(expectedVal.equals(actualValue)){
      return true;
    }else{
      return false;
    }
  }

  public void getAllForecastOnTwoDay(String day1, String day2){
    try{
    weatherList = ResponseParser.getDataForDay(response,day1);
    weatherList.addAll(ResponseParser.getDataForDay(response,day2));
    } catch (ParseException exp){
      logger.info("parsing error - {}",exp.toString());
    }
    Assert.assertTrue(!weatherList.isEmpty(),"Weather list for " + day1 + " and " + day2 + " was empty");
  }

  public boolean tempCheck(double minTemp, double maxTemp){
    boolean tempCheckFlag = false;
    for(Weather item:weatherList){
      logger.info("Day result - {} {}",item.getValid_date(),item.toString());
      if((item.getMin_temp() >= minTemp) && (item.getMax_temp() <= maxTemp)){
        tempCheckFlag = true;
      }
    }
    return tempCheckFlag;
  }

  public boolean uvCheck(double uv){
    boolean uvCheckFlag = true;
    for(Weather item:weatherList){
      //check uv on thursday and friday
      if((item.getUV() > uv)){
        uvCheckFlag = false;
      }
    }
    return uvCheckFlag;
  }

}
