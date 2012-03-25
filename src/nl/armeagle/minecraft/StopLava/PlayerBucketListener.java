package nl.armeagle.minecraft.StopLava;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerBucketListener implements Listener {
	private StopLava plugin;
	
	public PlayerBucketListener(StopLava plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if ( !this.plugin.isEnabled() ) {
			return;
		}
		// allow for players with permissions or op
		final Player player = event.getPlayer();
		if ( this.plugin.hasPermission(player, StopLava.CAN_USE_LAVA) ) {
			return;
		}
		
		if ( event.getBucket() == Material.LAVA_BUCKET ) {
			StopLava.log("Prevented "+ player.getName() + 
					" from emptying lava bucket at "+ event.getBlockClicked().getWorld().getName() +":"+ StopLava.locToStr(event.getBlockClicked().getLocation()));
			
			event.setCancelled(true);
			player.updateInventory();
		}
	}
	
	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		if ( !this.plugin.isEnabled() ) {
			return;
		}

		final Player player = event.getPlayer();
		// allow for players with permissions or op
		if ( this.plugin.hasPermission(player, StopLava.CAN_GET_LAVA) ) {
			return;
		}
		
		if ( event.getBlockClicked().getType() == Material.STATIONARY_LAVA ||
				event.getBlockClicked().getType() == Material.LAVA ) {
			
			StopLava.log("Prevented "+ player.getName() + 
					" from filling bucket with lava at "+ event.getBlockClicked().getWorld().getName() +":"+ StopLava.locToStr(event.getBlockClicked().getLocation()));
			event.setCancelled(true);
			player.updateInventory();
		}
	}
}
