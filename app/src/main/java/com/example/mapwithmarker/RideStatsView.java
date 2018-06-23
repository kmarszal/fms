package com.example.mapwithmarker;

import android.widget.TextView;

import com.example.mapwithmarker.model.Ride;
import com.example.mapwithmarker.model.TelemetryData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RideStatsView implements OnMapReadyCallback{
    private MapsMarkerActivity mainActivity;
    private GoogleMap rideMap;
    private TextView distanceText;
    private TextView timeText;
    private TextView costText;

    private TextView startCityText;
    private TextView endCityText;
    private TextView startProvinceText;
    private TextView endProvinceText;

    private TextView registrationText;

    private TextView distanceCostText;
    private TextView timeCostText;
    private TextView stopCostText;

    public RideStatsView(MapsMarkerActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void init() {
        this.distanceText = (TextView) mainActivity.findViewById(R.id.stats_distance);
        this.timeText = (TextView) mainActivity.findViewById(R.id.stats_time);
        this.costText = (TextView) mainActivity.findViewById(R.id.stats_cost);

        this.startCityText = (TextView) mainActivity.findViewById(R.id.stats_start_city);
        this.endCityText = (TextView) mainActivity.findViewById(R.id.stats_end_city);
        this.startProvinceText = (TextView) mainActivity.findViewById(R.id.stats_start_province);
        this.endProvinceText = (TextView) mainActivity.findViewById(R.id.stats_end_province);

        this.registrationText = (TextView) mainActivity.findViewById(R.id.stats_registration_number);

        this.distanceCostText = (TextView) mainActivity.findViewById(R.id.stats_distance_bottom);
        this.timeCostText = (TextView) mainActivity.findViewById(R.id.stats_time_bottom);
        this.stopCostText = (TextView) mainActivity.findViewById(R.id.stats_stop_bottom);
    }

    public void setRideStats(Ride ride){
        distanceText.setText(String.format(Locale.ENGLISH, "%f",ride.getTotalDistance()));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        try {
            if(ride.getDateFrom() != null && ride.getDateTo() != null) {
                Date begin = df.parse(ride.getDateFrom());
                Date end = df.parse(ride.getDateTo());
                long diff = end.getTime() - begin.getTime();
                timeText.setText(String.format("%d h, %d min, %d s",
                        TimeUnit.MILLISECONDS.toHours(diff),
                        TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
                        TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff))));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            timeText.setText(R.string.time_unknown);
        }
        costText.setText("there should be some cost");
        String startCity = ride.getStartAddress().getCity();
        String startProvince = ride.getStartAddress().getProvince();
        String endCity = ride.getEndAddress().getCity();
        String endProvince = ride.getEndAddress().getProvince();
        if(startCity != null) startCityText.setText(startCity.equals("null") ? "" : startCity);
        if(endCity != null) endCityText.setText(endCity.equals("null") ? "" : endCity);
        if(startProvince != null) startProvinceText.setText(startProvince.equals("null") ? "" : startProvince);
        if(endProvince != null) endProvinceText.setText(endProvince.equals("null") ? "" : endProvince);
        registrationText.setText(ride.getCarRegistrationNumber());
    }

    public void setTelemetryData(List<TelemetryData> telemetryData){
        if(telemetryData.size() > 0) {
            LatLng carPosStart = new LatLng(telemetryData.get(0).getLatitude(), telemetryData.get(0).getLongitude());
            rideMap.addMarker(new MarkerOptions().position(carPosStart).title("start"));
            LatLng carPosEnd = new LatLng(telemetryData.get(telemetryData.size() - 1).getLatitude(), telemetryData.get(telemetryData.size() - 1).getLongitude());
            rideMap.addMarker(new MarkerOptions().position(carPosEnd).title("end"));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.rideMap = googleMap;
    }
}
