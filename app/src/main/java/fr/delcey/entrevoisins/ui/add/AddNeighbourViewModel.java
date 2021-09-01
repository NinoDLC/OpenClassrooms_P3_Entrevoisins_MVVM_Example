package fr.delcey.entrevoisins.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.delcey.entrevoisins.data.NeighbourRepository;
import fr.delcey.entrevoisins.ui.utils.SingleLiveEvent;

public class AddNeighbourViewModel extends ViewModel {

    // Injected thanks to ViewModelFactory
    @NonNull
    private final NeighbourRepository neighbourRepository;

    // Default value is false : button should not be enabled at start
    private final MutableLiveData<Boolean> isSaveButtonEnabledMutableLiveData = new MutableLiveData<>(false);

    // Default value is a generated random profile image
    private final String avatarUrl = generateAvatarUrl();
    private final MutableLiveData<String> avatarUrlMutableLiveData = new MutableLiveData<>(avatarUrl);

    // Check https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();

    public AddNeighbourViewModel(@NonNull NeighbourRepository neighbourRepository) {
        this.neighbourRepository = neighbourRepository;
    }

    // Returns a LiveData, not a MutableLiveData. This is an extra security (ask about "immutability" your mentor)
    public LiveData<Boolean> getIsSaveButtonEnabledLiveData() {
        return isSaveButtonEnabledMutableLiveData;
    }

    public LiveData<String> getAvatarUrlLiveData() {
        return avatarUrlMutableLiveData;
    }

    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    public void onNameChanged(String name) {
        isSaveButtonEnabledMutableLiveData.setValue(!name.isEmpty());
    }

    public void onAddButtonClicked(
        @NonNull String name,
        @Nullable String address,
        @Nullable String phoneNumber,
        @Nullable String aboutMe
    ) {
        // Add neighbour to the repository...
        neighbourRepository.addNeighbour(name, avatarUrl, address, phoneNumber, aboutMe);
        // ... and close the Activity !
        closeActivitySingleLiveEvent.call();
    }

    @NonNull
    private String generateAvatarUrl() {
        return "https://i.pravatar.cc/150?u=" + System.currentTimeMillis();
    }
}
