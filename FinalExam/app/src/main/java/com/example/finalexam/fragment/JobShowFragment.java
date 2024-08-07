package com.example.finalexam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;


public class JobShowFragment extends Fragment {

   private View view;
   private RecyclerView projectsShow;

    public JobShowFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_job_show, container, false);

        return view;
    }

    private void initView(){
        projectsShow=view.findViewById(R.id.rv_ProjectsShow);

    }
}