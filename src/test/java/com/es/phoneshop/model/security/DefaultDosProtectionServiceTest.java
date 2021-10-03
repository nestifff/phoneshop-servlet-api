package com.es.phoneshop.model.security;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultDosProtectionServiceTest {

    private static DosProtectionService protectionService;

    @Before
    public void init() {
        protectionService = DefaultDosProtectionService.getInstance();
    }

    @Test
    public void getInstance_returnSame() {
        assertSame(protectionService, DefaultDosProtectionService.getInstance());
    }

    @Test
    public void isAllowed_true() {

        String ip = "ip1";
        boolean isAllowed = true;

        for (int i = 0; i < 15; ++i) {
            if (!protectionService.isAllowed(ip)) {
                isAllowed = false;
            }
        }
        assertTrue(isAllowed);
    }

    @Test
    public void isAllowed_false() {

        String ip = "ip2";

        for (int i = 0; i < 19; ++i) {
            protectionService.isAllowed(ip);
        }
        assertFalse(protectionService.isAllowed(ip));
    }

    @Test
    public void isAllowed_trueAnotherIp() {

        String ip = "ip3";

        for (int i = 0; i < 19; ++i) {
            protectionService.isAllowed(ip);
        }
        assertTrue(protectionService.isAllowed("new ip"));
    }


}