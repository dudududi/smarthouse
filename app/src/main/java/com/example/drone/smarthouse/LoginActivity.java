package com.example.drone.smarthouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class LoginActivity extends Activity {

    /**
     * Service to connect with server
     */
    private ConnectionService connectionService;

    /**
     * Fields to get the credential from EditText's
     */
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectionService = ConnectionService.getInstance();

        /**
         * Checking status of server
         */
        connectionService.checkServerAvailable(new ConnectionService.ResponseHandler() {
            @Override
            public void onResponseReceived(HashMap<String, Integer> response) {
                final EditText loginLabel = (EditText) findViewById(R.id.loginTest);
                final EditText passwdLabel = (EditText) findViewById(R.id.editText);

                final Button button = (Button) findViewById(R.id.loginButton);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        login = loginLabel.getText().toString();
                        password = passwdLabel.getText().toString();

                        /**
                         * Authentication with server
                         */
                        connectionService.authenticate(login, password, new ConnectionService.ResponseHandler() {
                            @Override
                            public void onResponseReceived(HashMap<String, Integer> response) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
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
            public void onError(String msg) {

            }
        });
    }


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
