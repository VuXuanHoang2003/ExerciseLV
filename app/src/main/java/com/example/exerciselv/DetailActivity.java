package com.example.exerciselv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.exerciselv.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent receivedIntent = getIntent();
        if (receivedIntent != null){
            String data = receivedIntent.getStringExtra("number");
            binding.tvDetail.setText(data);
            // Display in plain text to edit
            binding.ptEdit.setText(data);
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnintent = new Intent();
                returnintent.putExtra("edit-number", binding.ptEdit.getText().toString());
                //startActivity(returnintent);
                setResult(Activity.RESULT_OK, returnintent);
                finish();
            }
        });
    }
    public void onBackButton(View view) {
        super.onBackPressed();
    }
    public void onSubmitButton(View view) {

    }
}
