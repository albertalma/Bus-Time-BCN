package com.bustime.almacorp.bustime.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class AddStopActivity extends ActionBarActivity {

    private static final String PREFS_NAME = "Bus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * Called when the user clicks the Save button
     */
    public void onSave(View view) {
        saveStop(view.getRootView());
    }

    /**
     * Called when the user clicks the Cancel button
     */
    public void onCancel(View view) {
        finish();
    }

    private void showToast(String text) {
        Context context = this;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void saveStop(View view) {
        EditText name = (EditText) view.findViewById(R.id.text_name);
        EditText code = (EditText) view.findViewById(R.id.text_code);
        String stringName = name.getText().toString();
        String stringCode = code.getText().toString();
        if (stringName.equals("")) showToast(getResources().getString(R.string.emptyName));
        else if (stringCode.equals("")) showToast(getResources().getString(R.string.emptyCode));
        else {
            SharedPreferences codeList = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = codeList.edit();
            editor.putString(stringName, stringCode);
            editor.commit();
            finish();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.add_stop, container, false);

            return rootView;
        }
    }
}
