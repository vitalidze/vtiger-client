package su.litvak.vtiger;


import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaly
 * Date: 26.08.13
 * Time: 23:47
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        // vTiger user name
        final String userName = "admin";
        // vTiger API access key
        final String userAccessKey = "6sSVEhFbmqygRIys";

        Client client = ClientBuilder.newClient().register(JacksonFeature.class);

        WebTarget webTarget = client.target("http://vtiger54.litvak.su/webservice.php");
        Response r = webTarget.queryParam("operation", "getchallenge")
               .queryParam("username", userName)
                .request(MediaType.APPLICATION_JSON_TYPE)
               .get();

        if (r.getStatus() != 200) {
            System.out.println("Request failed with status: " + r.getStatus());
            System.exit(1);
        }

        GetChallengeResponse challenge = r.readEntity(GetChallengeResponse.class);

        if (!challenge.success) {
            System.out.println("Get challenge operation was unsuccessful: " + challenge.error.message);
            System.exit(1);
        }

        r = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.form(new Form()
                        .param("operation", "login")
                        .param("username", userName)
                        .param("accessKey", md5(challenge.result.token + userAccessKey))
                ));

        LoginResponse login = r.readEntity(LoginResponse.class);

        if (!login.success) {
            System.out.println("Login operation was unsuccessful: " + login.error.message);
            System.exit(1);
        }

        String sessionId = login.result.sessionName;
        System.out.println("Logged in. Session id = " + sessionId);

        r = webTarget.queryParam("operation", "getworkflows")
                .queryParam("sessionName", sessionId)
                .request(MediaType.APPLICATION_JSON_TYPE).get();

        GetWorkflowResponse response = r.readEntity(GetWorkflowResponse.class);

        System.out.println("Workflows:");
        for (GetWorkflowResponse.Workflow workflow : response.result.values()) {
            System.out.println(workflow.description);
        }

        webTarget.queryParam("operation", "logout")
                .queryParam("sessionName", sessionId)
                .request().get();

        System.out.println("Logged out");
    }

    private static String md5(String s) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
        md.update(s.getBytes());
        BigInteger hash = new BigInteger(1, md.digest());
        String result = hash.toString(16);
        while(result.length() < 32) { //40 for SHA-1
            result = "0" + result;
        }
        return result;
    }
}
