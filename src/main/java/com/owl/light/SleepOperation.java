package com.owl.light;

import com.owl.light.OperationType;

public class SleepOperation implements Operation {
    public final long sleepLengthMillis;

    public SleepOperation(long sleepLengthMillis) {
        this.sleepLengthMillis = sleepLengthMillis;
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SLEEP;
    }

    @Override
    public String toString() {
        return "SleepOperation{" + "sleepLengthMillis=" + sleepLengthMillis + '}';
    }
    
}
