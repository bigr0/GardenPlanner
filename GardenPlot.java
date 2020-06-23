package org.seattleschools.gardenplanner;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GardenPlot extends AppCompatActivity {
    // below are global variables
    String height;
    String width;
    int hInt;
    int columns;
    String mapName;
    Integer selectedType;
    String selectedName;
    String SavedMap = "";
    String plantInput="";
    boolean setupNewMap = true;
    private ArrayList<String> mapDat = new ArrayList<String>();

    private ArrayList<Integer> btnImages = new ArrayList<Integer>();
    private ArrayList<String> plantNames = new ArrayList<String>();
    private String condensedPlants;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {


        Bundle extras = getIntent().getExtras(); // import the data this started with
        if (extras != null) {
            String data = extras.getString("DATAVAL"); // call up the specific string that stores the data, call it data

            if (data.contains(" ")) {
                String datArr[] = data.split(" "); // split the data up by space and put it into an array
                height = datArr[0]; // assign the first value of the array to height
                width = datArr[1]; // assign the second value of the array to width
                hInt = Integer.parseInt(height); //convert the height into an integer
                columns = Integer.parseInt(width); //convert the width into an integer number of columns
                mapName = datArr[2];
                setupNewMap = true ;

            }
            else if (data.contains(",")){
                String datArr[] = data.split(","); // split the data up by space and put it into an array
                String baseInfo[]= datArr[0].split("/");
                height = baseInfo[1]; // assign the first value of the array to height
                width = baseInfo[2]; // assign the second value of the array to width
                hInt = Integer.parseInt(height); //convert the height into an integer
                columns = Integer.parseInt(width); //convert the width into an integer number of columns
                mapName = baseInfo[0];
                setupNewMap = false;
                for (int i = 1; i < datArr.length; i++){
                    mapDat.add(datArr[i]);
                }
            }
        }
        //LOAD EXISTING PLANT LIST
        //FOR LOOP
        //  THAT ADDS NAME FROM THE LIST TO plantNames
        //  TEMPORARY PlantDB CLASS CREATED WITH GOTTEN NAME
        //  SEARCH PlantDB FOR IMAGE
        //  ADD IMAGE TO btnImages


        /*for (int i = 0; i < importedNumPlants; i++){
            btnImages.add([plant class].getImage(i));
            plantNames.add([plant class].getName(i));
        }*/

        final Save plantList = new Save("plantList");
        String savedPlants[] = plantList.getSavedData().split(",");
        for (int i = 0; i < savedPlants.length; i++){
            addPlant(savedPlants[i]);

        }


        btnImages.add(R.drawable.dirt);
        plantNames.add("dirt");
        btnImages.add(R.drawable.grass);
        plantNames.add("grass");
        btnImages.add(R.drawable.wood);
        plantNames.add("wood");
        btnImages.add(R.drawable.test);
        plantNames.add("carrot");
        //TODO make a file of map names so in select garden we know exactly what files to grab
        Save mapNames = new Save("mapNames", "\n" + mapName);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_plot); // call up the xml file

        final GridView gridview = findViewById(R.id.gridview); // call up the gridview, assign to gridview
        System.out.println("Loaded up gridview");
        final ImageAdapter imAd = new ImageAdapter(this); //set up the image adapter
        System.out.println("Loaded up imAD");

        //imAd.thumbDBSetup();
        imAd.generateThumbs(columns*hInt); // use the imageadapter to generate the nessecary number of tiles
        gridview.setAdapter(imAd); //fill the gridview to what the imageAdapter has
        gridview.setNumColumns(columns); //set the number of grid columns to the number specified
        System.out.println("Loaded Map");
        final GridStringSaver savedMap = new GridStringSaver(imAd, mapName,hInt,columns);//generate the SaveFile
        final Save saveFile = new Save(mapName, savedMap.toString());
        System.out.println("Created Save File");

        if (setupNewMap==false){
            for (int i = 0; i < columns*hInt; i++){
                imAd.setName(i,mapDat.get(i));
                imAd.setThumb(i, btnImages.get(btnImages.indexOf(mapDat.get(i))));
                imAd.notifyDataSetChanged();
                gridview.setAdapter(imAd);
            }
        }


        final LinearLayout layout = findViewById(R.id.linButtons);
        for(int i = 0; i < btnImages.size(); i++){
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(plantNames.get(i));
            btnTag.setId(i);
            btnTag.setTag("button"+i);
            layout.addView(btnTag);

        }
        selectedType = btnImages.get(0);
        selectedName = plantNames.get(0);

        //plant search
        final Button search = findViewById(R.id.search); //button that confirms values
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText pIn = findViewById(R.id.plantInput);

                if (pIn.getText().equals("") == false) {
                    plantInput = pIn.getText().toString();
                }

                boolean plantFound=false;
                for (int i = 0; i < btnImages.size(); i++){
                    if (plantNames.get(i).equals(plantInput)){
                        plantFound = true;
                        selectedType = btnImages.get(i);
                        selectedName = plantNames.get(i);
                        break;
                    }
                    else{
                        plantFound = false;
                    }
                }
                if (plantFound == false){
                    addPlant(plantInput);
                }

/*
                if (plantFound == false){
                    PlantDB plant = new PlantDB(plantInput);
                    if (plant == null){

                    }
                    else {
                        plantNames.add(plantInput);
                        Bitmap plantBitmap = plant.imageBitmap();
                        Drawable tmp = new BitmapDrawable(getResources(), plantBitmap);
                        btnImages.add(plant.imageBitmap());
                        layout.;
                    }
                }
*/




            }
        });


        final Button mainMenu = findViewById(R.id.mainMenu); //button that confirms values
        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedMap.saveMap(imAd, mapName,hInt,columns);
                saveFile.saveData(savedMap.toString());
                condensePlants();
                plantList.saveData(condensedPlants);
                System.out.println("Map Saved as : " + savedMap.toString());
                Toast.makeText(GardenPlot.this, "Saved.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GardenPlot.this, MainActivity.class));
            }});

        final Button gardenPlots = findViewById(R.id.gardenPlots); //button that confirms values
        gardenPlots.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedMap.saveMap(imAd, mapName,hInt,columns);
                saveFile.saveData(savedMap.toString());
                condensePlants();
                plantList.saveData(condensedPlants);
                System.out.println("map Saved as : " + SavedMap.toString());
                Toast.makeText(GardenPlot.this, "Saved.",Toast.LENGTH_SHORT).show();
                // TODO: SAVE DATA, EXPORT AS NEW THINGAMABOBBER

                startActivity(new Intent(GardenPlot.this, SelectGarden.class));
            }});

        for (int i = 0; i < btnImages.size(); i++) {
            final int tmpindex = i;
            final Button tmpbuttonListner = findViewById(i);
            tmpbuttonListner.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    selectedType = btnImages.get(tmpindex);
                    selectedName = plantNames.get(tmpindex);
                }
            });
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //a listener to the tile clicking
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                    imAd.setThumb(position, selectedType);
                    imAd.setName(position, selectedName);
                    imAd.notifyDataSetChanged();
                    gridview.setAdapter(imAd);


        }});

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v, final int pos, long arg3) {
                PopupMenu popup = new PopupMenu(GardenPlot.this, v);
                popup.inflate(R.menu.more_info); // opens up a popup menu using the layout tile_customization
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(GardenPlot.this, imAd.getName(pos),Toast.LENGTH_SHORT).show();
                       // Intent intent = new Intent(GardenPlot.this, TileInfo.class); //create a new intent to start up the generated grid
                        //String exportDat = "" + imAd.getName(pos);
                        //intent.putExtra("TILETYPE",exportDat);
                        return true;
                    }
                });
                popup.show();
                return true;
            }






        });

    }

    //TODO: Add asynctask to handle e exceptions in protected code


    public void addPlant(String plant){
            PlantDB toCreate = new PlantDB(plant);
            plantNames.add(plant);
            btnImages.add(Integer.parseInt(toCreate.newDrawable().toString()));

    }

    public void condensePlants(){
        for (int i = 0; i<plantNames.size(); i++){
            condensedPlants = condensedPlants + plantNames.get(i) +",";
        }
    }

}


    /*
     public void showPopup(View v) {
            PopupMenu popup = new PopupMenu(this, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.tile_customization, popup.getMenu());
            popup.show();
        }
     */