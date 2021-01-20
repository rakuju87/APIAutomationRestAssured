package API.rest;

import API.models.Weather;
import io.restassured.response.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseParser {
  private static final Logger logger = LogManager.getLogger();

  public static String getFieldValue(Response response, String field) {
    return response.jsonPath().getString(field);
  }
  public static List<Weather> getDataForDay(Response response, String day) throws ParseException {
    List<Weather> weatherList = new ArrayList<>();
    List<Map<String, String>> data = response.jsonPath().getList("data");
    for(Map item:data){
      SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
      Date date=format1.parse(item.get("valid_date").toString());
      DateFormat format2=new SimpleDateFormat("EEEE");
      String finalDay=format2.format(date);
      if(finalDay.equalsIgnoreCase(day)){
        Weather weatherItem = new Weather(item.get("valid_date").toString(),Double.parseDouble(item.get("uv").toString()), Double.parseDouble(item.get("min_temp").toString()),Double.parseDouble(item.get("max_temp").toString()));
        weatherList.add(weatherItem);
//        logger.info("Day result - {} {}",item.get("valid_date"),weatherItem.toString());
      }
    }
    return weatherList;
  }
}
