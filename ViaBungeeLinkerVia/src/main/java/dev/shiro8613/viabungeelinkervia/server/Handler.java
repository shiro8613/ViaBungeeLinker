package dev.shiro8613.viabungeelinkervia.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.shiro8613.viabungeelinkervia.cache.Cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream stream = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String requestData = reader.readLine();
        if (requestData == null || requestData.isEmpty()) {
            exchange.sendResponseHeaders(400, 0);
            exchange.close();
            return;
        }

        String[] splitData = requestData.split("#");
        if (splitData.length < 2) {
            exchange.sendResponseHeaders(400, 0);
            exchange.close();
            return;
        }

        String uuid = splitData[0];
        String serverAddress = splitData[1];

        Cache.AddCache(uuid, serverAddress);
        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
