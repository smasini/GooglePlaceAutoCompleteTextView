package com.smasini.googleplaceautocompletetextview.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;
import com.smasini.googleplaceautocompletetextview.GooglePlaceAutoCompleteTextView;
import com.smasini.googleplaceautocompletetextview.R;
import com.smasini.googleplaceautocompletetextview.parser.PlaceJSONParser;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

/**
 * Project: GooglePlaceAutoCompleteTextView
 * Package: com.smasini.googleplaceautocompletetextview
 * Created by Simone Masini on 24/08/2015 at 21.28.
 */
public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

    private JSONObject jObject;
    private GooglePlaceAutoCompleteTextView autoCompleteTextView;

    public ParserTask(GooglePlaceAutoCompleteTextView autoCompleteTextView){
        super();
        this.autoCompleteTextView = autoCompleteTextView;
    }

    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {
        List<HashMap<String, String>> places = null;
        PlaceJSONParser placeJsonParser = new PlaceJSONParser();
        try{
            jObject = new JSONObject(jsonData[0]);
            // Getting the parsed data as a List construct
            places = placeJsonParser.parse(jObject);
        }catch(Exception e){
            Log.d("Exception", e.toString());
        }
        return places;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> result) {
        String[] from = new String[] { "description" };
        int[] to = new int[] { android.R.id.text1 };
        // Creating a SimpleAdapter for the AutoCompleteTextView
        Context context = autoCompleteTextView.getContext();
        int layout;
        switch (autoCompleteTextView.getTheme()){
            case 0:
                //dark
                layout = R.layout.dropdown_list_item_dark;
                break;
            case 1:
                //light
                layout = R.layout.dropdown_list_item_light;
                break;
            default:
                layout = R.layout.dropdown_list_item_light;
        }
        SimpleAdapter adapter = new SimpleAdapter(context, result, layout, from, to);
        // Setting the adapter
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.showDropDown();
    }
}
