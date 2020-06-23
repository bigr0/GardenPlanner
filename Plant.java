package org.seattleschools.gardenplanner;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.JsonReader;

import org.json.JSONObject;

public class Plant {

    private JSONObject MEDIAN_DAYS_TO_FIRST_H;
    private JSONObject MEDIAN_DAYS_TO_LAST_H;
    private JSONObject MEDIAN_DAYS_LIFE;
    private Drawable imageInstance;
    private String objectURL;



   public Plant(){}

   public Plant(String URL, Drawable image,JSONObject MDTH, JSONObject MDTLH, JSONObject MDL){
        objectURL = URL;
        imageInstance = image;
        MEDIAN_DAYS_TO_FIRST_H = MDTH;
        MEDIAN_DAYS_TO_LAST_H = MDTLH;
        MEDIAN_DAYS_LIFE = MDL;
   }
    //Daniel to interpret from image resources
   public Drawable image(){
       return imageInstance;
   }

    public JSONObject medianDaysToFirstHarvest(){
       return MEDIAN_DAYS_TO_FIRST_H;
    }

    public JSONObject medianDaysToLastHarvest(){
       return MEDIAN_DAYS_TO_LAST_H;
    }

    public JSONObject lifespan(){
       return MEDIAN_DAYS_LIFE;
    }

    public String returnURL(){
       return objectURL;
    }


}
