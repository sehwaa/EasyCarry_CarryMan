package com.example.sehwa.easycarry_carryman_final;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import connect.ApplicationController;
import model.Response.ResponseDeilveryList;
import model.Response.ResponseMyDeliveryList;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-21.
 */

public class DeliveryCheckActivity extends AppCompatActivity{

    ListView listView1;
    LockerTextListAdapter adapter;

    private NetworkServiceInterface networkServiceInterface;

    private ArrayList<SearchDeliveryData> searchDeliveryDataArrayList;

    private Button cancelBtn;
    private Button completeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverycheck);

        init();
        click();
        mydeliverynetwork();

    }

    private void init(){
        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new LockerTextListAdapter(this);
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();

        searchDeliveryDataArrayList = new ArrayList<SearchDeliveryData>();
    }

    private void click(){
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogview = layoutInflater.inflate(R.layout.cancel_or_complete_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryCheckActivity.this);
                builder.setView(dialogview);

                cancelBtn = (Button) dialogview.findViewById(R.id.cancelBtn);
                completeBtn = (Button) dialogview.findViewById(R.id.completeBtn);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydeliverycancelnetwork(position);
                        alertDialog.dismiss();
                    }
                });

                completeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydeliverycompletenetwork(position);
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }


    private void mydeliverynetwork(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<List<ResponseMyDeliveryList>> mydeliveryCall = networkServiceInterface.mydeliverylistCall(cookie, 0, 150);
        mydeliveryCall.enqueue(new Callback<List<ResponseMyDeliveryList>>() {
            @Override
            public void onResponse(Call<List<ResponseMyDeliveryList>> call, Response<List<ResponseMyDeliveryList>> response) {
                    if(response.code() == 200){
                        Gson gson = new Gson();
                        String response_result = gson.toJson(response.body());
                        System.out.println(response_result);

                        if(response_result.equals("[null]")){
                            Toast.makeText(getApplicationContext(), "조회할 정보가 없습니다!", Toast.LENGTH_SHORT).show();
                        } else if(response_result.contains("null")){
                            response_result.replace("null,", "");
                            System.out.println(response_result);
                            try {
                                JSONArray jsonArray = new JSONArray(response_result);
                                int count = 0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    System.out.println(obj);
                                    String lockerNum = obj.getString("lockerNum");
                                    String lockerPW = obj.getString("lockerPW");
                                    String lockerPhoto = obj.getString("lockerPhoto");
                                    String lockerSize = obj.getString("lockerSize");
                                    String _id = obj.getString("_id");
                                    String status = obj.getString("status");

                                    System.out.println(lockerNum+" "+ lockerPW+" "+ lockerSize+" "+ status);

                                    SearchDeliveryData searchDeliveryData = new SearchDeliveryData(_id);
                                    searchDeliveryDataArrayList.add(count, searchDeliveryData);
                                    count++;

                                    adapter.addItem(new LockerTextItem("No."+lockerNum+" locker", "PASSWORD : " + lockerPW, "SIZE : " + lockerSize, status));
                                    listView1.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            try {
                                JSONArray jsonArray = new JSONArray(response_result);
                                int count = 0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    System.out.println(obj);
                                    String lockerNum = obj.getString("lockerNum");
                                    String lockerPW = obj.getString("lockerPW");
                                    String lockerPhoto = obj.getString("lockerPhoto");
                                    String lockerSize = obj.getString("lockerSize");
                                    String _id = obj.getString("_id");
                                    String status = obj.getString("status");

                                    System.out.println(lockerNum+" "+ lockerPW+" "+ lockerSize+" "+ status);

                                    SearchDeliveryData searchDeliveryData = new SearchDeliveryData(_id);
                                    searchDeliveryDataArrayList.add(count, searchDeliveryData);
                                    count++;

                                    adapter.addItem(new LockerTextItem("No."+lockerNum+" locker", "PASSWORD : " + lockerPW, "SIZE : " + lockerSize, status));
                                    listView1.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            }

            @Override
            public void onFailure(Call<List<ResponseMyDeliveryList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
                System.out.println(call.toString());
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace());
            }
        });
    }

    private void mydeliverycancelnetwork(int position){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        String oid = searchDeliveryDataArrayList.get(position).getOid();
        Call<ResponseBody> mydeliverycancel = networkServiceInterface.mydeliverylistcancelCall(cookie, oid);
        mydeliverycancel.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(), "정상적으로 철회되었습니다!" , Toast.LENGTH_SHORT).show();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "로그인 후 이용해주세요!", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "이미 철회된 배송입니다!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void mydeliverycompletenetwork(int position){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        String oid = searchDeliveryDataArrayList.get(position).getOid();
        Call<ResponseBody> mydeliverycomplete = networkServiceInterface.mydeliverycompleteCall(cookie, oid);
        mydeliverycomplete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(), "정상적으로 처리되었습니다!" , Toast.LENGTH_SHORT).show();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "로그인 후 이용해주세요!", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "배송중인 항목이 아닙니다!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
