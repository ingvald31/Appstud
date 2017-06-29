package com.example.ingvald.appstud;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ingvald.appstud.model.WebServicesUtils;
import com.example.ingvald.appstud.model.bean.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, LocationListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Button bt_go;
    private Result bar;

    private LocationManager locationMgr;
    private Location location;
    private static final String URL_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private static final String URL_PHOTO_FIN="&key=AIzaSyBm4vkbIXLXNR4iwaheDWJuoc5l2UvuFOs";


    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Minimum (et non égale, c’est Android qui gère) 5 secondes et 200m de difference
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationMgr.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 200, this);
        if (locationMgr.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 200, this);    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }

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

        bt_go = (Button) findViewById(R.id.bt_go);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        bt_go.setOnClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            getLocation();


        }
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);
//        LatLng moi = new LatLng(location.getLatitude(), location.getLongitude());
//        mMap.addMarker(new MarkerOptions().position().title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View  view  = LayoutInflater.from(this).inflate(R.layout.activity_marker_evenement,null);
        Result bar = (Result) marker.getTag();

        ImageView ame_iv = (ImageView) view.findViewById(R.id.ame_iv);

        if (bar != null) {
            Glide.with(view.getContext())
                    .load(URL_PHOTO+ bar.getPhotos().get(0).getPhoto_reference()+URL_PHOTO_FIN)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ame_iv);
        }


        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Result result = (Result) marker.getTag();
        marker.hideInfoWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (mMap != null) {

                mMap.setMyLocationEnabled(true);

                getLocation();

            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == bt_go){
            if(location!=null){
                Intent intent = new Intent(MapsActivity.this, ListViewActivity.class);
                intent.putExtra("long", location.getLongitude()).putExtra("lat",location.getLatitude());
                startActivity(intent);
            }else{
                Toast.makeText(this, "Localisation obligatoire", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 15));

        MonAT monAT = new MonAT();
        monAT.execute();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public class MonAT extends AsyncTask {

        private Exception e;

        @Override
        protected Object doInBackground(Object[] objects) {
            ArrayList<Result> result = null;

            try {
                result = WebServicesUtils.getPlace(location.getLatitude()+","+location.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
                this.e = e;
            }


            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            ArrayList<Result> resultat = (ArrayList<Result>) o;

            if(e != null) {

            }
            else {

                for(int i = 0;i<resultat.size();i++){


                   Result bar = resultat.get(i);


                    MarkerOptions marker = new MarkerOptions();

                    marker.position(new LatLng(bar.getGeometry().getLocation().getLat(),bar.getGeometry().getLocation().getLng()));
                    marker.title(bar.getName());
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker (marker).setTag(bar);
                }
            }



        }
    }
}
