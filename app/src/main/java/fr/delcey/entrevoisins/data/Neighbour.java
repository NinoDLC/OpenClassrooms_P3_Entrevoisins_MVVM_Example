package fr.delcey.entrevoisins.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Neighbour {

    private final long id;
    @NonNull
    private final String name;
    @NonNull
    private final String avatarUrl;
    @Nullable
    private final String address;
    @Nullable
    private final String phoneNumber;
    @Nullable
    private final String aboutMe;

    private final boolean isFavorite;

    public Neighbour(
        long id,
        @NonNull String name,
        @NonNull String avatarUrl,
        @Nullable String address,
        @Nullable String phoneNumber,
        @Nullable String aboutMe,
        boolean isFavorite
    ) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.aboutMe = aboutMe;
        this.isFavorite = isFavorite;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getAboutMe() {
        return aboutMe;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbour neighbour = (Neighbour) o;
        return id == neighbour.id && isFavorite == neighbour.isFavorite && name.equals(neighbour.name) && Objects.equals(avatarUrl, neighbour.avatarUrl) && Objects.equals(address, neighbour.address) && Objects.equals(phoneNumber, neighbour.phoneNumber) && Objects.equals(aboutMe, neighbour.aboutMe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatarUrl, address, phoneNumber, aboutMe, isFavorite);
    }

    @NonNull
    @Override
    public String toString() {
        return "Neighbour{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", avatarUrl='" + avatarUrl + '\'' +
            ", address='" + address + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", aboutMe='" + aboutMe + '\'' +
            ", isFavorite=" + isFavorite +
            '}';
    }
}
