package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalexam.R;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.fragment.PersonFragment;
import com.example.finalexam.fragment.ProjectFragment;
import com.example.finalexam.overrideview.AddButton;

public class UserDesktop extends BaseActivity {

    private FrameLayout projectContainer;
    private FrameLayout personContainer;
    private int pagePosition = 0;

    private AddButton addButton;
    private ConstraintLayout projectPageButton;
    private ConstraintLayout personPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_desktop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.white));

        initView();
        initPage();
        initListener();
    }

    @SuppressLint("CommitTransaction")
    private void initPage() {
        ProjectFragment projectFragment = new ProjectFragment();
        PersonFragment personFragment = new PersonFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.user_project_container, projectFragment);
        transaction.add(R.id.user_person_container, personFragment);
        transaction.commit();
    }

    private void initListener() {
        addButton.setOnClickListener(v -> startActivity(new Intent(this, AddProjectActivity.class)));
        projectPageButton.setOnClickListener(v -> changePageTo(0));
        personPageButton.setOnClickListener(v -> changePageTo(1));
    }

    private void initView() {
        projectContainer = findViewById(R.id.user_project_container);
        personContainer = findViewById(R.id.user_person_container);
        addButton = findViewById(R.id.add_button);
        projectPageButton = findViewById(R.id.user_project_page_button);
        personPageButton = findViewById(R.id.user_person_page_button);
    }

    private void changePageTo(int pagePosition) {
        //防止浪费算力或丢失已经绘制的画面
        if (this.pagePosition == pagePosition) return;
        else this.pagePosition = pagePosition;

        if (pagePosition == 0) {
            projectContainer.setVisibility(View.VISIBLE);
            personContainer.setVisibility(View.INVISIBLE);
        } else {
            projectContainer.setVisibility(View.INVISIBLE);
            personContainer.setVisibility(View.VISIBLE);
        }
    }
}