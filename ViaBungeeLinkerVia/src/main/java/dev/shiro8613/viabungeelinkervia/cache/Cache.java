package dev.shiro8613.viabungeelinkervia.cache;

import java.util.*;

public class Cache {
    private static final List<CacheData> caches = new ArrayList<>();
    private static final Timer timer = new Timer();

    public static void StartCache() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!caches.isEmpty()) {
                    caches.removeIf(f -> f.time().after(new Date()));
                }
            }
        }, 0, 30 * 1000);
    }

    public static void StopCache() {
        timer.cancel();
    }

    public static void AddCache(String uuid, String address) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 20);
        CacheData data = new CacheData(uuid, address, calendar.getTime());
        caches.add(data);
    }

    public static String HasGetCache(String uuid) {
        for (CacheData data :caches) {
            if (data.uuid().equals(uuid)) {
                return data.address();
            }
        }

        return null;
    }

}
