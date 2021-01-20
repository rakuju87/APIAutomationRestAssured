package API.config;

import lombok.Data;
import utils.ConfigurationReader;

@Data
public class ApiConfiguration {
  private final String weatherApiUrl;
  private final String key;
  private ConfigurationReader configReader;
  public ApiConfiguration() {
    configReader = new ConfigurationReader ("config.properties");
    weatherApiUrl =  configReader.get("weatherApiURL");
    key = configReader.get("key");
  }
}
