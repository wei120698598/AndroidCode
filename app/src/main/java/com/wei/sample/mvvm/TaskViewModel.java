package com.wei.sample.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-11
 * @email weishuxin@icourt.cc
 */
public class TaskViewModel extends AndroidViewModel {
    private MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getString() {
        return mutableLiveData;
    }


    public void getData(){
        mutableLiveData.setValue("Data from viewModel");
    }

}
