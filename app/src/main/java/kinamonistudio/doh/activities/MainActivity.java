package kinamonistudio.doh.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kinamonistudio.doh.Constants;
import kinamonistudio.doh.R;
import kinamonistudio.doh.services.ForegroundService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) findViewById(R.id.button1);
        Button stopButton = (Button) findViewById(R.id.button2);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//                    showOverlayPermissionDialog();
//                } else {
                    startForegroundService();
//                }
                break;
            case R.id.button2:
                stopForegroundService();
                break;

            default:
                break;
        }
    }

    private void startForegroundService() {
        Intent startIntent = new Intent(MainActivity.this, ForegroundService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);
    }

    private void stopForegroundService() {
        Intent stopIntent = new Intent(MainActivity.this, ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);
    }

//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
//            if (!Settings.canDrawOverlays(this)) {
//                // SYSTEM_ALERT_WINDOW permission not granted...
//                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
//                showOverlayPermissionDialog();
//            } else {
//                startForegroundService();
//            }
//        }
//    }

//    public void showOverlayPermissionDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                .setMessage("In order for the app to work you need to enable the following permission")
//                .setIcon(R.mipmap.ic_launcher)
//                .setTitle(R.string.app_name)
//                .setPositiveButton("check permission", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//                    }
//                });
//        builder.show();
//    }
}
