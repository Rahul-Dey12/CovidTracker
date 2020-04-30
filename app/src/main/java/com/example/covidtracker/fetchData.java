package com.example.covidtracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;

import com.example.covidtracker.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data;
    String  datParsed;
    ArrayList<String> countries;
    Context context;
    ProgressDialog mProgressDialog;
    GoogleMap mMap;
    Geocoder mgeocoder;
    fetchData(Context context, GoogleMap googleMap){
        this.context=context;
        this.mMap=googleMap;
        this.mgeocoder = new Geocoder(context, Locale.ENGLISH);
        this.countries = new ArrayList<String>();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.covid19api.com/countries");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            data = stringBuilder.toString();
            JSONArray mjsonArray= new JSONArray(data);
            for(int i=0;i<mjsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) mjsonArray.get(i);
                countries.add(jsonObject.get("Country").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        for(int i=0;i<20;i++){
            List<Address> addresses=null;
            //Toast.makeText(context,countries.get(i),Toast.LENGTH_SHORT).show();
            try {
                addresses = mgeocoder.getFromLocationName(countries.get(i), 1);
                //Toast.makeText(context,countries.get(i),Toast.LENGTH_SHORT).show();
            }catch (IOException e) {

                e.printStackTrace();
            }
            if(addresses.size()>0){
                Address address = addresses.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                //Toast.makeText(context,""+lat+" "+lng,Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(countries.get(i)));}
//            }
        }
        //Toast.makeText(context,countries.size(),Toast.LENGTH_SHORT).show();
        LatLng india = new LatLng(19.527182, 77.885988);
        mMap.addMarker(new MarkerOptions().position(india).title("India"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        mProgressDialog.dismiss();
    }
}
