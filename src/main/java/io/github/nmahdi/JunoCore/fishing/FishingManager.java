package io.github.nmahdi.JunoCore.fishing;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;

public class FishingManager implements JunoManager {

	private boolean debugMode;
	private JCore main;

	public FishingManager(JCore main){
		this.main = main;
	}

	@Override
	public void debug(String s) {
		JLogger.debug(this, s);
	}

	@Override
	public void setDebugMode(boolean mode) {
		this.debugMode = mode;
	}

	@Override
	public boolean isDebugging() {
		return debugMode;
	}

	@Override
	public void onDisable() {

	}

}
