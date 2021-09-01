package fr.delcey.entrevoisins.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.ui.ViewModelFactory;
import fr.delcey.entrevoisins.ui.details.NeighbourDetailActivity;

public class NeighboursFragment extends Fragment {

    private static final String KEY_IS_ON_FAVORITE_MODE = "KEY_IS_ON_FAVORITE_MODE";

    public static Fragment newInstance(boolean isOnFavoriteMode) {
        NeighboursFragment fragment = new NeighboursFragment();

        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_ON_FAVORITE_MODE, isOnFavoriteMode);

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.neighbours_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("Please use NeighboursFragment.newInstance() to build the Fragment");
        }

        NeighboursViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(NeighboursViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.neighbours_rv);
        NeighboursAdapter adapter = new NeighboursAdapter(new OnNeighbourClickedListener() {
            @Override
            public void onNeighbourClicked(long neighbourId) {
                startActivity(NeighbourDetailActivity.navigate(requireContext(), neighbourId));
            }

            @Override
            public void onDeleteNeighbourClicked(long neighbourId) {
                viewModel.onDeleteNeighbourClicked(neighbourId);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel.getNeighbourViewStateItemsLiveData(getArguments().getBoolean(KEY_IS_ON_FAVORITE_MODE)).observe(getViewLifecycleOwner(), neighboursViewStateItems ->
            adapter.submitList(neighboursViewStateItems)
        );
    }
}
