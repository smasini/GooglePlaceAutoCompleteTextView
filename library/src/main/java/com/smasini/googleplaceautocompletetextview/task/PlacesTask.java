package com.smasini.googleplaceautocompletetextview.task;

import android.os.AsyncTask;
import android.util.Log;
import com.smasini.googleplaceautocompletetextview.GooglePlaceAutoCompleteTextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Project: GooglePlaceAutoCompleteTextView
 * Package: com.smasini.googleplaceautocompletetextview
 * Created by Simone Masini on 24/08/2015 at 21.26.
 */
public class PlacesTask extends AsyncTask<String, Void, String> {

    private ParserTask parserTask;
    private GooglePlaceAutoCompleteTextView autoCompleteTextView;

    public PlacesTask(GooglePlaceAutoCompleteTextView autoCompleteTextView){
        super();
        this.autoCompleteTextView = autoCompleteTextView;
    }

    @Override
    protected String doInBackground(String... place) {
        // For storing data from web service
        String data = "";
        // Obtain browser key from https://code.google.com/apis/console
        String key = "key=" + autoCompleteTextView.getApiKey();//api key - type browser
        String input="";
        try {
            input = "input=" + URLEncoder.encode(place[0], "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String types = "";
        if(autoCompleteTextView.getType()!=null && !autoCompleteTextView.getType().equals(""))
            types = "types=geocode";
        String sensor = "sensor=false";
        if(autoCompleteTextView.isSensor())
            sensor = "sensor=true";
        String language = "";
        if(autoCompleteTextView.getLanguage()!=null && !autoCompleteTextView.getLanguage().equals(""))
            language = "language=" + autoCompleteTextView.getLanguage();
        String params = input + "&" + sensor + "&" + key;
        if(!types.equals(""))
            params += "&"+types;
        if(!language.equals(""))
            params += "&"+language;

        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+params;
        try{
            // Fetching the data from we service
            data = downloadUrl(url);
        }catch(Exception e){
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Creating ParserTask
        parserTask = new ParserTask(autoCompleteTextView);
        // Starting Parsing the JSON string returned by Web Service
        parserTask.execute(result);
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();
        }catch(Exception e){
            Log.d("Exception url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
