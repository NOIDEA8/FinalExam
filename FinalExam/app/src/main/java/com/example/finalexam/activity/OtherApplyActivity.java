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
import com.example.finalexam.fragment.PersonFragment;
import com.example.finalexam.model.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class OtherApplyActivity extends AppCompatActivity {

    private RecyclerView otherRV;
    private final List<ProjectData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_apply);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.grey));
        initView();
        initListener();
        initRV();
    }

    private void initRV() {
        otherRV.setLayoutManager(new LinearLayoutManager(this));
        list.clear();
        list.addAll(PersonFragment.otherApplications);
        otherRV.setAdapter(new ApplyAdapter(this, list, true));
    }

    private void initListener() {

    }

    private void initView() {
        otherRV = findViewById(R.id.other_rv);
    }
}