package in.squarei.carcom.Fragments;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import in.squarei.carcom.Activities.DeviceListActivity;
import in.squarei.carcom.Interfaces.AppConstants;
import in.squarei.carcom.R;
import in.squarei.carcom.services.BluetoothChatService;

public class BluetoothConnectFragment extends Fragment implements View.OnClickListener {

    public interface InformUserDashboardActivity {
        void sendMessage(int msg);
    }

    private InformUserDashboardActivity informUserDashboardActivity;
    private static final String TAG = "BTConnectFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    // private ListView mConversationView;
    //private EditText mOutEditText;
    // private Button mSendButton;

    private Button btnBluetoohConnect;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    // private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    private BluetoothChatService mChatService = null; ////Instance for bluetooth chat service    //////

    public BluetoothConnectFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            //   setupChat();
            mChatService = new BluetoothChatService(getActivity(), mHandler);

            informUserDashboardActivity = (InformUserDashboardActivity) getActivity();
            // Initialize the buffer for outgoing messages
            mOutStringBuffer = new StringBuffer("");
        }
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = getActivity();
            switch (msg.what) {
                case AppConstants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            Log.i(TAG, "===========Status======== CONNECTED =====>>>to :" + mConnectedDeviceName);
                            informUserDashboardActivity.sendMessage(BluetoothChatService.STATE_CONNECTED);
                            if (mChatService != null) {
                                //   saveObject(mChatService, true);
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sendMessagee("HiBluetooth");
                                }
                            }, AppConstants.APP_SPLASH_TIME);

                            //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //      mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            Log.i(TAG, "===========Status======== CONNECTING");
                            informUserDashboardActivity.sendMessage(BluetoothChatService.STATE_CONNECTING);
                            if (mChatService != null) {
                                // saveObject(mChatService, false);
                            }
                            //setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                            Log.i(TAG, "===========Status======== STATE LISTENING=====");
                            informUserDashboardActivity.sendMessage(BluetoothChatService.STATE_LISTEN);
                            if (mChatService != null) {
                                // saveObject(mChatService, false);
                            }
                            break;
                        case BluetoothChatService.STATE_NONE:
                            informUserDashboardActivity.sendMessage(BluetoothChatService.STATE_NONE);
                            if (mChatService != null) {
                                //  saveObject(mChatService, false);
                            }
                            Log.i(TAG, "===========Status======== STATE NONE=====");
                            //setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case AppConstants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //     mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case AppConstants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case AppConstants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(AppConstants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case AppConstants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(AppConstants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bluetooth_connect, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBluetoohConnect = (Button) view.findViewById(R.id.btnBluetoohConnect);
        btnBluetoohConnect.setOnClickListener(this);
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBluetoohConnect:
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);  //// start activity to connect securely
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    //  setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        Log.i(TAG, "Device address is returned=======" + address);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.i(TAG, "Bluetooth Device from address=======" + device);
        // Attempt to connect to the device
        if (mChatService != null) {
            mChatService.connect(device, secure);
        } else {
            mChatService = new BluetoothChatService(getActivity(), mHandler);
            mChatService.connect(device, secure);

        }

    }

    private void sendMessagee(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), "Not Connected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            Log.i(TAG, "==================WRITING MESSAGE===============");
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);


            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //  mOutEditText.setText(mOutStringBuffer);
        }
    }

    private void saveObject(BluetoothChatService bluetoothChatService, boolean save) {
        // write object to file
        if (save) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("bluetoothChatService.ser");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bluetoothChatService != null) {
                    oos.writeObject(bluetoothChatService);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new FileOutputStream("bluetoothChatService.ser").close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
