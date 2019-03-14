package com.example.clase6;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.clase6.activity.AgregarContactoActivity;
import com.example.clase6.adapter.ContactoAdapter;
import com.example.clase6.database.AppDatabase;
import com.example.clase6.entity.Contactos;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_contactos;
    FloatingActionButton fab_agregar;

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_contactos = findViewById(R.id.recycler_contactos);
        fab_agregar = findViewById(R.id.fab_agregar);

        database = Room.databaseBuilder(this,AppDatabase.class, "db-contactos")
                .allowMainThreadQueries()
                .build();

        List<Contactos> list_contactos = database.getContactDao().getContactos();

        configurarAdaptador(list_contactos);

        fab_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarContactoActivity.class);
                startActivity(intent);
            }
        });

    }

    public void configurarAdaptador(List<Contactos> list_contactos){
        recycler_contactos.setAdapter(new ContactoAdapter(MainActivity.this,R.layout.item_contactos,list_contactos));
        recycler_contactos.setLayoutManager(new LinearLayoutManager(this));
    }
}
