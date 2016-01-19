package com.example.drone.smarthouse;


/**
 * Created by dudek on 1/19/16.
 */
public class ConnectionService{

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
    public void getTemperature(int roomNumber, ResponseHandler handler){

    }

    /**
     * Command to the service to set the current temperature from server.
     * @param temperature value, which should be set
     * @param roomNumber number of room, for which value will be set
     */
    public void setTemperature(int temperature, int roomNumber) {

    }


    /**
     * Command to the service to get the current status of room windows from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously
     */
    public void getWindowsStatus(int roomNumber, ResponseHandler handler){

    }


    /**
     * Command to the service to set the current status of room windows on server.
     * @param setOpen boolean, whether to windows should be opened
     * @param roomNumber number of room, for which value will be set
     */
    public void setWindowsStatus(boolean setOpen, int roomNumber){

    }

    /**
     * Command to the service to get the current status of room doors from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously
     */
    public void getDoorsStatus(int roomNumber, ResponseHandler handler){

    }

    /**
     * Command to the service to set the current status of doors from server.
     * @param setOpen boolean, whether the doors should be opened
     * @param roomNumber number of room, for which value will be set
     */
    public void setDoorsStatus(int setOpen, int roomNumber) {

    }


    /**
     * Command to the service to get the current status of light in room from server.
     * @param roomNumber number of room, from which value will be gained
     * @param handler handler to obtain result asynchronously
     */
    public void getLightsStatus(int roomNumber, ResponseHandler handler){

    }

    /**
     * Command to the service to set the current status of light in server.
     * @param setON boolean, whether the ligts should be turned on
     * @param roomNumber number of room, for which value will be set
     */
    public void setLightsStatus(int setON, int roomNumber){

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
    public void requestRoomList(ResponseHandler handler){

    }

    public interface ResponseHandler{
        void onResponseReceived(int receivedData);
    }

    private class ServiceHandler {
        public final static int GET = 1;
        public final static int POST = 2;

        public String makeRequest(String url, int method) {
            return null;
        }
    }


}
