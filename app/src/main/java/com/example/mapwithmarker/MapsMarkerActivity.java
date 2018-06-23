package com.example.mapwithmarker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import android.view.Menu;
import android.view.MenuInflater;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.mapwithmarker.model.Ride;
import com.example.mapwithmarker.model.RidesResponse;
import com.example.mapwithmarker.model.TelemetryData;
import com.example.mapwithmarker.model.TelemetryDataResponse;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout drawerLayout;
    private ViewFlipper viewFlipper;
    private Gson gson;
    private GoogleMap googleMap;
    private RequestSender requestSender;
    private MapsMarkerActivity mainActivity;
    private RideStatsView rideStatsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        gson = new GsonBuilder().create();

        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        requestSender = new RequestSender(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        viewFlipper.setDisplayedChild(ViewCode.LOGIN);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        switch (item.getItemId()){
                            case R.id.nav_rides_list:
                                viewFlipper.setDisplayedChild(ViewCode.RIDES);
                                break;
                            case R.id.nav_stats:
                                viewFlipper.setDisplayedChild(ViewCode.STATS);
                                break;
                            case R.id.nav_map:
                                viewFlipper.setDisplayedChild(ViewCode.MAP);
                                //sendRequestForCars();
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PieChart statChart = (PieChart) findViewById(R.id.chart);
        new CustomChart(statChart);

        final Button loginButton = (Button) findViewById(R.id.login_button);
        final EditText loginEdit = (EditText) findViewById(R.id.login_text_edit);
        final EditText passwordEdit = (EditText) findViewById(R.id.password_text_edit);
        final Button newAccountButton = (Button) findViewById(R.id.new_account_button);
        new LoginView(loginButton, newAccountButton, loginEdit, passwordEdit, requestSender, this).init();

        rideStatsView = new RideStatsView(this);
        rideStatsView.init();

        EditText firstNameEdit = (EditText) findViewById(R.id.first_name_text_edit);
        EditText lastNameEdit = (EditText) findViewById(R.id.last_name_text_edit);
        EditText emailEdit = (EditText) findViewById(R.id.email_text_edit);
        EditText registerPasswordEdit = (EditText) findViewById(R.id.register_password_text_edit);
        Button registerButton = (Button) findViewById(R.id.register_button);
        new RegisterView(firstNameEdit, lastNameEdit, emailEdit, registerPasswordEdit, registerButton, requestSender, this).init();

        final Button activationOkButton = (Button) findViewById(R.id.activation_ok_button);
        new ActivationView(activationOkButton, this).init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void toast(VolleyError error){
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            toast(R.string.error_timeout);
        } else if (error instanceof AuthFailureError) {
            toast(R.string.error_auth);
        } else if (error instanceof ServerError) {
            toast(R.string.error_server);
        } else if (error instanceof NetworkError) {
            toast(R.string.error_network);
        } else if (error instanceof ParseError) {
            toast(R.string.error_parse);
        }
    }

    public void toast(int stringRes){
        Toast.makeText(getApplicationContext(),
                getApplicationContext().getString(stringRes),
                Toast.LENGTH_LONG).show();
    }

    public void setView(int view) {
        viewFlipper.setDisplayedChild(view);
    }

    /*public void sendRequestForCars(){
        requestSender.sendRequestForCars(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CarsResponse carsResponse = gson.fromJson(response, CarsResponse.class);
                List<Car> cars = carsResponse.getData();
                googleMap.clear();
                for(Car car : cars){
                    LatLng carPos = new LatLng(car.getLatitude(), car.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(carPos).title(car.getModel()));
                }
                if(!cars.isEmpty()) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cars.get(0).getLatitude(), cars.get(0).getLongitude())));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }*/

    public void startMapRefresher(){
        MapRefresher mapRefresher = new MapRefresher(gson, requestSender, googleMap);
        mapRefresher.start();
    }

    public void sendRequestForRides(){
        requestSender.sendRequestForRides(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                RidesResponse ridesResponse = gson.fromJson(response, RidesResponse.class);
                List<Ride> rides = ridesResponse.getData();
                final ListView listView = (ListView) findViewById(R.id.rides_list);
                RidesAdapter ridesAdapter = new RidesAdapter(getApplicationContext(), mainActivity, rides);
                listView.setAdapter(ridesAdapter);
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void displayRide(Ride ride){
        /*requestSender.sendRequestForTelemetryData(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<TelemetryData> telemetryDataResponse = gson.fromJson(response, ArrayList.class);
                //List<TelemetryData> telemetryData = telemetryDataResponse.getData();
                rideStatsView.setTelemetryData(telemetryDataResponse);
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }},
        ride.getId());*/
        rideStatsView.setRideStats(ride);
    }
}
