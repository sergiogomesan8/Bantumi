package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class RestartMatch extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity actividadPrincipal = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtReiniciarPartida))
                .setMessage(getString(R.string.txtRestartDialog))
                .setPositiveButton(
                        getString(R.string.txtRestartDialogYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                actividadPrincipal.restartMatch();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtRestartDialogNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );

        return builder.create();
    }
}
