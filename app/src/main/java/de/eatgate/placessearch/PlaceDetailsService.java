package de.eatgate.placessearch;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ProMarkt on 19.01.2015.
 */
public class PlaceDetailsService {

    private final String place_id;
    private String API_KEY;

    public PlaceDetailsService(String apikey, String place_id) {
        this.API_KEY = apikey;
        this.place_id = place_id;
    }

    public void setApiKey(String apikey) {
        this.API_KEY = apikey;
    }

    /**
     * Fuehrt die Google-Place-Detail-Anfrage aus
     * @return Objekt vom Typ PlaceDetails, enthaelt alle Infos der Google-Place-Detail-Anfrage
     */
    public PlaceDetails findPlaceDetails() {

        String urlString = makeUrl();

        try {
            String json = getJSON(urlString);

            System.out.println(json);
            JSONObject object = new JSONObject(json);
            JSONObject array = object.getJSONObject("result");

            PlaceDetails pDetails = new PlaceDetails();

            try {
               pDetails = PlaceDetails.jsonToPlaceDetails((JSONObject) array);
               // Log.v("PlacesDetailsServices ", "name: " + pDetails.getName());
               // Log.e("PlacesDetailsService ", "Rating: " + pDetails.getRating());

            } catch (Exception e) {
               Log.e("Fehler (gefangen), ", "JSON to PlaceDetails");
            }
            return pDetails;

        } catch (JSONException ex) {
            Logger.getLogger(PlaceDetailsService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }

    // https://maps.googleapis.com/maps/api/place/search/json?location=28.632808,77.218276&radius=500&types=atm&sensor=false&key=apikey
    private String makeUrl() {
        StringBuilder urlString = new StringBuilder(
                "https://maps.googleapis.com/maps/api/place/details/json?");
        urlString.append("placeid=");
        urlString.append(place_id);
        urlString.append("&key=" + API_KEY);

        return urlString.toString();
    }

    protected String getJSON(String url) {
        return getUrlContents(url);
    }

    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()), 8);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            Log.e("Read Content:, ", "Exception");
            e.printStackTrace();
        }
        return content.toString();
    }
}