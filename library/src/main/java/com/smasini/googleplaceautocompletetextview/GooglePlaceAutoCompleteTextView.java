package com.smasini.googleplaceautocompletetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import com.smasini.googleplaceautocompletetextview.task.PlacesTask;
import java.util.HashMap;

/**
 * Project: GooglePlaceAutoCompleteTextView
 * Package: com.smasini.googleplaceautocompletetextview
 * Created by Simone Masini on 24/08/2015 at 20.39.
 */
public class GooglePlaceAutoCompleteTextView extends AutoCompleteTextView {

    private PlacesTask placesTask;
    private String mApiKey;
    private int mTheme;
    private String mLanguage;
    private String mType;
    private boolean mSensor;

    public GooglePlaceAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GooglePlaceAutoCompleteTextView,
                0, 0);

        try {
            mApiKey = a.getString(R.styleable.GooglePlaceAutoCompleteTextView_apiKeyGooglePlace);
            mLanguage = a.getString(R.styleable.GooglePlaceAutoCompleteTextView_languageSuggestions);
            mType = a.getString(R.styleable.GooglePlaceAutoCompleteTextView_placeType);
            mSensor = a.getBoolean(R.styleable.GooglePlaceAutoCompleteTextView_sensorEnabled, false);
            mTheme = a.getInteger(R.styleable.GooglePlaceAutoCompleteTextView_themeDropdownAutoComplete, 0);
        } finally {
            a.recycle();
        }

        this.setThreshold(1);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(placesTask!=null){
                    placesTask.cancel(true);
                }
                placesTask = new PlacesTask(GooglePlaceAutoCompleteTextView.this);
                placesTask.execute(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    /** Returns the place description corresponding to the selected item */
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        /** Each item in the autocompetetextview suggestion list is a hashmap object */
        HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
        return hm.get("description");
    }

    public int getTheme() {
        return mTheme;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public boolean isSensor() {
        return mSensor;
    }

    public String getType() {
        return mType;
    }
}
