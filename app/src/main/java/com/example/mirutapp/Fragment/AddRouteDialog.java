package com.example.mirutapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mirutapp.R;

import java.util.HashSet;
import java.util.Set;

public class AddRouteDialog extends DialogFragment {

    public interface ConnectMapFragment{
        int sendInputs(String url, String routeName, int alarmHour, int alarmMinute, Set<Integer> days);
    }

    public ConnectMapFragment connect;

    //Widgets
    private EditText editTextRouteName, editTextTime;
    private Button buttonCancelar, buttonGuardar;
    private CheckBox checkBoxMonday, checkBoxTuesday, checkBoxWednesday, checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dialog_add_route, container, false);

        //Attaching ids to widgets
        editTextRouteName = view.findViewById(R.id.editTextRouteName);
        editTextTime = view.findViewById(R.id.editTextTime);
        buttonCancelar = view.findViewById(R.id.buttonCancelar);
        buttonGuardar = view.findViewById(R.id.buttonGuardar);
        checkBoxMonday = view.findViewById(R.id.checkBoxMonday);
        checkBoxTuesday = view.findViewById(R.id.checkboxTuesday);
        checkBoxWednesday = view.findViewById(R.id.checkboxWednesday);
        checkBoxThursday = view.findViewById(R.id.checkBoxThursday);
        checkBoxFriday = view.findViewById(R.id.checkBoxFriday);
        checkBoxSaturday = view.findViewById(R.id.checkBoxSaturday);
        checkBoxSunday = view.findViewById(R.id.checkBoxSunday);

        //Cancel Dialog
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Save a route
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<Integer> days = new HashSet<Integer>();
                String routeName = editTextRouteName.getText().toString();
                if(!routeName.equals("")){
                        if(!checkBoxMonday.isChecked() && !checkBoxTuesday.isChecked() && !checkBoxWednesday.isChecked() && !checkBoxThursday.isChecked() && !checkBoxFriday.isChecked() && !checkBoxSaturday.isChecked() && !checkBoxSunday.isChecked()){
                            Toast.makeText(getContext(), "Debe seleccionar al menos un día.", Toast.LENGTH_LONG).show();
                        }else{
                            if(checkBoxMonday.isChecked()){
                                days.add(1);
                            }
                            if(checkBoxTuesday.isChecked()){
                                days.add(2);
                            }
                            if(checkBoxWednesday.isChecked()){
                                days.add(3);
                            }
                            if(checkBoxThursday.isChecked()){
                                days.add(4);
                            }
                            if(checkBoxFriday.isChecked()){
                                days.add(5);
                            }
                            if(checkBoxSaturday.isChecked()){
                                days.add(6);
                            }
                            if(checkBoxSunday.isChecked()){
                                days.add(0);
                            }
                        }
                    //String url, String routeName, int alarmHour, int alarmMinute, Set<Integer> days
                    //Obtener url
                    //Descomponer hora
                    String url = "hola";

                    /*if(connect.sendInputs(url, routeName,) == 1){
                        Toast.makeText(getContext(), "Se guardó", Toast.LENGTH_LONG).show();
                        getDialog().dismiss();
                    }*/
                    Toast.makeText(getContext(), "llego", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Debe ingresar nombre de ruta.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            connect = (ConnectMapFragment) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("AddRouteDialog","ClassCastException in onAttach: "+ e.getMessage());
        }
    }


}