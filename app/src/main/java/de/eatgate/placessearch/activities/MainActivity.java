package de.eatgate.placessearch.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


import de.eatgate.placessearch.R;
import de.eatgate.placessearch.entities.GPS;
import de.eatgate.placessearch.entities.Place;
import de.eatgate.placessearch.helpers.ListViewAdapter;
import de.eatgate.placessearch.services.PlacesService;


public class MainActivity extends ActionBarActivity {
//    private static final String API_KEY = "AIzaSyCi2JeBSkQ8RugIVb-BA5bvgbDEF9G-zto";
//    GPS gps;
//    private String types = "hospital";
//    private String radius = "1000.0";
//    private final String TAG = getClass().getSimpleName();
//    private GoogleMap mMap;
//    private String[] places;
//    private LocationManager locationManager;
//    private Location loc;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initCompo();
//        places = getResources().getStringArray(R.array.places);
//        currentLocation();
//        final ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        actionBar.setListNavigationCallbacks(ArrayAdapter.createFromResource(
//                        this, R.array.places, android.R.layout.simple_list_item_1),
//                new ActionBar.OnNavigationListener() {
//
//                    @Override
//                    public boolean onNavigationItemSelected(int itemPosition,
//                                                            long itemId) {
//                        Log.e(TAG,
//                                places[itemPosition].toLowerCase().replace("-",
//                                        "_"));
//                        if (loc != null) {
//                            mMap.clear();
//                            new GetPlaces(MainActivity.this,
//                                    places[itemPosition].toLowerCase().replace(
//                                            "-", "_").replace(" ", "_")).execute();
//                        }
//                        return true;
//                    }
//
//                });
//
//    }
//
//    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {
//
//        private ProgressDialog dialog;
//        private Context context;
//        private String places;
//
//        public GetPlaces(Context context, String places) {
//            this.context = context;
//            this.places = places;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Place> result) {
//            super.onPostExecute(result);
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//            for (int i = 0; i < result.size(); i++) {
//                mMap.addMarker(new MarkerOptions()
//                        .title(result.get(i).getName())
//                        .position(
//                                new LatLng(result.get(i).getLatitude(), result
//                                        .get(i).getLongitude()))
//                        .icon(BitmapDescriptorFactory
//                                .fromResource(R.drawable.mark_red))
//                        .snippet(result.get(i).getVicinity()));
//            }
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(new LatLng(result.get(0).getLatitude(), result
//                            .get(0).getLongitude())) // Sets the center of the map to
//                            // Mountain View
//                    .zoom(14) // Sets the zoom
//                    .tilt(30) // Sets the tilt of the camera to 30 degrees
//                    .build(); // Creates a CameraPosition from the builder
//            mMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(context);
//            dialog.setCancelable(false);
//            dialog.setMessage("Loading..");
//            dialog.isIndeterminate();
//            dialog.show();
//        }
//
//        @Override
//        protected ArrayList<Place> doInBackground(Void... arg0) {
//            PlacesService service = new PlacesService(API_KEY,radius,types);
//            ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808
//                    loc.getLongitude()); // 77.218276
//
//            for (int i = 0; i < findPlaces.size(); i++) {
//
//                Place placeDetail = findPlaces.get(i);
//                Log.e(TAG, "places : " + placeDetail.getName());
//            }
//            return findPlaces;
//        }
//
//    }
//
//    private void initCompo() {
//        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
//                .getMap();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    private void currentLocation() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        String provider = locationManager
//                .getBestProvider(new Criteria(), false);
//
//        Location location = locationManager.getLastKnownLocation(provider);
//
//        if (location == null) {
//            locationManager.requestLocationUpdates(provider, 0, 0, listener);
//        } else {
//            loc = location;
//            new GetPlaces(MainActivity.this, places[0].toLowerCase().replace(
//                    "-", "_")).execute();
//            Log.e(TAG, "location : " + location);
//        }
//
//    }
//
//    private LocationListener listener = new LocationListener() {
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//
//        @Override
//        public void onLocationChanged(Location location) {
//            Log.e(TAG, "location update : " + location);
//            loc = location;
//            locationManager.removeUpdates(listener);
//        }
//    };

    private PlacesService placesService;
    private static final String API_KEY = "AIzaSyAkh4OFxBkISnxaaqBtwPvVErtRdISo64M";
    GPS gps;
    private String types = "meal_takeaway|restaurant|meal_delivery";
    private String radius = "5000.0";
    private ArrayList<Place> places = new ArrayList<Place>();
    private ListView placesListView;
    ListViewAdapter adapter;
    private Button btnShowOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPS(this);
        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude: " + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            //  alert.showAlertDialog(MainActivity.this, "GPS Status",
            //          "Couldn't get location information. Please enable GPS",
            //          false);
            // stop executing code by return
            return;
        }


//        btnShowOnMap = (Button) findViewById(R.id.btn_show_map);

        new GetPlaces(MainActivity.this,radius,types).execute();

        /*btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(),PlaceMapActivity.class);
                i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
                i.putExtra("user_longitude", Double.toString(gps.getLongitude()));
                startActivity(i);
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Background Async Task to Load Google places
     * */
    private class GetPlaces extends AsyncTask<Void, Void, String> {

        private String types;
        private Context context;
        private String radius;

        public GetPlaces()
        {
            //
        }
        public GetPlaces(Context context, String radius, String types){
            this.context = context;
            this.types = types;
            this.radius = radius;
        }
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            places = new ArrayList<Place>();
        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(Void... arg0) {
            // creating Places class object
            placesService = new PlacesService(API_KEY,radius,types);

            places = placesService.findPlaces(gps.getLatitude(), // 28.632808
                    gps.getLongitude()); // 77.218276
            Log.e("Info","Anzahl: " + places.size());
            for (int i = 0; i < places.size(); i++) {

                Place placeDetail = places.get(i);
                Log.e("Leer", "places : " + placeDetail.getId());
            }
            return "";
        }

        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            placesListView = (ListView) findViewById(R.id.listplaces);
            adapter = new ListViewAdapter(MainActivity.this,places);
            placesListView.setAdapter(adapter);

        }

    }

}

