package dev.shiro8613.viabungeelinkervia;

import dev.shiro8613.viabungeelinkervia.cache.Cache;
import dev.shiro8613.viabungeelinkervia.server.Server;
import net.lenni0451.lambdaevents.EventHandler;
import net.lenni0451.optconfig.ConfigLoader;
import net.lenni0451.optconfig.provider.ConfigProvider;
import net.raphimc.viaproxy.ViaProxy;
import net.raphimc.viaproxy.plugins.ViaProxyPlugin;
import net.raphimc.viaproxy.plugins.events.Client2ProxyHandlerCreationEvent;

import java.io.File;
import java.io.IOException;

public class ViaBungeeLinkerVia extends ViaProxyPlugin {
    private Server server;

    @Override
    public void onEnable() {
        ConfigLoader<Config> configLoader = new ConfigLoader<>(Config.class);
        try {
            configLoader.loadStatic(ConfigProvider.file(new File(this.getDataFolder(), "config.yml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ViaProxy.EVENT_MANAGER.register(this);

        Cache.StartCache();
        try {
            server = new Server(Config.bindHttpServerAddress);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        server.stopServer();
        Cache.StopCache();
        super.onDisable();
    }

    @EventHandler
    public void onNettyEvent(Client2ProxyHandlerCreationEvent event) {
        event.setHandler(new CustomPacket());
    }

}
