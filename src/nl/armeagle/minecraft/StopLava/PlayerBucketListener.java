package nl.armeagle.minecraft.StopLava;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class PlayerBucketListener extends PlayerListener {
	private StopLava plugin;
	
	public PlayerBucketListener(StopLava plugin) {
		this.plugin = plugin;
	}
	
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
			
			// make it so the client is updated too
			player.setItemInHand(null); // need to set to null first or else apparently the client doesn't notice the change
			// then set the bucket back to normal as before
			this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin,
				new Runnable() {public void run() {
					player.setItemInHand(new ItemStack(Material.BUCKET, 1));
				}}
				, 1);
		}
	}
	
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		if ( !this.plugin.isEnabled() ) {
			return;
		}

		final Player player = event.getPlayer();
		// allow for players with permissions or op
		if ( this.plugin.hasPermission(player, StopLava.CAN_USE_LAVA) ) {
			return;
		}
		
		if ( event.getBlockClicked().getType() == Material.STATIONARY_LAVA ||
				event.getBlockClicked().getType() == Material.LAVA ) {
			
			StopLava.log("Prevented "+ player.getName() + 
					" from filling bucket with lava at "+ event.getBlockClicked().getWorld().getName() +":"+ StopLava.locToStr(event.getBlockClicked().getLocation()));
			event.setCancelled(true);

			
			// make it so the client is updated too
			player.setItemInHand(null); // need to set to null first or else apparently the client doesn't notice the change
			// then set the bucket back to normal as before
			this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin,
				new Runnable() {public void run() {
					player.setItemInHand(new ItemStack(Material.BUCKET, 1));
				}}
				, 1);
			
			player.sendBlockChange(event.getBlockClicked().getLocation(),
					event.getBlockClicked().getType(),
					event.getBlockClicked().getData());
			
		}
	}
}
