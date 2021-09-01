package fr.delcey.entrevoisins.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import fr.delcey.entrevoisins.R;

public class NeighboursAdapter extends ListAdapter<NeighboursViewStateItem, NeighboursAdapter.ViewHolder> {

    private final OnNeighbourClickedListener listener;

    public NeighboursAdapter(OnNeighbourClickedListener listener) {
        super(new ListNeighbourItemCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.neighbours_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    // Ask your mentor why this class is static ! This is important.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;
        private final TextView nameTextView;
        private final ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatarImageView = itemView.findViewById(R.id.neighbours_item_iv_avatar);
            nameTextView = itemView.findViewById(R.id.neighbours_item_tv_name);
            deleteImageView = itemView.findViewById(R.id.neighbours_item_iv_delete);
        }

        public void bind(NeighboursViewStateItem item, OnNeighbourClickedListener listener) {
            itemView.setOnClickListener(v -> listener.onNeighbourClicked(item.getId()));
            Glide.with(avatarImageView)
                .load(item.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(avatarImageView);
            nameTextView.setText(item.getName());
            deleteImageView.setOnClickListener(v -> listener.onDeleteNeighbourClicked(item.getId()));
        }
    }

    private static class ListNeighbourItemCallback extends DiffUtil.ItemCallback<NeighboursViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull NeighboursViewStateItem oldItem, @NonNull NeighboursViewStateItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NeighboursViewStateItem oldItem, @NonNull NeighboursViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
