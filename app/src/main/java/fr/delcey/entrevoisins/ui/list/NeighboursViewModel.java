package fr.delcey.entrevoisins.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.delcey.entrevoisins.data.Neighbour;
import fr.delcey.entrevoisins.data.NeighbourRepository;

public class NeighboursViewModel extends ViewModel {

    // Injected thanks to ViewModelFactory
    @NonNull
    private final NeighbourRepository neighbourRepository;

    public NeighboursViewModel(@NonNull NeighbourRepository neighbourRepository) {
        this.neighbourRepository = neighbourRepository;
    }

    // For simplicity's sake, I put the parameter "isFavorite". This is a bad practice. Forget about it. Don't do it.
    // Getters for LiveData in the ViewModel should never have a parameter.
    // The "better way" of passing a parameter from the View to the ViewModel is a bit more complicated and requires DI (with Hilt for example).
    // Check "Assisted Injected Hilt" with your mentor to better understand this topic !
    public LiveData<List<NeighboursViewStateItem>> getNeighbourViewStateItemsLiveData(boolean isOnFavoriteMode) {
        return Transformations.map(neighbourRepository.getNeighboursLiveData(), neighbours -> {
            List<NeighboursViewStateItem> neighboursViewStateItems = new ArrayList<>();

            // This is called mapping !
            // Ask your mentor why it is important to separate "data" models (like Neighbour class)
            // and "view" models (like NeighboursViewStateItem)
            for (Neighbour neighbour : neighbours) {
                // If we're not in "favorite mode", add all Neighbours. Else, add only favorite neighbours !
                if (!isOnFavoriteMode || neighbour.isFavorite()) {
                    neighboursViewStateItems.add(
                        new NeighboursViewStateItem(
                            neighbour.getId(),
                            neighbour.getName(),
                            neighbour.getAvatarUrl()
                        )
                    );
                }
            }

            return neighboursViewStateItems;
        });
    }

    public void onDeleteNeighbourClicked(long neighbourId) {
        neighbourRepository.deleteNeighbour(neighbourId);
    }
}
