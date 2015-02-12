package de.eatgate.placessearch.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.helpers.ListViewAdapter;


public class RegisterActivity extends Activity {
    private Button rgBtn;

    // private ArrayList<String> selectListe = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);
        TextView vornameView = (TextView) findViewById(R.id.vorname);
        TextView nachnameView = (TextView) findViewById(R.id.nachname);
        TextView emailView = (TextView) findViewById(R.id.email);
        rgBtn = (Button)findViewById(R.id.registerBtn);
        rgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Send ... ", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                new MakeRegister().execute("http://192.168.70.22:80/EatGate/api/WWWBewertungPortal");
            }
        });

        final Spinner spinner =
                (Spinner) findViewById(R.id.sp_gender);
        final int pos = spinner.getSelectedItemPosition();
        final String[] genderStrArr =
                getResources().getStringArray(R.array.gen_werte);
        final String genderStr = genderStrArr[pos];


        // selectListe.add("female");
        // selectListe.add("male");
        //  ListAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, selectListe);
        //  ListView selectView = (ListView) findViewById(R.id.listViewSelect);
        //  selectView.setAdapter(listAdapter);
    }

    public void sendeDaten() {
        final ProgressDialog verlauf = ProgressDialog.show(
                this,
                "Bitte warten...",
                "Daten werden gesendet",
                true, // zeitlich unbeschränkt
                false); // nicht unterbrechbar

        new Thread() {
            public void run() {
                sendeDatenAnServer();
                verlauf.dismiss(); // dialog schließen
            }
        }.start();
    }

    public void sendeDatenAnServer() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String POSTRegister (String url, JSONObject personJSON){
        InputStream inputStream = null;
        String result = "";
        try {
            Log.i("Create HttpClient: ", "wait ...");
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            // convert JSONObject to JSON to String
            json = personJSON.toString();

            // set json to StringEntity
            StringEntity se = new StringEntity(json);

            // set httpPost Entity
            httpPost.setEntity(se);

            // Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        Log.i("Show result: ", result);

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    /**
     *  Helper class for Registration
     */
    private class MakeRegister extends AsyncTask<String, Void, String> {

        public MakeRegister() {

        }

        /**
         * Erledige bevor Ausführen von Task
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("Ausfgeführt", "ja");
        }


        @Override
        protected String doInBackground(String... urls) {

            TextView vornameView = (TextView) findViewById(R.id.vorname);
            TextView nachnameView = (TextView) findViewById(R.id.nachname);
            TextView emailView = (TextView) findViewById(R.id.email);
            JSONObject personJSON = new JSONObject();
            try {
                personJSON.put("Vorname", vornameView.getText());
                personJSON.put("Name", nachnameView.getText());
                personJSON.put("Nickname", "e");
                personJSON.put("Email", emailView.getText());
                personJSON.put("Passwort", "123");
                personJSON.put("Geschlecht", "m");
                personJSON.put("AvartarID", "15");
            }
            catch (JSONException ex) {

            }
            //Toast.makeText(getBaseContext(), "Enter Sir !", Toast.LENGTH_LONG).show();

            return POSTRegister(urls[0],personJSON);
        }


        protected void onPostExecute() {

        }

    }

}
