package ru.job4j.cars.service;

public class FilterDB {

    private String name;
    private String paramName;
    private Object paramValue;

    public FilterDB(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParam(String paramName, Object paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public boolean withParameter() {
        return paramName != null;
    }
}
