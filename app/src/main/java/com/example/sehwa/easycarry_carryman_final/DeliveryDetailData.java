package com.example.sehwa.easycarry_carryman_final;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class DeliveryDetailData {

    private String lockerNum;
    private String lockerPW;
    private String lockerSize;
    private String lockerPhoto;
    private String _id;

    public DeliveryDetailData(String lockerNum, String lockerPW, String lockerSize, String lockerPhoto, String _id) {
        this.lockerNum = lockerNum;
        this.lockerPW = lockerPW;
        this.lockerSize = lockerSize;
        this.lockerPhoto = lockerPhoto;
        this._id = _id;
    }

    public String getLockerSize() {
        return lockerSize;
    }

    public String getLockerPW() {
        return lockerPW;
    }

    public String getLockerNum() {
        return lockerNum;
    }

    public String getLockerPhoto() {
        return lockerPhoto;
    }

    public String getLockerId() {
        return _id;
    }

}
