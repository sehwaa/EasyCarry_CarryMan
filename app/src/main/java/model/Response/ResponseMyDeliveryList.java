package model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class ResponseMyDeliveryList {

    String lockerNum;
    String lockerPW;
    String lockerSize;
    String lockerPhoto;
    String status;
    String _id;

    public ResponseMyDeliveryList(String lockerNum, String lockerPW, String lockerSize, String lockerPhoto, String status, String oid) {
        this.lockerNum = lockerNum;
        this.lockerPW = lockerPW;
        this.lockerSize = lockerSize;
        this.lockerPhoto = lockerPhoto;
        this.status = status;
        this._id = oid;
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

    public String getStatus() {
        return status;
    }

    public String getOid() {
        return _id;
    }

}
