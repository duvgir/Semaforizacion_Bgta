package com.example.duvangiraldo.semaforizacion;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Duvan Giraldo on 30/04/2017.
 */

public class DBHelper extends SQLiteOpenHelper {




    private static String DB_PATH="/data/data/com.example.duvangiraldo.semaforizacion/databases";
    private static String DB_NAME="Semaforos.db";
    private SQLiteDatabase mydatabase;
    private final Context myContext;


    public DBHelper(Context context){
        super(context,DB_NAME,null,1);
        this.myContext=context;
    }

    public void  CreateDataBase() throws IOException{

        boolean dbExist=checkDataBase();

        if(dbExist){

        }

        else{

            this.getReadableDatabase();
        }




    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDb=null;

        String myPath=DB_PATH+DB_NAME;
        checkDb=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);

        if (checkDb!=null){
            checkDb.close();
        }

        return checkDb!=null ? true :false;
    }


    private void CopyDataBase() throws IOException{

        InputStream myInput=myContext.getAssets().open(DB_NAME);
        String outFileName=DB_PATH+DB_NAME;

        OutputStream myOutput= new FileOutputStream(outFileName);

        byte[] buffer=new byte[1024];
        int length;


        while ((length=myInput.read(buffer))>0){

            myOutput.write(buffer,0,length);

        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public  void Open() throws SQLException{


        try{
            CreateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String myPath=DB_PATH+DB_NAME;

        mydatabase=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);


    }



    @Override
    public synchronized void close() {
        if(mydatabase != null)
            mydatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList ConsultarLatitud(){

        ArrayList<Double> lista=new ArrayList<>();

        SQLiteDatabase mydatabase=this.getWritableDatabase();

        String query="SELECT *FROM semaforos";
        Cursor registros=mydatabase.rawQuery(query,null);

        if(registros.moveToFirst()){

            do{
                lista.add(registros.getDouble(0));
            }while(registros.moveToFirst());


        }



        return lista;

    }
}
