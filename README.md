# GooglePlaceAutoCompleteTextView
Autocomplete textview with the suggestion from the google place

## Usage
Tested on Android Studio.
Import it as a dependency:
```
compile 'com.smasini.googleplaceautocompletetextview:com.smasini.googleplaceautocompletetextview:1.1@aar'
```
In your layout:
```
xmlns:custom="http://schemas.android.com/apk/res-auto"
```
```
<com.smasini.googleplaceautocompletetextview.GooglePlaceAutoCompleteTextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/atv_places"
            android:hint="Address here"
            custom:apiKeyGooglePlace="@string/google_place_api_key"
            custom:languageSuggestions="it"
            custom:sensorEnabled="false"
            custom:placeType="geocode"
            custom:themeDropdownAutoComplete="light_theme"
            />
```
