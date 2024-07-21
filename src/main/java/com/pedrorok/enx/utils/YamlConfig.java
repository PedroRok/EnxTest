package com.pedrorok.enx.utils;

import com.destroystokyo.paper.ParticleBuilder;
import com.pedrorok.enx.Main;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rok, Pedro Lucas nmm. Created on 29/12/2023
 * @project ArtequeGames
 */
public class YamlConfig {

	private Plugin plugin;

	private final String name;

	private File file;

	private FileConfiguration config;

	public String getName() {
		return name;
	}

	public Location getLocation(String path) {
		ConfigurationSection section = getConfig().getConfigurationSection(path);
		if (section == null) return null;
		World world = Bukkit.getWorld(section.getString("world"));
		double x = section.getDouble("x");
		double y = section.getDouble("y");
		double z = section.getDouble("z");
		float yaw = (float) section.getDouble("yaw");
		float pitch = (float) section.getDouble("pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public void setLocation(String path, Location location) {
		ConfigurationSection section = getSection(path);
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());
		section.set("world", location.getWorld().getName());
	}

	/**
	 * Seta o Plugin da Config
	 *
	 * @param plugin Plugin
	 */
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * @return Arquivo
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return Config ({@link FileConfiguration}
	 */
	public FileConfiguration getConfig() {
		return config;
	}

	public YamlConfig(String name, Plugin plugin) {
		this.plugin = plugin;
		if (plugin == null)
			this.plugin = JavaPlugin.getProvidingPlugin(getClass());
		this.name = name;
		reloadConfig();
	}

	public YamlConfig(String name) {
		this(name, null);
	}

	/**
	 * Recarrega a config pelo conteudo do Arquivo
	 *
	 * @return Config
	 */
	public YamlConfig reloadConfig() {
		file = new File(plugin.getDataFolder(), name);
		if (!file.exists()) {
			file.getParentFile().mkdir();
			saveDefaultConfig();
		}
		config = YamlConfiguration.loadConfiguration(file);
		return this;
	}

	/**
	 * Salva Config em forma de Texto no Arquivo
	 *
	 * @return Config
	 */
	public YamlConfig saveConfig() {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return this;
	}

	/**
	 * @param path Path
	 * @return Pega uma mensagem
	 */
	public String message(String path) {
		return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
	}

	public List<String> getMessages(String path) {
		List<String> messages = new ArrayList<>();

		for (String line : getStringList(path)) {
			messages.add(toChatMessage(line));
		}

		return messages;

	}

	/**
	 * Salva a Config padr§o caso n§o existe a Arquivo
	 */
	public void saveDefaultConfig() {
		if (plugin.getResource(name) != null)
			plugin.saveResource(name, false);
	}

	/**
	 * Salva a config padr§o
	 */
	public void saveResource() {
		plugin.saveResource(name, true);
	}

	/**
	 * Remove a Secao
	 *
	 * @param path Secao
	 */
	public void remove(String path) {
		config.set(path, null);
	}

	/**
	 * Salva os padr§es da Config
	 *
	 * @return
	 */
	public YamlConfig saveDefault() {
		config.options().copyDefaults(true);
		saveConfig();
		return this;
	}

	/**
	 * @param path Secao
	 * @return Item da Secao
	 */
	public ItemStack getItem(String path) {
		return (ItemStack) getItemStack(path);
	}

	@SuppressWarnings("deprecation")
	public void setItem(String path, ItemStack item) {
		set(path, item);
	}

	public Location toLocation(String path) {
		String text = getString(path);
		String[] split = text.split(",");
		World world = Bukkit.getWorld(split[0]);
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		float yaw = Float.parseFloat(split[4]);
		float pitch = Float.parseFloat(split[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}

	public void saveLocation(String path, Location location) {
		String text = location.getWorld().getName() + "," +
				location.getX() + "," +
				location.getY() + "," +
				location.getZ() + "," +
				location.getYaw() + "," +
				location.getPitch();
		set(path, text);
	}


	public static String toChatMessage(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String toConfigMessage(String text) {
		return text.replace("§", "&");
	}

	public boolean delete() {
		return file.delete();
	}

	public boolean exists() {
		return file.exists();
	}

	public void add(String path, Object value) {
		config.addDefault(path, value);
	}

	public boolean contains(String path) {
		return config.contains(path);
	}

	public ConfigurationSection create(String path) {
		return config.createSection(path);
	}

	public Object get(String path) {

		return config.get(path);

	}

	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	public ConfigurationSection getSection(String path) {
		if (!config.isConfigurationSection(path)) {
			config.createSection(path);
		}
		return config.getConfigurationSection(path);
	}

	public double getDouble(String path) {
		return config.getDouble(path);
	}

	public int getInt(String path) {
		return config.getInt(path);
	}

	public List<Integer> getIntegerList(String path) {
		return config.getIntegerList(path);
	}

	public ItemStack getItemStack(String path) {
		return config.getItemStack(path);
	}

	public Set<String> getKeys(boolean deep) {
		return config.getKeys(deep);
	}

	public List<?> getList(String path) {
		return config.getList(path);
	}

	public long getLong(String path) {
		return config.getLong(path);
	}

	public List<Long> getLongList(String path) {
		return config.getLongList(path);
	}

	public List<Map<?, ?>> getMapList(String path) {
		return config.getMapList(path);
	}

	public String getString(String path) {
		return config.getString(path);
	}

	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	public Map<String, Object> getValues(boolean deep) {
		return config.getValues(deep);
	}

	public void set(String path, Object value) {
		config.set(path, value);
	}

	/**
	 * Pega um builder de partículas de uma seção
	 *
	 * @since 21/07/2024
	 *
	 * @param section Seção da configuração
	 * @Return Builder de partículas
	 */
	public ParticleBuilder getParticleBuilder(String section) {
		String particleString = config.getString(section + ".type");

		// Verifica se a partícula é válida
		Particle particle = null;
		try {
			particle = Particle.valueOf(particleString);
		} catch (IllegalArgumentException e) {
            Main.LOGGER.error("Invalid particle type: \"{}\" in section {}", particleString, section);
			return null;
		}

		// Valores universais das partículas
		int amount = config.getInt(section + ".amount");
		double extra = config.getDouble(section + ".extra");
		double offset = config.getDouble(section + ".offset");

		// Pega e verifica a cor da partícula caso seja colorida
		Color colorTo = null;
		Color color = null;
		try {
			switch (particle) {
				case DUST_COLOR_TRANSITION: {
					int r = config.getInt(section + ".color.to-r");
					int g = config.getInt(section + ".color.to-g");
					int b = config.getInt(section + ".color.to-b");
					colorTo = Color.fromRGB(r, g, b);
				}
				case DUST: {
					int r = config.getInt(section + ".color.r");
					int g = config.getInt(section + ".color.g");
					int b = config.getInt(section + ".color.b");
					color = Color.fromRGB(r, g, b);
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			Main.LOGGER.error("Invalid particle color in section: {}" , section);
			return null;
		}

		// Cria o builder de partículas coloridas
		if (color != null) {
			ParticleBuilder particleBuilder = new ParticleBuilder(particle).count(amount).extra(extra).offset(offset, offset, offset);
			if (colorTo != null) {
				particleBuilder.colorTransition(color, colorTo);
				return particleBuilder;
			}
			particleBuilder.color(color);
			return particleBuilder;
		}

		String materialString = config.getString(section + ".material");
		if (materialString != null) {
			Material material = null;
			try {
				material = Material.valueOf(materialString);
			} catch (IllegalArgumentException e) {
				Main.LOGGER.error("Invalid material type: \"{}\" in section {}", materialString, section);
				return null;
			}
			return new ParticleBuilder(particle).count(amount).extra(extra).offset(offset, offset, offset).data(material.createBlockData());
		}
		return new ParticleBuilder(particle).count(amount).extra(extra).offset(offset, offset, offset);
	}

}