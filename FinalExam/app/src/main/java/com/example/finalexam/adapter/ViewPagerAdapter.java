package com.example.finalexam.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> datas;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> datas) {
        super(fragmentActivity);
        this.datas = datas;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return datas.get(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
