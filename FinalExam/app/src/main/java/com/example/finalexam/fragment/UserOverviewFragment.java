package com.example.finalexam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;
import com.example.finalexam.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserOverviewFragment extends Fragment {

    private View view;
    private static final String TAG = "UserOverviewFragment";
    public static final List<UserData> list = new ArrayList<>();


    public UserOverviewFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_overview, container, false);


        return view;
    }
}