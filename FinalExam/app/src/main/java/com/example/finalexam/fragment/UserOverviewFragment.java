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

import retrofit2.Retrofit;

public class UserOverviewFragment extends Fragment{

    private View view;
    private RecyclerView userListView;
    private static final String TAG = "UserOverviewFragment";


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
    public void initView(){
        userListView=view.findViewById(R.id.user_overview_list);
    }
}