package com.example.clase6.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "contactos")
public class Contactos
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int codigo;

    @ColumnInfo(name = "nombres")
    private String nombres;

    @ColumnInfo(name = "apellidos")
    private String apellidos;

    @ColumnInfo(name = "imagen")
    private String imagen;

    //@Ignore
    //private String sueldo_calculado;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
