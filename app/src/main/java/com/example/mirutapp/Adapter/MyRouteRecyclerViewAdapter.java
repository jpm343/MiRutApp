package com.example.mirutapp.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mirutapp.Model.Route;
import com.example.mirutapp.R;
import com.example.mirutapp.Fragment.RouteFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRouteRecyclerViewAdapter extends RecyclerView.Adapter<MyRouteRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    //private final OnListFragmentInteractionListener mListener;
    private  List<Route> routes = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Route currentRoute = routes.get(position);
        System.out.println(currentRoute.getRouteName());
        holder.routeName.setText(currentRoute.getRouteName());
        holder.routeHour.setText(String.valueOf(currentRoute.getAlarmHour())+":");
        holder.routeMinutes.setText(String.valueOf(currentRoute.getAlarmMinute()));
        if(!currentRoute.getDays().contains(0)){
            holder.sunday.setVisibility(View.GONE);
        }
        if(!currentRoute.getDays().contains(1)){
            holder.monday.setVisibility(View.GONE);
        }
        if(!currentRoute.getDays().contains(2)){
            holder.tuesday.setVisibility(View.GONE);
        }
        if(!currentRoute.getDays().contains(3)){
            holder.wednesday.setVisibility(View.GONE);
        }
        if(!currentRoute.getDays().contains(4)){
            holder.thursday.setVisibility(View.GONE);
        }
        if(!currentRoute.getDays().contains(5)){
            holder.friday.setVisibility(View.GONE);
        }
        if(!currentRoute.getDays().contains(6)){
            holder.saturday.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {return routes.size();}

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView routeName, routeHour, routeMinutes, monday, tuesday, wednesday, thursday, friday, saturday, sunday;

        public ViewHolder(View view) {
            super(view);
            routeName = view.findViewById(R.id.routeName);
            routeHour = view.findViewById(R.id.routeHour);
            routeMinutes = view.findViewById(R.id.routeMinutes);
            monday = view.findViewById(R.id.idMonday);
            tuesday = view.findViewById(R.id.idTuesday);
            wednesday = view.findViewById(R.id.idWednesday);
            thursday = view.findViewById(R.id.idThursday);
            friday = view.findViewById(R.id.idFriday);
            saturday = view.findViewById(R.id.idSaturday);
            sunday = view.findViewById(R.id.idSunday);
        }

    }
}
