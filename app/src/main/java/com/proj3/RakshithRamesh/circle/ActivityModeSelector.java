package com.proj3.RakshithRamesh.circle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//Rakshith Ramesh

public class ActivityModeSelector extends AppCompatActivity {

    static boolean drawModeOn = true, deleteModeOn = false, moveModeOn = true;
    static String defaultColor="BLACK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Circle- Draw");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }



    public void switchToDeleteMode(MenuItem selectedItem) {
        setTitle("Circle - Delete ");
        drawModeOn = false;
        moveModeOn = false;
        deleteModeOn = true;
    }

    public void switchToMoveMode(MenuItem selectedItem) {
        setTitle("Circle - Move ");
        drawModeOn = false;
        moveModeOn = true;
        deleteModeOn = false;
    }

    public void switchToDrawMode(MenuItem selectedItem) {
        setTitle("Circle - Draw");
        drawModeOn = true;
        moveModeOn = false;
        deleteModeOn = false;
    }
}