package com.firstapp.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ExpensesTabFragment extends Fragment {


    private String gName; // group name


    static ExpensesTabFragment newInstance(String gName) {
        Bundle args = new Bundle();
        args.putString("group_name", gName);
        ExpensesTabFragment f = new ExpensesTabFragment();
        return f;
    }


}