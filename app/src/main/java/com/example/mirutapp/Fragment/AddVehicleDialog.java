package com.example.mirutapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.R;

public class AddVehicleDialog extends DialogFragment {

    //Interface which passes the parameters to the VehicleFragment
    public interface ConnectFragment{
        int sendInput(String patente, String alias, Vehicle.CarType type, boolean selloVerde);
        int updateInput(String patenteNew, String patenteOld, String alias);
    }

    public ConnectFragment connectFragment;

    //widgets for dialog
    private EditText editTextPatente, editTextAlias;
    private Button buttonGuardar, buttonCancelar;
    private RadioButton radioAuto, radioMoto, radioCamion, radioSi ,radioNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.dialog_add_vehicle, container, false);

        //Texts
        editTextPatente = view.findViewById(R.id.editTextPatente);
        editTextAlias = view.findViewById(R.id.editTextAlias);

        //Radio buttons
        radioAuto = view.findViewById(R.id.radio_auto);
        radioMoto = view.findViewById(R.id.radio_moto);
        radioCamion = view.findViewById(R.id.radio_camion);
        radioSi = view.findViewById(R.id.radio_si);
        radioNo = view.findViewById(R.id.radio_no);
        radioMoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    radioSi.setEnabled(false);
                    radioNo.setEnabled(false);
                }else{
                    radioSi.setEnabled(true);
                    radioNo.setEnabled(true);
                }
            }
        });

        //Button Cancelar
        buttonCancelar = view.findViewById(R.id.buttonCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Button Guardar
        buttonGuardar = view.findViewById(R.id.buttonGuardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPatente = editTextPatente.getText().toString();
                String inputAlias = editTextAlias.getText().toString();

                Vehicle.CarType type;
                boolean selloVerde = false;

                if(radioAuto.isChecked()) {
                    type = Vehicle.CarType.AUTO;
                }else if(radioCamion.isChecked()) {
                    type = Vehicle.CarType.CAMION;
                }else if(radioMoto.isChecked()){
                    type = Vehicle.CarType.MOTO;
                    selloVerde = false;
                }else{
                    type = null;
                }

                if(!inputPatente.equals("")){
                    if(!inputAlias.equals("")){
                        if(type != null){
                            if(radioSi.isEnabled() && radioNo.isEnabled()){
                                if(radioSi.isChecked()) {
                                    selloVerde = true;
                                    if(connectFragment.sendInput(inputPatente, inputAlias, type, selloVerde) == 1)
                                        getDialog().dismiss();
                                }else if(radioNo.isChecked()) {
                                    selloVerde = false;
                                    if(connectFragment.sendInput(inputPatente, inputAlias, type, selloVerde) == 1)
                                        getDialog().dismiss();
                                }else
                                    Toast.makeText(getContext(), "Debe seleccionar si es sello verde.", Toast.LENGTH_LONG).show();
                            }else{
                                if(connectFragment.sendInput(inputPatente, inputAlias, type, selloVerde) == 1)
                                    getDialog().dismiss();
                            }
                        }else{
                            Toast.makeText(getContext(), "Debe seleccionar un tipo de vehiculo.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Debe ingresar un nombre.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Debe ingresar una patente.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            connectFragment = (ConnectFragment) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("AddVehicleDialog","ClassCastException in onAttach: "+ e.getMessage());
        }
    }
}
