package dev.shiro8613.viaBungeeLinkerBungee;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class Client {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String address;

    public Client(String address) {
        this.address = "http://" + address;
    }

    public boolean Send(String uuid, String serverAddress) throws IOException, InterruptedException {
        String data = uuid + "#" + serverAddress;
        HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofString(data);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.address + "/connect"))
                .POST(publisher)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

}
