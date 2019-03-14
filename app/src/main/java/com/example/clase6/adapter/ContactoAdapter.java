package com.example.clase6.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clase6.R;
import com.example.clase6.entity.Contactos;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoAdapterViewHolder>{

    Context context;
    int item_contactos;
    List<Contactos> list_contactos;

    public ContactoAdapter(Context context, int item_contactos, List<Contactos> list_contactos) {

        this.context = context;
        this.item_contactos = item_contactos;
        this.list_contactos = list_contactos;
    }

    @Override
    public ContactoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contactos, parent, false);
        return new ContactoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactoAdapterViewHolder holder, int position) {

        final Contactos contactos = list_contactos.get(position);

        holder.tv_nombres.setText(contactos.getNombres().toString());
        holder.tv_apellidos.setText(contactos.getApellidos().toString());



    }

    @Override
    public int getItemCount() {
        return list_contactos.size();
    }

    public class ContactoAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nombres,tv_apellidos;
        ImageView img_foto;
        ConstraintLayout contenedor;


        public ContactoAdapterViewHolder(View itemView) {
            super(itemView);

            tv_nombres = itemView.findViewById(R.id.tv_nombres);
            tv_apellidos = itemView.findViewById(R.id.tv_apellidos);
            img_foto = itemView.findViewById(R.id.img_foto);
            contenedor = itemView.findViewById(R.id.contenedor);
        }
    }
}


