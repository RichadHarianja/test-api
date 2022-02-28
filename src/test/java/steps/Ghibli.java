package steps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Ghibli {
  private static final Logger logger = Logger.getLogger(Ghibli.class.getName());

  @Test
  @Given("^I have hit API$")
  public void hitApi() throws IOException {
    Response response;
    String BASE_URL = "https://ghibliapi.herokuapp.com/vehicles/";

    RestAssured.baseURI = BASE_URL;
    RequestSpecification requestSpecification = RestAssured.given();
    requestSpecification.header("Content-Type", "application/json");
    response = requestSpecification.get(BASE_URL);

    logger.info(":: RESPONSE :: " + response.getBody().asString());


    Assert.assertEquals("923d70c9-8f15-4972-ad53-0128b261d628",
        response.getBody().asString().contains("id"),
        true);
    Assert.assertEquals("Red Wing", response.getBody().asString().contains("name"),
        true);

    List<String> resp = response.jsonPath().get();
    JsonArray jsonArray = new Gson().toJsonTree(resp).getAsJsonArray();


    for (int i = 0; i < jsonArray.size(); i++) {
      if (jsonArray.get(i)
          .getAsJsonObject()
          .get("id")
          .getAsString()
          .equals("4e09b023-f650-4747-9ab9-eacf14540cfb")) {
        Assert.assertEquals(jsonArray.get(i).getAsJsonObject().get("name").getAsString(),
            "Air Destroyer Goliath");
        Assert.assertEquals(jsonArray.get(i).getAsJsonObject().get("vehicle_class").getAsString(),
            "Airship");
      }
    }

  }

}
