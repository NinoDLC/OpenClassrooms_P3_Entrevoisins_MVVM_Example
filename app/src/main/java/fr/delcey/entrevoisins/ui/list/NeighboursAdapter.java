package fr.delcey.entrevoisins.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import fr.delcey.entrevoisins.databinding.NeighboursItemBinding;

public class NeighboursAdapter extends ListAdapter<NeighboursViewStateItem, NeighboursAdapter.ViewHolder> {

    private final OnNeighbourClickedListener listener;

    public NeighboursAdapter(OnNeighbourClickedListener listener) {
        super(new ListNeighbourItemCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(NeighboursItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    // Ask your mentor why this class is static ! This is important.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NeighboursItemBinding binding;

        public ViewHolder(@NonNull NeighboursItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NeighboursViewStateItem item, OnNeighbourClickedListener listener) {
            itemView.setOnClickListener(v -> listener.onNeighbourClicked(item.getId()));
            Glide.with(binding.neighboursItemImageViewAvatar)
                .load(item.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.neighboursItemImageViewAvatar);
            binding.neighboursItemTextViewName.setText(item.getName());
            binding.neighboursItemImageViewDelete.setOnClickListener(v -> listener.onDeleteNeighbourClicked(item.getId()));
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
