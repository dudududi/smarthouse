package com.example.drone.smarthouse;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * Created by dudek on 1/19/16.
 */
public class ConnectionService{
    private String TAG = "SmartHouse";
    private String baseUrl = "http://188.166.117.195/api/rooms";

    private ConnectionService(){
        webService = new ServiceHandler();
    }

    private static class SingletonHolder {
        private final static ConnectionService instance = new ConnectionService();
    }

    private ServiceHandler webService;

    /**
     * Returns the instance of ConnectionService class
     * @return singleton instance
     */
    public static ConnectionService getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * Command to the service to get the current temperature from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously
     */
    public void getTemperature(final int roomNumber, final ResponseHandler handler){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/temperature";
                    String temperature = webService.makeRequest(url, ServiceHandler.GET);
                    HashMap<String, Integer> response = new HashMap<>();
                    response.put("temperature", Integer.valueOf(temperature));
                    handler.onResponseReceived(response);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    handler.onError(e.getMessage());
                }

                return null;
            }
        }.execute();

    }

    /**
     * Command to the service to set the current temperature from server.
     * @param temperature value, which should be set
     * @param roomNumber number of room, for which value will be set
     */
    public void setTemperature(final int temperature, final int roomNumber) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/temperature";
                    webService.makeRequest(url, ServiceHandler.POST);
                    Log.d(TAG, "Temperature changed: " + temperature);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }

                return null;
            }
        }.execute();
    }


    /**
     * Command to the service to get the current status of room windows from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously (0-closed, 1-open)
     */
    public void getWindowsStatus(final int roomNumber, final ResponseHandler handler){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/windows";
                    String isOpen = webService.makeRequest(url, ServiceHandler.GET);
                    HashMap<String, Integer> response = new HashMap<>();
                    response.put("windows-open", Integer.valueOf(isOpen));
                    handler.onResponseReceived(response);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    handler.onError(e.getMessage());
                }

                return null;
            }
        }.execute();
    }


    /**
     * Command to the service to set the current status of room windows on server.
     * @param setOpen boolean, whether to windows should be opened
     * @param roomNumber number of room, for which value will be set
     */
    public void setWindowsStatus(final boolean setOpen, final int roomNumber){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/windows";
                    webService.makeRequest(url, ServiceHandler.POST);
                    Log.d(TAG, "Windows status changed: " + setOpen);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }

                return null;
            }
        }.execute();
    }

    /**
     * Command to the service to get the current status of room doors from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously (0-closed, 1-open)
     */
    public void getDoorsStatus(final int roomNumber, final ResponseHandler handler){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/doors";
                    String isOpen = webService.makeRequest(url, ServiceHandler.GET);
                    HashMap<String, Integer> response = new HashMap<>();
                    response.put("doors-open", Integer.valueOf(isOpen));
                    handler.onResponseReceived(response);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    handler.onError(e.getMessage());
                }

                return null;
            }
        }.execute();
    }

    /**
     * Command to the service to set the current status of doors from server.
     * @param setOpen boolean, whether the doors should be opened
     * @param roomNumber number of room, for which value will be set
     */
    public void setDoorsStatus(final int setOpen, final int roomNumber) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/doors";
                    webService.makeRequest(url, ServiceHandler.POST);
                    Log.d(TAG, "Windows status changed: " + setOpen);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }

                return null;
            }
        }.execute();
    }


    /**
     * Command to the service to get the current status of light in room from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously (0-turnedOFF, 1-turnedON)
     */
    public void getLightsStatus(final int roomNumber, final ResponseHandler handler){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/lights";
                    String isOn = webService.makeRequest(url, ServiceHandler.GET);
                    HashMap<String, Integer> response = new HashMap<>();
                    response.put("lights-open", Integer.valueOf(isOn));
                    handler.onResponseReceived(response);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    handler.onError(e.getMessage());
                }

                return null;
            }
        }.execute();
    }

    /**
     * Command to the service to set the current status of light in server.
     * @param setON boolean, whether the ligts should be turned on
     * @param roomNumber number of room, for which value will be set
     */
    public void setLightsStatus(final int setON, final int roomNumber){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl + "/"+roomNumber+"/lights";
                    webService.makeRequest(url, ServiceHandler.POST);
                    Log.d(TAG, "Windows status changed: " + setON);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }

                return null;
            }
        }.execute();
    }

    /**
     * Command to the service to check whether the status is available or not.
     * @param handler handler to obtain result asynchronously
     */
    public void checkServerAvailable(ResponseHandler handler){

    }

    /**
     * Command to the service to obtain the list of all rooms from server.
     * @param handler handler to obtain result asynchronously
     */
    public void requestRoomList(final ResponseHandler handler){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String url = baseUrl;
                    String json = webService.makeRequest(url, ServiceHandler.GET);
                    JSONArray list = new JSONArray(json);
                    HashMap<String, Integer> response = new HashMap<>();
                    for (int i = 0; i< list.length(); i++){
                        JSONObject jsonObject = list.getJSONObject(i);
                        response.put(jsonObject.getString("room_name"), jsonObject.getInt("room_number"));
                    }
                    handler.onResponseReceived(response);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    handler.onError(e.getMessage());
                }

                return null;
            }
        }.execute();
    }

    /**
     * Interface to obtain the results of asynchronous calls. When no error occurs, the function
     * onResponseReceived will be invoked. Otherwise, onError function will be invoked.
     */
    public interface ResponseHandler{
        /**
         * Invoked, when response will be received successfully.
         * @param response is HashMap, where key is String, and value is Integer.
         */
        void onResponseReceived(HashMap<String, Integer> response);

        /**
         * Invoked, when any error occurs.
         * @param msg message of the error.
         */
        void onError(String msg);
    }



    private class ServiceHandler {
        public final static String GET = "GET";
        public final static String POST = "POST";

        public String makeRequest(String url, String method) throws Exception {
            HttpURLConnection connection;
            BufferedReader reader;
            StringBuffer buffer;
            int status;

            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setRequestMethod(method);
            status = connection.getResponseCode();
            Log.d(TAG, "Status: " + status);
            if (status == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }
            connection.disconnect();
            reader.close();
            if (status != 200) throw new Exception(buffer.toString());
            return buffer.toString();
        }
    }

}
