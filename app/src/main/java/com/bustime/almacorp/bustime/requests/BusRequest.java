package com.bustime.almacorp.bustime.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.bustime.almacorp.bustime.model.BusTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BusRequest extends Request<List<BusTime>> {

    private final Response.Listener<List<BusTime>> listener;

    public BusRequest(String url, Response.Listener<List<BusTime>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(List<BusTime> response) {
        listener.onResponse(response);
    }

    private static List<BusTime> getTimesFromHTML(String html) {
        List<BusTime> busTimes = new ArrayList<BusTime>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("[data-role=listview] li");
        for (Element element : elements) {
            String busName = element.select("div:nth-child(1) b").text();
            int lastIndex = busName.indexOf(" - ");
            if (lastIndex != -1) {
                busName = busName.substring(6, lastIndex);
                String time = element.select("div:nth-child(1) b span:nth-child(1)").text();
                BusTime busTime = new BusTime(busName,time);
                busTimes.add(busTime);
            }
        }
        return busTimes;
    }

    @Override
    protected Response<List<BusTime>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
            return Response.success(getTimesFromHTML(json), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}