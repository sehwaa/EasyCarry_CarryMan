package model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEHWA on 2017-10-21.
 */

public class ResponseDeilveryList {
    private String _id;
    private String station_start;
    private String station_dest;
    private String requestedTime;
    private String precaution;
    private String created_at;
    private int totalPrice;

    @SerializedName("lockers")
    private List<OrderLockers> lockers = new ArrayList<OrderLockers>();

    public class OrderLockers{
        protected String lockerNum;
        protected String lockerSize;
        protected String lockerPW;
        protected String lockerPhoto;
        protected String _id;

        public String getLockerNum(){
            return lockerNum;
        }

        public String getLockerSize(){
            return lockerSize;
        }

        public String getLockerPW(){
            return lockerPW;
        }

        public String getLockerPhoto(){
            return lockerPhoto;
        }

        public String get_id() {
            return _id;
        }
    }

    public String getOid(){
        return _id;
    }

    public String getStation_start(){
        return station_start;
    }

    public String getStation_dest(){
        return station_dest;
    }

    public String getRequestedTime(){
        return requestedTime;
    }

    public String getPrecaution(){
        return precaution;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getCreated_at() {
        return created_at;
    }

}
