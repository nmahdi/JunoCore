package io.github.nmahdi.JunoCore.player.display;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardManager implements JunoManager {

	private JCore main;
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
	private Date date = new Date();

	private String LOCATION_TEAM = "location";
	private String COINS_TEAM = "coins";
	private String DATE_TEAM = "date";

	public ScoreboardManager(JCore main){
		this.main = main;
	}

	public void set(GamePlayer player){
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = scoreboard.registerNewObjective("JunoCore", Criteria.DUMMY, Component.text("Juno Core").color(NamedTextColor.RED));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		obj.getScore(ChatColor.BLUE + ChatColor.BOLD.toString() + ">>Location").setScore(15);

		Team loc = scoreboard.registerNewTeam(LOCATION_TEAM);
		loc.addEntry(ChatColor.BLACK.toString());
		loc.prefix(Component.text(player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ()).color(NamedTextColor.AQUA));
		obj.getScore(ChatColor.BLACK.toString()).setScore(14);

		obj.getScore(" ").setScore(13);

		obj.getScore(ChatColor.GOLD + ChatColor.BOLD.toString() + ">>Coins").setScore(12);

		Team c = scoreboard.registerNewTeam(COINS_TEAM);
		c.addEntry(ChatColor.GOLD.toString());
		c.prefix(Component.text(player.getCoins()).color(NamedTextColor.GOLD));
		obj.getScore(ChatColor.GOLD.toString()).setScore(11);

		obj.getScore("").setScore(10);

		obj.getScore(ChatColor.GRAY + formatter.format(date)).setScore(9);

		/*Team date = scoreboard.registerNewTeam(DATE_TEAM);
		date.addEntry(DATE_ENTRY);
		date.prefix(Component.text(formatter.format(date)).color(JColors.GRAY));
		obj.getScore(DATE_ENTRY).setScore(10); */

		player.setScoreboard(scoreboard);
	}

	public void update(GamePlayer player){
		Scoreboard scoreboard = player.getScoreboard();

		scoreboard.getTeam(LOCATION_TEAM).prefix(Component.text(player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ()).color(NamedTextColor.AQUA));
		scoreboard.getTeam(COINS_TEAM).prefix(Component.text(player.getCoins()).color(NamedTextColor.GOLD));
	}

	@Override
	public boolean isDebugging() {
		return false;
	}

	@Override
	public void onDisable() {

	}
}
