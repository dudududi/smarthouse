package com.example.drone.smarthouse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Przyklad uzycia asynchronicznego wywolania:
        final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectionService.getInstance().requestRoomList(new ConnectionService.ResponseHandler() {
                    @Override
                    public void onResponseReceived(HashMap<String, Integer> response) {
                        for(Map.Entry<String, Integer> entry : response.entrySet()) {
                            String key = entry.getKey();
                            Log.d("TAG", key + ": "+ response.get(key));
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        Log.d("TAG", "Error: " + msg);
                    }
                });

                //jak wywolujemy jakis setter, to nie musimy sie martwic o odpowiedz - Logi zapisza się na serwerze
                ConnectionService.getInstance().setTemperature(30, 1);

                //autentykacja do logowania
                ConnectionService.getInstance().authenticate("ryanchenkie@gmail.com", "secret", new ConnectionService.ResponseHandler() {
                    @Override
                    public void onResponseReceived(HashMap<String, Integer> response) {
                        Log.d("TAG", "User authenticated.");
                    }

                    @Override
                    public void onError(String msg) {
                        Log.d("TAG", "Error: " + msg);
                    }
                });

                //sprawdzanie dostępności serwera
                ConnectionService.getInstance().checkServerAvailable(new ConnectionService.ResponseHandler() {
                    @Override
                    public void onResponseReceived(HashMap<String, Integer> response) {
                        Log.d("TAG", "Server available");
                    }

                    @Override
                    public void onError(String msg) {
                        Log.d("TAG", "Error: " + msg);
                    }
                });
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
