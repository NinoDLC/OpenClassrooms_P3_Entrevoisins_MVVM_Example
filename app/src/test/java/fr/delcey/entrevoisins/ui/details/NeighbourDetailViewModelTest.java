package fr.delcey.entrevoisins.ui.details;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import android.app.Application;

import androidx.annotation.DrawableRes;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.delcey.entrevoisins.LiveDataTestUtils;
import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.data.Neighbour;
import fr.delcey.entrevoisins.data.NeighbourRepository;

@RunWith(MockitoJUnitRunner.class)
public class NeighbourDetailViewModelTest {

    private static final long DEFAULT_NEIGHBOUR_ID = 42;
    private static final String DEFAULT_NEIGHBOUR_NAME = "DEFAULT_NEIGHBOUR_NAME";
    private static final String MOCKED_SOCIAL_RESOURCE = "MOCKED_SOCIAL_RESOURCE";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Application application;

    @Mock
    private NeighbourRepository neighbourRepository;

    private MutableLiveData<Neighbour> neighbourMutableLiveData;

    private NeighbourDetailViewModel viewModel;

    @Before
    public void setUp() {
        // Reinitialize LiveData every test
        neighbourMutableLiveData = new MutableLiveData<>();

        // Mock resources
        given(application.getString(R.string.facebook_link, DEFAULT_NEIGHBOUR_NAME)).willReturn(MOCKED_SOCIAL_RESOURCE);

        // Mock LiveData returned from Repository
        given(neighbourRepository.getNeighbourLiveData(DEFAULT_NEIGHBOUR_ID)).willReturn(neighbourMutableLiveData);

        // Set default values to LiveData
        Neighbour neighbour = getDefaultNeighbour(false);
        neighbourMutableLiveData.setValue(neighbour);

        viewModel = new NeighbourDetailViewModel(application, neighbourRepository);
    }

    @Test
    public void nominal_case() {
        // When
        LiveDataTestUtils.observeForTesting(viewModel.getNeighbourDetailViewStateLiveData(DEFAULT_NEIGHBOUR_ID), value -> {
            // Then
            // Step 1 for Then : assertions...
            assertEquals(getDefaultNeighbourDetailViewState(), value);

            // ... Step 2 for Then : verify !
            verify(neighbourRepository).getNeighbourLiveData(DEFAULT_NEIGHBOUR_ID);
            verifyNoMoreInteractions(neighbourRepository);
        });
    }

    @Test
    public void edge_case_is_favorite() {
        // Given
        Neighbour neighbour = getDefaultNeighbour(true);
        neighbourMutableLiveData.setValue(neighbour);

        // When
        LiveDataTestUtils.observeForTesting(viewModel.getNeighbourDetailViewStateLiveData(DEFAULT_NEIGHBOUR_ID), value -> {
            // Then
            // Step 1 for Then : assertions ! If neighbour is favorite, the icon should be different...
            assertEquals(getDefaultNeighbourDetailViewState(R.drawable.ic_star_24dp), value);

            // ... Step 2 for Then : verify !
            verify(neighbourRepository).getNeighbourLiveData(DEFAULT_NEIGHBOUR_ID);
            verifyNoMoreInteractions(neighbourRepository);
        });
    }

    @Test
    public void verify_on_add_favorite_clicked() {
        // Given
        long neighbourId = 7;

        // When
        viewModel.onAddFavoriteClicked(neighbourId);

        // Then
        verify(neighbourRepository).toggleFavoriteNeighbour(neighbourId);
        verifyNoMoreInteractions(neighbourRepository);
    }

    // region IN (this is the default values that "enters" the ViewModel)
    private Neighbour getDefaultNeighbour(boolean isFavorite) {
        return new Neighbour(
            DEFAULT_NEIGHBOUR_ID,
            DEFAULT_NEIGHBOUR_NAME,
            "avatarUrl",
            "address",
            "phoneNumber",
            "aboutMe",
            isFavorite
        );
    }
    // endregion IN

    // region OUT (this is the default values that are exposed by the ViewModel)
    private NeighbourDetailViewState getDefaultNeighbourDetailViewState() {
        return getDefaultNeighbourDetailViewState(R.drawable.ic_star_border_24dp);
    }

    private NeighbourDetailViewState getDefaultNeighbourDetailViewState(@DrawableRes int starDrawableRes) {
        return new NeighbourDetailViewState(
            DEFAULT_NEIGHBOUR_NAME,
            "avatarUrl",
            starDrawableRes,
            "address",
            "phoneNumber",
            MOCKED_SOCIAL_RESOURCE,
            "aboutMe"
        );
    }
    // endregion OUT
}