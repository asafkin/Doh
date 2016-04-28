package kinamonistudio.doh;

/**
 * Created by Asaf on 22/04/2016.
 */
public class Constants {

    public interface ACTION {
        public static String MAIN_ACTION = "kinamonistudio.doh.foregroundservice.action.main";
        public static String DISABLE_TOUCHES_ACTION = "kinamonistudio.doh.foregroundservice.action.disabletouches";
        public static String ENABLE_TOUCHES_ACTION = "kinamonistudio.doh.foregroundservice.action.enabletouches";
        public static String STARTFOREGROUND_ACTION = "kinamonistudio.doh.foregroundservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "kinamonistudio.doh.foregroundservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
