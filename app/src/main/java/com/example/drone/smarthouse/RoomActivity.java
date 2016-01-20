package com.example.drone.smarthouse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

public class RoomActivity extends Activity {
    /**
     * Service to connect with server
     */
    private ConnectionService connectionService;

    /**
     * variables containing the information
     */
    private int room, windowsStatus, lightStatus, doorStatus;
    private Integer temperatureValue;


    /**
     * Method to init values of variables
     */
    private void initValues(){
        connectionService.getDoorsStatus(room, new ConnectionService.ResponseHandler() {
            @Override
            public void onResponseReceived(HashMap<String, Integer> response) {
                doorStatus = response.get("door_status");
            }

            @Override
            public void onError(String msg) {
                Log.d("TAG", "Error: "+msg);
            }
        });

        connectionService.getLightsStatus(room, new ConnectionService.ResponseHandler() {
            @Override
            public void onResponseReceived(HashMap<String, Integer> response) {
                lightStatus = response.get("light_status");
            }

            @Override
            public void onError(String msg) {
                Log.d("TAG", "Error: "+msg);
            }
        });

        connectionService.getWindowsStatus(room, new ConnectionService.ResponseHandler() {
            @Override
            public void onResponseReceived(HashMap<String, Integer> response) {
                windowsStatus = response.get("window_status");
            }

            @Override
            public void onError(String msg) {
                Log.d("TAG", "Error: "+msg);
            }
        });

        connectionService.getTemperature(room, new ConnectionService.ResponseHandler() {
            @Override
            public void onResponseReceived(HashMap<String, Integer> response) {
                temperatureValue = response.get("temperature");
            }

            @Override
            public void onError(String msg) {
                Log.d("TAG", "Error: "+msg);
            }
        });
    }

    /**
     * Methods to set proper status.
     * @return
     */
    private String getDoorStatus(){
        if(doorStatus==0)
            return "zamkniete";
        else return "otwarte";
    }

    private String getLightStatus(){
        if(lightStatus==0)
            return "zamkniete";
        else return "otwarte";
    }

    private String getWindowStatus(){
        if(windowsStatus==0)
            return "zamkniete";
        else return "otwarte";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        room = getIntent().getIntExtra("room", 0);
        connectionService = ConnectionService.getInstance();
        TextView lightCondition = (TextView) findViewById(R.id.lightCondition);
        TextView doorCondition = (TextView) findViewById(R.id.doorCondition);
        TextView windowCondition = (TextView) findViewById(R.id.windowCondition);
        TextView temperatureCondition = (TextView) findViewById(R.id.temperatureCondition);
        this.initValues();
        lightCondition.setText(this.getLightStatus());
        doorCondition.setText(this.getDoorStatus());
        windowCondition.setText(this.getWindowStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
