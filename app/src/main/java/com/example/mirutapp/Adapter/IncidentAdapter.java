package com.example.mirutapp.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mirutapp.Fragment.IncidentListDialogFragment;
import com.example.mirutapp.Model.Incident;
import com.example.mirutapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link IncidentListDialogFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.ViewHolder> {

    private List<Incident> incidents = new ArrayList<>();

    @NonNull
    @Override
    public IncidentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_incident_list_dialog_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentAdapter.ViewHolder holder, int position) {
        final Incident currentIncident = incidents.get(position);
        holder.title.setText(currentIncident.getDescription());
        holder.link.setText(currentIncident.getLink());
    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public void setList(List<Incident> incidents) {
        this.incidents = incidents;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView link, title;
        public  ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.idDescription);
            link = view.findViewById(R.id.idLink);
        }
    }
}