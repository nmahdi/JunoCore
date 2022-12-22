package io.github.nmahdi.JunoCore.effects;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class PacketManager implements JunoManager {

	private boolean debugMode;

	private JCore main;
	private Random random;
	private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

	public PacketManager(JCore main){
		debugMode = main.getConfig().getBoolean("debug-mode.packet");
		this.main = main;
		this.random = main.getRandom();
	}

	/**
	 * @param player Player to show
	 * @param block Block affected
	 * @param stage 0-9
	 */
	public void showBlockBreak(Player player, Block block, int stage) throws InvocationTargetException {
		PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
		packet.getIntegers().write(0, player.getEntityId()+1);
		packet.getIntegers().write(1, stage);

		protocolManager.sendServerPacket(player, packet);
	}

	public void hideBlockBreak(Player player, Block block) throws InvocationTargetException {
		showBlockBreak(player, block, -1);
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	@Override
	public void setDebugMode(boolean mode) {

	}

	@Override
	public boolean isDebugging() {
		return debugMode;
	}

	@Override
	public void onDisable() {

	}

}
