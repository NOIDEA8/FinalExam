package com.example.finalexam.helper;



public interface UserDataShowInterface {
    //TODO 以下四个方法均为回调方法，如果需要使用，可以查看UserPresenter
    void userLog(int STATUS);

    void userRegister(int STATUS);
    void projectListResult(int STATUS);

    void projectDetail(int STATUS);

}
