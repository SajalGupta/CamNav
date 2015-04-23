package com.example.sajal.camnav;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Sajal on 21-04-2015.
 */
public class MyAlert extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No Places Found");
        builder.setMessage("There might be no points of interest in the walking radius defined OR Bad internet connectivity");
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.setPositiveButton("Change Walking Radius",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), MyPreferencesActivity.class));
            }
        });
        Dialog dialog = builder.create();

        return dialog;
    }
}
