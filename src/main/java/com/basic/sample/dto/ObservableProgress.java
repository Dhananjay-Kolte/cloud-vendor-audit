package com.basic.sample.dto;

import java.util.concurrent.atomic.AtomicInteger;

public class ObservableProgress {
    private final int target;
    private final AtomicInteger value = new AtomicInteger(0);

    public ObservableProgress(int target) {
        this.target = target;
    }

    public ObservableProgress increment(int v){
        value.getAndAdd(v);
        return this;
    }

    public int getTarget() {
        return target;
    }

    public int getValue() {
        return value.get();
    }
    
    public ObservableProgress complete() {
        value.getAndAdd(getTarget() - getValue());
        return this;
    }

    @Override
    public String toString() {
        return "ObservableProgress{" +
                "target=" + target +
                ", value=" + value +
                '}';
    }
}
