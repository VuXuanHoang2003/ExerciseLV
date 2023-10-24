package com.example.exerciselv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.exerciselv.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyViewModel model;
    private LViewModel lvmodel;
    private int position = -1;
    private ArrayList<Integer> arrayList;
    private ArrayAdapter<Integer> arrayAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        model = new ViewModelProvider(this).get(MyViewModel.class);
        lvmodel = new ViewModelProvider(this).get(LViewModel.class);
        arrayList = new ArrayList<Integer>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        binding.lvCount.setAdapter(arrayAdapter);
        onResume();

        if (savedInstanceState != null) {
            Parcelable listViewState = savedInstanceState.getParcelable("listViewState");
            binding.lvCount.onRestoreInstanceState(listViewState);
        }
        //tạo danh sách mỗi khi ấn button
        model.getNumbers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCount.setText("" + integer);
            }
        });

        lvmodel.getListNumbers().observe(this, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                updateListView(integers);
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.increaseNumber();
                Integer i = model.getNumbers().getValue();
                lvmodel.addItem(i);
            }
        });

        //tạo phương thức khi ấn giữ 1 item trong danh sách thì sẽ xóa item đó
        binding.lvCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                arrayList.remove(i);
//                arrayAdapter.notifyDataSetChanged();
                lvmodel.removeItem(i);
                return false;
            }
        });

        //tạo phương thức khi ấn 1 item trong danh sách thì sẽ hiện ra 1 cửa sô chứa số đó
        binding.lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("number", arrayList.get(i).toString());
                position = i;
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("edit-number");
            if (position != -1) {
                // Lấy dữ liệu trong DetailActivity và cập nhật trong lvmodel
//                arrayList.set(position, Integer.parseInt(updatedNumber));
//                arrayAdapter.notifyDataSetChanged();
                lvmodel.updateItem(position, Integer.parseInt(updatedNumber));
            }
        }
    }

    private void updateListView(ArrayList<Integer> integers){
        arrayAdapter.clear();
        arrayAdapter.addAll(integers);
        arrayAdapter.notifyDataSetChanged();
    }
    //Phương thức được gọi khi ấn nút FloatingActionButton
    public void onSubCounterButton(View view){
        model.decreaseNumber();
        Integer i = model.getNumbers().getValue();
        lvmodel.addItem(i);
    }
    // 2 hàm dưới lưu và khôi phục ListView khi xoay màn hình
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listViewState", binding.lvCount.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable listViewState = savedInstanceState.getParcelable("listViewState");
        binding.lvCount.onRestoreInstanceState(listViewState);
    }
}
