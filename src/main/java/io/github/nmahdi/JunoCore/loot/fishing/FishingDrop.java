package io.github.nmahdi.JunoCore.loot.fishing;

public class FishingDrop {

	private int weight;
	private int weightIndex;

	public FishingDrop(int weight, int weightIndex){
		this.weight = weight;
		this.weightIndex = weightIndex;
	}

	public int getWeight() {
		return weight;
	}

	public int getWeightIndex() {
		return weightIndex;
	}
}
