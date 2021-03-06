package com.mcdr.corruption.config;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mcdr.corruption.Corruption;


public abstract class GlobalConfig extends BaseConfig {
	public static boolean checkUpdateOnStartup = true;
	public static boolean reloadAfterUpdating = true;
	
	public enum CommandParam {
		IGNORE_DELAY (120) {@Override public String getNode() {return "Command.Ignore.Delay";}},
		SPAWN_MAX (50) {@Override public String getNode() {return "Command.Spawn.Max";}};
		
		private int value;
		
		private CommandParam(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public abstract String getNode();
		
		public void setValue(int value) {
			this.value = value;
		}
	}
	
	public enum MessageParam {
		PLAYER_FOUND_BOSS_1 ("&cYou attacked a Corrupted {BOSSNAME} boss!") {@Override public String getNode() {return "Message.PlayerFoundBoss.ToPlayer";}},
		PLAYER_FOUND_BOSS_2 ("&c{PLAYER} found a Corrupted {BOSSNAME}!") {@Override public String getNode() {return "Message.PlayerFoundBoss.ToOthers";}},
		BOSS_FOUND_PLAYER_1 ("&cA Corrupted {BOSSNAME} found you!") {@Override public String getNode() {return "Message.BossFoundPlayer.ToPlayer";}},
		BOSS_FOUND_PLAYER_2 ("&cA Corrupted {BOSSNAME} found {PLAYER}!") {@Override public String getNode() {return "Message.BossFoundPlayer.ToOthers";}},
		PROXIMITY ("&4You sense a magical disruption...") {@Override public String getNode() {return "Message.Proximity";}},
		CUSTOMBOSSNAME("Corrupted {BOSSNAME} &7[{HEALTH}/{MAXHEALTH}]") {@Override public String getNode() {return "Message.CustomBossName";}},
		VIEWERMESSAGE("Corrupted {BOSSNAME} Health: &7{HEALTH} (-{DAMAGE})") {@Override public String getNode() {return "Message.ViewerMessage";}},
		VIEWERDEFEATED("Corrupted {BOSSNAME} has been defeated."){@Override public String getNode() {return "Message.ViewerDefeated";}},
		VIEWERDAMAGEABSORBED("Corrupted {BOSSNAME}'s armour absorbed the damage."){@Override public String getNode() {return "Message.ViewerDamageAbsorbed";}};
		
		private String message;
		
		private MessageParam(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
		
		public abstract String getNode();
		
		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	public enum TaskParam {
		DRAW_BOSS_EFFECT (1.0) {@Override public String getNode() {return "Task.DrawBossEffect";}},
		CHECK_ENTITY_EXISTENCE (2.0) {@Override public String getNode() {return "Task.CheckEntityExistence";}},
		CHECK_ENTITY_PROXIMITY (0.5) {@Override public String getNode() {return "Task.CheckEntityProximity";}},
		SAVE_PLAYER_DATA (150.0) {@Override public String getNode() {return "Task.SavePlayerData";}},
		LOAD_PLAYER_DATA (5.0) {@Override public String getNode() {return "Task.LoadPlayerData";}};
		
		private double value;
		
		private TaskParam(double value) {
			this.value = value;
		}
		
		public double getValue() {
			return value;
		}
		
		public abstract String getNode();
		
		public void setValue(double value) {
			this.value = value;
		}
	}
	
	public enum BossParam {
		OVERWRITE_DROPS (false) {@Override public String getNode() {return "Boss.OverwriteDrops";}},
		ENABLE_BIOMES (false) {@Override public String getNode() {return "Boss.EnableBiomes";}},
		USE_HEALTH_AS_MULTIPLIER (true) {@Override public String getNode() {return "Boss.SetHealthAsMultiplier";}},
		USE_DAMAGE_AS_MULTIPLIER (true) {@Override public String getNode() {return "Boss.SetDamageAsMultiplier";}},
		USE_EXPERIENCE_AS_MULTIPLIER (true) {@Override public String getNode() {return "Boss.SetExperienceAsMultiplier";}};
		
		private boolean boolValue;
		
		private BossParam(boolean value){
			setValue(value);
		}		
		
		public boolean getValue(){
			return boolValue;
		}
		
		public abstract String getNode();
		
		public void setValue(boolean value) {
			this.boolValue = value;
		}
	}
	
	public static void Load() {
		File file = new File(Corruption.in.getDataFolder().getPath(), "config.yml");
		
		if (!file.exists())
			copyResource(file, "com/mcdr/corruption/config/config.yml");
		
		YamlConfiguration yamlConfig = loadConfig(file);
		
		if(yamlConfig.isSet("CheckUpdateOnStartup"))
			checkUpdateOnStartup = yamlConfig.getBoolean("CheckUpdateOnStartup");
		else {
			yamlConfig.set("CheckUpdateOnStartup", true);
			saveConfig(yamlConfig, "config.yml");
		}
		
		if(yamlConfig.isSet("ReloadAfterUpdating"))
			checkUpdateOnStartup = yamlConfig.getBoolean("ReloadAfterUpdating");
		else {
			yamlConfig.set("ReloadAfterUpdating", true);
			saveConfig(yamlConfig, "config.yml");
		}
		
		LoadCommandParams(yamlConfig);
		LoadMessageParams(yamlConfig);
		LoadTaskParams(yamlConfig);
		LoadBossParams(yamlConfig);
	}
	
	private static void LoadCommandParams(YamlConfiguration yamlConfig) {
		for (CommandParam commandParam : CommandParam.values()) {
			String node = commandParam.getNode();
			
			if (!yamlConfig.contains(node)) {
				Corruption.l.warning("["+Corruption.pluginName+"] Adding '" + node + "' in config file.");
				yamlConfig.set(node, commandParam.getValue());
				continue;
			}
			saveConfig(yamlConfig, "config.yml");
			commandParam.setValue(yamlConfig.getInt(node));
		}
	}
	
	private static void LoadMessageParams(YamlConfiguration yamlConfig) {
		for (MessageParam messageParam : MessageParam.values()) {
			String node = messageParam.getNode();
			
			if (!yamlConfig.contains(node)) {
				Corruption.l.warning("["+Corruption.pluginName+"] Adding '" + node + "' in config file.");
				yamlConfig.set(node, messageParam.getMessage());
				continue;
			}
			saveConfig(yamlConfig, "config.yml");
			messageParam.setMessage(yamlConfig.getString(node));
		}
	}
	
	private static void LoadTaskParams(YamlConfiguration yamlConfig) {
		for (TaskParam taskParam : TaskParam.values()) {
			String node = taskParam.getNode();
				
			if (!yamlConfig.contains(node)) {
				Corruption.l.warning("["+Corruption.pluginName+"] Adding '" + node + "' in config file.");
				yamlConfig.set(node, taskParam.getValue());
				continue;
			}
			saveConfig(yamlConfig, "config.yml");
			taskParam.setValue(yamlConfig.getDouble(node));
		}
	}
	
	private static void LoadBossParams(YamlConfiguration yamlConfig) {
		for (BossParam bossParam : BossParam.values()) {
			String node = bossParam.getNode();

			if (!yamlConfig.contains(node)) {
				Corruption.l.warning("["+Corruption.pluginName+"] Adding '" + node + "' in config file.");
				yamlConfig.set(node, bossParam.getValue());
				continue;
			}
			saveConfig(yamlConfig, "config.yml");
			bossParam.setValue(yamlConfig.getBoolean(node));
		}
	}
}
