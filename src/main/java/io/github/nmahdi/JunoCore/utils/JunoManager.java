package io.github.nmahdi.JunoCore.utils;

public interface JunoManager {

    default void debug(String s){
        JLogger.debug(this, s);
    }

    boolean isDebugging();
    void onDisable();

}
