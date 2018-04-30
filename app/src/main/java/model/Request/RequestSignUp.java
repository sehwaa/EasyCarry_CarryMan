package model.Request;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class RequestSignUp {
    private String carrierNum;
    private String name;
    private String phoneNo;
    private String password;

    public RequestSignUp(String carrierNum, String name, String phoneNo, String password){
        this.carrierNum = carrierNum;
        this.name = name;
        this.phoneNo = phoneNo;
        this.password = password;
    }
}
