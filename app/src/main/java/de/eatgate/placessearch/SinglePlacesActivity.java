package de.eatgate.placessearch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ProMarkt on 19.01.2015.
 */
public class SinglePlacesActivity extends Activity {

    GPS gps;
    private ArrayList<Place> places = new ArrayList<Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_place);

        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
            String value = b.getString("name");
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(value);

        }
    }
}
