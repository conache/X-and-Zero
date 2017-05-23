package ClientModules;


/**
 * Created by cristi on 5/19/17.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        new Client("localhost", 8100);
        System.out.println("finished");

    }
}
