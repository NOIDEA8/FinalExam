package com.example.finalexam.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finalexam.Activity.LogActivity;
import com.example.finalexam.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class ManagerLogFragment extends DialogFragment {
    private static final String TAG="ManagerLogFragment";
    private LogActivity activity;
    private TextInputEditText passwordEdit;
    private ImageButton back;
    private Button confirm;
    private View view;
    private Dialog dialog;
    private String password;

    public ManagerLogFragment(LogActivity activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用自定义布局创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_manager_log, null);
        builder.setView(view);
        // 这里可以初始化对话框中的控件
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        initView();
        initListener();
        return dialog;
    }

    private void initView(){
        passwordEdit=view.findViewById(R.id.password_manager_editText);
        back=view.findViewById(R.id.back_managerLog);
        confirm=view.findViewById(R.id.confirm_managerLog);
    }

    private void initListener(){

        back.setOnClickListener(v->{
            dialog.dismiss();
        });

        confirm.setOnClickListener(v->{
            password= Objects.requireNonNull(passwordEdit.getText()).toString();;
            if(password.isEmpty()){
                Toast.makeText(requireContext(),"请输入口令",Toast.LENGTH_SHORT).show();
            }else {
                Log.d(TAG,password);
                //按了确定就把窗口去掉
                dialog.dismiss();
                //这里只是将password传到log活动中而已，log活动中无此成员变量
                activity.setManagerPassword(password);

            }
        });
    }

}