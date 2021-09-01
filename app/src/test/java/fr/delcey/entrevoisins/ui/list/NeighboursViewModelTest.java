package fr.delcey.entrevoisins.ui.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import fr.delcey.entrevoisins.LiveDataTestUtils;
import fr.delcey.entrevoisins.data.Neighbour;
import fr.delcey.entrevoisins.data.NeighbourRepository;

@RunWith(MockitoJUnitRunner.class)
public class NeighboursViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private NeighbourRepository neighbourRepository;

    private MutableLiveData<List<Neighbour>> neighboursMutableLiveData;

    private NeighboursViewModel viewModel;

    @Before
    public void setUp() {
        // Reinitialize LiveData every test
        neighboursMutableLiveData = new MutableLiveData<>();

        // Mock LiveData returned from Repository
        given(neighbourRepository.getNeighboursLiveData()).willReturn(neighboursMutableLiveData);

        // Set default values to LiveData
        List<Neighbour> neighbours = getDefaultNeighbours();
        neighboursMutableLiveData.setValue(neighbours);

        viewModel = new NeighboursViewModel(neighbourRepository);
    }

    @Test
    public void nominal_case() {
        // When
        LiveDataTestUtils.observeForTesting(viewModel.getNeighbourViewStateItemsLiveData(false), value -> {
            // Then
            // Step 1 for Then : assertions...
            assertEquals(getDefaultNeighbourViewStateItems(), value);

            // ... Step 2 for Then : verify !
            verify(neighbourRepository).getNeighboursLiveData();
            verifyNoMoreInteractions(neighbourRepository);
        });
    }

    @Test
    public void edge_case_no_more_neighbour() {
        // Given
        List<Neighbour> neighbours = new ArrayList<>();
        // Now, repository will return a LiveData that contains an empty list, like if we deleted all neighbours
        neighboursMutableLiveData.setValue(neighbours);

        // When
        LiveDataTestUtils.observeForTesting(viewModel.getNeighbourViewStateItemsLiveData(false), value -> {
            // Then
            // Now, the exposed value from the ViewModel should be an empty list !
            assertEquals(0, value.size());

            verify(neighbourRepository).getNeighboursLiveData();
            verifyNoMoreInteractions(neighbourRepository);
        });
    }

    // For every public method in the ViewModel, we should verify that data passed to the ViewModel
    // is correctly dispatched to the underlying components (the repository, in this instance)
    @Test
    public void verify_on_delete_clicked() {
        // Given
        long neighbourId = 666;

        // When
        viewModel.onDeleteNeighbourClicked(neighbourId);

        // Then
        Mockito.verify(neighbourRepository).deleteNeighbour(neighbourId);
        Mockito.verifyNoMoreInteractions(neighbourRepository);
    }

    // region FAVORITE
    @SuppressWarnings("ConstantConditions")
    @Test
    public void nominal_case_favorites() {
        // Given
        boolean isFavorite = true;

        // When
        LiveDataTestUtils.observeForTesting(viewModel.getNeighbourViewStateItemsLiveData(isFavorite), value -> {
            // Then
            // By default, there's no favorite neighbour
            assertEquals(0, value.size());

            verify(neighbourRepository).getNeighboursLiveData();
            verifyNoMoreInteractions(neighbourRepository);
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void edge_case_favorites_are_added() {
        // Given
        boolean isFavorite = true;

        List<Neighbour> neighbours = getDefaultNeighbours();
        neighbours.add(getDefaultNeighbour(4, true));
        // Now, repository will return a LiveData that contains 5 Neighbours (one of which is favorite), like if added a Neighbour as favorite
        neighboursMutableLiveData.setValue(neighbours);

        // When
        LiveDataTestUtils.observeForTesting(viewModel.getNeighbourViewStateItemsLiveData(isFavorite), value -> {
            // Then
            // We should expose the only favorite we have
            assertEquals(1, value.size());
            assertEquals(getDefaultNeighbourViewStateItem(4), value.get(0));

            verify(neighbourRepository).getNeighboursLiveData();
            verifyNoMoreInteractions(neighbourRepository);
        });
    }
    // endregion FAVORITE

    // region IN (this is the default values that "enters" the ViewModel)
    private List<Neighbour> getDefaultNeighbours() {
        List<Neighbour> neighbours = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            neighbours.add(getDefaultNeighbour(i, false));
        }

        return neighbours;
    }

    @NonNull
    private Neighbour getDefaultNeighbour(int index, boolean isFavorite) {
        return new Neighbour(
            index,
            "name" + index,
            "avatarUrl" + index,
            "address" + index,
            "phoneNumber" + index,
            "aboutMe" + index,
            isFavorite
        );
    }
    // endregion IN

    // region OUT (this is the default values that are exposed by the ViewModel)
    private List<NeighboursViewStateItem> getDefaultNeighbourViewStateItems() {
        List<NeighboursViewStateItem> neighbours = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            neighbours.add(getDefaultNeighbourViewStateItem(i));
        }

        return neighbours;
    }

    @NonNull
    private NeighboursViewStateItem getDefaultNeighbourViewStateItem(int index) {
        return new NeighboursViewStateItem(
            index,
            "name" + index,
            "avatarUrl" + index
        );
    }
    // endregion OUT
}