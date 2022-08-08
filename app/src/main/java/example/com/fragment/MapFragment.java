package example.com.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import example.com.ConflictsViewModel;
import example.com.R;
import example.com.model.Conflict;

//public class MapFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_map, container, false);
//    }
//}

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    ConflictsViewModel viewModel;
    MarkerOptions options = new MarkerOptions();
    int count = 0;

    public MapFragment()  {
        getMapAsync(this);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        this.googleMap = googleMap;

        viewModel = new ViewModelProvider(requireActivity()).get(ConflictsViewModel.class);
        // when data is updated, add market to map
        viewModel.getConflictsMutableLiveData().observe(getViewLifecycleOwner(), list -> {
            int newCount = list.size();

            for (int i = count; i < newCount; i++) {
                Conflict conflict = list.get(i);

                // if isn't loading item, add marker
                if (!conflict.isLoading()) {
                    LatLng point = new LatLng(Float.parseFloat(conflict.getLatitude()),
                            Float.parseFloat(conflict.getLongitude()));
                    options.position(point);
                    String title = conflict.getConflictName();
                    if (title.length() > 10) {
                        title = title.substring(0, 10) + "...";
                    }
                    options.title(title);
                    options.snippet("add desc ...");
                    googleMap.addMarker(options);
                    count++;
                }
            }
        });

//        LatLng vietnam = new LatLng(14.0583, 108.2772);
//        this.googleMap.addMarker(new MarkerOptions().position(vietnam).title("Marker in Vietnam"));
//        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(vietnam));
//
//        this.googleMap.setOnMapClickListener(latLng -> {
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title(latLng.latitude + " : "+ latLng.longitude);
//            this.googleMap.clear();
//            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//            this.googleMap.addMarker(markerOptions);
//        });
    }
}