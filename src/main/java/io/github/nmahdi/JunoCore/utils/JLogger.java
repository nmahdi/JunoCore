package io.github.nmahdi.JunoCore.utils;


import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JLogger {

    private static boolean debug = false;
    private static Logger logger = Bukkit.getLogger();
    private static String suffix = "[JunoCore] ";
    private static String debugSuffix = "[JunoCore/DEBUG] ";

    public static void debug(JunoManager junoManager, String s){
        if(junoManager.isDebugging()) logger.info(debugSuffix + s);
    }

    public static void debug(String s){
        if(debug) logger.info(debugSuffix + s);
    }

    public static void log(String s){
        logger.info(suffix + s);
    }

    public static void error(String s){
        logger.log(Level.SEVERE, suffix + s);
    }

}
