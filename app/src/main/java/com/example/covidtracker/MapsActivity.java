package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.textclassifier.ConversationActions;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Geocoder mgeocoder;
    ArrayList<String> countries;
    ArrayList<String> count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        countries=new ArrayList<>();
        count=new ArrayList<>();
        countries.add("United States");
        count.add("10,64,353");
        countries.add("Spain");
        count.add("2,12,917");
        countries.add("Italy");
        count.add("2,03,591");
        countries.add("United Kingdom");
        count.add("1,65,221");
        countries.add("Germany");
        count.add("1,61,539");
        countries.add("France");
        count.add("1,28,442");
        countries.add("Turkey");
        count.add("1,17,589");
        countries.add("Russia");
        count.add("99,399");
        countries.add("Iran");
        count.add("93,657");
        countries.add("China");
        count.add("84,369");
        countries.add("Brazil");
        count.add("79,361");
        countries.add("Canada");
        count.add("51,597");
        countries.add("Belgium");
        count.add("47,859");
        countries.add("Netherlands");
        count.add("38,802");
        countries.add("Peru");
        count.add("33,931");
        countries.add("Switzerland");
        count.add("29,407");
        countries.add("Ecuador");
        count.add("24,675");
        countries.add("Portugal");
        count.add("24,505");
        countries.add("Saudi Arabia");
        count.add("21,402");
        countries.add("Sweden");
        count.add("20,302");
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
        new fetchData(this,mMap).execute();
        mgeocoder = new Geocoder(this, Locale.ENGLISH);
        for(String country:countries){
            try {
                List<Address> addresses = mgeocoder.getFromLocationName (country,1);
                //Toast.makeText(this,addresses.toString(),Toast.LENGTH_SHORT).show();
                Address address = addresses.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(country));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        LatLng india = new LatLng(19.527182, 77.885988);
        mMap.addMarker(new MarkerOptions().position(india).title("India"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }
}
