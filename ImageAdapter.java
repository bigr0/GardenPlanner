//the code that makes images on the grid work. Leave this alone.

package org.seattleschools.gardenplanner;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<Integer> mThumbIds = new ArrayList<Integer>();
    private ArrayList<String> names = new ArrayList<String>();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(75 , 75));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds.get(position));
        return imageView;
    }
    
    public void generateThumbs(int numThumbs){
        for (int i = 0; i < numThumbs;i++){
            mThumbIds.add(R.drawable.dirt);
        }
        for (int i = 0; i < numThumbs;i++){
            names.add("dirt");
        }
    }

    public void setThumb(int thumbID,Integer type){
         //  pulls the respective thumbnail from the list
            mThumbIds.remove(thumbID);
            mThumbIds.add(thumbID,type);
        notifyDataSetChanged();
    }
    public void setName(int tileID,String name){
        names.remove(tileID);
        names.add(tileID,name);
        notifyDataSetChanged();
    }
    public String getName(int tileID){
        return names.get(tileID);
    }


    }





