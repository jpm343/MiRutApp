package com.example.mirutapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mirutapp.Adapter.MyHazardRecyclerViewAdapter;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Incident;
import com.example.mirutapp.Model.Location;
import com.example.mirutapp.R;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HazardFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Incident> incidents = new ArrayList<>();
    RecyclerView recyclerIncident;
   // private HazardViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HazardFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HazardFragment newInstance(int columnCount) {
        HazardFragment fragment = new HazardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //dependency injection
        ((MiRutAppApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hazard_list, container, false);
        recyclerIncident = view.findViewById(R.id.incidentList);
        recyclerIncident.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerIncident.setHasFixedSize(true);

        final MyHazardRecyclerViewAdapter adapter = new MyHazardRecyclerViewAdapter();
        // Set the adapter
        recyclerIncident.setAdapter(adapter);



        List<Incident> incidentsFake = new ArrayList<>();
        String[][] poly= {{"-33.5009345","-70.7100724"},{"-33.5009345","-70.7100724"}};
        Location location = new Location(poly,"Pedro Aguirre Cerda");
        Location location2 = new Location(poly,"General Velásquez");
        Location location3 = new Location(poly,"Exequiel Fernandez");
        Incident incident = new Incident(12,"4:45","465","HAZARD","http://www.uoct.cl/estado_de_transito/cerrado-corredor-de-transporte-publico-en-pedro-aguirre-cerda-al-poniente-altura-lo-errazuriz-por-colision-de-buses-cerrillos/","ONE_DIRECTION","Cerrado corredor de transporte público en Pedro Aguirre Cerda al poniente altura Lo Errázuriz, por colisión de buses (Cerrillos)",location);
        Incident incident2 = new Incident(12,"4:45","465","HAZARD","http://www.uoct.cl/estado_de_transito/semaforo-apagado-en-general-velasquez-vargas-fontecilla-quinta-normal-5/","ONE_DIRECTION","Semáforo apagado en General Velásquez / Vargas Fontecilla  (Quinta Normal)",location2);
        Incident incident3 = new Incident(12,"4:45","465","HAZARD","http://www.uoct.cl/estado_de_transito/semaforo-apagado-en-exequiel-fernandez-las-encinas-nunoa/","ONE_DIRECTION","Semáforo apagado en Exequiel Fernandez / Las Encinas (Ñuñoa)",location3);
        incidentsFake.add(incident);
        incidentsFake.add(incident2);
        incidentsFake.add(incident3);
        adapter.setIncidents(incidentsFake);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
