package com.example.finalexam.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ApplyAdapter;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.model.ProjectData;

public class VerifyActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView descriptionView;
    private TextView urlView;
    private TextView yesButton;
    private TextView noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.grey));
        initView();
        initListener();
        showData();
    }

    private void showData() {
        ProjectData data = ApplyAdapter.clickData;
        nameView.setText(data.getProjectName());
        descriptionView.setText(data.getDescription());
    }

    private void initListener() {
        yesButton.setOnClickListener(v -> {

        });
        noButton.setOnClickListener(v -> {

        });
    }

    private void initView() {
        nameView = findViewById(R.id.verify_name);
        descriptionView = findViewById(R.id.verify_description);
        urlView = findViewById(R.id.verify_url);
        yesButton = findViewById(R.id.verify_yes_button);
        noButton = findViewById(R.id.verify_no_button);
    }
}