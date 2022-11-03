package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.utils.InternalMemoryManager;

public class ReloadMatch extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtOpcionRecuperar))
                .setMessage(getString(R.string.txtRestartDialog))
                .setPositiveButton(
                        getString(R.string.txtReloadMatchDialogYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                main.reloadMatch();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtReloadMatchDialogNo),
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
