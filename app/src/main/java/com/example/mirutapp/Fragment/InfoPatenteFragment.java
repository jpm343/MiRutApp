package com.example.mirutapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirutapp.R;
import com.example.mirutapp.ViewModel.InfoPatenteViewModel;
import com.example.mirutapp.WebService.InfoPatenteWebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoPatenteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoPatenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoPatenteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private InfoPatenteWebService webService;
    private String responseBody;

    private OnFragmentInteractionListener mListener;

    private SearchView searchPatente;

    //TextViews
    private TextView textViewPatInfo;
    private TextView textViewMuniInfo;
    private TextView textViewRevTecInfo;
    private TextView textViewRevGasesInfo;
    private TextView textViewEstadoPatenteInfo;

    public InfoPatenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoPatenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoPatenteFragment newInstance(String param1, String param2) {
        InfoPatenteFragment fragment = new InfoPatenteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //test value.
        //String patente = "bbdd12";

        searchPatente = (SearchView) getView().findViewById(R.id.searchViewPatente);
        searchPatente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://mirutapp.herokuapp.com/")
                        .build();
                webService = retrofit.create(InfoPatenteWebService.class);
                Call<ResponseBody> call = webService.getInfoPatente(query);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //prints para debug
                        System.out.println(response.code());
                        try {
                            responseBody = response.body().string();
                            System.out.println(responseBody);
                            assignValues(responseBody);

                        } catch(IOException e) {
                            responseBody = "";
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        responseBody = "";
                    }
                });
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupTextViews(){
        textViewPatInfo = (TextView) getView().findViewById(R.id.textViewPatInfo);
        textViewMuniInfo = (TextView) getView().findViewById(R.id.textViewMuniInfo);
        textViewRevTecInfo = (TextView) getView().findViewById(R.id.textViewRevTecInfo);
        textViewRevGasesInfo = (TextView) getView().findViewById(R.id.textViewRevGasesInfo);
        textViewEstadoPatenteInfo = (TextView) getView().findViewById(R.id.textViewEstadoPatenteInfo);
    }

    //assign values to TextViews
    private void assignValues(String response){
        setupTextViews();
        textViewPatInfo.setText("");
        textViewMuniInfo.setText("");
        textViewRevTecInfo.setText("");
        textViewRevGasesInfo.setText("");
        textViewEstadoPatenteInfo.setText("");
        JSONObject object = stringToJson(response);
        Object status = null;
        try{
            Integer statusCode = (int) object.get("status");
            if(statusCode == 200){
                textViewPatInfo.setText(object.get("patente").toString());
                textViewMuniInfo.setText(object.get("municipalidad").toString());
                textViewRevTecInfo.setText(object.get("Revisión técnica Válida hasta el 31 de mayo de 2019").toString());
                textViewRevGasesInfo.setText(object.get("Revisión de gases Válida hasta el 31 de mayo de 2019").toString());
                textViewEstadoPatenteInfo.setText(object.get("estado_patente").toString());
            }else {
                Toast.makeText(getContext(), "¡Patente no existe! Pruebe con otra patente.", Toast.LENGTH_LONG).show();
            }
        }catch(JSONException j){
            System.out.println("Problem in obtaining status");
        }
    }

    //Converts a string to JSONObject
    private JSONObject stringToJson(String json){
        JSONObject res;
        JSONObject obj = null;
        try{
             res = new JSONObject(json);
             String response = res.get("response").toString();
             obj = new JSONObject(response);
             System.out.println(obj);
        }catch (Throwable t){
            System.out.println("Could not parse string to JSON");
        }
        return obj;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_patente, container, false);
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
