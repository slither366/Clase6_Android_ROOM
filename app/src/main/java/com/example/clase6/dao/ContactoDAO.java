package com.example.clase6.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.clase6.entity.Contactos;

import java.util.List;

@Dao
public interface ContactoDAO {

    @Insert
    public void insert(Contactos... contactos);

    @Update
    public void update(Contactos... contactos);

    @Delete
    public void delete(Contactos... contactos);

    @Query("select * from contactos")
    public List<Contactos>  getContactos();

    @Query("select * from contactos where codigo = :codigo")
    public Contactos getContactWithId(int codigo);
}
