package com.bustime.almacorp.bustime.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.bustime.almacorp.bustime.app.R;


public class DeleteDialogFragment extends DialogFragment  {
	
	public interface DeleteDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog, String key);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	DeleteDialogListener ddListener;

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle dArgs = getArguments();
        final String key = dArgs.getString("key");
        builder.setMessage(R.string.delete_code)
               .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ddListener.onDialogPositiveClick(DeleteDialogFragment.this,key);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ddListener.onDialogNegativeClick(DeleteDialogFragment.this);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            ddListener = (DeleteDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}