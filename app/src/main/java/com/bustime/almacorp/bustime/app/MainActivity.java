package com.bustime.almacorp.bustime.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bustime.almacorp.bustime.adapters.ListAdapter;
import com.bustime.almacorp.bustime.dialogs.DeleteDialogFragment;

import java.util.Map;


public class MainActivity extends ActionBarActivity implements DeleteDialogFragment.DeleteDialogListener {

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

    private void openAddActivity() {
        Intent intent = new Intent(this, AddStopActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_new:
                openAddActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String key) {
        SharedPreferences codeList = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = codeList.edit();
        editor.remove(key);
        editor.commit();
        getSupportFragmentManager().findFragmentById(R.id.container).onResume();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //Only cancel
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String PREFS_NAME = "Bus";
        private View rootView;

        public PlaceholderFragment() {
        }

        private void setOnLongClickListener(ListView listView, final ListAdapter adapter) {
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    Map.Entry<String, String> item = adapter.getItem(position);
                    DialogFragment dialog = new DeleteDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("key", item.getKey());
                    dialog.setArguments(args);
                    dialog.show(getActivity().getSupportFragmentManager(), "DeleteDialogFragment");
                    return true;
                }
            });

        }

        private void setOnClickListener(ListView list, final ListAdapter adapter) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map.Entry<String, String> item = adapter.getItem(position);
                    Intent intent = new Intent(getActivity(), DisplayBusActivity.class);
                    intent.putExtra("code", item.getValue());
                    startActivity(intent);
                }
            });
        }

        private void initializeListView(Map<String, ?> mapBus) {
            ListView list = (ListView) rootView.findViewById(R.id.listView);
            ListAdapter adapter = new ListAdapter(getActivity(), mapBus);
            list.setAdapter(adapter);
            setOnClickListener(list, adapter);
            setOnLongClickListener(list, adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences codeList = getActivity().getSharedPreferences(PREFS_NAME, 0);
            Map<String, ?> mapBus = codeList.getAll();
            initializeListView(mapBus);
        }
    }
}
