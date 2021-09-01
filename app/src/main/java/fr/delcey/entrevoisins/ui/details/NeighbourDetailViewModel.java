package fr.delcey.entrevoisins.ui.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.data.NeighbourRepository;

public class NeighbourDetailViewModel extends ViewModel {

    // Injected thanks to ViewModelFactory
    @NonNull
    private final Application application;

    // Injected thanks to ViewModelFactory
    @NonNull
    private final NeighbourRepository neighbourRepository;

    public NeighbourDetailViewModel(@NonNull Application application, @NonNull NeighbourRepository neighbourRepository) {
        this.application = application;
        this.neighbourRepository = neighbourRepository;
    }

    // For simplicity's sake, I put the parameter "neighbourId". This is a bad practice. Forget about it. Don't do it.
    // Getters for LiveData in the ViewModel should never have a parameter.
    // The "better way" of passing a parameter from the View to the ViewModel is a bit more complicated and requires DI (with Hilt for example).
    // Check "Assisted Injected Hilt" with your mentor to better understand this topic !
    public LiveData<NeighbourDetailViewState> getNeighbourDetailViewStateLiveData(long neighbourId) {
        // This is called mapping !
        // Ask your mentor why it is important to separate "data" models (like Neighbour class)
        // and "view" models (like NeighbourDetailViewState)
        return Transformations.map(
            neighbourRepository.getNeighbourLiveData(neighbourId),
            neighbour -> new NeighbourDetailViewState(
                neighbour.getName(),
                neighbour.getAvatarUrl(),
                // Here's one interesting mapping : I transform the boolean 'isFavorite' into the correct drawable : either filled or empty
                neighbour.isFavorite() ? R.drawable.ic_star_24dp : R.drawable.ic_star_border_24dp,
                neighbour.getAddress(),
                neighbour.getPhoneNumber(),
                // Another interesting mapping here, using the resources in the ViewModel to combine Strings
                application.getString(R.string.facebook_link, neighbour.getName()),
                neighbour.getAboutMe()
            )
        );
    }

    public void onAddFavoriteClicked(long neighbourId) {
        neighbourRepository.toggleFavoriteNeighbour(neighbourId);
    }
}
