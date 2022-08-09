package example.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import example.com.ConflictsViewModel;
import example.com.PaginationScrollListener;
import example.com.R;
import example.com.adapter.ConflictAdapter;
import example.com.model.Conflict;

/**
 * <h3>Fragment to show list of conflicts using recyclerview</h3>
 * <ul>
 *     <li>Using pagination scroll listener for better performance</li>
 * </ul>
 */
public class ListFragment extends Fragment {

    FragmentActivity    listener;
    ConflictAdapter     conflictAdapter;
    RecyclerView        rcvConflicts;
    ConflictsViewModel  viewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Activity)) {
            return;
        }
        this.listener = (FragmentActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView   = inflater.inflate(R.layout.fragment_list, container, false);
        rcvConflicts    = rootView.findViewById(R.id.rcv_conflicts);
        viewModel       = new ViewModelProvider(requireActivity()).get(ConflictsViewModel.class);
        conflictAdapter = new ConflictAdapter(this::onClickButtonSeeInMap);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set up for recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(listener);
        rcvConflicts.setLayoutManager(linearLayoutManager);
        rcvConflicts.setAdapter(conflictAdapter);

        // implement abstract class PaginationScrollListener to call API when scroll to end
        rcvConflicts.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            public void loadMoreItems() {
                viewModel.callApiGetConflicts();
            }

            @Override
            public boolean isLoading() {
                return viewModel.isLoading();
            }
        });


        // when conflicts list change, update recyclerview - notify dataset changed
        viewModel.getConflictsMutableLiveData().observe(getViewLifecycleOwner(), list -> {
            if (conflictAdapter != null) {
                int oldSize = conflictAdapter.getItemCount();
                conflictAdapter.setData(list);
                // this line avoid warning:
                //      --- Recyclerview - Cannot call this method in a scroll callback
                rcvConflicts.post(() -> conflictAdapter.notifyItemRangeChanged(oldSize,
                        list.size() - oldSize + 1));
            }
        });

        // call for the first time (no need scrolling)
        viewModel.callApiGetConflicts();
    }

    // release context
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void onClickButtonSeeInMap(Conflict conflict) {
        double latitude = Double.parseDouble(conflict.getLatitude());
        double longitude = Double.parseDouble(conflict.getLongitude());

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        listener.startActivity(intent);
    }
}