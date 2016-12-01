package com.oracle.homework.core.domain;


public class DataWrapper {
    private AdaptedData data;

    public DataWrapper() {
    }

    public DataWrapper(AdaptedData data) {

        this.data = data;
    }

    public AdaptedData getData() {
        return data;
    }

    public void setData(AdaptedData data) {
        this.data = data;
    }
}
