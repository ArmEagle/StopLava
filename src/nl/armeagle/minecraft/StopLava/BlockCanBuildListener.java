package nl.armeagle.minecraft.StopLava;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockCanBuildListener implements Listener {
    private StopLava plugin;
    
    public BlockCanBuildListener(StopLava plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if ( !this.plugin.isEnabled() ) {
            return;
        }
        // allow for players with permissions or op
        if ( event.getPlayer().hasPermission(StopLava.CAN_USE_LAVA) ) {
            return;
        }
        
        if ( event.getBlockPlaced().getType() == Material.LAVA ||
                event.getBlockPlaced().getType() == Material.STATIONARY_LAVA ) {
            StopLava.log("Prevented "+ event.getPlayer().getName() + 
                    " from placing lava at "+ StopLava.locToStr(event.getBlock().getLocation()));
            
            event.setCancelled(true);
            event.getPlayer().updateInventory();            
        }
    }
}