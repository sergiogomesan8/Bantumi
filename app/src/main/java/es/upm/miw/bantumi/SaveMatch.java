package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.MainActivity;
import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.utils.InternalMemoryManager;

public class SaveMatch extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity actividadPrincipal = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtOpcionGuardar))
                .setMessage(getString(R.string.txtSaveMatchDialog))
                .setPositiveButton(
                        getString(R.string.txtSaveMatchDialogYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                actividadPrincipal.saveMatch();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtSaveMatchDialogNo),
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
