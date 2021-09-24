package com.firstapp.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class BalancesTabFragment extends Fragment {


    private String gName; // group name


    static BalancesTabFragment newInstance(String gName) {
        Bundle args = new Bundle();
        args.putString("group_name", gName);
        BalancesTabFragment f = new BalancesTabFragment();
        return f;
    }


}