package com.example.finalexam.Helper;



public interface UserDataShowInterface {
    //TODO 以下四个方法均为回调方法，如果需要使用，可以查看UserPresenter
    void log(int STATUS);

    void register(int STATUS);

    void updateUserData(int STATUS);

    void updateUserImage(int STATUS);

}