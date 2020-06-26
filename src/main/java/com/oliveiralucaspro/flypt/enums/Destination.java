package com.oliveiralucaspro.flypt.enums;

public enum Destination {

    OPO("OPO"), OPORTO("OPO"), PORTO("OPO"), LIS("LIS"), LISBON("LIS"), LISBOA("LIS");

    private String destinationCode;

    Destination(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public static Destination findByKey(String key) {
        for (Destination v : values()) {
            if (v.toString().equalsIgnoreCase(key)) {
                return v;
            }
        }
        return null;
    }

    public static Destination findByValue(String value) {
        for (Destination v : values()) {
            if (v.getDestinationCode().equals(value)) {
                return v;
            }
        }
        return null;
    }
}
