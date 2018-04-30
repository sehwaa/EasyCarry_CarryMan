package com.example.sehwa.easycarry_carryman_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import connect.ApplicationController;
import model.Request.RequestSignIn;
import model.Response.ResponseSignIn;
import networkservice.NetworkServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-25.
 */

public class LoginActivity extends AppCompatActivity {

    private Button signinBtn;
    private EditText emailEt;
    private EditText passwordEt;
    private TextView signupTxt;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        click();
    }

    private void init(){
        signinBtn = (Button)findViewById(R.id.signinBtn);
        emailEt = (EditText)findViewById(R.id.emailEt);
        passwordEt = (EditText)findViewById(R.id.pwdEt);
        signupTxt = (TextView)findViewById(R.id.signupTxt);

        //네트워크 초기화
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();

    }

    private void click(){
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                signinnetwork(email, password);
            }
        });

        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String success = preferences.getString("Success", "");
        if(success.equals("ok")){
            Intent intent = new Intent(LoginActivity.this, NaviMainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void signinnetwork(String email, String password){
        RequestSignIn requestSignIn = new RequestSignIn(email, password);
        Call<ResponseSignIn> getResponseSignIn = networkServiceInterface.signInCall(requestSignIn);
        getResponseSignIn.enqueue(new Callback<ResponseSignIn>() {

            //응답 성공
            @Override
            public void onResponse(Call<ResponseSignIn> call, Response<ResponseSignIn> response) {
                System.out.println(response.code());
                System.out.println(response.message());

                if(response.code() == 200){
                    //기사 번호 가져오기
                    String carrierNum = response.body().getCarrierNum();
                    //이름 가져오기
                    String name = response.body().getName();
                    //전화번호 가져오기
                    String phoneNo = response.body().getPhoneNo();

                    System.out.println(phoneNo);

                    //쿠키 값 저장
                    String cookie = response.headers().get("Set-Cookie");

                    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("carrierNum", carrierNum);
                    editor.putString("name", name);
                    editor.putString("phoneNo", phoneNo);
                    editor.putString("Cookie", cookie);
                    editor.putString("Success", "ok");
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "로그인 되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, NaviMainActivity.class);
                    startActivity(intent);
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "올바르지 않은 정보입니다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "알 수 없는 오류!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseSignIn> call, Throwable t) {
                System.out.println("네트워크 오류");

                Toast.makeText(getApplicationContext(), "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
