package com.example.sehwa.easycarry_carryman_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import connect.ApplicationController;
import model.Response.ResponseDeilveryList;
import networkservice.NetworkServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class AllDeliveryListActivity extends AppCompatActivity {

    private ListView listView1;
    private DeliveryListAdapter adapter;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alldelivery);

        init();
        alldeliverynetwork(0, 10);
    }

    private void init(){
        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new DeliveryListAdapter(this);
        //네트워크 초기화
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    private void alldeliverynetwork(int last, int size){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<List<ResponseDeilveryList>> getDeliveryList = networkServiceInterface.alldeliveryCall(cookie, last, size);
        getDeliveryList.enqueue(new Callback<List<ResponseDeilveryList>>() {
            @Override
            public void onResponse(Call<List<ResponseDeilveryList>> call, Response<List<ResponseDeilveryList>> response) {
                System.out.println(response.code());
                System.out.println(response.message());

                if(response.code() == 200){
                    Gson gson = new Gson();
                    String response_result = gson.toJson(response.body());

                    try {
                        JSONArray jsonArray = new JSONArray(response_result);
                        for(int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            JSONArray ja = new JSONObject(obj.toString()).getJSONArray("lockers");
                            System.out.println(obj);

                            if(jsonArray.length() == 0){
                                Toast.makeText(getApplicationContext(), "조회된 내역이 없습니다!", Toast.LENGTH_SHORT).show();
                            }else{
                                if(ja.length() == 0){
                                    //Toast.makeText(getApplicationContext(), "락커 비어있음", Toast.LENGTH_SHORT).show();
                                }else{
                                    String requestedTime = obj.getString("requestedTime");
                                    String temp = requestedTime.replace("T"," ");
                                    String parserequestedTime = temp.replace("Z", "");
                                    String requestedTimefinal = parserequestedTime.replace(".000", "");
                                    String station_start = obj.getString("station_start");
                                    String station_dest = obj.getString("station_dest");

                                    if(ja.length() > 1){
                                        for(int j = 0; j < ja.length(); j++){
                                            JSONObject jaobj = ja.getJSONObject(i);
                                            String lockerNum = jaobj.getString("lockerNum");
                                            String lockerSize = jaobj.getString("lockerSize");
                                            String lockerPW = jaobj.getString("lockerPW");

                                            adapter.addItem(new DeliveryItem(station_start, station_dest, requestedTimefinal));
                                            listView1.setAdapter(adapter);
                                        }
                                    }else{
                                        JSONObject jaobj = ja.getJSONObject(0);
                                        String lockerNum = jaobj.getString("lockerNum");
                                        String lockerSize= jaobj.getString("lockerSize");
                                        String lockerPW = jaobj.getString("lockerPW");

                                        adapter.addItem(new DeliveryItem(station_start, station_dest, requestedTimefinal));
                                        listView1.setAdapter(adapter);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "로그인해주세요!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseDeilveryList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "조회된 내역이 없습니다!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(AllDeliveryListActivity.this, NaviMainActivity.class);
        startActivity(intent);
        finish();
    }
}
