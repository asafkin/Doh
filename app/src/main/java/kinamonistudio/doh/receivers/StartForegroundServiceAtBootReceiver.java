package kinamonistudio.doh.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kinamonistudio.doh.Constants;
import kinamonistudio.doh.services.ForegroundService;

/**
 * Created by Asaf on 23/04/2016.
 */
public class StartForegroundServiceAtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, ForegroundService.class);
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            context.startService(serviceIntent);
        }
    }
}
