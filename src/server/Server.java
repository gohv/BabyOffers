package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.Offer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Georgy on 8/2/2016.
 */
public class Server {

        private Date lastShuffle = new Date();



        public static void run() {
            HttpServer server = null;

            try {
                server = HttpServer.create(new InetSocketAddress(12345), 0);
                server.createContext("/offers", new MyHandler());
                server.setExecutor(null); // creates a default executor

                server.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        static class MyHandler implements HttpHandler {
            private static Date lastShuffle = new Date();

            public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
                long diffInMillies = date2.getTime() - date1.getTime();
                return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
            }
            @Override
            public void handle(HttpExchange t)  {


                if(getDateDiff(lastShuffle,new Date(), TimeUnit.SECONDS) > 15*60){
                    Collections.shuffle(Offer.offers);
                    Offer.save();
                    lastShuffle = new Date();
                }
                Map<String, String> queries = queryToMap(t.getRequestURI().getQuery());
                int start = Integer.parseInt(queries.get("s"));
                int end = Integer.parseInt(queries.get("e"));


                String listJson = new Gson().toJson(Offer.offers.subList(start, end));

                String response = String.format("{\"offers\": %s, \"size\": %s}", listJson, Offer.offers.size());
                byte[] bytes;

                final Headers headers = t.getResponseHeaders();

                headers.set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
                bytes = response.getBytes(StandardCharsets.UTF_8);

                try{
                t.sendResponseHeaders(200, bytes.length);
                OutputStream os = t.getResponseBody();
                os.write(bytes);
                os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }




    }


