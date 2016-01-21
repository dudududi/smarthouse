package com.example.drone.smarthouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private void initComponents() {
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
                enableLoginButton();
            }

            @Override
            public void onError(String msg) {
                showErrorPopup(msg, true);
                Log.d("TAG", "Error: " + msg);
            }
        });
    }

    private void showErrorPopup(String msg, final boolean closeApp) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Error!").setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (closeApp) LoginActivity.this.finish();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void enableLoginButton() {
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
                        showErrorPopup(msg, false);
                        Log.d("TAG", "Error: " + msg);
                    }
                });
            }
        });
    }

}
