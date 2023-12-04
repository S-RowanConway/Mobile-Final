package com.example.finalproject;

// Rowan Conway

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebViewClient;

import com.example.finalproject.databinding.ActivityDetailsBinding;
import com.example.finalproject.databinding.ActivityMainBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    // region wikiPage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            finish();
            return;
        }
        City city;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            city = extras.getParcelable("city", City.class);
        }
        else {
            city = extras.getParcelable("city");
        }
        if (city == null)
        {
            finish();
        }
        else {
            binding.webView.setWebViewClient(new WebViewClient());
            binding.webView.loadUrl("https://www.wikidata.org/wiki/" + city.wikiData);
        }
    }
    // endregion
}