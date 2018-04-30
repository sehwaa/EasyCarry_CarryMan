package com.example.sehwa.easycarry_carryman_final;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by SEHWA on 2017-10-25.
 */

public class MyInfoFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), MyInfoActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
