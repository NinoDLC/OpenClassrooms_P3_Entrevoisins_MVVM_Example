package fr.delcey.entrevoisins.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.delcey.entrevoisins.LiveDataTestUtils;
import fr.delcey.entrevoisins.config.BuildConfigResolver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NeighbourRepositoryTest {

    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    private static final String DEFAULT_AVATAR_URL = "DEFAULT_AVATAR_URL";
    private static final String DEFAULT_ADDRESS = "DEFAULT_ADDRESS";
    private static final String DEFAULT_PHONE_NUMBER = "DEFAULT_PHONE_NUMBER";
    private static final String DEFAULT_ABOUT_ME = "DEFAULT_ABOUT_ME";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private BuildConfigResolver buildConfigResolver;

    private NeighbourRepository neighbourRepository;

    @Before
    public void setUp() {
        given(buildConfigResolver.isDebug()).willReturn(false);

        neighbourRepository = new NeighbourRepository(buildConfigResolver);

        verify(buildConfigResolver).isDebug();
        verifyNoMoreInteractions(buildConfigResolver);
    }

    @Test
    public void nominal_case_list_is_empty_for_prod() {
        // When
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(0, value.size());
        });
    }

    @Test
    public void add_neighbour_should_change_list() {
        // When
        addDefaultNeighbour();
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(getDefaultNeighbourList(1), value);
        });
    }

    @Test
    public void add_neighbour_twice_should_change_list() {
        // Given
        addDefaultNeighbour();

        // When
        addDefaultNeighbour();
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(getDefaultNeighbourList(2), value);
        });
    }

    @Test
    public void add_neighbour_and_get_from_id() {
        // When
        addDefaultNeighbour();
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighbourLiveData(0), value -> {
            // Then
            assertEquals(getDefaultNeighbour(0), value);
        });
    }

    @Test
    public void add_neighbour_twice_and_get_from_id() {
        // Given
        addDefaultNeighbour();

        // When
        addDefaultNeighbour();
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighbourLiveData(1), value -> {
            // Then
            assertEquals(getDefaultNeighbour(1), value);
        });
    }

    @Test
    public void add_and_delete_neighbour() {
        // Given
        addDefaultNeighbour();

        // When
        neighbourRepository.deleteNeighbour(0);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(0, value.size());
        });
    }

    @Test
    public void add_twice_and_delete_one_neighbour() {
        // Given
        addDefaultNeighbour();
        addDefaultNeighbour();

        // When
        neighbourRepository.deleteNeighbour(1);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(getDefaultNeighbourList(1), value);
        });
    }

    @Test
    public void add_twice_and_delete_twice_neighbour() {
        // Given
        addDefaultNeighbour();
        addDefaultNeighbour();

        // When
        neighbourRepository.deleteNeighbour(0);
        neighbourRepository.deleteNeighbour(1);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(0, value.size());
        });
    }

    @Test
    public void deleting_nonexistent_neighbour_should_not_crash() {
        // When
        neighbourRepository.deleteNeighbour(0);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(0, value.size());
        });
    }

    @Test
    public void toggle_neighbour() {
        // Given
        addDefaultNeighbour();

        // When
        neighbourRepository.toggleFavoriteNeighbour(0);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(Collections.singletonList(getDefaultNeighbour(0, true)), value);
        });
    }

    @Test
    public void toggle_neighbour_with_1_out_of_2_neighbours() {
        // Given
        addDefaultNeighbour();
        addDefaultNeighbour();

        // When
        neighbourRepository.toggleFavoriteNeighbour(1);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(
                Arrays.asList(
                    getDefaultNeighbour(0, false),
                    getDefaultNeighbour(1, true)
                ),
                value
            );
        });
    }

    @Test
    public void toggle_neighbour_with_2_neighbours() {
        // Given
        addDefaultNeighbour();
        addDefaultNeighbour();

        // When
        neighbourRepository.toggleFavoriteNeighbour(1);
        neighbourRepository.toggleFavoriteNeighbour(0);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighboursLiveData(), value -> {
            // Then
            assertEquals(
                Arrays.asList(
                    getDefaultNeighbour(0, true),
                    getDefaultNeighbour(1, true)
                ),
                value
            );
        });
    }

    @Test
    public void toggle_neighbour_and_get_by_id() {
        // Given
        addDefaultNeighbour();
        addDefaultNeighbour();

        // When
        neighbourRepository.toggleFavoriteNeighbour(1);
        LiveDataTestUtils.observeForTesting(neighbourRepository.getNeighbourLiveData(1), value -> {
            // Then
            assertEquals(getDefaultNeighbour(1, true), value);
        });
    }

    // region DEFAULTS
    private void addDefaultNeighbour() {
        neighbourRepository.addNeighbour(DEFAULT_NAME, DEFAULT_AVATAR_URL, DEFAULT_ADDRESS, DEFAULT_PHONE_NUMBER, DEFAULT_ABOUT_ME);
    }

    private List<Neighbour> getDefaultNeighbourList(int count) {
        List<Neighbour> neighbours = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            neighbours.add(getDefaultNeighbour(i));
        }

        return neighbours;
    }

    private Neighbour getDefaultNeighbour(long index) {
        return getDefaultNeighbour(index, false);
    }

    private Neighbour getDefaultNeighbour(long index, boolean isFavorite) {
        return new Neighbour(
            index,
            DEFAULT_NAME,
            DEFAULT_AVATAR_URL,
            DEFAULT_ADDRESS,
            DEFAULT_PHONE_NUMBER,
            DEFAULT_ABOUT_ME,
            isFavorite
        );
    }
    // endregion DEFAULTS
}