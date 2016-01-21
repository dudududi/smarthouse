package com.example.drone.smarthouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;

public class RoomActivity extends Activity {
    /**
     * Service to connect with server
     */
    private ConnectionService connectionService;

    /**
     * variables containing the information about room
     */
    private int room, windowsStatus, lightStatus, doorStatus, temperatureValue;

    /**
     * Labels to show status of room components
     */
    private TextView lightCondition, windowCondition, doorCondition, temperatureCondition;

    /**
     * Buttons to service App
     */
    private Button returnButton, doorButton, windowButton, lightButton;

    /**
     * Seekbar to change temperature
     */
    private SeekBar temperatureBar;

    /**
     * Method to init components
     */
    private void initComponents(){
        lightCondition = (TextView) findViewById(R.id.lightCondition);
        doorCondition = (TextView) findViewById(R.id.doorCondition);
        windowCondition = (TextView) findViewById(R.id.windowCondition);
        temperatureCondition = (TextView) findViewById(R.id.temperatureCondition);
        returnButton = (Button) findViewById(R.id.returnButton);
        connectionService = ConnectionService.getInstance();
        room = getIntent().getIntExtra("room", 0);
        temperatureBar = (SeekBar) findViewById(R.id.temperatureBar);
        lightButton = (Button) findViewById(R.id.lightsButton);
        doorButton = (Button) findViewById(R.id.doorButton);
        windowButton = (Button) findViewById(R.id.windowsButton);
    }

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
     * Methods to get Door status.
     * @return String
     */
    private String getDoorStatus(){
        if(doorStatus==0)
            return "zamkniete";
        else return "otwarte";
    }

    /**
     * Method to get Light status.
     * @return String
     */
    private String getLightStatus(){
        if(lightStatus==0)
            return "zamkniete";
        else return "otwarte";
    }

    /**
     * Method to get Window status.
     * @return String
     */
    private String getWindowStatus(){
        if(windowsStatus==0)
            return "zamkniete";
        else return "otwarte";
    }

    /**
     * Method to get status of Temperature in String.
     * @return String
     */
    private String getTemperatureStatus(){
        return Integer.toString(this.temperatureValue);
    }

    public void setWindowsStatus(int windowsStatus) {
        this.windowsStatus = windowsStatus;
    }

    public void setLightStatus(int lightStatus) {
        this.lightStatus = lightStatus;
    }

    public void setDoorStatus(int doorStatus) {
        this.doorStatus = doorStatus;
    }

    public void setTemperatureValue(int temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        this.initComponents();
        this.initValues();
        lightCondition.setText(this.getLightStatus());
        doorCondition.setText(this.getDoorStatus());
        windowCondition.setText(this.getWindowStatus());
        temperatureCondition.setText(this.getTemperatureStatus());
        temperatureBar.setProgress(this.temperatureValue);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (windowsStatus == 0) {
                    connectionService.setWindowsStatus(true, room);
                    RoomActivity.this.setWindowsStatus(1);
                } else {
                    connectionService.setWindowsStatus(false, room);
                    RoomActivity.this.setWindowsStatus(0);
                }
                RoomActivity.this.windowCondition.setText(RoomActivity.this.getWindowStatus());
            }
        });

        doorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doorStatus==0){
                    connectionService.setDoorsStatus(1, room);
                    RoomActivity.this.setDoorStatus(1);
                }else{
                    connectionService.setDoorsStatus(0, room);
                    RoomActivity.this.setDoorStatus(0);
                }
                RoomActivity.this.doorCondition.setText(RoomActivity.this.getDoorStatus());
            }
        });

        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lightStatus==0){
                    connectionService.setLightsStatus(1, room);
                    RoomActivity.this.setLightStatus(1);
                }else{
                    connectionService.setLightsStatus(0, room);
                    RoomActivity.this.setLightStatus(0);
                }
                RoomActivity.this.lightCondition.setText(RoomActivity.this.getLightStatus());
            }
        });

        temperatureBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                RoomActivity.this.setTemperatureValue(temperatureBar.getProgress());
                RoomActivity.this.connectionService.setTemperature(temperatureBar.getProgress(), room);
                RoomActivity.this.temperatureCondition.setText(RoomActivity.this.getTemperatureStatus());
            }
        });

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
