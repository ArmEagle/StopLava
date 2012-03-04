package nl.armeagle.minecraft.StopLava;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class StopLava extends JavaPlugin{
	public final static String CAN_GET_LAVA = "StopLava.canGetLava";
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
			pm.registerEvents(this.playerListener, this);
			pm.registerEvents(this.blockListener, this);
			
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
				StopLava.log("Permissions plugin detected.");
			} else {
				StopLava.log("Permissions plugin not detected, defaulting to Bukkit's built-in system.");
			}
		}
	}
	
	public boolean hasPermission(Player player, String path) {
		if ( this.permissionHandler != null ) {
			return this.permissionHandler.has(player, path);
		} else {
			return player.hasPermission(path);
		}
	}
	
	public static void log(String string) {
		log.info("StopLava: "+ string);
	}

	public static String locToStr(Location loc) {
		return loc.getBlockX() +","+ loc.getBlockY() +","+ loc.getBlockZ();
	}
}
