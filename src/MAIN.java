import model.Downloader;
import server.Server;

import java.io.IOException;

/**
 * Created by Georgy on 7/27/2016.
 */
public class MAIN {
    public static void main(String[] args) throws IOException {

        Downloader downloader = new Downloader();

        Server server = new Server();

            server.run();
            downloader.run();



    }
}
