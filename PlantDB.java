package org.seattleschools.gardenplanner;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public class PlantDB {

    private Bitmap imgBitmap;
    private JSONObject firstHarvest;
    private JSONObject lastHarvest;
    private JSONObject lifespan;
    public Plant savePlant;
    /**
     * @param baseURL
     * URL before any modifiers, changes or directories are added
     * @param modifier
     * parsed user data that is appended to baseURL
     * @return newURL
     * Modified url with directory and JSON tag
     */
        private static String modURL(String baseURL, String modifier){
            String newURL = baseURL + modifier;
            newURL += ".json";
            return newURL;
    }


    /**
     * @param rd
     * Reader Object used as parameter to append StringBuilder()
     * @return String builder toString(), contains built string from reader object
     * @throws IOException In/Out Exception to test output errors from reader
     */
        private static String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

    //https://stackoverflow.com/questions/3572463/what-is-context-on-android
    /**
     *
     * @param url
     * takes modURL return and builds InputStream
     * @return Raw, pre-parsed JSON info
     * @throws IOException thrown if problem with InputStream.openStream()
     * @throws JSONException thrown if a problem with JSON parsing occurs from a reader error occurs
     * in readAll()
     */
        public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject info = new JSONObject(jsonText);
                return info;
            } finally {
                is.close();
            }

        }

        //TODO: Test JSONObject builders below for parsing errors

    /**
     * Takes in a string of raw JSON and breaks it down into an array of strings in which we can
     * access information from different variables
     * @param JSON String of raw JSON
     */
    private void  setMedianLifespan(JSONObject JSON){
        lifespan = JSON;
        }

    /**
     * @param JSON item to set to Plant Object
     * @return medHarvest Median harvest date
     */
    public void setDaysToFirstHarvest(JSONObject JSON){
        firstHarvest = JSON;
            }


    /**
     *
     * @param JSON @param JSON item to set to Plant Object
     */
    public void setDaysToLastHarvest(JSONObject JSON){
        lastHarvest = JSON;
    }


    public void setFullJSON(JSONObject newOB){
       //this.fullJSONObject = newOB;
    }

    //used to get JSON info in a 1:1 exchange

    public JSONObject getDaysToFirstHarvest(){
        return firstHarvest;
    }

    public JSONObject getDaysToLastHarvest(){
        return lastHarvest;
    }

    public JSONObject getLifespan(){
        return lifespan;
    }

    public Plant getSavePlant() {
        return savePlant;
    }

    /**
     *
     * @param modifier
     * @return
     * @throws IOException
     */
    //key: 12631466-14a0214a6c1db5acc956532a9
    //readsJSON Call must be in doInBackround();
    public String getImageURL(String modifier) throws IOException{
        String url = "https://pixabay.com/api/?key=12631466-14a0214a6c1db5acc956532a9&q="+ modifier +"&image_type=photo";
        return url;
    }
    //readsJSON Call must be in doInBackround();
    public Bitmap buildImage(String url) throws IOException{
        URL imageURL = new URL(getImageURL(url));

            BitmapFactory openedStream = new BitmapFactory();
            Bitmap image = openedStream.decodeStream(imageURL.openConnection().getInputStream());
            //(imageURL.openConnection().getInputStream())
        return image;
    }


    public Drawable newDrawable(){
        BitmapDrawable btDraw = new BitmapDrawable(Resources.getSystem(), this.imgBitmap);
        Drawable returnImage = btDraw;
        return returnImage;
    }
    /**
     * @throws IOException
     */


        //readsJSON Call must be in doInBackround();
        public void makePlant(String gardenURL) throws IOException{
            Drawable plantImage = newDrawable();
            Plant plantInstance = new Plant(gardenURL,plantImage,getDaysToFirstHarvest(),getDaysToLastHarvest(),getLifespan());
            /**
             *
             * Add setting algorithm for drawable plant data
             *
             */
            //plantInstance
            savePlant = plantInstance;
        }
        //TODO: Fix Base Case of Dirt and additonal parsed regex
        public PlantDB(String name){
                String growStuff = "https://www.growstuff.org/crops/";
                String streamURL = modURL(growStuff, name);
                SaveTask st = new SaveTask();
                st.execute(streamURL, name);

            //makePlant(streamURL);
            //JSONObject json = readJsonFromUrl(streamURL);
            //makePlant(growStuff + name,json);
            //System.out.println(json.toString());


        }
    private class SaveTask extends AsyncTask<String, Void, String>{
        @Override
        //ask daniel about security exception
        public String doInBackground(String... strings) {
            try {

                    JSONObject newJSON = PlantDB.readJsonFromUrl(strings[0]);
                    setDaysToFirstHarvest(newJSON.getJSONObject("median_days_to_first_harvest"));
                    setDaysToLastHarvest(newJSON.getJSONObject("median_days_to_last_harvest"));
                    setMedianLifespan(newJSON.getJSONObject("median_lifespan"));

                imgBitmap = buildImage(getImageURL(strings[1]));

                makePlant(strings[0]);
            } catch (IOException i) {
                System.out.println(i.getMessage());
            } catch (JSONException j) {
                System.out.println(j.getMessage());
            }
        return null;
        }


        //@Override
        /*protected void onPostExecute(String s)  {

            super.onPostExecute(s);
        }*/
    }
    }




   // Example of a Json Object being successfully being built
    /*public JsonObject buildBook() {
        JsonObject value = Json.createObjectBuilder()
                .add("firstName", "John")
                .add("lastName", "Smith")
                .add("age", 25)
                .add("address", Json.createObjectBuilder()
                        .add("streetAddress", "21 2nd Street")
                        .add("city", "New York")
                        .add("state", "NY")
                        .add("postalCode", "10021"))
                .add("phoneNumber", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "212 555-1234"))
                        .add(Json.createObjectBuilder()
                                .add("type", "fax")
                                .add("number", "646 555-4567")))
                .build();
        return value
    }*/

//https://growstuff.org/crops/tomato.json
