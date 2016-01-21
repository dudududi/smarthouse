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
     * Fields to aggregate the credential from EditText's
     */
    private String login, password;

    /**
     * Fields to get the credential
     */
    private EditText loginLabel, passwdLabel;

    /**
     * Button to Log In.
     */
    private Button button;
    private Intent intent;

    /**
     * Method to init components
     */
    private void initComponents(){
        connectionService = ConnectionService.getInstance();
        loginLabel = (EditText) findViewById(R.id.loginTest);
        passwdLabel = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.loginButton);
        intent = new Intent(LoginActivity.this, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initComponents();

        /**
         * Checking status of server
         */
        connectionService.checkServerAvailable(new ConnectionService.ResponseHandler() {
            @Override
            public void onResponseReceived(HashMap<String, Integer> response) {
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
                Log.d("TAG", "Error: " + msg);
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
