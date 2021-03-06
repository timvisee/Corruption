package com.mcdr.corruption.listener;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.mcdr.corruption.CorruptionAPI;
import com.mcdr.corruption.config.MagicSpellsConfig;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellTargetEvent;

public class CorMagicSpellsListener implements Listener {
    
	private List<String> enabledSpells;
	private List<String> disabledSpells;
	
    public CorMagicSpellsListener(){
        enabledSpells = MagicSpellsConfig.enabledSpells;
        disabledSpells = MagicSpellsConfig.disabledSpells;
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpellTarget(SpellTargetEvent event){
        if(MagicSpellsConfig.allEnabled){
        	return;
	    }
        
        if(CorruptionAPI.isBoss(event.getTarget())){
	        Spell s = event.getSpell();
	        if(MagicSpellsConfig.useWhitelist){
	        	if(!(enabledSpells.contains(s.getName()))){
	        		event.setCancelled(true);
	        	}
	        }
	        else{
	        	if(disabledSpells.contains(s.getName())){
	        		event.setCancelled(true);
	        	}
	        }
        }
    }
}