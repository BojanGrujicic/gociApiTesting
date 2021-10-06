package base;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class ApiPostRequests {

    public static String baseApiURl = "https://web_api.netnation-dev.devtech-labs.com/api/";
    public static String loginUrl = "login";

    public String getJwtToken() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(baseApiURl+loginUrl)
                .header("Content-Type", "application/json")
                .body("{\"username\":\"admin@gmail.com\",\"password\":\"Admin@12345\"}")
                .asJson();

        return "Bearer " + response.getBody().getObject().get("token");
    }
}
