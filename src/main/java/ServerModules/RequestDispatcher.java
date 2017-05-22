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
        String s = components[0].trim();

        switch( s ){
            case "username":
                return GameManager.instance().addUser(client, components[1].trim());
            case "opponent":
                return GameManager.instance().assignOpponent(client);
            case "start":
                return GameManager.instance().startGame(client);
            case "hit":
                return GameManager.instance().moveFrom(client, message);
            default:
                return  "Request not recognised";
        }

    }

}
