package example.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.clustering.ClusterManager;

import example.com.ConflictsViewModel;
import example.com.MyClusterItem;
import example.com.model.Conflict;


public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    ConflictsViewModel viewModel;
    ClusterManager<MyClusterItem> clusterManager;
    Context listener;
    int count = 0;

    public MapFragment()  {
        getMapAsync(this);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.listener = (FragmentActivity) activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        this.googleMap = googleMap;

        setUpClusterer();

        viewModel = new ViewModelProvider(requireActivity()).get(ConflictsViewModel.class);
        // when data is updated, add market to map
        viewModel.getConflictsMutableLiveData().observe(getViewLifecycleOwner(), list -> {
            int newCount = list.size();

            for (int i = count; i < newCount; i++) {
                Conflict conflict = list.get(i);

                // if isn't loading item, add marker
                if (!conflict.isLoading()) {
                    double lat = Double.parseDouble(conflict.getLatitude());
                    double lng = Double.parseDouble(conflict.getLongitude());
                    String title = conflict.getConflictName();
                    String snippet = "add desc ...";

                    if (title.length() > 10) {
                        title = title.substring(0, 10) + "...";
                    }

                    MyClusterItem offsetItem = new MyClusterItem(lat, lng, title, snippet);
                    clusterManager.addItem(offsetItem);

                    count++;
                }
            }
        });
    }

    void setUpClusterer() {
        // sửa lại thành việt nam
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        clusterManager = new ClusterManager<>(listener, googleMap);

        clusterManager.setAnimation(false);

        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager); // nghiên cứu sau
    }
}