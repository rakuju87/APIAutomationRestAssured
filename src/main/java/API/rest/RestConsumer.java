package API.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.in;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestConsumer {
  private static final Logger logger = LogManager.getLogger();
  /**
   * Function to return the RequestSpecification for the API
   *
   * @return RequestSpecification instance that can be passed to executeApi method
   */
  public static RequestSpecification fetchBaseRequest(String url) {
    return given().baseUri(url);
  }
  /**
   * Function to return the base RequestSpecification for the API with basic auth
   *
   * @param url for the resource
   * @param userName User name for Basic auth
   * @param password Password for Basic auth
   * @return RequestSpecification instance that can be passed to executeApi method
   */
  public static RequestSpecification fetchBaseRequest(String url, String userName, String password) {
    return given().baseUri(url).auth().basic(userName, password);
  }
  /**
   * Function to execute the API call and return the RestAssured response
   *
   * @param httpMethod type to execute
   * @param request    prepared for the http call
   * @return RestAssured Response
   */
  public static Response executeApi(Method httpMethod, RequestSpecification request) {
    return executeApi(httpMethod, request, true);
  }
  /**
   * Function to execute the API call and return the RestAssured response
   *
   * @param httpMethod type to execute
   * @param request    prepared for the http call
   * @return RestAssured Response
   */
  public static Response executeApi(Method httpMethod, RequestSpecification request,
      boolean verifySuccess) {
    Response response = execute(httpMethod, request);
    if(verifySuccess)
      verifySuccess(response);

    return response;
  }
  private static Response execute(Method httpMethod, RequestSpecification request) {
    Response response = null;
    switch (httpMethod) {
      case GET:
        response = given().spec(request).get();
        break;

      case POST:
        response = given().spec(request).post();
        break;

      case DELETE:
        response = given().spec(request).delete();
        break;

      case PUT:
        response = given().spec(request).put();
        break;

      case PATCH:
        response = given().spec(request).patch();
        break;

      case HEAD:
        response = given().spec(request).head();
        break;

      default:
        throw new RuntimeException("Unsupported HTTP method: " + httpMethod);
    }
    return response;
  }
  private static void verifySuccess(Response response) {
    try {
      response.then().statusCode(in(Arrays.asList(200, 201, 204)));
    } catch (AssertionError e) {
      logger.error("Non success response code received {} : {}",
          response.getStatusCode(), response.getStatusLine());
      logger.error(response.asString());
      throw new RuntimeException(
          "Response status code: expected success but was " + response.statusCode(), e);
    }
  }


}
