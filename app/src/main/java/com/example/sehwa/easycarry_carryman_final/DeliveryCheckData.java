package com.example.sehwa.easycarry_carryman_final;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class DeliveryCheckData {
    private String station_start;
    private String station_dest;
    private String requestedTime;
    private String lockerNum;
    private String lockerPW;
    private String lockerSize;
    private int totalPrice;

    public DeliveryCheckData(String station_start, String station_dest, String requestedTime, String lockerNum, String lockerPW, String lockerSize, int totalPrice) {
        this.station_start = station_start;
        this.station_dest = station_dest;
        this.requestedTime = requestedTime;
        this.lockerNum = lockerNum;
        this.lockerPW = lockerPW;
        this.lockerSize = lockerSize;
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getLockerNum() {
        return lockerNum;
    }

    public String getLockerPW() {
        return lockerPW;
    }

    public String getLockerSize() {
        return lockerSize;
    }

    public String getRequestedTime() {
        return requestedTime;
    }

    public String getStation_dest() {
        return station_dest;
    }

    public String getStation_start() {
        return station_start;
    }

}
