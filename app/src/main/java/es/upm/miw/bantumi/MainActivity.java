package es.upm.miw.bantumi;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import es.upm.miw.bantumi.model.BantumiViewModel;
import es.upm.miw.bantumi.model.MatchEntity;
import es.upm.miw.bantumi.model.MatchViewModel;
import es.upm.miw.bantumi.utils.InternalMemoryManager;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_POSICIONES = 14;
    int numInicialSemillas;

    protected final String LOG_TAG = "MiW";

    JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;
    MatchViewModel matchViewModel;
    InternalMemoryManager internalMemoryManager;
    SharedPreferences preferences;

    JuegoBantumi.Turno turno;
    private String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia el ViewModel y el juego, y asigna observadores a los huecos

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        savePreferences();
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, turno, numInicialSemillas);
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        internalMemoryManager = new InternalMemoryManager();
        crearObservadores();
    }

    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos   posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
           case R.id.opcPreferences: // @todo Preferencias
                savePreferences();
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            case R.id.opcAjustes:
                Intent intentMatch1 = new Intent(MainActivity.this, AjustesActivity.class);
                startActivityForResult(intentMatch1, 2022);
                break;
            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;

            // @TODO!!! resto opciones
            case R.id.opcReiniciarPartida:
                RestartMatch restartMatchDialog = new RestartMatch();
                restartMatchDialog.show(getSupportFragmentManager(), "frgReiniciar");
                break;

            case R.id.opcGuardarPartida:
                SaveMatch saveMatchDialog = new SaveMatch();
                saveMatchDialog.show(getSupportFragmentManager(), "frgSaveMatch");
                break;

            case R.id.opcRecuperarPartida:
                ReloadMatch reloadMatchDialog = new ReloadMatch();
                reloadMatchDialog.show(getSupportFragmentManager(), "frgReloadMatch");
                break;

            case R.id.opcMejoresResultados:
                Intent intentMatch2 = new Intent(this, MatchActivity.class);
                startActivity(intentMatch2);
                break;

            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    /**
     * Acción que se ejecuta al pulsar sobre un hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        String texto = (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas)
                ? "Gana Jugador 1"
                : "Gana Jugador 2";
        Snackbar.make(
                        findViewById(android.R.id.content),
                        texto,
                        Snackbar.LENGTH_LONG
                )
                .show();

        // @TODO guardar puntuación
        MatchEntity matchEntity = new MatchEntity(name, juegoBantumi.getDate(), juegoBantumi.userScore(), juegoBantumi.opponentScore());
        this.matchViewModel.insert(matchEntity);


        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setName(data.getStringExtra(AjustesActivity.PARAM_NAME));
    }


    protected void setName(String name){
        this.name = name;
    }



    protected void savePreferences(){
        numInicialSemillas = Integer.parseInt(preferences.getString(
                getString(R.string.intSemillas),
                String.valueOf(R.integer.intNumInicialSemillas)));
        boolean switchJugadorInicial = preferences.getBoolean(getString(R.string.keyPrimerTurno), false);
        if(switchJugadorInicial == true){
            turno = JuegoBantumi.Turno.turnoJ2;
        }
        else{
            turno = JuegoBantumi.Turno.turnoJ1;
        }
        if(juegoBantumi != null) {
            juegoBantumi.setNumInicialSemillas(numInicialSemillas);
            juegoBantumi.setTurnoInicial(turno);
        }
    }


    protected void saveMatch(){
        this.internalMemoryManager.saveMatch(this.getApplicationContext(), juegoBantumi.serializa());
    }

    protected void reloadMatch(){
        juegoBantumi.deserializa(this.internalMemoryManager.reloadMatch(this.getApplicationContext()));
    }

    protected void restartMatch() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}