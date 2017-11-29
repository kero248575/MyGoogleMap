package com.example.auser.mygooglemap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setmMap(0,0,"幾內亞灣");
        // Add a marker in Sydney and move the camera
    }

    void setmMap(double lat,double lng,String title){
        LatLng lating = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(lating).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lating));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        int[] ids = {R.id.pointOfViewSpinner, R.id.spotSpinner, R.id.mapTypeSpinner};
        int[] arrays = {R.array.point_of_view, R.array.spot, R.array.map_type};

        for (int i = 0; i < ids.length; i++) {
            MenuItem item = menu.findItem(ids[i]);
            Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, arrays[i], R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            spinner.setGravity(Gravity.RIGHT);
            spinner.setOnItemSelectedListener(itemSelectedListener);
        }
        return true;
    }
    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position==0)
                return;
            String itemName=((TextView)view).getText().toString();
            Toast.makeText(MapsActivity.this,((TextView)view).getText(),Toast.LENGTH_SHORT).show();
            switch (parent.getId()){
                case R.id.pointOfViewSpinner:
                    switch (position){
                        case 1:
                            setPointOfView(0,16.9F);
                            break;
                        case 2:
                            setPointOfView(60,17);
                            break;

                    }
                    break;

                case R.id.spotSpinner:
                    switch (position){
                        case 1:
                            setmMap(40.6892128,-74.044512,itemName);
                            break;
                        case 2:
                            setmMap(48.8581964,2.2938632,itemName);
                            break;
                        case 3:
                            setmMap(30.8216698,111.0029461,itemName);
                            break;
                    }
                    break;

                case R.id.mapTypeSpinner:
                    switch (position){
                        case 1:
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            break;
                        case 2:
                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            break;
                        case 3:
                            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            break;
                        case 4:
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            break;
                        case 5:
                            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                            break;
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    void setPointOfView(float angle, float level) {
        LatLng latlng = mMap.getCameraPosition().target;//////下面為2D與3D視角切換
        CameraUpdate camUpdate = CameraUpdateFactory.
                newCameraPosition(new CameraPosition.Builder()
                        .target(latlng)
                        .tilt(angle)
                        .zoom(level)///地圖放大縮小最大不能超過20
                        .build());
        mMap.animateCamera(camUpdate);
    }
}


