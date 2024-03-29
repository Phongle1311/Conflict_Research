package example.com.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.MessageFormat;
import java.util.List;

import example.com.R;
import example.com.model.Conflict;

public class ConflictAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    final static int TYPE_ITEM      = 0;
    final static int TYPE_LOADING   = 1;

    List<Conflict> mConflicts;
    IClickButtonSeeInMap listener;

    public interface IClickButtonSeeInMap {
        void onClickButtonSeeInMap(Conflict conflict);
    }

    public ConflictAdapter(IClickButtonSeeInMap listener) {
        this.listener = listener;
    }

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

            int deathsA         = conflict.getDeathsA();
            int deathsB         = conflict.getDeathsB();
            int deathsCivilians = conflict.getDeathsCivilians();
            int deathsUnknown   = conflict.getDeathsUnknown();
            int totalDeaths     = deathsA + deathsB + deathsCivilians + deathsUnknown;
            String dateStart    = conflict.getDateStart();
            String dateEnd      = conflict.getDateEnd();
            String adm1         = conflict.getAdm1();
            String adm2         = conflict.getAdm2();

            // without this line, recycling dirty views will cause issues
            conflictViewHolder.vItem.setBackgroundResource(R.drawable.normal_border);
            conflictViewHolder.tvConflictName.setText(conflict.getConflictName());
            conflictViewHolder.tvTotalDeaths.setText(String.valueOf(totalDeaths));
            conflictViewHolder.tvCountry.setText(conflict.getCountry());
            conflictViewHolder.tvWhereCoordinates.setText(String.valueOf(conflict
                    .getWhereCoordinates()));
            conflictViewHolder.vToggle.setVisibility(View.GONE);
            conflictViewHolder.tvSideA.setText(conflict.getSideA());
            conflictViewHolder.tvSideB.setText(conflict.getSideB());

            conflictViewHolder.setDataChart(deathsA, deathsB, deathsCivilians, deathsUnknown);

            conflictViewHolder.tvNumberDeathsOfA
                    .setText(MessageFormat.format("{0}", deathsA));
            conflictViewHolder.tvNumberDeathsOfB
                    .setText(MessageFormat.format("{0}", deathsB));
            conflictViewHolder.tvNumberDeathsOfCivilians
                    .setText(MessageFormat.format("{0}", deathsCivilians));
            conflictViewHolder.tvNumberUnknownDeaths
                    .setText(MessageFormat.format("{0}", deathsUnknown));

            if (dateStart.equals(dateEnd))
                conflictViewHolder.tvDate.setText(dateStart);
            else
                conflictViewHolder.tvDate.setText(String.format("%s - %s", dateStart, dateEnd));

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

            conflictViewHolder.btnSeeInMap
                    .setOnClickListener(view -> listener.onClickButtonSeeInMap(conflict));
        }
    }

    public static class ConflictViewHolder extends RecyclerView.ViewHolder{
        final View      vItem;
        final TextView  tvConflictName;
        final TextView  tvTotalDeaths;
        final TextView  tvCountry;
        final TextView  tvWhereCoordinates;
        final View      vToggle;
        final PieChart  pieChart;
        final TextView  tvNumberDeathsOfA;
        final TextView  tvNumberDeathsOfB;
        final TextView  tvNumberDeathsOfCivilians;
        final TextView  tvNumberUnknownDeaths;
        final TextView  tvDate;
        final TextView  tvSideA;
        final TextView  tvSideB;
        final TextView  tvAdm1;
        final TextView  tvAdm2;
        final TextView  tvSourceArticle;
        final TextView  tvSourceOriginal;
        final Button    btnSeeInMap;
        boolean         isSelected;

        public ConflictViewHolder(@NonNull View itemView) {
            super(itemView);

            vItem                       = itemView;
            tvConflictName              = itemView.findViewById(R.id.tv_conflict_name);
            tvTotalDeaths               = itemView.findViewById(R.id.tv_total_deaths);
            tvCountry                   = itemView.findViewById(R.id.tv_country);
            tvWhereCoordinates          = itemView.findViewById(R.id.tv_where_coordinates);
            pieChart                    = itemView.findViewById(R.id.chart_deaths);
            tvNumberDeathsOfA           = itemView.findViewById(R.id.tv_number_deaths_of_a);
            tvNumberDeathsOfB           = itemView.findViewById(R.id.tv_number_deaths_of_b);
            tvNumberDeathsOfCivilians   = itemView.findViewById(R.id.tv_number_deaths_of_civilians);
            tvNumberUnknownDeaths       = itemView.findViewById(R.id.tv_number_unknown_deaths);
            tvDate                      = itemView.findViewById(R.id.tv_date);
            tvSideA                     = itemView.findViewById(R.id.tv_side_A);
            tvSideB                     = itemView.findViewById(R.id.tv_side_B);
            tvAdm1                      = itemView.findViewById(R.id.tv_adm_1);
            tvAdm2                      = itemView.findViewById(R.id.tv_adm_2);
            tvSourceArticle             = itemView.findViewById(R.id.tv_source_article);
            tvSourceOriginal            = itemView.findViewById(R.id.tv_source_original);
            vToggle                     = itemView.findViewById(R.id.v_toggle);
            btnSeeInMap                 = itemView.findViewById(R.id.btn_see_in_map);
            isSelected                  = false;
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

        public void setDataChart(int A, int B, int civilians, int unknown) {
            // without this line, recycling dirty views will cause issues
            pieChart.clearChart();

            pieChart.addPieSlice(
                    new PieModel(
                            "A",
                            A,
                            Color.parseColor("#FFA726")));
            pieChart.addPieSlice(
                    new PieModel(
                            "B",
                            B,
                            Color.parseColor("#66BB6A")));
            pieChart.addPieSlice(
                    new PieModel(
                            "Civilians",
                            civilians,
                            Color.parseColor("#EF5350")));
            pieChart.addPieSlice(
                    new PieModel(
                            "Unknown",
                            unknown,
                            Color.parseColor("#a8006d")));

            pieChart.startAnimation();
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}