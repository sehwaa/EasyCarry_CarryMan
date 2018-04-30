package com.example.sehwa.easycarry_carryman_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import connect.ApplicationController;
import model.Request.RequestSignUp;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class SignUpActivity extends AppCompatActivity {

    private EditText firstnameEt;
    private EditText lastnameEt;
    private EditText emailEt;
    private EditText phoneEt;
    private EditText passwordEt;
    private EditText confirmpasswordEt;
    private Button okBtn;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        click();
    }

    private void init(){
        firstnameEt = (EditText)findViewById(R.id.firstnameEt);
        lastnameEt = (EditText)findViewById(R.id.lastnameEt);
        emailEt = (EditText)findViewById(R.id.emailEt);
        phoneEt = (EditText)findViewById(R.id.phonenumEt);
        passwordEt = (EditText)findViewById(R.id.pwdEt);
        confirmpasswordEt = (EditText)findViewById(R.id.copwdEt);
        okBtn = (Button) findViewById(R.id.signupBtn);

        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    private void click(){

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEt.getText().toString().equals("") || firstnameEt.getText().toString().equals("") || lastnameEt.getText().toString().equals("") || phoneEt.getText().toString().equals("") || passwordEt.getText().toString().equals("") || confirmpasswordEt.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "빠짐없이 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    if(!passwordEt.getText().toString().equals(confirmpasswordEt.getText().toString())){
                        Toast.makeText(getApplicationContext(), "패스워드/패스워드 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        String name = firstnameEt.getText().toString() + lastnameEt.getText().toString();
                        signupnetwork(emailEt.getText().toString(), name, phoneEt.getText().toString(), passwordEt.getText().toString());
                    }
                }
            }
        });
    }

    private void signupnetwork(String carrierNum, String name, String phoneNo, String password){
        final RequestSignUp requestSignUp = new RequestSignUp(carrierNum, name, phoneNo, password);
        Call<ResponseBody> signupCall = networkServiceInterface.signUpCall(requestSignUp);
        signupCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                System.out.println(response.message());

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
