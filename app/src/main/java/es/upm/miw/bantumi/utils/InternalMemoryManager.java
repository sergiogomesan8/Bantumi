package es.upm.miw.bantumi.utils;

import android.util.Log;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AccessControlContext;

public class InternalMemoryManager {

    protected final String LOG_TAG = "MiW";
    protected final String fileName = "juegoBantumi";

    public void saveMatch(Context context, String tableSemillas) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(this.fileName, Context.MODE_PRIVATE);

            fos.write(tableSemillas.getBytes());
            fos.write('\n');
            fos.close();
            Log.i(LOG_TAG, "Save semillas");
            Log.i(LOG_TAG, tableSemillas);
        } catch (Exception e) {
            Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String reloadMatch(Context context) {
        BufferedReader fin;
        StringBuilder sb = new StringBuilder();
        try {
            fin = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));

            String linea = fin.readLine();
            while(linea != null){
                sb.append(linea).append("\n");
                linea = fin.readLine();
            }
            Log.i(LOG_TAG, String.valueOf(sb));

            fin.close();
            Log.i(LOG_TAG, "Click contenido Fichero -> MOSTRAR fichero");
        } catch (IOException e) {
            Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();

    }
}
