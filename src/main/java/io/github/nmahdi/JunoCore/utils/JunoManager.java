package io.github.nmahdi.JunoCore.utils;

public interface JunoManager {

    default void debug(String s){
        JLogger.debug(this, s);
    }

    void setDebugMode(boolean mode);
    boolean isDebugging();
    void onDisable();

}
