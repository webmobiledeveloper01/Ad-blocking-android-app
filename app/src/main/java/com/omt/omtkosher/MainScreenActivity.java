package com.omt.omtkosher;

import static android.app.Service.START_STICKY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainScreenActivity extends AppCompatActivity {

    private static final int VPN_PERMISSION_REQUEST_CODE = 1001;
   //private static final int VPN_REQUEST_CODE = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        CardView store = findViewById(R.id.store_cv);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreenActivity.this,AppStore.class);
                startActivity(i);
            }
        });

        // Check if the VPN permission is granted
        if (!isVpnPermissionGranted()) {
            // Request VPN permission
            Intent vpnPermissionIntent = VpnService.prepare(this);
            if (vpnPermissionIntent != null) {
                startActivityForResult(vpnPermissionIntent, VPN_PERMISSION_REQUEST_CODE);
            } else {
                startVpnService(); // Permission already granted
            }
        } else {
            startVpnService(); // Permission already granted
        }
    }

    private boolean isVpnPermissionGranted() {
        return VpnService.prepare(this) == null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VPN_PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startVpnService();
            } else {
                Toast.makeText(this, "Denied Access", Toast.LENGTH_SHORT).show();
                // User denied VPN permission request, handle this accordingly
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void startVpnService() {
        Intent vpnServiceIntent = new Intent(this, AdBlockerVPNService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(vpnServiceIntent);
        } else {
            startService(vpnServiceIntent);
        }
    }


//    private void startVpnService() {
//        Intent vpnIntent = AdBlockerVPNService.prepare(this);
//        if (vpnIntent != null) {
//            startActivityForResult(vpnIntent, VPN_REQUEST_CODE);
//        } else {
//            onActivityResult(VPN_REQUEST_CODE, RESULT_OK, null);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == VPN_REQUEST_CODE && resultCode == RESULT_OK) {
//            startService(new Intent(this, MyVPNService.class));
//        }
//    }
//
//    private void stopVpnService() {
//        stopService(new Intent(this, MyVPNService.class));
//    }

}