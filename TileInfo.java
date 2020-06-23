package org.seattleschools.gardenplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TileInfo extends AppCompatActivity {
    String tileType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras(); // import the data this started with
        if (extras != null) {
            String data = extras.getString("DATAVAL"); // call up the specific string that stores the data, call it data
            tileType = data;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_info); // call up the xml file
    }


}
