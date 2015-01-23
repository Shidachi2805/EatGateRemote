package de.eatgate.placessearch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ProMarkt on 19.01.2015.
 */
public class SinglePlacesActivity extends Activity {

    private ListView listView_we_day;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_place);

        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
            String str_name = b.getString("name");
            TextView tv_name = (TextView) findViewById(R.id.name);
            tv_name.setText(str_name);


            String str_adresse = b.getString("adresse");
            TextView tv_adress = (TextView) findViewById(R.id.adresse);
            tv_adress.setText(str_adresse);



            ArrayList<String> arrList = b.getStringArrayList("openhours");
           // String tag = null;
            if(arrList != null)
            {
                Log.e("singlePlace", "arraylist: " + arrList.size());
                TextView tv_OpenHours = (TextView) findViewById(R.id.str_oppenHours);
                tv_OpenHours.setVisibility(View.VISIBLE);
                listView_we_day = (ListView)findViewById(R.id.weekday_list);
                listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow,arrList);
                listView_we_day.setAdapter(listAdapter);

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_back:
                Intent intent = new Intent(this, PlaceMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
//    private class ListViewAdapter_Weekday extends ArrayAdapter<String> {
//        private Context context;
//        private List<String> tag = new ArrayList<String>();
//        public ListViewAdapter_Weekday(Context context, List<String> tag) {
//            super(context, R.layout.single_place, tag);
//            this.context = context;
//            this.tag = tag;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View row = inflater.inflate(R.layout.single_place, null, false);
//
//            // ImageView icon = (ImageView) row.findViewById(R.id.iv_gender);
//            // LinearLayout linLayout = (LinearLayout) row.findViewById(R.id.listPlacesLayout);
//            TextView name = (TextView) row.findViewById(R.id.tag);
//            //TextView age = (TextView) row.findViewById(R.id.age_value);
//            // TextView separator = (TextView) row.findViewById(R.id.seperator);
//
//            name.setText(tag.indexOf(position));
//
//            return row;
//        }
//    }
}
