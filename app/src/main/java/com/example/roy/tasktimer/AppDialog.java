package com.example.roy.tasktimer;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Roy on 7/13/17.
 */

public class AppDialog extends DialogFragment {

    public static final String DIALOG_ID = "id";
    public static final String DIALOG_MESSAGE = "message";
    public static final String DIALOG_POSITIVE_RID = "positive_rid";
    public static final String DIALOG_NEGATIVE_RID = "negative_rid";

    private DialogEvents dialogEvents;

    interface DialogEvents {
        void onPositiveDialogResult(int dialogId, Bundle args);

        void onNegativeDialogResult(int dialogId, Bundle args);

        void onDialogCancelled(int dialogId);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Bundle args = getArguments();
        final int dialogId;
        String messageString;
        int positiveStringId;
        int negativeStringId;

        if (args != null) {
            dialogId = args.getInt(DIALOG_ID);
            messageString = args.getString(DIALOG_MESSAGE);
            if (dialogId == 0 || messageString == null) {
                throw new IllegalArgumentException("DIALOG_ID OR MESSAGE_STRING is missing in the bundle");
            }
            positiveStringId = args.getInt(DIALOG_POSITIVE_RID);
            if (positiveStringId == 0) positiveStringId = R.string.ok;

            negativeStringId = args.getInt(DIALOG_NEGATIVE_RID);
            if (negativeStringId == 0) negativeStringId = R.string.cancel;
        } else {
            throw new IllegalArgumentException("bundle was empty, dialog cannot start");
        }

        builder.setMessage(messageString)
                .setPositiveButton(positiveStringId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (dialogEvents != null)
                            dialogEvents.onPositiveDialogResult(dialogId, args);
                    }
                })
                .setNegativeButton(negativeStringId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (dialogEvents != null)
                            dialogEvents.onNegativeDialogResult(dialogId, args);
                    }
                });

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof DialogEvents)) {
            throw new ClassCastException(context.toString() + "must implement interface DialogEvents");
        }
        dialogEvents = (DialogEvents) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof DialogEvents)) {
            throw new ClassCastException(activity.toString() + "must implement interface DialogEvents");
        }
        dialogEvents = (DialogEvents) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogEvents = null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (dialogEvents != null) {
            dialogEvents.onDialogCancelled(getArguments().getInt(DIALOG_ID));
        }
    }
    
}
