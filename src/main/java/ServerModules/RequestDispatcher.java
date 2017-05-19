package ServerModules;

import java.net.Socket;

/**
 * Created by cristi on 5/19/17.
 */
public class RequestDispatcher {

    private final String client;

    public RequestDispatcher(String client) {
        this.client = client;
    }

    public String dispatch(String message) throws Exception {
        String[] components = message.split(" ");

        if( components.length != 2){
            return "Invalid number of arguments in request";
        }

        String s = components[0].trim();
        if (s.equals("username")) {
            return GameManager.instance().addUser(client, components[1].trim());

        } else if (s.equals("opponent")) {
            return GameManager.instance().assignOpponent(client);
        }
        return null;
    }

}
