package steps;

import config.Reader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class SendRequest {
  private static final Logger logger = Logger.getLogger(SendRequest.class.getName());
  private static Response response;

  private static final String BASE_URL = "https://reqres.in/api/";

  public String sendRequest(String path) throws IOException {
    try {
      Reader reader = new Reader();
      BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
      RequestSpecification requestSpecification = RestAssured.given();
      requestSpecification.header("Content-Type", "application/json");

      String request = reader.readFileRequest(bufferedReader);
      ArrayList<String> listApi =
          new ArrayList<>(Arrays.asList(request.split(System.lineSeparator())));

      int i = 0;
      for (String empty : listApi) {
        if (empty == null || empty.equals("")) {
          break;
        } else {
          if (listApi.get(i).contains(BASE_URL)) {
            response = requestSpecification.get(listApi.get(i));
            i++;
          } else {
            i += 1;
          }
        }
        String jsonToString = response.asString();

        FileWriter fileWriter = null;

        if (path.contains("src/test/resources/data/file1.txt")) {
          fileWriter = new FileWriter("src/test/resources/response/second_response" + i + ".json");
        } else if (path.contains("src/test/resources/data/file.txt")){
          fileWriter = new FileWriter("src/test/resources/response/first_response" + i + ".json");
        }
        assert fileWriter != null;
        fileWriter.write(jsonToString);
        fileWriter.flush();
        fileWriter.close();
      }
    } catch (ConnectException e) {
      logger.info("Exception is :: " + e.getMessage());
    }
    return "SUCCESS";
  }
}
