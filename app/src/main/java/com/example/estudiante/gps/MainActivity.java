package com.example.estudiante.gps;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GPSDataContentProvider gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gps = new GPSDataContentProvider();

        if(gps.onCreate()){

            //Esta funcion es para enviarle los parametros de Carlos
            //onLocationChanged(Location loc);


            //Esto es sólo para efectos de prueba:
            ContentValues values = new ContentValues();

            values.put(GPSData.GPSPoint.LONGITUDE,"100");
            values.put(GPSData.GPSPoint.LATITUDE,"200");
            values.put(GPSData.GPSPoint.TIME,"300");

            //esta funcion es para insertar
            getContentResolver().insert(GPSDataContentProvider.CONTENT_URI, values);

            //esta funcion es para consultar
            Cursor c = gps.consultar();


            if (c.moveToFirst()) {
                do{

                    if(c.moveToFirst()){
                        float lat;
                        float longitud;
                        int time;

                        lat = c.getFloat(1);
                        longitud = c.getFloat(2);
                        time = c.getInt(3);

                        Toast.makeText(getApplicationContext(),"Esto es latitud:"+lat+"Esto es longitud:"+longitud+"Esto es time:"+time,Toast.LENGTH_SHORT).show();
                    }


                } while (c.moveToNext());
            }

        }


    }


    public void onLocationChanged( final Location loc){


        Thread th= new Thread() {
            public void run()
            {


                ContentValues values = new ContentValues();

                Double lon = loc.getLongitude();
                Long time = loc.getTime();
                values.put(GPSData.GPSPoint.LONGITUDE, loc.getLongitude());
                values.put(GPSData.GPSPoint.LATITUDE, loc.getLatitude());
                values.put(GPSData.GPSPoint.TIME, loc.getTime());


                //AlarmManager chromtap

                //string del GPS y luego cada 15 minutos se graba en el
                { try
                {
                    while(true)
                    {
                        //Thread.sleep(15000);
                        Thread.sleep(5000);
                        {

                            //Toast.makeText(getApplicationContext(), "Latitude " + loc.getLatitude() + " et longitude " + loc.getLongitude(), Toast.LENGTH_SHORT).show();

                            getContentResolver().insert(GPSDataContentProvider.CONTENT_URI, values);


                        }
                    }

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                }
            }

        };
        th.start();


    }
}
