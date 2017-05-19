package ClientModules;

import java.io.IOException;

/**
 * Created by cristi on 5/19/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new Client("localhost", 8100);
    }
}
