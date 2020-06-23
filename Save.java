package org.seattleschools.gardenplanner;
import android.content.Context;
import android.widget.Toast;
import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save {
    //private Context context = null;
    //private File path = context.getFilesDir();
    private String savedData = "";
    private File textSave;
    private String nameOfFile = "";


    /**
     *This instance sets up a file with the given name and sets the
     * @param name String used as the name of the file
     * @param text String of the data
     */
    public Save(String name, String text){
        textSave = new File("/data/user/0/org.seattleschools.gardenplanner/files", name);
        saveData(text);
        nameOfFile = name;
    }

    /**
     * this instance sets up a file with the given name
     * @param name
     */
    public Save(String name){
        textSave = new File("/data/user/0/org.seattleschools.gardenplanner/files",name);
        nameOfFile = name;
    }
    /**
     * This will get the data that is saved in the file on the phone
     * @return a string of data that was saved
     */
    public String getSavedData(){
        //if (textSave.exists()){
            try(FileInputStream fileInputStream = new FileInputStream(textSave)) {
                int ch = fileInputStream.read();
                String tempTextSave = "";
                while(ch != -1) {
                    tempTextSave += (char)ch;
                    ch = fileInputStream.read();
                }
                savedData = tempTextSave;
            } catch (FileNotFoundException e) {
                // exception handling
            } catch (IOException e) {
                // exception handling
            }
       // }
        return savedData;
    }

    /**
     * this saves the given string to the phone or devices memory as a written file
     * @param newSave a string that will be saved to the phone
     */
    public void saveData(String newSave) {
        this.savedData = newSave;
        try(FileOutputStream stream = new FileOutputStream(textSave)){
            stream.write(savedData.getBytes());
        }catch (IOException e){

        }
    }

    /**
     * Returns the given name of the file
     * @return nameOfFile
     */
    public String getName(){
        return nameOfFile;
    }
}
