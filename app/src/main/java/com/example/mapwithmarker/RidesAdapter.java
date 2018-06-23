package com.example.mapwithmarker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mapwithmarker.model.Ride;

import java.util.List;

public class RidesAdapter extends BaseAdapter{
    static class ViewHolder {
        TextView car;
        TextView date;
    }

    private List<Ride> data;
    private LayoutInflater layoutInflater;
    private MapsMarkerActivity mainActivity;

    public RidesAdapter(Context context, MapsMarkerActivity mainActivity, List<Ride> data) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mainActivity = mainActivity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            View newView = layoutInflater.inflate(R.layout.ride_list_item, viewGroup, false);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.displayRide(data.get(i));
                    mainActivity.setView(ViewCode.RIDE_STATS);
                }
            });
            ViewHolder holder = new ViewHolder();
            holder.car = (TextView) newView.findViewById(R.id.ride_car_name);
            holder.date = (TextView) newView.findViewById(R.id.ride_date);
            newView.setTag(holder);
            holder.car.setText(data.get(i).getCarBrand() + " " + data.get(i).getCarModel());
            String[] dateTime = data.get(i).getDateFrom().split("T");
            holder.date.setText(dateTime[0] + "\n" + dateTime[1]);
            return newView;
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.car.setText(data.get(i).getCarBrand() + " " + data.get(i).getCarModel());
            String[] dateTime = data.get(i).getDateFrom().split("T");
            holder.date.setText(dateTime[0] + "\n" + dateTime[1]);
        }
        return view;
    }
}
