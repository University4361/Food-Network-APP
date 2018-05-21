package com.example.julia.delivery.mainscreen;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julia.delivery.MainActivity;
import com.example.julia.delivery.objects.OrderPreview;
import com.google.android.gms.maps.*;

import com.example.julia.delivery.R;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapTabFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    List<OrderPreview> previewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_tab, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                OrderPreview orderPreview = new OrderPreview();

                orderPreview.setId((int)marker.getTag());

                ((MainActivity)getActivity()).switchToOrderFragment(orderPreview);
                return false;
            }
        });

        if (previewList == null) {
            return;
        }

        for (OrderPreview preview :
                previewList) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(preview.getAddress().getLatitude(), preview.getAddress().getLongitude()))
                    .title(preview.getAddress().getStreet())).setTag(preview.getId());

        }
        /*map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));*/


        /*LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        //Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        Location location = getMyLocation();
        if (location != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }*/
    }

    private Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        return myLocation;
    }

    public void SetupAddresses(List<OrderPreview> previews) {
        if (mMap == null) {
            previewList = previews;
            return;
        }

        for (OrderPreview preview :
                previews) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(preview.getAddress().getLatitude(), preview.getAddress().getLongitude()))
                    .title(preview.getCustomer().getName() + "/n" + preview.getCustomer().getPhoneNumber()));
        }
    }
}
