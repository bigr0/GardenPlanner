package org.seattleschools.gardenplanner;

//import statements
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Journal extends AppCompatActivity {
    //global variable that saves the text that is written by the user
    private static String journalTextSave = "Type Here";
    private static int numberOfPages = 1;



    //this method runs when the activity is started
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Declaration of variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //Allows for context to find directory and path
        final Context context = this;
        //creates a file path for the text save feature
        final File path = context.getFilesDir();
        final Save journalTextFile = new Save("Journal" + numberOfPages);
        /*creates the file for the text save feature
        final File journalTextFile = new File(path, "Journal.txt");*/
        final ArrayList<Save> pages = new ArrayList<>();
        pages.add(journalTextFile);

        //this creates the journal view as a View object
        final TextView journal = findViewById(R.id.journal);
        final TextView pageNumber = findViewById(R.id.pageNum);

        //this creates the different buttons as a Button Objects
        Button save = findViewById(R.id.Save);
        Button prev = findViewById(R.id.previousPage);
        Button next = findViewById(R.id.nextPage);

        //Sets the title to the correct page
        pageNumber.setText("Page " + numberOfPages);

        //This tests if there is a file at the given pathname and then will take what is in the file and save it to the screen
        /*if (journalTextFile.exists()){
            try(FileInputStream fileInputStream = new FileInputStream(journalTextFile)) {
                int ch = fileInputStream.read();
                String tempTextSave = "";
                while(ch != -1) {
                     tempTextSave += (char)ch;
                    ch = fileInputStream.read();
                }
                journalTextSave = tempTextSave;
            } catch (FileNotFoundException e) {
                // exception handling
            } catch (IOException e) {
                // exception handling
            }
        }*/
        journalTextSave = journalTextFile.getSavedData();

        //this if statement will check if the journalTextSave has any saved data; if it does then it will paste it into the journal writing space
        if (!journalTextSave.equalsIgnoreCase("")){
            journal.setText(journalTextSave);
            //String test = textFromFile.next();
            //journal.setText(test);
        }else{
            journal.setText("Type Here");
            System.out.println("NOTHING IS SAVED");
        }

        //this allows the app to recognize when the button is clicked
        fab.setOnClickListener(new View.OnClickListener() {
            //this FAB will take the user back to the main menu
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Journal.this, MainActivity.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        //when the save button is clicked it will set the journalTextSave to what text is in the writing space
        save.setOnClickListener(new View.OnClickListener() {
            //this will run the code inside when the save button is clicked
            @Override
            public void onClick(View v) {
                String newJournalText = String.valueOf(journal.getText());
                //journalTextSave = test;
                /*try(FileOutputStream stream = new FileOutputStream(journalTextFile)){
                    stream.write(newJournalText.getBytes());
                }catch (IOException e){

                }*/
                Save temp = new Save("journal" + numberOfPages);
                temp.saveData(newJournalText);
                Toast.makeText(Journal.this, "Your journal is saved to " + temp.getName(), Toast.LENGTH_LONG).show();
            }
        });

        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will set the displayed journal to a new
                if (numberOfPages <= 6){
                    numberOfPages ++;
                    pageNumber.setText("Page " + numberOfPages);
                    if(pages.size() >= numberOfPages){
                        journal.setText(pages.get(numberOfPages).getSavedData());
                    }else{
                        journal.setText("Type Here");
                        Save file = new Save("journal" + numberOfPages);
                        pages.add(file);

                    }
                    /*Save fileText = new Save("Journal" + numberOfPages);
                    String textFromFile = fileText.getSavedData();
                    String journalEntry =  textFromFile;

                    if (journalEntry.equalsIgnoreCase("")){
                        journal.setText("Type Here");
                    }else{
                        journal.setText(journalEntry);
                    }*/

                }else{
                    Toast.makeText(Journal.this, "You cannot make anymore pages", Toast.LENGTH_LONG).show();
                }
            }
        });
        //TODO fix issue where the text that was saved does not show up when going backwards
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfPages >= 2) {
                    numberOfPages--;
                    //this sets the page number to the correct page
                    pageNumber.setText("Page " + numberOfPages);
                    //this will set the displayed text to the previous page
                    //Save fileText = new Save("Journal" + numberOfPages);
                    String journalEntry = pages.get(numberOfPages).getSavedData();
                    if (journalEntry.equalsIgnoreCase("")){
                        journal.setText("Type Here");
                    }else{
                        journal.setText(journalEntry);
                    }


                }else{
                    //Toast "Can not go back any farther"
                    Toast.makeText(Journal.this, "You cannot go back anymore pages", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


//  /data/user/0/org.seattleschools.gardenplanner/files
/*
AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Look at this dialog!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        */