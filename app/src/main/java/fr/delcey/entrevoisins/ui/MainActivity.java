package fr.delcey.entrevoisins.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.ui.add.AddNeighbourActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        ViewPager viewPager = findViewById(R.id.main_vp);
        viewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));
        TabLayout tabs =  findViewById(R.id.main_tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.main_fab_add);
        fab.setOnClickListener(v -> startActivity(AddNeighbourActivity.navigate(this)));
    }
}