package kinamonistudio.doh;

import android.content.Context;

/**
 * Created by Asaf on 23/04/2016.
 */
public class Utils {

    public static int dpToPx(Context context, int dp){
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int pxToDp(Context context, int px){
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }
}
