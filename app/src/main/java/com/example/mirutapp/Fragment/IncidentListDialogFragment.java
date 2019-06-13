package com.example.mirutapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mirutapp.Model.Incident;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mirutapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     IncidentListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link IncidentListDialogFragment.Listener}.</p>
 */
public class IncidentListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private Listener mListener;
    private List<Incident> incidents = new ArrayList<>();
    private RecyclerView recyclerIncidents;

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    // TODO: Customize parameters
    public static IncidentListDialogFragment newInstance(List<Incident> incidents ) {
        final IncidentListDialogFragment fragment = new IncidentListDialogFragment();
        System.out.println("ACA SE VEN LS HAZARDS" + incidents.get(0).getDescription());
        fragment.setIncidents(incidents);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident_list_dialog, container, false);
        recyclerIncidents = view.findViewById(R.id.incidentList);
        recyclerIncidents.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerIncidents.setHasFixedSize(true);
        final ItemAdapter adapter = new ItemAdapter();
        recyclerIncidents.setAdapter(adapter);
        adapter.setIncidents(incidents);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onItemClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView incidentDescription, incidentStreet, incidentLink;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_incident_list_dialog_item, parent, false));
            View view = inflater.inflate(R.layout.fragment_incident_list_dialog_item, parent, false);
            // TODO: Customize the item layout
            incidentDescription = view.findViewById(R.id.idDescription);
            incidentStreet = view.findViewById(R.id.idStreet);
            incidentLink = view.findViewById(R.id.idLink);
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        //private final int mItemCount;
        private  List<Incident> incidents = new ArrayList<>();
        ItemAdapter() {

        }

        public void setIncidents(List<Incident> incidents) {
            this.incidents = incidents;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Incident currentIncident = incidents.get(position);
            holder.incidentDescription.setText(currentIncident.getDescription());
            holder.incidentStreet.setText(currentIncident.getLocation().getStreet());
            holder.incidentLink.setText(currentIncident.getLink());
        }

        @Override
        public int getItemCount() {
            return incidents.size();
        }

    }

}
