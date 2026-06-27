package com.banking_core_system.banking_core_system.common.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IbanGenerator {

    private static final AtomicLong SEQUENCE = new AtomicLong(1);

    private IbanGenerator() {
    }

    public static String generate() {

        long value = SEQUENCE.getAndIncrement();

        return String.format(
                "MA64BK%018d",
                value
        );
    }

}
