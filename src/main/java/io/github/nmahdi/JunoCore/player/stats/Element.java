package io.github.nmahdi.JunoCore.player.stats;

import io.github.nmahdi.JunoCore.player.stats.PlayerStat;

public enum Element {
	Fire(PlayerStat.FireElement),
	Water(PlayerStat.WaterElement),
	Air(PlayerStat.AirElement),
	Earth(PlayerStat.EarthElement),
	Lightning(PlayerStat.LightningElement),
	Ice(PlayerStat.IceElement);

	private PlayerStat playerElement;

	Element(PlayerStat playerElement){
		this.playerElement = playerElement;
	}

	public PlayerStat getPlayerElement() {
		return playerElement;
	}
}
