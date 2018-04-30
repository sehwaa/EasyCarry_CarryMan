package model.Request;

/**
 * Created by SEHWA on 2017-10-21.
 */

public class RequestSignIn {
    private String carrierNum;
    private String password;

    public RequestSignIn(String carrierNum, String password){
        this.carrierNum = carrierNum;
        this.password = password;
    }

    public String getCarrierNum(){
        return carrierNum;
    }

    public String getPassword(){
        return password;
    }

}
