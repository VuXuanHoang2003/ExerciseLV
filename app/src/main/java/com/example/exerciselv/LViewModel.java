package com.example.exerciselv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class LViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Integer>> lNumbers; // ListNumbers

    public LiveData<ArrayList<Integer>> getListNumbers() {
        if (lNumbers == null) {
            lNumbers = new MutableLiveData<>();
            lNumbers.setValue(new ArrayList<>());
        }
        return lNumbers;
    }

    public void addItem(Integer val) {
        ArrayList<Integer> currentList = lNumbers.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(val);
        lNumbers.setValue(currentList);
    }

    public void removeItem(int i) {
        ArrayList<Integer> currentList = lNumbers.getValue();
        if (currentList != null && i >= 0 && i < currentList.size()) {
            currentList.remove(i);
            lNumbers.setValue(currentList);
        }
    }

    public void updateItem(int i, int newVal) {
        ArrayList<Integer> currentList = lNumbers.getValue();
        if (currentList != null && i >= 0 && i < currentList.size()) {
            currentList.set(i, newVal);
            lNumbers.setValue(currentList);
        }
    }
}
