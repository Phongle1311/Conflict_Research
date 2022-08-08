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

            conflictViewHolder.tvConflictName.setText(conflict.getConflictName());
            int totalDeaths = Integer.parseInt(conflict.getDeathsA())
                    + Integer.parseInt(conflict.getDeathsB())
                    + Integer.parseInt(conflict.getDeathsCivilians())
                    + Integer.parseInt(conflict.getDeathsUnknown());
            conflictViewHolder.tvTotalDeaths.setText(String.valueOf(totalDeaths));
            conflictViewHolder.tvCountry.setText(conflict.getCountry());
            conflictViewHolder.tvWhereCoordinates.setText(String.valueOf(conflict.getWhereCoordinates()));
            conflictViewHolder.vToggle.setVisibility(View.GONE);
            conflictViewHolder.tvSideA.setText(conflict.getSideA());
            conflictViewHolder.tvSideB.setText(conflict.getSideB());

            String adm1 = conflict.getAdm1();
            String adm2 = conflict.getAdm2();
            if (adm1 == null || adm1.equals("")) {
                conflictViewHolder.tvAdm1.setVisibility(View.GONE);
            }
            else {
                conflictViewHolder.tvAdm1.setText(String.format("adm1: %s", adm1));
            }
            if (adm2 == null || adm2.equals("")) {
                conflictViewHolder.tvAdm2.setVisibility(View.GONE);
            }
            else {
                conflictViewHolder.tvAdm2.setText(String.format("adm2: %s", adm2));
            }

            conflictViewHolder.tvSourceArticle.setText(conflict.getSourceArticle());
            String sourceOriginal = conflict.getSourceOriginal();
            if (sourceOriginal == null || sourceOriginal.equals("")) {
                conflictViewHolder.tvSourceOriginal.setVisibility(View.GONE);
            }
            else {
                conflictViewHolder.tvSourceOriginal.setText(String.format("(%s)", sourceOriginal));
            }

            conflictViewHolder.setOnClickItemListener();
        }
    }

    public static class ConflictViewHolder extends RecyclerView.ViewHolder{
        final View      vItem;
        final TextView  tvConflictName;
        final TextView  tvTotalDeaths;
        final TextView  tvCountry;
        final TextView  tvWhereCoordinates;
        final View      vToggle;
        final TextView  tvSideA;
        final TextView  tvSideB;
        final TextView  tvAdm1;
        final TextView  tvAdm2;
        final TextView  tvSourceArticle;
        final TextView  tvSourceOriginal;
        boolean         isSelected;

        public ConflictViewHolder(@NonNull View itemView) {
            super(itemView);

            vItem               = itemView;
            tvConflictName      = itemView.findViewById(R.id.tv_conflict_name);
            tvTotalDeaths       = itemView.findViewById(R.id.tv_total_deaths);
            tvCountry           = itemView.findViewById(R.id.tv_country);
            tvWhereCoordinates  = itemView.findViewById(R.id.tv_where_coordinates);
            tvSideA             = itemView.findViewById(R.id.tv_side_A);
            tvSideB             = itemView.findViewById(R.id.tv_side_B);
            tvAdm1              = itemView.findViewById(R.id.tv_adm_1);
            tvAdm2              = itemView.findViewById(R.id.tv_adm_2);
            tvSourceArticle     = itemView.findViewById(R.id.tv_source_article);
            tvSourceOriginal    = itemView.findViewById(R.id.tv_source_original);
            vToggle             = itemView.findViewById(R.id.v_toggle);
            isSelected          = false;
        }

        public void setOnClickItemListener() {
            vItem.setOnClickListener(view->{
                if (isSelected) {
                    vItem.setBackgroundResource(R.drawable.normal_border);
                }
                else {
                    vItem.setBackgroundResource(R.drawable.selected_border);
                }
                isSelected = !isSelected;

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