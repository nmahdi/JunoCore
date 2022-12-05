package io.github.nmahdi.JunoCore.player.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ComponentBuilder {

	private final TextComponent.Builder component = Component.text();

	public ComponentBuilder append(String name, TextColor color){
		component.append(Component.text(name).color(color));
		return this;
	}

	public ComponentBuilder append(String name, TextColor color, boolean bold){
		component.append(Component.text(name).color(color).decoration(TextDecoration.BOLD, bold));
		return this;
	}

	public TextComponent build() {
		return component.build().decoration(TextDecoration.ITALIC, false);
	}
}
