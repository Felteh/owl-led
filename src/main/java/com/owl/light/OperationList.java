package com.owl.light;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OperationList {

    public final List<Operation> operations;

    public OperationList() {
        operations = new ArrayList<>();
    }

    private OperationList(List<Operation> operations) {
        this.operations = operations;
    }

    public OperationList withOps(Operation... ops) {
        return new OperationList(Arrays.asList(ops));
    }

    public OperationList withOps(List<Operation> ops) {
        return new OperationList(ops);
    }

}
