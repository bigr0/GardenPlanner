package org.seattleschools.gardenplanner;

import org.seattleschools.gardenplanner.ImageAdapter;

public class GridStringSaver {
    String SavedMap = "";
    String Save;
    public GridStringSaver(ImageAdapter imAd, String mapName, int height, int width){
        SavedMap = "";
        SavedMap += "" + mapName + "/" + height + "/" + width +",";
        for (int i = 0; i < (height * width); i++) {
            SavedMap = SavedMap + imAd.getName(i) + ",";
        }

    }
    public void saveMap(ImageAdapter imAd, String mapName, int height, int width){
        SavedMap = "";
        SavedMap += "" + mapName + "/" + height + "/" + width +",";
        for (int i = 0; i < (height * width); i++) {
            SavedMap = SavedMap + imAd.getName(i) + ",";
        }

    }
    public String toString(){
        return SavedMap;
    }
}
