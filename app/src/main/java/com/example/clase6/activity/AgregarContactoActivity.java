package com.example.clase6.activity;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.clase6.R;
import com.example.clase6.dao.ContactoDAO;
import com.example.clase6.database.AppDatabase;
import com.example.clase6.entity.Contactos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarContactoActivity extends AppCompatActivity {

    File directorioImagenes;
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    EditText edt_nombres, edt_apellidos;
    ImageView mPhotoImageView;

    Button btn_tomar_foto, btn_grabar_contacto;
    String mCurrentPhotoPath;

    private Uri photoUri;

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        edt_nombres = findViewById(R.id.edt_nombres);
        edt_apellidos = findViewById(R.id.edt_apellidos);
        mPhotoImageView = findViewById(R.id.img_foto);
        btn_tomar_foto = findViewById(R.id.btn_tomar_foto);
        btn_grabar_contacto = findViewById(R.id.btn_guardar_contacto);

        btn_tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        btn_grabar_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase database = Room.databaseBuilder(AgregarContactoActivity.this,AppDatabase.class, "db-contactos")
                        .allowMainThreadQueries()
                        .build();

                ContactoDAO contactoDAO = database.getContactDao();

                guardarImagenStore();

                Contactos contactos = new Contactos();
                contactos.setNombres(edt_nombres.getText().toString());
                contactos.setApellidos(edt_apellidos.getText().toString());
                contactos.setImagen(directorioImagenes.getAbsolutePath());

                contactoDAO.insert(contactos);

            }
        });

    }

    private void guardarImagenStore() {

        mPhotoImageView.buildDrawingCache();
        Bitmap bitmap = mPhotoImageView.getDrawingCache();

        OutputStream fileOuputStream = null;
        Uri uri;

        try{
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "imagenesguardadas" + File.separator);
            file.mkdir();

            directorioImagenes = new File(file,"charizar.jpg");

            uri = Uri.fromFile(directorioImagenes);

            fileOuputStream = new FileOutputStream(directorioImagenes);

        }catch (Exception ex){

        }

        bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOuputStream);

        try {
            fileOuputStream.flush();
            fileOuputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void checkPermission() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},225);
            }

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},226);
            }

        }else{
            abrirCamara();
        }

    }

    private void abrirCamara() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(photoFile !=null){
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE,"My_Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION,"Photo taken on" + System.currentTimeMillis());
                photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePictureIntent,REQUEST_CODE_TAKE_PHOTO);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK){
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                mPhotoImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
