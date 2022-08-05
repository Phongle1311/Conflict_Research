package example.com.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import example.com.R;

//public class MapFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_map, container, false);
//    }
//}

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    public MapFragment()  {
        getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;

        LatLng vietnam = new LatLng(14.0583, 108.2772); // 14.0583° N, 108.2772° E
        this.googleMap.addMarker(new MarkerOptions().position(vietnam).title("Marker in Vietnam"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(vietnam));

        this.googleMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(latLng.latitude + " : "+ latLng.longitude);
            googleMap.clear();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            googleMap.addMarker(markerOptions);
        });
    }
}