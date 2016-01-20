package com.example.drone.smarthouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private HashMap<String, Integer> roomsMap;
    private ArrayList<String> rooms;
    private ArrayAdapter<String> roomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        connectionService = ConnectionService.getInstance();
        rooms = new ArrayList<>();

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Button button = (Button) findViewById(R.id.sendRoom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra("room", roomsMap.get(spinner.getSelectedItem().toString()));
                startActivity(intent);
            }
        });


        connectionService.requestRoomList(new ConnectionService.ResponseHandler() {
                @Override
                public void onResponseReceived(HashMap<String, Integer> response) {
                    for (Map.Entry<String, Integer> entry : response.entrySet()) {
                        rooms.add(entry.getKey());
                    }
                    roomsMap = response;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roomAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, rooms);
                            spinner.setAdapter(roomAdapter);
                            spinner.setSelection(0);
                            roomAdapter.notifyDataSetChanged();
                            Log.d("TAG", spinner.getSelectedItem().toString());
                        }
                    });
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
