package com.example.sehwa.easycarry_carryman_final;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import connect.ApplicationController;
import model.Response.ResponseDeilveryList;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-21.
 */

public class DeliveryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView startlineTxt;
    private TextView startstationTxt;
    private Spinner lineSpinner;
    private Spinner stationSpinner;

    private ArrayAdapter<String> linespinnerAdapter = null;

    private ArrayAdapter<String> stationspinnerAdapter_1 = null;
    private ArrayAdapter<String> stationspinnerAdapter_2 = null;
    private ArrayAdapter<String> stationspinnerAdapter_3 = null;
    private ArrayAdapter<String> stationspinnerAdapter_4 = null;
    private ArrayAdapter<String> stationspinnerAdapter_5 = null;
    private ArrayAdapter<String> stationspinnerAdapter_6 = null;
    private ArrayAdapter<String> stationspinnerAdapter_7 = null;
    private ArrayAdapter<String> stationspinnerAdapter_8 = null;
    private ArrayAdapter<String> stationspinnerAdapter_9 = null;

    private Button selectstartstationBtn;

    private ListView listView1;
    private DeliveryListAdapter adapter;

    private SwipeRefreshLayout mSwipeResfreshLayout;

    private ArrayList<DeliveryDetailData> deliveryDetailDataArrayList;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);

        init();
        click();
    }

    private void init(){
        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new DeliveryListAdapter(this);
        selectstartstationBtn = (Button)findViewById(R.id.selectstartstationBtn);
        startlineTxt = (TextView)findViewById(R.id.startlineTxt);
        startstationTxt = (TextView) findViewById(R.id.startstationTxt);

        mSwipeResfreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeResfreshLayout.setOnRefreshListener(this);

        linespinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.line));
        stationspinnerAdapter_1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.dark_blue_line));
        stationspinnerAdapter_2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.green_line));
        stationspinnerAdapter_3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.orange_line));
        stationspinnerAdapter_4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.blue_line));
        stationspinnerAdapter_5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.purple_line));
        stationspinnerAdapter_6 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.dark_orange_line));
        stationspinnerAdapter_7 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.dark_green_line));
        stationspinnerAdapter_8 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.pink_line));

        deliveryDetailDataArrayList = new ArrayList<DeliveryDetailData>();
        //네트워크 초기화
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    private void click(){

        startstationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();

                final View dialogview = layoutInflater.inflate(R.layout.select_station_dialog, null);

                lineSpinner = (Spinner) dialogview.findViewById(R.id.selectlineSpinner);
                lineSpinner.setAdapter(linespinnerAdapter);

                stationSpinner = (Spinner) dialogview.findViewById(R.id.selectstationSpinner);

                selectstartstationBtn = (Button) dialogview.findViewById(R.id.selectstartstationBtn);

                lineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                stationSpinner.setAdapter(stationspinnerAdapter_1);
                                break;
                            case 1:
                                stationSpinner.setAdapter(stationspinnerAdapter_2);
                                break;
                            case 2:
                                stationSpinner.setAdapter(stationspinnerAdapter_3);
                                break;
                            case 3:
                                stationSpinner.setAdapter(stationspinnerAdapter_4);
                                break;
                            case 4:
                                stationSpinner.setAdapter(stationspinnerAdapter_5);
                                break;
                            case 5:
                                stationSpinner.setAdapter(stationspinnerAdapter_6);
                                break;
                            case 6:
                                stationSpinner.setAdapter(stationspinnerAdapter_7);
                                break;
                            case 7:
                                stationSpinner.setAdapter(stationspinnerAdapter_8);
                                break;
                            case 8:
                                stationSpinner.setAdapter(stationspinnerAdapter_9);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryActivity.this);
                builder.setView(dialogview);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                selectstartstationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startstationTxt.setText(stationSpinner.getSelectedItem().toString());
                        startlineTxt.setText(lineSpinner.getSelectedItem().toString());
                        alertDialog.dismiss();
                        deliverynetwork(stationSpinner.getSelectedItem()+"역".toString(), 0, 30);
                    }
                });
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendData(position);
            }
        });
    }

    private void deliverynetwork(String start, int last, int size){

        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<List<ResponseDeilveryList>> getDeliveryList = networkServiceInterface.deliveryCall(cookie, start, last, size);
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
                        int count = 0;
                        for(int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            JSONArray ja = new JSONObject(obj.toString()).getJSONArray("lockers");
                            System.out.println(obj);

                            if(jsonArray.length() == 0){
                                Toast.makeText(getApplicationContext(), "조회된 내역이 없습니다!", Toast.LENGTH_SHORT).show();
                            }else{
                                if(ja.length() == 0){
                                    Toast.makeText(getApplicationContext(), "락커 비어있음", Toast.LENGTH_SHORT).show();
                                }else{
                                    String requestedTime = obj.getString("requestedTime");
                                    String temp = requestedTime.replace("T"," ");
                                    String parserequestedTime = temp.replace("Z", "");
                                    String requestedTimefinal = temp.replace(".000Z", "");
                                    String station_start = obj.getString("station_start");
                                    String station_dest = obj.getString("station_dest");
                                    //String precaution = obj.getString("precaution");

                                    if(ja.length() > 1){
                                        for(int j = 0; j < ja.length(); j++){
                                            JSONObject jaobj = ja.getJSONObject(j);
                                            String lockerNum = jaobj.getString("lockerNum");
                                            String lockerSize = jaobj.getString("lockerSize");
                                            String lockerPW = jaobj.getString("lockerPW");
                                            String lockerPhoto = jaobj.getString("lockerPhoto");
                                            String lockerID = jaobj.getString("_id");

                                            System.out.println(jaobj);

                                            adapter.addItem(new DeliveryItem(station_start, station_dest, requestedTimefinal));
                                            listView1.setAdapter(adapter);
                                            DeliveryDetailData deliveryDetailData = new DeliveryDetailData(lockerNum, lockerSize, lockerPW, lockerPhoto, lockerID);
                                            deliveryDetailDataArrayList.add(count, deliveryDetailData);
                                            count++;
                                        }
                                    }else{
                                        JSONObject jaobj = ja.getJSONObject(0);
                                        String lockerNum = jaobj.getString("lockerNum");
                                        String lockerSize= jaobj.getString("lockerSize");
                                        String lockerPW = jaobj.getString("lockerPW");
                                        String lockerPhoto = jaobj.getString("lockerPhoto");
                                        String lockerID = jaobj.getString("_id");

                                        adapter.addItem(new DeliveryItem(station_start, station_dest, requestedTimefinal));
                                        listView1.setAdapter(adapter);
                                        DeliveryDetailData deliveryDetailData = new DeliveryDetailData(lockerNum, lockerSize, lockerPW, lockerPhoto,lockerID);
                                        deliveryDetailDataArrayList.add(count, deliveryDetailData);
                                        count++;
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
    public void onRefresh() {
        deliverynetwork(stationSpinner.getSelectedItem()+"역".toString(), 0, 30);
        mSwipeResfreshLayout.setRefreshing(false);
    }

    public void sendData(int position){
        String lockerNum = deliveryDetailDataArrayList.get(position).getLockerNum();
        String lockerPW = deliveryDetailDataArrayList.get(position).getLockerPW();
        String lockerSize = deliveryDetailDataArrayList.get(position).getLockerSize();
        String lockerPhoto = deliveryDetailDataArrayList.get(position).getLockerPhoto();
        String lockerID = deliveryDetailDataArrayList.get(position).getLockerId();
        Intent intent = new Intent(DeliveryActivity.this, DeliveryDetailActivity.class);

        intent.putExtra("lockerNum", lockerNum);
        intent.putExtra("lockerPW", lockerPW);
        intent.putExtra("lockerSize", lockerSize);
        intent.putExtra("lockerPhoto", lockerPhoto);
        intent.putExtra("lockerId", lockerID);
        startActivityForResult(intent, 1);
        finish();
    }
}
