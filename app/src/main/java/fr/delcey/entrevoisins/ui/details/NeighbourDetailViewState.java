package fr.delcey.entrevoisins.ui.details;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class NeighbourDetailViewState {

    @NonNull
    private final String name;
    @NonNull
    private final String avatarUrl;
    @DrawableRes
    private final int starDrawableRes;
    @Nullable
    private final String location;
    @Nullable
    private final String phone;
    @Nullable
    private final String social;
    @Nullable
    private final String about;

    public NeighbourDetailViewState(
        @NonNull String name,
        @NonNull String avatarUrl,
        int starDrawableRes,
        @Nullable String location,
        @Nullable String phone,
        @Nullable String social,
        @Nullable String about
    ) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.starDrawableRes = starDrawableRes;
        this.location = location;
        this.phone = phone;
        this.social = social;
        this.about = about;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getStarDrawableRes() {
        return starDrawableRes;
    }

    @Nullable
    public String getLocation() {
        return location;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getSocial() {
        return social;
    }

    @Nullable
    public String getAbout() {
        return about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighbourDetailViewState that = (NeighbourDetailViewState) o;
        return starDrawableRes == that.starDrawableRes && name.equals(that.name) && avatarUrl.equals(that.avatarUrl) && Objects.equals(location, that.location) && Objects.equals(phone, that.phone) && Objects.equals(social, that.social) && Objects.equals(about, that.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatarUrl, starDrawableRes, location, phone, social, about);
    }

    @NonNull
    @Override
    public String toString() {
        return "NeighbourDetailViewState{" +
            "name='" + name + '\'' +
            ", avatarUrl='" + avatarUrl + '\'' +
            ", starDrawableRes=" + starDrawableRes +
            ", location='" + location + '\'' +
            ", phone='" + phone + '\'' +
            ", social='" + social + '\'' +
            ", about='" + about + '\'' +
            '}';
    }
}
