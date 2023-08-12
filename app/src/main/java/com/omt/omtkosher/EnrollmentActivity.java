package com.omt.omtkosher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.vipulasri.timelineview.TimelineView;
import com.omt.omtkosher.databinding.ActivityEnrollmentBinding;



public class EnrollmentActivity extends AppCompatActivity {

    ActivityEnrollmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnrollmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.included4.tv.setText("4");
        binding.included4.tv.setBackgroundResource(R.drawable.circleundone);
        binding.included3.tv.setText("3");
        binding.included3.tv.setBackgroundResource(R.drawable.circleundone);
        binding.included2.tv.setText("2");
        binding.included2.tv.setBackgroundResource(R.drawable.circleundone);
        binding.included1.tv.setText("1");


    }
}