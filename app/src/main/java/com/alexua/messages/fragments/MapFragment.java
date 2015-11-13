package com.alexua.messages.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexua.messages.R;
import com.alexua.messages.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by olkh on 11/13/2015.
 */
public class MapFragment extends BaseFragment  implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private Circle circle;

    public static final String TAG = "FRAGMENT_MAP";
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(50.449362, 30.479365);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Тут мы живем с солнышком ))"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(50.449362, 30.479365))
                .radius(10000)
                .strokeColor(Color.RED));

        mMap.setMyLocationEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);


    }

    @Override
    public void onMapClick(LatLng point) {

//        mMap.animateCamera(CameraUpdateFactory.newLatLng(point));

    }

    @Override
    public void onMapLongClick(LatLng point) {

        if(circle != null) {
            circle.remove();
        }

        circle = mMap.addCircle(new CircleOptions().center(point).radius(10000).strokeColor(Color.RED));

    }

}
