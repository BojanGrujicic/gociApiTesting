import base.ApiPostRequests;
import base.ApiRequests;
import kong.unirest.UnirestException;
import org.junit.Test;

public class RunTest {

    ApiRequests getRequests = new ApiRequests();

    public RunTest() throws UnirestException {
    }

    @Test
    public void getAllUsers() throws UnirestException {
        System.out.println("-----------------------------");
        getRequests.fetchWebSpaceId();
        for (int i=0;i< getRequests.webSpaceId.size();i++){
            System.out.println(getRequests.webSpaceId.get(i));
        }
    }

    @Test
    public void printWebSpaceId(){
        System.out.println(getRequests.webSpaceId.size());
    }
}
