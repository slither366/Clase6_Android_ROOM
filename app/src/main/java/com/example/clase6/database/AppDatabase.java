package com.example.clase6.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.clase6.dao.ContactoDAO;
import com.example.clase6.entity.Contactos;

@Database(entities = {Contactos.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactoDAO getContactDao();

}
