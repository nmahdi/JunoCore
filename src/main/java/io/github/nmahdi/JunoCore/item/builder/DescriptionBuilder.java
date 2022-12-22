package io.github.nmahdi.JunoCore.item.builder;

import io.github.nmahdi.JunoCore.utils.JLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;

public class DescriptionBuilder {

	private ArrayList<Component> list = new ArrayList<>();
	private TextComponent.Builder current = Component.text();

	public DescriptionBuilder(String string, TextColor color){
		current.append(Component.text(string).color(color));
	}

	public DescriptionBuilder(Component component){
		current.append(component);
	}

	/**
	 * Used to skip the first line in the description.
	 * @param skip if true skips first line
	 */
	public DescriptionBuilder(boolean skip){
		if(skip) list.add(Component.empty());
	}

	public DescriptionBuilder append(String string, TextColor color){
		current.append(Component.text(string).color(color));
		return this;
	}

	public DescriptionBuilder append(String string, TextColor color, boolean bold){
		current.append(Component.text(string).color(color).decoration(TextDecoration.BOLD, bold));
		return this;
	}

	public DescriptionBuilder append(String string, NamedTextColor color){
		current.append(Component.text(string).color(color));
		return this;
	}

	public DescriptionBuilder append(String string, NamedTextColor color, boolean bold){
		current.append(Component.text(string).color(color).decoration(TextDecoration.BOLD, bold));
		return this;
	}

	public DescriptionBuilder append(Component component){
		current.append(component);
		return this;
	}

	public DescriptionBuilder appendAll(ArrayList<Component> components){
		for(Component component : components){
			list.add(component.decoration(TextDecoration.ITALIC, false));
		}
		return this;
	}

	public DescriptionBuilder insert(int index, String string, TextColor color, boolean bold){
		list.add(index, Component.text(string).color(color).decoration(TextDecoration.BOLD, bold));
		return this;
	}

	public DescriptionBuilder insert(int index, String string, TextColor color){
		list.add(index, Component.text(string).color(color).decoration(TextDecoration.ITALIC, false));
		return this;
	}

	public DescriptionBuilder insert(int index, Component component){
		list.add(index, component.decoration(TextDecoration.ITALIC, false));
		return this;
	}

	/**
	 *	Starts a new line, clears temp component.
	 */
	public DescriptionBuilder endLine(){
		list.add(current.decoration(TextDecoration.ITALIC, false).build());
		current = Component.text();
		return this;
	}

	/**
	 *	Adds an empty line, clears temp component
	 */
	public DescriptionBuilder skipLine(){
		list.add(Component.empty());
		current = Component.text();
		return this;
	}

	public ArrayList<Component> getList() {
		return list;
	}
}
