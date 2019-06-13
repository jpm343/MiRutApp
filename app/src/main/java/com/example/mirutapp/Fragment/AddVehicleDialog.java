package com.example.mirutapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mirutapp.R;

public class AddVehicleDialog extends DialogFragment {

    //Interface which passes the parameters to the VehicleFragment
    public interface ConnectFragment{
        int sendInput(String patente, String alias);
        int updateInput(String patenteNew, String patenteOld, String alias);
    }

    public ConnectFragment connectFragment;

    //widgets for dialog
    private EditText editTextPatente, editTextAlias;
    private Button buttonGuardar, buttonCancelar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.dialog_add_vehicle, container, false);

        //Texts
        editTextPatente = view.findViewById(R.id.editTextPatente);
        editTextAlias = view.findViewById(R.id.editTextAlias);

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

                if(!inputAlias.equals("")){
                    if(!inputPatente.equals("")){
                        if(connectFragment.sendInput(inputPatente, inputAlias) == 1)
                            getDialog().dismiss();
                    }else{
                        Toast.makeText(getContext(), "Debe ingresar una patente", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Debe ingresar un Nombre.", Toast.LENGTH_LONG).show();
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
