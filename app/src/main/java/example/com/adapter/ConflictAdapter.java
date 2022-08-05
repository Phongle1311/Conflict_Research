package example.com.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import example.com.R;
import example.com.model.Conflict;

public class ConflictAdapter extends RecyclerView.Adapter<ConflictAdapter.ConflictViewHolder>{
    List<Conflict> mConflicts;

    public ConflictAdapter(List<Conflict> conflicts) {
        this.mConflicts = conflicts;
    }

    @NonNull
    @Override
    public ConflictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conflict, parent, false);

        return new ConflictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConflictViewHolder holder, int position) {
        Conflict conflict = mConflicts.get(position);
        if (conflict == null)
            return;

        holder.tvConflictName.setText(conflict.getConflictName());
        int totalDeaths = Integer.parseInt(conflict.getDeathsA())
                + Integer.parseInt(conflict.getDeathsB())
                + Integer.parseInt(conflict.getDeathsCivilians())
                + Integer.parseInt(conflict.getDeathsUnknown());
        holder.tvTotalDeaths.setText(String.valueOf(totalDeaths));
        holder.tvCountry.setText(conflict.getCountry());
        holder.tvWhereCoordinates.setText(String.valueOf(conflict.getWhereCoordinates()));
        holder.vToggle.setVisibility(View.GONE);
        holder.setOnClickItemListener();
    }

    @Override
    public int getItemCount() {
        if (mConflicts == null)
            return 0;
        return mConflicts.size();
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
}
