package dev.shiro8613.viabungeelinkervia;

import net.lenni0451.optconfig.annotations.Description;
import net.lenni0451.optconfig.annotations.OptConfig;
import net.lenni0451.optconfig.annotations.Option;

@OptConfig
public class Config {
    @Option("bind")
    @Description("bind http server address")
    public static String bindHttpServerAddress = "0.0.0.0:3000";
}
