package org.seattleschools.gardenplanner;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectGarden extends AppCompatActivity {
    int idNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Takes the file of Garden names and use scanner and direct file location to individually pull each file and make a button for them
        Boolean stuffInScanner = true;
        File fileOfGardens = new File("/data/user/0/org.seattleschools.gardenplanner/files/mapNames");
        Context context = this;
        //HorizontalScrollView scrollView = findViewById(R.id.horizontalScrollView);
        //LinearLayout layout = findViewById(R.id.linearLayout);
        LinearLayout otherThing = findViewById(R.id.helloWorld);

        /*LayoutInflater inflater = LayoutInflater.from(context);
        View View = inflater.inflate(R.layout.content_select_garden, null);*/
        final Button garden1 = findViewById(R.id.garden1);
        garden1.setText("Garden 1");
        final Button garden2 = findViewById(R.id.garden2);
        garden2.setText("Garden 2");
        final Button garden3 = findViewById(R.id.garden3);
        garden3.setText("Garden 3");

        LinearLayout.LayoutParams test = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //ArrayLists for the buttons of Gardens
        ArrayList<Button> buttonsForFiles = new ArrayList<>();
        ArrayList<String> fileNames = new ArrayList<>();
        buttonsForFiles.add(garden1);
        buttonsForFiles.add(garden2);
        buttonsForFiles.add(garden3);



        try (Scanner mapNames = new Scanner(fileOfGardens)){
           while (stuffInScanner) {
            String fileName = mapNames.next();
            fileNames.add(fileName);
            stuffInScanner = mapNames.hasNext();
            /*this is old code that can bu used for when we dynamical create buttons
            System.out.println("HELLLLLLLLLLAASDFASDFASFDSAAFSDFAsdfa " + layout);
            String name = mapNames.next();
            Button btnTag = new Button(context);
            //btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(name);
            btnTag.setId(idNumber);
            btnTag.setTag(name);
            btnTag.setHeight(50);
            btnTag.setWidth(50);
            buttonsForFiles.add(btnTag);
            layout.addView(btnTag, test);
            stuffInScanner = mapNames.hasNext();
            idNumber++;*/
            }
        }catch(FileNotFoundException e){

        }catch(NullPointerException e){

        }

        for(int i = 0; i < fileNames.size(); i++){
            String name = fileNames.get(i);
            Button tempButton = buttonsForFiles.get(i);
            tempButton.setText(name);

            /*//this is old code that can bu used for when we dynamical create buttons
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(fileNames.get(i));
            btnTag.setId(i);
            btnTag.setTag("button"+i);
            otherThing.addView(btnTag);*/
        }
        //tells the app once the button is clicked where to go
        /*for (int i = 0; i < idNumber; i++) {
            final int tmpindex = i;
            final Button tempButtonListener = findViewById(i);
            tempButtonListener.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Save file = new Save(tempButtonListener.getText().toString());
                    String exportDat = file.getSavedData();
                    Intent intent = new Intent(SelectGarden.this, GardenPlot.class);
                    intent.putExtra("DATAVAL", exportDat);
                }
            });
        }*/
        garden1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!garden1.getText().equals("Garden 3")) {
                    Save file = new Save(garden1.getText().toString());
                    String exportDat = file.getSavedData();
                    Intent intent = new Intent(SelectGarden.this, GardenPlot.class);
                    intent.putExtra("DATAVAL", exportDat);
                    startActivity(intent);
                }else{
                    Toast.makeText(SelectGarden.this, "That Garden has not been created yet", Toast.LENGTH_LONG).show();
                }
            }
        });

        garden2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!garden2.getText().equals("Garden 3")) {
                    Save file = new Save(garden2.getText().toString());
                    String exportDat = file.getSavedData();
                    Intent intent = new Intent(SelectGarden.this, GardenPlot.class);
                    intent.putExtra("DATAVAL", exportDat);
                    startActivity(intent);
                }else{
                    Toast.makeText(SelectGarden.this, "That Garden has not been created yet", Toast.LENGTH_LONG).show();
                }
            }
        });

        garden3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!garden3.getText().equals("Garden 3")) {
                    Save file = new Save(garden3.getText().toString());
                    String exportDat = file.getSavedData();
                    Intent intent = new Intent(SelectGarden.this, GardenPlot.class);
                    intent.putExtra("DATAVAL", exportDat);
                    startActivity(intent);
                }else{
                    Toast.makeText(SelectGarden.this, "That Garden has not been created yet", Toast.LENGTH_LONG).show();
                }
            }
        });



        //this allows the app to recognize when the button is clicked
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //this FAB will take the user back to the main menu
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectGarden.this, MainActivity.class));
            }
        });

    }

}
