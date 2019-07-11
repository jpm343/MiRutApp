package com.example.mirutapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.mirutapp.Adapter.IncidentAdapter;
import com.example.mirutapp.Model.Incident;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mirutapp.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     IncidentListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link OnListFragmentInteractionListener }.</p>
 */
public class IncidentListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private  OnListFragmentInteractionListener mListener;
    List<Incident> incidents = new ArrayList<>();
    LinkedHashSet<Incident> incidentsHash = new LinkedHashSet<>();
    RecyclerView recyclerIncidets;

    // TODO: Customize parameters
    public static IncidentListDialogFragment newInstance(int itemCount) {
        final IncidentListDialogFragment fragment = new IncidentListDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_incident_list_dialog,container,false);
        Bundle bundles = getArguments();
        incidentsHash = (LinkedHashSet<Incident>) bundles.getSerializable("INCIDENTS");
        // Copy incidentHash into incidents
        incidents.addAll(0,incidentsHash);

        //Header

        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.header);
        recyclerIncidets = view.findViewById(R.id.incidentList);
        recyclerIncidets.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerIncidets.setHasFixedSize(true);
        header.attachTo(recyclerIncidets);

        final IncidentAdapter adapter = new IncidentAdapter();
        recyclerIncidets.setAdapter(adapter);
        adapter.setList(incidents);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnListFragmentInteractionListener){
            mListener =  (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Uri uri);

    }

}
