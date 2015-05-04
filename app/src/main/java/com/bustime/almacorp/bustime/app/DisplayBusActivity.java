package com.bustime.almacorp.bustime.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bustime.almacorp.bustime.adapters.BusAdapter;
import com.bustime.almacorp.bustime.model.BusTime;
import com.bustime.almacorp.bustime.requests.BusInfoConnector;
import com.bustime.almacorp.bustime.requests.BusRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class DisplayBusActivity extends ActionBarActivity {

    private static String stopCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Intent intent = getIntent();
        stopCode = intent.getStringExtra("code");
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        private void showToast(String text) {
            Context context = getActivity();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        public void doRequest(final View rootView) {
            final ProgressBar load = (ProgressBar) rootView.findViewById(R.id.progressBar);
            load.setVisibility(View.VISIBLE);
            BusRequest busRequest = new BusRequest(getActivity().getString(R.string.url_bus, stopCode),
                    new Response.Listener<List<BusTime>>() {
                        @Override
                        public void onResponse(List<BusTime> response) {
                            ListView list = (ListView) rootView.findViewById(R.id.listView);
                            BusAdapter adapter = new BusAdapter(getActivity(), response);
                            list.setAdapter(adapter);
                            load.setVisibility(View.GONE);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ProgressBar load = (ProgressBar) rootView.findViewById(R.id.progressBar);
                            load.setVisibility(View.GONE);
                            showToast(getString(R.string.error));
                        }
                    }
            );

            BusInfoConnector.getInstance(getActivity()).getRequestQueue().add(busRequest);
        }

        private boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView;

            if (isOnline()) {
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                doRequest(rootView);
            } else {
                rootView = inflater.inflate(R.layout.network_error, container, false);
            }

            return rootView;
        }
    }
}
