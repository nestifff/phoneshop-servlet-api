package com.es.phoneshop.model.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultDosProtectionService implements DosProtectionService {

    private static final long THRESHOLD = 20;

    private final Map<String, Long> countMapById = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor;
    private final Runnable clearerCountMapRunnable;

    private static volatile DosProtectionService instance;

    private DefaultDosProtectionService() {

        clearerCountMapRunnable = this::clearCountMap;

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(clearerCountMapRunnable, 0, 1, TimeUnit.MINUTES);
    }

    public static DosProtectionService getInstance() {

        if (instance == null) {
            synchronized (DefaultDosProtectionService.class) {

                if (instance == null) {
                    instance = new DefaultDosProtectionService();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {

        long count = countMapById.containsKey(ip) ? countMapById.get(ip) : 0;
        countMapById.put(ip, count + 1);

        return count + 1 <= THRESHOLD;
    }

    private void clearCountMap() {
        countMapById.clear();
    }
}
