package in.squarei.carcom.Interfaces;

/**
 * Created by mohit kumar on 4/26/2017.
 */

public interface AppConstants {

    String APP_NAME = "Car Control";
    int APP_SPLASH_TIME = 3000;

    /* animation type*/
    int ANIMATION_SLIDE_UP = 0x01;
    int ANIMATION_SLIDE_LEFT = 0x02;

    // User Status Details
    String IS_LOGIN = "islogin";
    String PASS_KEY = "passKey";
    String USER_KEY = "userKey";


    // Message types sent from the BluetoothChatService Handler
    int MESSAGE_STATE_CHANGE = 1;
    int MESSAGE_READ = 2;
    int MESSAGE_WRITE = 3;
    int MESSAGE_DEVICE_NAME = 4;
    int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    String DEVICE_NAME = "device_name";
    String TOAST = "toast";
}
