package com.example.seuprofissional.ui.BuscarProfisisonal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BuscarProfiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BuscarProfiViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}