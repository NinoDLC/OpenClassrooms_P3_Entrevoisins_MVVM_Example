package fr.delcey.entrevoisins.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.delcey.entrevoisins.MainApplication;
import fr.delcey.entrevoisins.config.BuildConfigResolver;
import fr.delcey.entrevoisins.data.NeighbourRepository;
import fr.delcey.entrevoisins.ui.add.AddNeighbourViewModel;
import fr.delcey.entrevoisins.ui.details.NeighbourDetailViewModel;
import fr.delcey.entrevoisins.ui.list.NeighboursViewModel;

/**
 * Very similar to "DI" class in the MVC project, this class is the "root" of
 * the Dependency Injection for this project.
 * <p>
 * It is a singleton itself (check this pattern on Google) so it is available
 * globally for every ViewModel. Its purpose is to create every ViewModel
 * and inject the correct dependency for each. This pattern is called the
 * factory pattern (you can check this pattern on Google, too).
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                        new NeighbourRepository(
                            new BuildConfigResolver()
                        )
                    );
                }
            }
        }

        return factory;
    }

    // This field inherit the singleton property from the ViewModelFactory : it is scoped to the ViewModelFactory
    // Ask your mentor about DI scopes (Singleton, ViewModelScope, ActivityScope)
    @NonNull
    private final NeighbourRepository neighbourRepository;

    private ViewModelFactory(@NonNull NeighbourRepository neighbourRepository) {
        this.neighbourRepository = neighbourRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NeighboursViewModel.class)) {
            return (T) new NeighboursViewModel(
                neighbourRepository
            );
        } else if (modelClass.isAssignableFrom(AddNeighbourViewModel.class)) {
            return (T) new AddNeighbourViewModel(
                neighbourRepository
            );
        } else if (modelClass.isAssignableFrom(NeighbourDetailViewModel.class)) {
            return (T) new NeighbourDetailViewModel(
                MainApplication.getInstance(),
                neighbourRepository
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}