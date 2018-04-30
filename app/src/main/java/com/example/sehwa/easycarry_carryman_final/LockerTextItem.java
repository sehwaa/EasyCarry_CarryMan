package com.example.sehwa.easycarry_carryman_final;

/**
 * Created by SEHWA on 2017-10-23.
 */

public class LockerTextItem {

    private String[] mData;

    private boolean mSelectable = true;

    public LockerTextItem(String[] obj) {
        mData = obj;
    }

    public LockerTextItem(String obj01, String obj02, String obj03 , String obj04) {

        mData = new String[4];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
        mData[3] = obj04;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    public void setData(String[] obj) {
        mData = obj;
    }

    public int compareTo(IconTextItem other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return 0;
    }
}
