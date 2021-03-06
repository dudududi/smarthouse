package com.example.drone.smarthouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private Spinner spinner;
    private Button button;
    private Intent intent;

    /**
     * Method to init components
     */
    private void initComponents(){
        connectionService = ConnectionService.getInstance();
        rooms = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.sendRoom);
        intent = new Intent(MainActivity.this, RoomActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        this.initComponents();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error!").setMessage(msg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });                    Log.d("TAG", "Error: " + msg);
                }
            });
    }

}
