package com.example.mirutapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mirutapp.Adapter.MyRouteRecyclerViewAdapter;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.R;
import com.example.mirutapp.ViewModel.RouteViewModel;
import com.example.mirutapp.ViewModel.RouteViewModelFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RouteFragment extends Fragment {
    @Inject
    RouteViewModelFactory viewModelFactory;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RouteViewModel viewModel;

    //Important attribute for routes
    private ArrayList<Route> routes = new ArrayList<>();
    private RecyclerView recyclerRoutes;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RouteFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RouteFragment newInstance(int columnCount) {
        RouteFragment fragment = new RouteFragment();
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

        View view = inflater.inflate(R.layout.route_fragment_item_list, container, false);
        recyclerRoutes = view.findViewById(R.id.routeList);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRoutes.setHasFixedSize(true);

        final MyRouteRecyclerViewAdapter adapter = new MyRouteRecyclerViewAdapter();
        recyclerRoutes.setAdapter(adapter);

        /*viewModel = ViewModelProviders.of(this,viewModelFactory).get(RouteViewModel.class);
        viewModel.init();
        viewModel.getRoutes().observe(this, new Observer<List<Route>>() {
            @Override
            public void onChanged(List<Route> routes) {
                adapter.setRoutes(routes);
                recyclerRoutes.setAdapter(adapter);
            }
        });*/
        List<Route> routesFake = new ArrayList<>();
        //FAKE RUTAS
        Set<Integer> days = new HashSet<Integer> ();
        days.add(1);
        days.add(2);
        days.add(0);
        Route ruta = new Route("https:1123","Casa a Trabajo",15,45,days);
        Set<Integer> days2 = new HashSet<Integer> ();
        days2.add(1);
        days2.add(2);
        days2.add(3);
        days2.add(5);
        days2.add(6);
        Route ruta2 = new Route("https:11232323","Casa a casa abuela",22,10,days2);
        routesFake.add(ruta2);
        routesFake.add(ruta);
        adapter.setRoutes(routesFake);
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
        void onFragmentInteraction(Uri uri);
    }
}
