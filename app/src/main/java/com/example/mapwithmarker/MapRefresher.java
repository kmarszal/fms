package com.example.mapwithmarker;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mapwithmarker.model.Car;
import com.example.mapwithmarker.model.CarsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

public class MapRefresher extends Thread {
    private boolean running = true;
    private RequestSender requestSender;
    private GoogleMap googleMap;
    private Gson gson;
    private boolean firstRun = true;

    public MapRefresher(Gson gson, RequestSender requestSender, GoogleMap googleMap){
        this.gson = gson;
        this.googleMap = googleMap;
        this.requestSender = requestSender;
    }

    public void kill() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                requestSender.sendRequestForCars(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CarsResponse carsResponse = gson.fromJson(response, CarsResponse.class);
                        List<Car> cars = carsResponse.getData();
                        googleMap.clear();
                        for(Car car : cars){
                            LatLng carPos = new LatLng(car.getLatitude(), car.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(carPos).title(car.getBrand() + " " + car.getModel()));
                        }
                        if(firstRun) {
                            if (!cars.isEmpty()) {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cars.get(0).getLatitude(), cars.get(0).getLongitude())));
                            }
                            firstRun = false;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Thread.sleep(10 * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
