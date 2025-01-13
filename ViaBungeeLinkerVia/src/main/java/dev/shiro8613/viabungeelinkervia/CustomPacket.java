package dev.shiro8613.viabungeelinkervia;

import dev.shiro8613.viabungeelinkervia.cache.Cache;
import io.netty.channel.ChannelHandlerContext;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.packet.impl.handshaking.C2SHandshakingClientIntentionPacket;
import net.raphimc.viaproxy.proxy.client2proxy.Client2ProxyHandler;

import java.util.List;

public class CustomPacket extends Client2ProxyHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        if(packet instanceof C2SHandshakingClientIntentionPacket packet1) {
            List<String> s = new java.util.ArrayList<>(List.of(packet1.address.split("\0")));
            if (s.size() > 3) {
                String address = Cache.HasGetCache(s.get(2));
                if (address != null) {
                    s.remove(0);
                    String[] splitAddress = address.split("/");
                    if (splitAddress.length > 1) {
                        s.add(0, splitAddress[0]);
                        packet1.address = String.join("\0", s);
                        super.channelRead0(channelHandlerContext, packet1);
                        return;
                    }
                }
            }
        }
        super.channelRead0(channelHandlerContext, packet);
    }
}
