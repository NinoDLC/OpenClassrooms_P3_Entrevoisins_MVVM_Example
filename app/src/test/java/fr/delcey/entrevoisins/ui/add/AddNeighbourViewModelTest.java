package fr.delcey.entrevoisins.ui.add;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.hamcrest.core.StringStartsWith;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.delcey.entrevoisins.LiveDataTestUtils;
import fr.delcey.entrevoisins.data.NeighbourRepository;

@RunWith(MockitoJUnitRunner.class)
public class AddNeighbourViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private NeighbourRepository neighbourRepository;

    private AddNeighbourViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new AddNeighbourViewModel(neighbourRepository);
    }

    @Test
    public void nominal_case_save_button_enabled() {
        // When
        LiveDataTestUtils.observeForTesting(viewModel.getIsSaveButtonEnabledLiveData(), value ->
            // Then
            assertFalse(value)
        );
    }

    @Test
    public void nominal_case_avatar_url() {
        // When
        LiveDataTestUtils.observeForTesting(viewModel.getAvatarUrlLiveData(), value ->
            // Then
            assertThat(value, new StringStartsWith("https://i.pravatar.cc/150?u="))
        );
    }

    @Test
    public void nominal_case_add_button_clicked() {
        // Given
        String name = "name";
        String address = "address";
        String phoneNumber = "phoneNumber";
        String aboutMe = "aboutMe";

        // When
        viewModel.onAddButtonClicked(name, address, phoneNumber, aboutMe);
        LiveDataTestUtils.observeForTesting(viewModel.getCloseActivitySingleLiveEvent(), value -> {
            // Then
            verify(neighbourRepository).addNeighbour(
                eq(name),
                startsWith("https://i.pravatar.cc/150?u="),
                eq(address),
                eq(phoneNumber),
                eq(aboutMe)
            );
            verifyNoMoreInteractions(neighbourRepository);
        });
    }

    @Test
    public void edge_case_on_name_changed() {
        // Given
        String name = "Nino";

        // When
        viewModel.onNameChanged(name);
        LiveDataTestUtils.observeForTesting(viewModel.getIsSaveButtonEnabledLiveData(), value ->
            // Then
            assertTrue(value)
        );
    }

    @Test
    public void edge_case_on_name_changed_empty() {
        // Given
        String name = "";

        // When
        viewModel.onNameChanged(name);
        LiveDataTestUtils.observeForTesting(viewModel.getIsSaveButtonEnabledLiveData(), value ->
            // Then
            assertFalse(value)
        );
    }

    @Test
    public void edge_case_on_name_changed_empty_after_filled() {
        // Given
        String name = "Nino";
        String empty = "";

        // When
        viewModel.onNameChanged(name);
        viewModel.onNameChanged(empty);
        LiveDataTestUtils.observeForTesting(viewModel.getIsSaveButtonEnabledLiveData(), value ->
            // Then
            assertFalse(value)
        );
    }
}