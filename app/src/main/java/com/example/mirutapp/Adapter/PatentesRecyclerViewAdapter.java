package com.example.mirutapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mirutapp.Fragment.AddVehicleDialog;
import com.example.mirutapp.LocalDataBase.RevisionTecnica;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Vehicle;
import com.example.mirutapp.R;
import com.example.mirutapp.Repository.VehicleRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.inject.Inject;

public class PatentesRecyclerViewAdapter extends RecyclerView.Adapter<PatentesRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private Context mContext;
    public Dialog updateVehicleDialog;
    public Dialog infoVehicleDialog;
    EditText editTextPatente, editTextAlias;
    RadioButton radioAuto, radioMoto, radioCamion, radioSi, radioNo;
    Button buttonGuardar, buttonCancelar, buttonVolver;
    TextView tvPatente, tvAlias, tvTipo, tvSello, tvRevTec, tvRestriccion;

    @Inject
    VehicleRepository vehicleRepository;

    public PatentesRecyclerViewAdapter(ArrayList<Vehicle> vehiclesList, Context mContext) {
        this.vehiclesList = vehiclesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_patentes_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        //injection dependencies
        ((MiRutAppApplication) mContext.getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        //Initialize update Vehicle Dialog
        updateVehicleDialog = new Dialog(mContext);
        updateVehicleDialog.setContentView(R.layout.dialog_add_vehicle);

        //Texts
        editTextPatente = updateVehicleDialog.findViewById(R.id.editTextPatente);
        editTextAlias = updateVehicleDialog.findViewById(R.id.editTextAlias);

        //Radio buttons
        radioAuto = updateVehicleDialog.findViewById(R.id.radio_auto);
        radioMoto = updateVehicleDialog.findViewById(R.id.radio_moto);
        radioCamion = updateVehicleDialog.findViewById(R.id.radio_camion);
        radioSi = updateVehicleDialog.findViewById(R.id.radio_si);
        radioNo = updateVehicleDialog.findViewById(R.id.radio_no);

        //Buttons
        buttonCancelar = updateVehicleDialog.findViewById(R.id.buttonCancelar);
        buttonGuardar = updateVehicleDialog.findViewById(R.id.buttonGuardar);


        //---------------------
        //Initialize View information of a vehicle dialog
        infoVehicleDialog = new Dialog(mContext);
        infoVehicleDialog.setContentView(R.layout.dialog_info_vehicle);

        //TextViews
        tvPatente = infoVehicleDialog.findViewById(R.id.textViewPatenteInfo);
        tvAlias = infoVehicleDialog.findViewById(R.id.textViewAliasInfo);
        tvTipo = infoVehicleDialog.findViewById(R.id.textViewTipoInfo);
        tvSello = infoVehicleDialog.findViewById(R.id.textViewSelloInfo);
        tvRevTec = infoVehicleDialog.findViewById(R.id.textViewRevTecInfo);
        tvRestriccion = infoVehicleDialog.findViewById(R.id.textViewRestriccionInfo);

        //Button
        buttonVolver = infoVehicleDialog.findViewById(R.id.buttonVolver);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textPatente.setText(vehiclesList.get(position).getPatente());
        holder.textAlias.setText(vehiclesList.get(position).getAlias());

        //Delete Button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("¿Estás seguro que quieres eliminar el vehículo "+vehiclesList.get(position).getPatente().toUpperCase()+"?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vehicleRepository.deleteVehicle(vehiclesList.get(position).getPatente());
                        Toast.makeText(mContext, "Vehículo eliminado exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Edit Button
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVehicleDialog.show();

                //Assign all values
                //EditTexts
                editTextPatente.setText(vehiclesList.get(position).getPatente());
                editTextAlias.setText(vehiclesList.get(position).getAlias());

                //RadioButtons
                switch(vehiclesList.get(position).getType()){
                    case AUTO:
                        radioAuto.setChecked(true);
                        break;
                    case CAMION:
                        radioCamion.setChecked(true);
                        break;
                    case MOTO:
                        radioMoto.setChecked(true);
                        radioSi.setEnabled(false);
                        radioNo.setEnabled(false);
                        break;
                }

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

                if(radioSi.isEnabled() && radioNo.isEnabled()){
                    if(vehiclesList.get(position).hasSelloVerde()){
                        radioSi.setChecked(true);
                    }else{
                        radioNo.setChecked(true);
                    }
                }

                //Buttons
                buttonCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateVehicleDialog.dismiss();
                    }
                });
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
                                            vehicleRepository.updateVehicle(vehiclesList.get(position).getPatente(),inputPatente, inputAlias, type, selloVerde);
                                            updateVehicleDialog.dismiss();
                                        }else if(radioNo.isChecked()) {
                                            selloVerde = false;
                                            vehicleRepository.updateVehicle(vehiclesList.get(position).getPatente(),inputPatente, inputAlias, type, selloVerde);
                                            updateVehicleDialog.dismiss();
                                        }else
                                            Toast.makeText(mContext, "Debe seleccionar si es sello verde.", Toast.LENGTH_LONG).show();
                                    }else{
                                        System.out.println(vehiclesList.get(position).getPatente());
                                        System.out.println(inputPatente);
                                        System.out.println(inputAlias);
                                        System.out.println(type);
                                        System.out.println(selloVerde);
                                        vehicleRepository.updateVehicle(vehiclesList.get(position).getPatente(),inputPatente, inputAlias, type, selloVerde);
                                        updateVehicleDialog.dismiss();
                                    }
                                }else{
                                    Toast.makeText(mContext, "Debe seleccionar un tipo de vehiculo.", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(mContext, "Debe ingresar un nombre.", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(mContext, "Debe ingresar una patente.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Information of a vehicle Button
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoVehicleDialog.show();

                //assign values
                tvPatente.setText(vehiclesList.get(position).getPatente());
                tvAlias.setText(vehiclesList.get(position).getAlias());
                tvTipo.setText(vehiclesList.get(position).getType().toString());
                if(vehiclesList.get(position).hasSelloVerde())
                    tvSello.setText("Si");
                else
                    tvSello.setText("No");


                int month;
                SparseIntArray rules = RevisionTecnica.rules;
                if(vehiclesList.get(position).getType() == Vehicle.CarType.MOTO) {
                    month = rules.keyAt(rules.indexOfValue(Character.getNumericValue(vehiclesList.get(position).getPatente().charAt(4))));
                }else
                    month = rules.keyAt(rules.indexOfValue(Character.getNumericValue(vehiclesList.get(position).getPatente().charAt(5))));
               tvRevTec.setText(getMonthSpanish(month));

                //tvRestriccion = infoVehicleDialog.findViewById(R.id.textViewRestriccionInfo);

                buttonVolver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        infoVehicleDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }

    public String getMonthSpanish(Integer month){
        String mes = null;
        switch (month){
            case 0:
                mes = "enero";
                break;
            case 1:
                mes = "febrero";
                break;
            case 2:
                mes = "marzo";
                break;
            case 3:
                mes = "abril";
                break;
            case 4:
                mes = "mayo";
                break;
            case 5:
                mes = "junio";
                break;
            case 6:
                mes = "julio";
                break;
            case 7:
                mes = "agosto";
                break;
            case 8:
                mes = "septiembre";
                break;
            case 9:
                mes = "octubre";
                break;
            case 10:
                mes = "noviembre";
                break;
            case 11:
                mes = "diciembre";
                break;
        }
        return mes;
    }
    public void setInfoList(ArrayList<Vehicle> vehiclesList){
        this.vehiclesList = vehiclesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textPatente;
        TextView textAlias;
        FloatingActionButton deleteButton, editButton, viewButton;
        LinearLayout misPatentesLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPatente = (TextView) itemView.findViewById(R.id.textPatente);
            textAlias = (TextView) itemView.findViewById(R.id.textAlias);
            deleteButton = (FloatingActionButton) itemView.findViewById(R.id.deleteButton);
            editButton = (FloatingActionButton) itemView.findViewById(R.id.editButton);
            viewButton = (FloatingActionButton) itemView.findViewById(R.id.viewButton);
            misPatentesLayout = itemView.findViewById(R.id.misPatentesLayout);
        }
    }
}
