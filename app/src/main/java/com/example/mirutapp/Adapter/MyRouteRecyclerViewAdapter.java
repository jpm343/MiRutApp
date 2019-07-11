package com.example.mirutapp.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;

import com.example.mirutapp.Fragment.AddRouteDialog;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.R;
import com.example.mirutapp.Fragment.RouteFragment.OnListFragmentInteractionListener;
import com.example.mirutapp.Repository.RouteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRouteRecyclerViewAdapter extends RecyclerView.Adapter<MyRouteRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    //private final OnListFragmentInteractionListener mListener;
    private  List<Route> routes = new ArrayList<>();
    private Context mContext;

    @Inject
    RouteRepository routeRepository;

    public MyRouteRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_fragment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //injection dependencies
        ((MiRutAppApplication) mContext.getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Route currentRoute = routes.get(position);
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

        //Delete button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("¿Estás seguro que quieres eliminar la ruta "+currentRoute.getRouteName()+"?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        routeRepository.deleteRoute(currentRoute.getId());
                        Toast.makeText(mContext, "Ruta eliminada exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Edit button
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRouteDialog dialog = new AddRouteDialog(null,true,currentRoute);
                //dialog.setTargetFragment(MyRouteRecyclerViewAdapter.this,1);
                //dialog.setTargetFragment();
                //FragmentManager manager = ((Activity)mContext).getFragmentManager();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                dialog.show(manager,"addRouteVehicle");
            }
        });

        //View button
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //MapsFragment mf = new MapsFragment();

            }
        });

    }

    @Override
    public int getItemCount() {return routes.size();}

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView routeName, routeHour, routeMinutes, monday, tuesday, wednesday, thursday, friday, saturday, sunday;
        FloatingActionButton deleteButton, editButton, viewButton;
        LinearLayout misRutasLayout;

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
            deleteButton = (FloatingActionButton) itemView.findViewById(R.id.deleteButton);
            editButton = (FloatingActionButton) itemView.findViewById(R.id.editButton);
            viewButton = (FloatingActionButton) itemView.findViewById(R.id.viewButton);
            misRutasLayout = itemView.findViewById(R.id.myRoutesLayout);
        }

    }
}
