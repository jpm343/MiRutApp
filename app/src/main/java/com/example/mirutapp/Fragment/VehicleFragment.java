package com.example.mirutapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mirutapp.Adapter.PatentesRecyclerViewAdapter;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.R;
import com.example.mirutapp.ViewModel.VehicleViewModel;
import com.example.mirutapp.ViewModel.VehicleViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehicleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleFragment extends Fragment implements AddVehicleDialog.ConnectFragment {
    @Inject
    VehicleViewModelFactory viewModelFactory;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VehicleViewModel viewModel;

    private OnFragmentInteractionListener mListener;

    //Important attributes for vehicles
    private ArrayList<String> patenteList = new ArrayList<>();
    private ArrayList<String> aliasList = new ArrayList<>();
    private RecyclerView recyclerView;

    //Attributes for dialogFragment (add vehicle)
    private FloatingActionButton addVehicleButton;

    public VehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehicleFragment newInstance(String param1, String param2) {
        VehicleFragment fragment = new VehicleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //dependency injection
        ((MiRutAppApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vehicle, container, false);

        //Button which opens the dialogFragment
        addVehicleButton = view.findViewById(R.id.addVehicleButton);

        addVehicleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AddVehicleDialog dialog = new AddVehicleDialog();
                dialog.setTargetFragment(VehicleFragment.this,1);
                dialog.show(getFragmentManager(),"AddVehicleDialog");
            }
        });

        //RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewPatentes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Instance the recyclerView adapter
        final PatentesRecyclerViewAdapter prvAdapter = new PatentesRecyclerViewAdapter(patenteList,aliasList,this.getContext());
        recyclerView.setAdapter(prvAdapter);

        //Initialize viewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VehicleViewModel.class);
        viewModel.init();

        //Save all vehicles in the lists and set the recyclerView's adapter
        viewModel.getVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                patenteList.clear();
                aliasList.clear();
                for (Vehicle vehicle: vehicles) {
                    patenteList.add(vehicle.getPatente());
                    aliasList.add(vehicle.getAlias());
                }
                prvAdapter.setInfoList(patenteList,aliasList);
                recyclerView.setAdapter(prvAdapter);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public int sendInput(String patente, String alias, Vehicle.CarType type, boolean selloVerde) {
        //Saves a vehicle
        VehicleViewModel.Status status =viewModel.saveVehicle(patente,alias,type,selloVerde);
        if(status == VehicleViewModel.Status.ERROR){
            Toast.makeText(getContext(), "Error en el formato de la patente. (Ejemplo: AABB12 para auto o camión)", Toast.LENGTH_LONG).show();
            return 0;
        } else if(status == VehicleViewModel.Status.ERROR_MOTO) {
            Toast.makeText(getContext(), "Error en el formato de la patente. (Ejemplo: AA123 ó AAA12 para motos)", Toast.LENGTH_LONG).show();
            return 0;
        }
            return 1;
    }

    @Override
    public int updateInput(String patenteOld, String patenteNew, String alias) {
        VehicleViewModel.Status status = viewModel.updateVehicle(patenteOld,patenteNew,alias);
        if(status == VehicleViewModel.Status.OK){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
