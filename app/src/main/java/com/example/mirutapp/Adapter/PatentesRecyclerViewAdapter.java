package com.example.mirutapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mirutapp.Fragment.AddVehicleDialog;
import com.example.mirutapp.Fragment.VehicleFragment;
import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.R;
import com.example.mirutapp.Repository.VehicleRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.inject.Inject;

public class PatentesRecyclerViewAdapter extends RecyclerView.Adapter<PatentesRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> patentesList = new ArrayList<>();
    private ArrayList<String> aliasList = new ArrayList<>();
    private Context mContext;
    @Inject
    VehicleRepository vehicleRepository;

    public PatentesRecyclerViewAdapter(ArrayList<String> patentesList, ArrayList<String> aliasList, Context mContext) {
        this.patentesList = patentesList;
        this.aliasList = aliasList;
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

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textPatente.setText(patentesList.get(position));
        holder.textAlias.setText(aliasList.get(position));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                //builder.setTitle();
                builder.setMessage("¿Estás seguro que quieres eliminar el vehículo "+patentesList.get(position)+"?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vehicleRepository.deleteVehicle(patentesList.get(position));
                        Toast.makeText(mContext, "Vehículo eliminado exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AddVehicleDialog dialog = new AddVehicleDialog();
                dialog.setTargetFragment(VehicleFragment.this,1);
                dialog.show(getFragmentManager(),"AddVehicleDialog");*/
                //vehicleRepository.updateVehicle(patentesList.get(position),"hola23","alias nuevo");
            }
        });

        holder.misPatentesLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, aliasList.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return patentesList.size();
    }

    public void setInfoList(ArrayList<String> patentesList, ArrayList<String> aliasList){
       this.patentesList = patentesList;
       this.aliasList = aliasList;
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
