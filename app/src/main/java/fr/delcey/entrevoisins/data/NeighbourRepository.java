package fr.delcey.entrevoisins.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.delcey.entrevoisins.config.BuildConfigResolver;

public class NeighbourRepository {

    private final MutableLiveData<List<Neighbour>> neighboursLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public NeighbourRepository(BuildConfigResolver buildConfigResolver) {
        // At startup, when creating repo, if we're in debug mode, add random Neighbours
        if (buildConfigResolver.isDebug()) {
            generateRandomNeighbours();
        }
    }

    public void addNeighbour(
        @NonNull String name,
        @NonNull String avatarUrl,
        @Nullable String address,
        @Nullable String phoneNumber,
        @Nullable String aboutMe
    ) {
        List<Neighbour> neighbours = neighboursLiveData.getValue();

        if (neighbours == null) return;

        neighbours.add(
            new Neighbour(
                maxId++,
                name,
                avatarUrl,
                address,
                phoneNumber,
                aboutMe,
                false
            )
        );

        neighboursLiveData.setValue(neighbours);
    }

    public void deleteNeighbour(long neighbourId) {
        List<Neighbour> neighbours = neighboursLiveData.getValue();

        if (neighbours == null) return;

        for (Iterator<Neighbour> iterator = neighbours.iterator(); iterator.hasNext(); ) {
            Neighbour neighbour = iterator.next();

            if (neighbour.getId() == neighbourId) {
                iterator.remove();
                break;
            }
        }

        neighboursLiveData.setValue(neighbours);
    }

    public void toggleFavoriteNeighbour(long id) {
        List<Neighbour> neighbours = neighboursLiveData.getValue();

        if (neighbours == null) return;

        for (int i = 0; i < neighbours.size(); i++) {
            Neighbour neighbour = neighbours.get(i);
            if (neighbour.getId() == id) {
                neighbours.remove(i);
                neighbours.add(
                    i,
                    // This is ugly to copy unchanged fields in Java but immutability is very important !
                    // Setters are a terrible practice for models outside MVC world.
                    // Ask about your mentor why immutability of models is so important.
                    new Neighbour(
                        neighbour.getId(),
                        neighbour.getName(),
                        neighbour.getAvatarUrl(),
                        neighbour.getAddress(),
                        neighbour.getPhoneNumber(),
                        neighbour.getAboutMe(),
                        !neighbour.isFavorite()
                    )
                );
                break;
            }
        }

        neighboursLiveData.setValue(neighbours);
    }

    public LiveData<List<Neighbour>> getNeighboursLiveData() {
        return neighboursLiveData;
    }

    public LiveData<Neighbour> getNeighbourLiveData(long neighbourId) {
        // We use a Transformation here so whenever the neighboursLiveData changes, the underlying lambda will be called too, and
        // the Neighbour will be re-emitted (with potentially new information like isFavorite set to true or false)

        // This Transformation transforms a List of Neighbours into a Neighbour (matched by its ID)
        return Transformations.map(neighboursLiveData, neighbours -> {
            for (Neighbour neighbour : neighbours) {
                if (neighbour.getId() == neighbourId) {
                    return neighbour;
                }
            }

            return null;
        });
    }

    private void generateRandomNeighbours() {
        addNeighbour(
            "Caroline",
            "https://i.pravatar.cc/150?u=a042581f4e29026704d",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Jack",
            "https://i.pravatar.cc/150?u=a042581f4e29026704e",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Chloé",
            "https://i.pravatar.cc/150?u=a042581f4e29026704f",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Vincent",
            "https://i.pravatar.cc/150?u=a042581f4e29026704a",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Elodie",
            "https://i.pravatar.cc/150?u=a042581f4e29026704b",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Sylvain",
            "https://i.pravatar.cc/150?u=a042581f4e29026704c",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Laetitia",
            "https://i.pravatar.cc/150?u=a042581f4e29026703d",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Dan",
            "https://i.pravatar.cc/150?u=a042581f4e29026703b",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Joseph",
            "https://i.pravatar.cc/150?u=a042581f4e29026704d",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Emma",
            "https://i.pravatar.cc/150?u=a042581f4e29026706d",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Patrick",
            "https://i.pravatar.cc/150?u=a042581f4e29026702d",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
        addNeighbour(
            "Ludovic",
            "https://i.pravatar.cc/150?u=a042581f3e39026702d",
            "Saint-Pierre-du-Mont ; 5km",
            "+33 6 86 57 90 14",
            "Bonjour !\nJe souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !\nJ'aime les jeux de cartes tels la belote et le tarot.."
        );
    }
}
