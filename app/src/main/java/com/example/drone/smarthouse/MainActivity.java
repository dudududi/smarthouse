package com.example.drone.smarthouse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    /**
     * Service to connect with server
     */
    private ConnectionService connectionService;

    /**
     * Containers to aggregate information about rooms
     */
    private ArrayList<String> rooms;
    private ArrayAdapter<String> roomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        connectionService = ConnectionService.getInstance();
        rooms = new ArrayList<>();
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

            connectionService.requestRoomList(new ConnectionService.ResponseHandler() {
                @Override
                public void onResponseReceived(HashMap<String, Integer> response) {
                    for (Map.Entry<String, Integer> entry : response.entrySet()) {
                        rooms.add(entry.getKey());
                    }
                    roomAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, rooms);
                    spinner.setAdapter(roomAdapter);
                }

                @Override
                public void onError(String msg) {
                    Log.d("TAG", "Error: " + msg);
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
