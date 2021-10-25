package com.firstapp.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firstapp.myapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private SupportMapFragment mapFragment;
    private int REQUEST_CODE = 111;
    private ConnectivityManager manager;
    private NetworkInfo networkInfo;
    private Geocoder geocoder;
    private double selectedLat,selectedLng;
    private List<Address> addresses;
    private String selectedAddress;
    public String globalAddress;
    private Marker marker;
    SearchView searchView;
    public String item_name;
    public String cost;
    public String rcode;
    public String gName;
    public int memberId;
    public int billId;
    public String currency;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = findViewById(R.id.sv_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,10);
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    String nAddress = address.getAddressLine(0);
                    globalAddress = nAddress;
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker((new MarkerOptions().position(latLng).title(location)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            selectedLat = latLng.latitude;
                            selectedLng = latLng.longitude;
                            GetAddress(selectedLat,selectedLng);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    CheckConnection();
                                    if(networkInfo.isConnected() && networkInfo.isAvailable()){
                                        selectedLat = latLng.latitude;
                                        selectedLng = latLng.longitude;
                                        GetAddress(selectedLat,selectedLng);
                                    }
                                    else{
                                        Toast.makeText(MapsActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    // Remove the marker
                                    marker.remove();
                                }
                            });
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckConnection(){
        manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();
    }

    public void GetAddress(double mLat,double mLng){
        geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        if(mLat != 0){
            try{
                addresses = geocoder.getFromLocation(mLat,mLng,1);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if(addresses != null){
                String mAddress = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String knownName = addresses.get(0).getSubLocality();
                String country = addresses.get(0).getCountryName();

                selectedAddress = knownName +", " + city + ", " + state + ", " + country;

                

                if(mAddress != null){
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(mLat,mLng);
                    markerOptions.position(latLng).title(selectedAddress);

                    mMap.addMarker(markerOptions).showInfoWindow();

                }

                else{
                    Toast.makeText(this , "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this , "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this , "LatLng Null", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void confirm(View view) {

        Intent addloc = getIntent();
        item_name = addloc.getStringExtra("item_name");
        rcode = addloc.getStringExtra("reqCode");
        cost = addloc.getStringExtra("cost");
        gName = addloc.getStringExtra(GroupListActivity.EXTRA_TEXT_GNAME);
        memberId = addloc.getIntExtra("memberId",-1);
        billId = addloc.getIntExtra("billId",-1);
        currency = addloc.getStringExtra("grpCurrency");


        Intent Confirm = new Intent(MapsActivity.this, AddEditBillActivity.class);
        Confirm.putExtra("location", globalAddress);
        Confirm.putExtra("item_name", item_name );
        Confirm.putExtra("requestCode",Integer.parseInt(rcode));
        Confirm.putExtra("cost", cost );
        Confirm.putExtra(GroupListActivity.EXTRA_TEXT_GNAME,gName);
        Confirm.putExtra("billMemberId",memberId);
        Confirm.putExtra("billId",billId);
        Confirm.putExtra("groupCurrency",currency);




        startActivity(Confirm);

    }
}

