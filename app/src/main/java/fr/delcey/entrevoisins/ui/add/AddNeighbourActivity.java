package fr.delcey.entrevoisins.ui.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.ui.ViewModelFactory;

public class AddNeighbourActivity extends AppCompatActivity {

    public static Intent navigate(Context context) {
        return new Intent(context, AddNeighbourActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_neighbour_activity);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddNeighbourViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddNeighbourViewModel.class);

        ImageView addNeighbourAvatar = findViewById(R.id.add_neighbour_avatar);
        TextInputEditText nameEditText = findViewById(R.id.add_neighbour_name_tiet);
        TextInputEditText phoneNumberEditText = findViewById(R.id.add_neighbour_phone_number_tiet);
        TextInputEditText addressEditText = findViewById(R.id.add_neighbour_address_tiet);
        TextInputEditText aboutMeEditText = findViewById(R.id.add_neighbour_about_me_tiet);
        Button addNeighbourButton = findViewById(R.id.add_neighbour_button);

        bindAvatar(viewModel, addNeighbourAvatar);
        bindName(viewModel, nameEditText);
        bindAddButton(viewModel, nameEditText, phoneNumberEditText, addressEditText, aboutMeEditText, addNeighbourButton);

        viewModel.getCloseActivitySingleLiveEvent().observe(this, aVoid -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindAvatar(AddNeighbourViewModel viewModel, ImageView addNeighbourAvatar) {
        viewModel.getAvatarUrlLiveData().observe(this, avatarUrl ->
            Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.ic_account)
                .apply(RequestOptions.circleCropTransform())
                .into(addNeighbourAvatar));
    }

    private void bindName(AddNeighbourViewModel viewModel, TextInputEditText nameEditText) {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onNameChanged(s.toString());
            }
        });
    }

    private void bindAddButton(AddNeighbourViewModel viewModel, TextInputEditText nameEditText, TextInputEditText phoneNumberEditText, TextInputEditText addressEditText, TextInputEditText aboutMeEditText, Button addNeighbourButton) {
        //noinspection ConstantConditions
        addNeighbourButton.setOnClickListener(v -> viewModel.onAddButtonClicked(
            nameEditText.getText().toString(),
            addressEditText.getText().toString(),
            phoneNumberEditText.getText().toString(),
            aboutMeEditText.getText().toString()
        ));
        viewModel.getIsSaveButtonEnabledLiveData().observe(this, isSaveButtonEnabled -> addNeighbourButton.setEnabled(isSaveButtonEnabled));
    }
}
