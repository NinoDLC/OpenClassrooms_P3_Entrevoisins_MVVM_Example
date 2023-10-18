package fr.delcey.entrevoisins.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fr.delcey.entrevoisins.databinding.MainActivityBinding;
import fr.delcey.entrevoisins.ui.add.AddNeighbourActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivityBinding binding = MainActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.mainViewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));
        binding.mainTabLayout.setupWithViewPager(binding.mainViewPager);
        binding.mainFloatingActionButtonAdd.setOnClickListener(v -> startActivity(AddNeighbourActivity.navigate(this)));
    }
}