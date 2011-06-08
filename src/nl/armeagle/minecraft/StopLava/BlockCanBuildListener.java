package nl.armeagle.minecraft.StopLava;

import org.bukkit.Material;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockCanBuildListener extends BlockListener {
	private StopLava plugin;
	
	public BlockCanBuildListener(StopLava plugin) {
		this.plugin = plugin;
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {
		if ( !this.plugin.isEnabled() ) {
			return;
		}
		// allow for players with permissions or op
		if ( this.plugin.hasPermission(event.getPlayer(), StopLava.CAN_USE_LAVA) ) {
			return;
		}
		
		if ( event.getItemInHand().getType() == Material.LAVA ||
			 event.getItemInHand().getType() == Material.STATIONARY_LAVA ||
			 event.getItemInHand().getType() == Material.LAVA_BUCKET) {
			StopLava.log("Prevented "+ event.getPlayer().getDisplayName() + 
					" from placing lava at "+ StopLava.locToStr(event.getBlock().getLocation()));
			event.setCancelled(true);
		}
	}
	
	
}
