package fr.delcey.entrevoisins.ui.list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class NeighboursViewStateItem {

    private final long id;
    @NonNull
    private final String name;
    @NonNull
    private final String avatarUrl;

    public NeighboursViewStateItem(long id, @NonNull String name, @NonNull String avatarUrl) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighboursViewStateItem that = (NeighboursViewStateItem) o;
        return id == that.id && name.equals(that.name) && avatarUrl.equals(that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatarUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "NeighboursViewStateItem{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", avatarUrl='" + avatarUrl + '\'' +
            '}';
    }
}
