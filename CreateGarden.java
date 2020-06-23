package org.seattleschools.gardenplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CreateGarden extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_garden);
        final Button button = findViewById(R.id.button); //button that confirms values

        button.setOnClickListener(new View.OnClickListener() // onlclick listener for aforementioned values
        {
            public void onClick(View v) {
                TextView hT = findViewById(R.id.heightText);
                TextView wT = findViewById(R.id.widthText);
                EditText nIn = findViewById(R.id.editText3);
                EditText hIn = findViewById(R.id.editText);  //get the value entered into the height textbox; assign to hIn
                EditText wIn = findViewById(R.id.editText2);//get the value entered into the width textbox; assign to wIn
                String nInput = "";
                String hInput = "";
                String wInput = "";
                //
                if (nIn.getText() != null) {
                    nInput = nIn.getText().toString();
                }
                else {
                    Toast.makeText(CreateGarden.this, "Please Enter a Name.",Toast.LENGTH_LONG).show();
                }
                //
                if (hIn.getText() != null) {
                    if (Integer.parseInt(hIn.getText().toString()) <= 0 || Integer.parseInt(hIn.getText().toString()) > 15) {
                        Toast.makeText(CreateGarden.this, "Please Enter a Height between 1 and 15.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        hInput = hIn.getText().toString();
                    }
                }
                else {
                        Toast.makeText(CreateGarden.this, "Please Enter a Height.", Toast.LENGTH_LONG).show();
                }
                //
                if (wIn.getText() != null) {
                    if (Integer.parseInt(wIn.getText().toString()) <= 0 || Integer.parseInt(wIn.getText().toString()) > 15) {
                        Toast.makeText(CreateGarden.this, "Please Enter a Width between 1 and 15.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        wInput = wIn.getText().toString();
                    }
                }
                else {
                    Toast.makeText(CreateGarden.this, "Please Enter a Width.", Toast.LENGTH_LONG).show();
                }



                hT.setText(hInput); // display entered height on the page
                wT.setText(wInput); // display entered height on the page
                String exportDat = "" + hInput + " " + wInput + " " + nInput ; // create a single string containing both height and width separated by a space for exporting
                Intent intent = new Intent(CreateGarden.this, GardenPlot.class); //create a new intent to start up the generated grid
                intent.putExtra("DATAVAL",exportDat); // add the export string to the intent
                startActivity(intent); // start the garden plot with the export string


            }

        });
    }

}

