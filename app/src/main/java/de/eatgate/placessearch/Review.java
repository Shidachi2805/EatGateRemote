package de.eatgate.placessearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ProMarkt on 21.01.2015.
 */
public class Review {
    private String authorName;
    private String authorUrl;
    private double rating;
    private String text;
    private String time;

    private Review() {

    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Review jsonToReview(JSONObject jsonObject) {
        try {
            Review result = new Review();
            result.setRating(jsonObject.getDouble("rating"));
            result.setText(jsonObject.getString("text"));
            // toDo noch die restlichen Datenfelder
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
