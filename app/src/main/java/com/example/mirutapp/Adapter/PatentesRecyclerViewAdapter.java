package com.example.mirutapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mirutapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PatentesRecyclerViewAdapter extends RecyclerView.Adapter<PatentesRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> patentesList = new ArrayList<>();
    private ArrayList<String> aliasList = new ArrayList<>();
    private Context mContext;

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
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        System.out.println("SE AGREGO UN NUEVO ELEMENTO AL FRAGMENTO");
        holder.textPatente.setText(patentesList.get(position));
        holder.textAlias.setText(aliasList.get(position));
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textPatente;
        TextView textAlias;
        LinearLayout misPatentesLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPatente = (TextView) itemView.findViewById(R.id.textPatente);
            textAlias = (TextView) itemView.findViewById(R.id.textAlias);
            misPatentesLayout = itemView.findViewById(R.id.misPatentesLayout);
        }
    }
}
