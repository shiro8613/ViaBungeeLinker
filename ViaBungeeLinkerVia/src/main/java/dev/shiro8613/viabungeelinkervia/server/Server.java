package dev.shiro8613.viabungeelinkervia.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server extends Thread {
    private final HttpServer server;

    public Server(String bindAddress) throws IOException {
        String[] splitAddress = bindAddress.split(":");
        String host = splitAddress[0];
        int port = Integer.parseInt(splitAddress[1]);

        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.server.createContext("/connect", new Handler());
    }

    @Override
    public void run() {
        super.run();
        this.server.start();
    }

    public void stopServer() {
        this.server.stop(0);
    }
}
