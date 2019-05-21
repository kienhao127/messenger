package com.example.messenger.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<CharSequence> path = new MutableLiveData<>();
    private MutableLiveData<LocalFile> localFile = new MutableLiveData<>();

    public void setLocalFile(LocalFile localFile) {this.localFile.setValue(localFile);}
    public void setPath(CharSequence path){
        this.path.setValue(path);
    }

    public LiveData<CharSequence> getPath(){
        return this.path;
    }

    public LiveData<LocalFile> getLocalFile(){
        return this.localFile;
    }
}
