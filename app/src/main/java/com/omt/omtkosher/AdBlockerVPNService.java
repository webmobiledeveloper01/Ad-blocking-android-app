package com.omt.omtkosher;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.os.ParcelFileDescriptor;

import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

public class AdBlockerVPNService extends VpnService {

    private Thread vpnThread;
    private ParcelFileDescriptor vpnInterface;
    private static final String CHANNEL_ID = "MyVPNServiceChannel";
    private static final String CHANNEL_NAME = "My VPN Service";




    private ParcelFileDescriptor createVpnInterface() throws Exception {
        VpnService.Builder builder = new VpnService.Builder();
        builder.setMtu(1500);
        builder.addAddress("94.140.15.15", 32);
        //builder.addRoute("0.0.0.0", 0);
        builder.addDnsServer("176.103.130.130");
        builder.setBlocking(true);
        builder.setSession(getString(R.string.app_name));

        // Establish the VPN interface
        ParcelFileDescriptor vpnInterface = builder.establish();
        if (vpnInterface != null) {
            return vpnInterface;
        } else {
            throw new Exception("VPN interface creation failed.");
        }
    }

    private void stopVPN() {
        try {
            if (vpnInterface != null) {
                vpnInterface.close();
                vpnInterface = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void run() {
        try {
            // Create a new VPN interface
            vpnInterface = createVpnInterface();
            // Set up a VPN tunnel and intercept network traffic
            // Implement the logic to block ads or analyze packets here
            // Remember to respect user consent and provide a way to enable/disable ad-blocking

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           stopSelf();// Stop the service when the VPN thread completes its task
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, createNotification());

        // Start the VPN service in a separate thread
        vpnThread = new Thread(this::run);
        vpnThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (vpnThread != null) {
            vpnThread.interrupt();
        }
        stopForeground(true);



    }

    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("VPN is running")
                .setContentText("Ad Blocker is active.")
                .setSmallIcon(R.drawable.green_tick);

        // Set pending intent if you want to open your app when the notification is clicked.
        // For simplicity, we will not set any pending intent here.

        return builder.build();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }


}
