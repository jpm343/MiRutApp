package com.example.mirutapp.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mirutapp.Fragment.HazardFragment.OnListFragmentInteractionListener;
import com.example.mirutapp.Model.Incident;
import com.example.mirutapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHazardRecyclerViewAdapter extends RecyclerView.Adapter<MyHazardRecyclerViewAdapter.ViewHolder> {

    private List<Incident> incidents = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hazard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Incident incident = incidents.get(position);
        holder.iDescription.setText(incident.getDescription());
        holder.iStreet.setText(incident.getLocation().getStreet());
        holder.iLink.setText(incident.getLink());
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView iDescription, iLink, iStreet;

        public ViewHolder(View view) {
            super(view);
            iDescription = view.findViewById(R.id.idDescription);
            iLink = view.findViewById(R.id.idLink);
            iStreet = view.findViewById(R.id.idStreet);
        }

    }
}
