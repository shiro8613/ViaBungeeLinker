package dev.shiro8613.viaBungeeLinkerBungee;

import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;

public final class ViaBungeeLinkerBungee extends Plugin implements Listener {
    private Client client;

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
        try {
            makeConfig();
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            String serverAddress = configuration.getString("viaserver");
            client = new Client(serverAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onServerConnectRequest(ServerConnectEvent event) {
        ServerInfo serverInfo = event.getTarget();
        SocketAddress socketAddress = serverInfo.getSocketAddress();
        String connectingServerAddress = socketAddress.toString(); //これで接続先のサーバーのアドレスをコンフィグから取得できる

        ProxiedPlayer player = event.getPlayer();
        String uuid = player.getUniqueId().toString().replace("-", "");

        getProxy().getScheduler().runAsync(this, () -> {
            try {
                if(!client.Send(uuid, connectingServerAddress)) {
                    event.isCancelled();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void makeConfig() throws IOException {
        // Create plugin config folder if it doesn't exist
        if (!getDataFolder().exists()) {
            getLogger().info("Created config folder: " + getDataFolder().mkdir());
        }

        File configFile = new File(getDataFolder(), "config.yml");

        // Copy default config if it doesn't exist
        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile); // Throws IOException
            InputStream in = getResourceAsStream("config.yml"); // This file must exist in the jar resources folder
            in.transferTo(outputStream); // Throws IOException
        }
    }
}
