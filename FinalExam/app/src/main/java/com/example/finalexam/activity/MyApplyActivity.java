package com.example.finalexam.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ApplyAdapter;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.fragment.PersonFragment;
import com.example.finalexam.fragment.ProjectFragment;
import com.example.finalexam.model.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class MyApplyActivity extends AppCompatActivity {

    private RecyclerView mineRV;
    private final List<ProjectData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_apply);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
        initRV();
    }

    private void initRV() {
        mineRV.setLayoutManager(new LinearLayoutManager(this));
        list.clear();
        list.addAll(PersonFragment.myProjects);
        list.addAll(PersonFragment.myApplications);
        mineRV.setAdapter(new ApplyAdapter(this, list, false));
    }

    private void initListener() {

    }

    private void initView() {
        mineRV = findViewById(R.id.mine_rv);
    }
}