package com.example.sehwa.easycarry_carryman_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import connect.ApplicationController;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class DeliveryDetailActivity extends AppCompatActivity {

    private String lockerSize;
    private String lockerNum;
    private String lockerPW;
    private String lockerPhoto;
    private String lockerId;

    private TextView lockersizeTxt;
    private TextView lockernumTxt;
    private TextView lockerpwTxt;

    private ImageView imageView;

    private Button okBtn;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_detail);

        init();
        getIntentResult();
        setTextView();
        setImageView();
        click();
    }

    private void init(){
        lockersizeTxt = (TextView) findViewById(R.id.lockersizeTxt);
        lockernumTxt = (TextView)findViewById(R.id.lockernumTxt);
        lockerpwTxt = (TextView) findViewById(R.id.lockerpwTxt);
        okBtn = (Button) findViewById(R.id.okBtn);
        imageView = (ImageView)findViewById(R.id.imageView2);
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }


    private void getIntentResult(){
        Intent intent = getIntent();
        lockerNum = intent.getStringExtra("lockerNum");
        lockerPW = intent.getStringExtra("lockerPW");
        lockerSize = intent.getStringExtra("lockerSize");
        lockerId = intent.getStringExtra("lockerId");
        lockerPhoto = intent.getStringExtra("lockerPhoto");
        setResult(RESULT_OK, intent);
    }

    private void setTextView(){
        lockernumTxt.setText(lockerNum);
        lockerpwTxt.setText(lockerPW);
        lockersizeTxt.setText(lockerSize);
    }

    private void setImageView(){

        Picasso.with(this)
                .load("https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-866782882107/"+lockerPhoto)
                .into(imageView);

    }

    private void click(){
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmylist(lockerId);
            }
        });
    }

    private void addmylist(String lockerID){
    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
    String cookie = preferences.getString("Cookie", "");

    Call<ResponseBody> addmylist = networkServiceInterface.addMyListCall(cookie, lockerID);
    addmylist.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response.code() == 200){
                Toast.makeText(getApplicationContext(), "마이리스트에 추가되었습니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeliveryDetailActivity.this, DeliveryActivity.class);
                startActivity(intent);
                finish();

            }else if(response.code() == 400){
                Toast.makeText(getApplicationContext(), "이미 예약된 락커입니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeliveryDetailActivity.this, DeliveryActivity.class);
                startActivity(intent);
                finish();

            }else if(response.code() == 401){
                Toast.makeText(getApplicationContext(), "로그인한 후 이용해주세요!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeliveryDetailActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else if(response.code() == 404){
                Toast.makeText(getApplicationContext(), "취소된 락커입니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeliveryDetailActivity.this, DeliveryActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
        }
    });
    }

}
