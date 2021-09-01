package fr.delcey.entrevoisins.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.ui.ViewModelFactory;

public class NeighbourDetailActivity extends AppCompatActivity {

    private static final String KEY_NEIGHBOUR_ID = "KEY_NEIGHBOUR_ID";

    public static Intent navigate(Context context, long neighbourId) {
        Intent intent = new Intent(context, NeighbourDetailActivity.class);

        intent.putExtra(KEY_NEIGHBOUR_ID, neighbourId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.neighbour_detail_activity);

        Toolbar toolbar = findViewById(R.id.neighbour_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.neighbour_detail_ctl);
        ImageView avatarImageView = findViewById(R.id.neighbour_detail_iv_avatar);
        FloatingActionButton starFab = findViewById(R.id.neighbour_detail_fab);
        TextView nameTextView = findViewById(R.id.neighbour_detail_tv_name);
        TextView locationTextView = findViewById(R.id.neighbour_detail_tv_location);
        TextView phoneTextView = findViewById(R.id.neighbour_detail_tv_phone);
        TextView socialTextView = findViewById(R.id.neighbour_detail_tv_social);
        TextView aboutMeTextView = findViewById(R.id.neighbour_detail_tv_about_me);

        long neighbourId = getIntent().getLongExtra(KEY_NEIGHBOUR_ID, -1);

        if (neighbourId == -1) {
            throw new IllegalStateException("Please use NeighbourDetailActivity.navigate() to launch the Activity");
        }

        NeighbourDetailViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(NeighbourDetailViewModel.class);
        viewModel.getNeighbourDetailViewStateLiveData(neighbourId).observe(this, neighbourDetailViewState -> {
            toolbarLayout.setTitle(neighbourDetailViewState.getName());
            Glide.with(avatarImageView).load(neighbourDetailViewState.getAvatarUrl()).into(avatarImageView);
            starFab.setImageResource(neighbourDetailViewState.getStarDrawableRes());
            nameTextView.setText(neighbourDetailViewState.getName());
            locationTextView.setText(neighbourDetailViewState.getLocation());
            phoneTextView.setText(neighbourDetailViewState.getPhone());
            socialTextView.setText(neighbourDetailViewState.getSocial());
            aboutMeTextView.setText(neighbourDetailViewState.getAbout());
        });

        starFab.setOnClickListener(v -> viewModel.onAddFavoriteClicked(neighbourId));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
