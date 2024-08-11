package com.example.finalexam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.presenter.WebSocketPresenter;

import java.util.ArrayList;
import java.util.List;

public class UserOverviewFragment extends Fragment {

    private View view;
    private static final String TAG = "UserOverviewFragment";
    private static List<UserData> userOnlineList;


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

    public static void setUserOnlineList(List<UserData> userOnlineList) {
        UserOverviewFragment.userOnlineList = userOnlineList;
    }
}