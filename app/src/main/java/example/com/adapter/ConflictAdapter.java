package example.com.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import example.com.R;
import example.com.model.Conflict;

public class ConflictAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    final static int TYPE_ITEM = 0;
    final static int TYPE_LOADING = 1;

    List<Conflict> mConflicts;

    public void setData(List<Conflict> conflicts) {
        this.mConflicts = conflicts;
    }

    @Override
    public int getItemViewType(int position) {
        if (mConflicts != null)
            if (mConflicts.get(position).isLoading())
                return TYPE_LOADING;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (mConflicts == null)
            return 0;
        return mConflicts.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_conflict, parent, false);
            return new ConflictViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loading, parent, false);
        return new LoadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            Conflict conflict = mConflicts.get(position);
            if (conflict == null)
                return;

            ConflictViewHolder conflictViewHolder = (ConflictViewHolder) holder;
//            Log.d("tag", "bind - type item: "+conflict.getConflictName());

            conflictViewHolder.tvConflictName.setText(conflict.getConflictName());
            int totalDeaths = Integer.parseInt(conflict.getDeathsA())
                    + Integer.parseInt(conflict.getDeathsB())
                    + Integer.parseInt(conflict.getDeathsCivilians())
                    + Integer.parseInt(conflict.getDeathsUnknown());
            conflictViewHolder.tvTotalDeaths.setText(String.valueOf(totalDeaths));
            conflictViewHolder.tvCountry.setText(conflict.getCountry());
            conflictViewHolder.tvWhereCoordinates.setText(String.valueOf(conflict.getWhereCoordinates()));
            conflictViewHolder.vToggle.setVisibility(View.GONE);
            conflictViewHolder.setOnClickItemListener();
        }
    }

    public static class ConflictViewHolder extends RecyclerView.ViewHolder{
        final View vItem;
        final TextView tvConflictName;
        final TextView tvTotalDeaths;
        final TextView tvCountry;
        final TextView tvWhereCoordinates;
        final View vToggle;

        public ConflictViewHolder(@NonNull View itemView) {
            super(itemView);

            vItem = itemView;
            tvConflictName = itemView.findViewById(R.id.tv_conflict_name);
            tvTotalDeaths = itemView.findViewById(R.id.tv_total_deaths);
            tvCountry = itemView.findViewById(R.id.tv_country);
            tvWhereCoordinates = itemView.findViewById(R.id.tv_where_coordinates);
            vToggle = itemView.findViewById(R.id.v_toggle);
        }

        public void setOnClickItemListener() {
            vItem.setOnClickListener(view->{
                if (vToggle.getVisibility() == View.VISIBLE) {
                    vToggle.animate()
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    vToggle.setVisibility(View.GONE);
                                }
                            });
                }
                else {
                    vToggle.setAlpha(0.0f);
                    vToggle.setVisibility(View.VISIBLE);
                    vToggle.animate()
                            .alpha(1.0f)
                            .setDuration(300)
                            .setListener(null);
                }

            });
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}