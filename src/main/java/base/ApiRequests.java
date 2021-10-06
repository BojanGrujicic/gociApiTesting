package base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.http.ParseException;

import java.util.ArrayList;

public class ApiRequests {

    ApiPostRequests postRequests = new ApiPostRequests();

    String token = postRequests.getJwtToken();

    String baseUrl = "https://web_api.netnation-dev.devtech-labs.com/api/";
    String servicePlan = "service-plans";
    String cusromers = "customers";
    String migrationPath = "migrationbatch?name=";

    public ApiRequests() throws UnirestException {
    }

    public void getServicePlans() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(baseUrl + servicePlan)
                .header("Authorization", token)
                .asJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(JsonParser.parseString(response.getBody().toString()));
        System.out.println(json);
    }

    public void getCustomersByServicePlan() throws UnirestException, InterruptedException {
        //  Unirest.config().connectTimeout(5000).socketTimeout(5000);
        HttpResponse<JsonNode> response = Unirest.post(baseUrl + cusromers)
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body("{\"ServicePlanId\":5,\"PageNumber\":1,\"RecordsPerPage\":10}")
                .asJson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(JsonParser.parseString(response.getBody().toString()));
        System.out.println(json);
    }

    public void getMigrationStatus(String migrationName) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(baseUrl + migrationPath + migrationName)
                .header("Authorization", token)
                .asJson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(JsonParser.parseString(response.getBody().toString()));

        JSONObject root = new JSONObject(json);

        System.out.println(root);
        System.out.println(root.get("id"));
        System.out.println(root.get("name"));

        JSONArray migration = root.getJSONArray("migrations");
        System.out.println(gson.toJson(JsonParser.parseString(migration.toString())));

        JSONObject myObj = response.getBody().getObject();

        String msg = myObj.getString("error_message");
        JSONArray results = myObj.getJSONArray("migrations");
    }

    public ArrayList<String> customerId = new ArrayList<>();
    public ArrayList<String> subscriptionId = new ArrayList<>();
    public final ArrayList<String> webSpaceId = new ArrayList<>();

    public void fetchWebSpaceId() throws UnirestException, ParseException {
        customerId.add("1000111");
        customerId.add("1000121");
        customerId.add("1000122");
        System.out.println(customerId.size());

        for (int i = 0; i < customerId.size(); i++) {
            getSubscriptionId(customerId.get(i));
            HttpResponse<JsonNode> response = Unirest.get("http://10.98.1.80/test/public/index.php/getWebspacesList/" + subscriptionId.get(i))
                    .asJson();
            System.out.println(response.getBody());
            String json = response.getBody().toString();
            JSONArray jarr = new JSONArray(json);
            JSONObject jobj = jarr.getJSONObject(0);
            String id = jobj.get("webspace_id").toString();
            System.out.println(id);
            webSpaceId.add(id);
            System.out.println("------------------------------");
        }
    }

    public void getSubscriptionId(String customerId) throws UnirestException, ParseException {
        HttpResponse<JsonNode> response = Unirest.get("http://10.98.1.80/test/public/index.php/getCustomerSubscriptions/" + customerId)
                .asJson();
        String json = response.getBody().toString();
        JSONArray jarr = new JSONArray(json);
        JSONObject jobj = jarr.getJSONObject(0);
        String id = jobj.get("subscriptionID").toString();
        subscriptionId.add(id);
    }
}

