package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalproject.databinding.ActivityMainBinding;

import java.util.Comparator;
import java.util.function.ToIntFunction;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchButton.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            DataSource source = DataSource.getInstance(this);
            binding.globe.setVisibility(View.INVISIBLE);
            source.search(binding.searchBar.getText().toString(), cities -> {
                binding.progressBar.setVisibility(View.INVISIBLE);
                cities.sort(Comparator.comparingInt((ToIntFunction<City>) city -> city.population).reversed());
                ItemAdapter adapter = new ItemAdapter(cities);
                binding.recyclerView.setAdapter(adapter);
                binding.recyclerView.setVisibility(View.VISIBLE);

            }, error -> {
                Toast.makeText(this, getString(R.string.error_message, error.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.INVISIBLE);
            });
        });
    }
}