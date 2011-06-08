package nl.armeagle.minecraft.StopLava;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class StopLava extends JavaPlugin{
	public final static String CAN_USE_LAVA = "StopLava.canUseLava";
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	private BlockCanBuildListener blockListener;
	private PlayerBucketListener playerListener;
	private boolean hasRegistered = false;
	public PermissionHandler permissionHandler;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		StopLava.log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
	}

	@Override
	public void onEnable() {
		if ( !this.hasRegistered ) {
			this.hasRegistered = true;
			PluginManager pm = this.getServer().getPluginManager();
			this.blockListener = new BlockCanBuildListener(this);
			this.playerListener = new PlayerBucketListener(this);
			pm.registerEvent(Type.PLAYER_BUCKET_EMPTY, this.playerListener, Priority.Normal, this);
			pm.registerEvent(Type.PLAYER_BUCKET_FILL, this.playerListener, Priority.Normal, this);
			pm.registerEvent(Type.BLOCK_PLACE, this.blockListener, Priority.Normal, this);
			
			this.setupPermissions();
		}
		PluginDescriptionFile pdfFile = this.getDescription();
		StopLava.log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}
	
	private void setupPermissions() {
		Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
		
		if (this.permissionHandler == null) {
			if (permissionsPlugin != null) {
				this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
				StopLava.log("Permission system detected, being Op makes no difference.");
			} else {
				StopLava.log("Permission system not detected, defaulting to Op.");
			}
		}
	}
	
	public boolean hasPermission(Player player, String path) {
		if ( this.permissionHandler != null ) {
			StopLava.log("has permission: "+ this.permissionHandler.has(player, path));
			return this.permissionHandler.has(player, path);
		} else {
			StopLava.log("is op: "+ player.isOp());
			return player.isOp();
		}
	}
	
	public static void log(String string) {
		log.info("StopLava: "+ string);
	}

	public static String locToStr(Location loc) {
		return loc.getBlockX() +","+ loc.getBlockY() +","+ loc.getBlockZ();
	}
}
