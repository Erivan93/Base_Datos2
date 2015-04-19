package com.example.alvarado.base_datos2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {


        private EditText numero_control, nombre1, escuela1, edad1,telefono1; //Creamos las variables
        private Cursor fila;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

           //Obtener los datos de los campos
            numero_control = (EditText) findViewById(R.id.et_identificador);
            nombre1 = (EditText) findViewById(R.id.et_nombreyapellido);
            escuela1 = (EditText) findViewById(R.id.et_escuela);
            edad1 = (EditText) findViewById(R.id.et_edad);
            telefono1 =  (EditText) findViewById(R.id.et_telefono);
    }


    public void alta(View v) {
        //Abrimos la conexion De SQLite
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"votantes", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String numero_control1  = numero_control.getText().toString();
        String nombre = nombre1.getText().toString();
        String colegio = escuela1.getText().toString();
        String nromesa = edad1.getText().toString();
        String telefono = telefono1.getText().toString();

        ContentValues registro = new ContentValues();  //es una clase para guardar datos

        registro.put("numero_control", numero_control1);
        registro.put("nombre1", nombre);
        registro.put("escuela", colegio);
        registro.put("edad1", nromesa);
        registro.put("telefono1",telefono);

        bd.insert("votantes", null, registro);
        bd.close();
        numero_control.setText("");
        nombre1.setText("");
        escuela1.setText("");
        edad1.setText("");
        telefono1.setText("");
        Toast.makeText(this, "Se cargaron los datos de la persona",
                Toast.LENGTH_SHORT).show();
    }
     // Codigo para la consulta
    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.

        String numero_control1 = numero_control.getText().toString();
        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select nombre,colegio,nromesa,telefono  from votantes where numero_control1=" + numero_control1, null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            nombre1.setText(fila.getString(0));
            escuela1.setText(fila.getString(1));

            edad1.setText(fila.getString(2));
            telefono1.setText(fila.getString(3));
        } else
            Toast.makeText(this, "No existe una persona con ese numero de control" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }
    //Codigo para dar de baja
    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = numero_control.getText().toString();
        int cant = bd.delete("votantes", "dni=" + dni, null); // (votantes es la nombre de la tabla, condición)
        bd.close();
        numero_control.setText("");
        nombre1.setText("");
        escuela1.setText("");
        edad1.setText("");
        telefono1.setText("");
        if (cant == 1)
            //Mensaje si se borro el registro.
            Toast.makeText(this, "Se borró la persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
        else
        //Mensaje si no se encuen tra el registro.
            Toast.makeText(this, "No existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }
        //Codigo para modificar
    public void modificacion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = numero_control.getText().toString();
        String nombre = nombre1.getText().toString();
        String colegio = escuela1.getText().toString();
        String nromesa = edad1.getText().toString();
        String telefono = telefono1.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("colegio", colegio);
        registro.put("nromesa", nromesa);
        registro.put("telefono", telefono);
        int cant = bd.update("votantes", registro, "numero_control1=" + numero_control, null);
        bd.close();
        if (cant == 1)
            //Mensaje si se modifcaron correctamente los campos
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
        // Mensaje si el campo a modificar no existe
            Toast.makeText(this, "no existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }
}