package utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationReader {
    private static final Logger logger = LogManager.getLogger();
    private Properties properties = new Properties();

    public ConfigurationReader(String fileName) {
      addConfiguration(fileName);
    }
  public void addConfiguration(String fileName) {
    properties = new Properties(properties);

    logger.info("Loading configuration from {}", fileName);
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName)) {
      properties.load(inputStream);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
  public String get(String key) {
    return get(key, true);
  }
  public String get(String key, boolean checkNotNull) throws NullPointerException {
    String propValue = System.getProperty(key) != null ? System.getProperty(key) :
        System.getenv().getOrDefault(key, properties.getProperty(key));
    if (checkNotNull) {
      checkNotNull(propValue,  " Property '" + key + "' not found.");
      checkArgument(!propValue.toLowerCase().contains("lastpass"),
          " Property '" + key + "' was not set.");
      return propValue;
    }
    return propValue;
  }
}
