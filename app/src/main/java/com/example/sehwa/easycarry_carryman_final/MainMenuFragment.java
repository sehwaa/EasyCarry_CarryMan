package com.example.sehwa.easycarry_carryman_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class MainMenuFragment extends Fragment {

    private LinearLayout deliveryLayout;
    private LinearLayout confirmdelivery;
    private LinearLayout chat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_carrymanhome, container, false);

        deliveryLayout = (LinearLayout) view.findViewById(R.id.deliveryLayout);
        confirmdelivery = (LinearLayout) view.findViewById(R.id.confirmdelivery);
        //chat = (LinearLayout) view.findViewById(R.id.chat);


        deliveryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intent);
            }
        });

        confirmdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeliveryCheckActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
