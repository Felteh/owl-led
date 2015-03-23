package com.owl.udpsockets.sup.impl;

import com.owl.udpsockets.sup.Operation;
import com.owl.udpsockets.sup.OperationType;

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
