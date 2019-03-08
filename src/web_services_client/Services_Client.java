package web_services_client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

public class Services_Client 
{
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/web_services/webresources";

    public Services_Client() 
    {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("web_services");
    }

    public void putJson(Object requestEntity) throws ClientErrorException 
    {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }
    
    public String Login(String username, String password) throws ClientErrorException 
    {
        WebTarget resource = webTarget;
        
        resource = resource.path(java.text.MessageFormat.format("{0}&{1}", new Object[]{username, password}));
        
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public String Words(String first_letter, String last_letter, String token) throws ClientErrorException 
    {
        WebTarget resource = webTarget;

        if (first_letter != null) 
            resource = resource.queryParam("first_letter", first_letter);
        
        if (last_letter != null) 
            resource = resource.queryParam("last_letter", last_letter);
        
        if (token != null) 
            resource = resource.queryParam("token", token);
        
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    

    public void close() 
    {
        client.close();
    }
}
