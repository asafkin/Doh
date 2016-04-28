package kinamonistudio.doh.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;

import kinamonistudio.doh.Constants;
import kinamonistudio.doh.R;
import kinamonistudio.doh.activities.MainActivity;
import kinamonistudio.doh.views.OverlayView;

/**
 * Created by Asaf on 22/04/2016.
 */
public class ForegroundService extends Service {

    private static final String TAG = ForegroundService.class.getSimpleName();

    WindowManager windowManager;
    OverlayView overlayView;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "In onCreate");

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Intent disableIntent = new Intent(this, ForegroundService.class);
            disableIntent.setAction(Constants.ACTION.DISABLE_TOUCHES_ACTION);
            PendingIntent pDisableIntent = PendingIntent.getService(this, 0, disableIntent, 0);

            Intent enableIntent = new Intent(this, ForegroundService.class);
            enableIntent.setAction(Constants.ACTION.ENABLE_TOUCHES_ACTION);
            PendingIntent pEnableIntent = PendingIntent.getService(this, 0, enableIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Doh!")
                    .setTicker("Doh!")
                    .setContentText("Doh! blocker")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_play, "Block", pDisableIntent)
                    .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Unblock", pEnableIntent).build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
        } else if (intent.getAction().equals(Constants.ACTION.DISABLE_TOUCHES_ACTION)) {
            addOverlayView();
            Log.i(TAG, "Clicked Disable Touches");
        } else if (intent.getAction().equals(Constants.ACTION.ENABLE_TOUCHES_ACTION)) {
            removeOverlayView();
            Log.i(TAG, "Clicked Enable Touches");
        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy");
//        removeOverlayView();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }

    private void addOverlayView() {

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                PixelFormat.TRANSLUCENT);
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
//                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,

        overlayView = new OverlayView(this);
        windowManager.addView(overlayView, params);
    }

    private void removeOverlayView() {
        if (overlayView != null) {
            windowManager.removeView(overlayView);
            overlayView.setOnTouchListener(null);
            overlayView = null;
        }
    }
}
