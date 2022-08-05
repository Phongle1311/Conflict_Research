package example.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import example.com.R;
import example.com.adapter.ConflictAdapter;
import example.com.api.ApiService;
import example.com.model.Conflict;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {
    FragmentActivity listener;
    List<Conflict> conflicts;
    ConflictAdapter conflictAdapter;
    RecyclerView rcvConflicts;

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
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        rcvConflicts = rootView.findViewById(R.id.rcv_conflicts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(listener);
        rcvConflicts.setLayoutManager(linearLayoutManager);

        conflicts = new ArrayList<>();
        conflictAdapter = new ConflictAdapter(conflicts);
        rcvConflicts.setAdapter(conflictAdapter);

        callApiGetConflicts();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    void callApiGetConflicts() {
        ApiService.service.getListConflicts("test_ucdp_ged181", "secret", 10, 1).enqueue(new Callback<List<Conflict>>() {
            @Override
            public void onResponse(@NonNull Call<List<Conflict>> call, @NonNull Response<List<Conflict>> response) {
                List<Conflict> list = response.body();
                if (list != null){
                    int oldSize = conflicts.size();
                    conflicts.addAll(list);
                    conflictAdapter.notifyItemRangeChanged(oldSize, list.size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Conflict>> call, @NonNull Throwable t) {
                Toast.makeText(listener, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}