package de.eatgate.placessearch.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import de.eatgate.placessearch.R;


public class RegisterActivity extends Activity {
    private Button rgBtn;

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
                new MakeRegister().execute("http://localhost:22700/api/WWWBewertungPortal");
            }
        });

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

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            // 4. convert JSONObject to JSON to String
            json = personJSON.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

            Log.e("Asny",result);

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

        }


        @Override
        protected String doInBackground(String... urls) {

            TextView vornameView = (TextView) findViewById(R.id.vorname);
            TextView nachnameView = (TextView) findViewById(R.id.nachname);
            TextView emailView = (TextView) findViewById(R.id.email);
            JSONObject personJSON = new JSONObject();
            try {
                personJSON.put("Vorname", vornameView.getText());
                personJSON.put("Nachname", nachnameView.getText());
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
