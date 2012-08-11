package nl.armeagle.minecraft.StopLava;

import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StopLava extends JavaPlugin{
    public final static String CAN_GET_LAVA = "StopLava.canGetLava";
    public final static String CAN_USE_LAVA = "StopLava.canUseLava";
    
    private static final Logger log = Logger.getLogger("Minecraft");
    
    private BlockCanBuildListener blockListener;
    private PlayerBucketListener playerListener;
    private boolean hasRegistered = false;
    
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
        }
        PluginDescriptionFile pdfFile = this.getDescription();
        StopLava.log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }
    
    public static void log(String string) {
        log.info("StopLava: "+ string);
    }

    public static String locToStr(Location loc) {
        return loc.getBlockX() +","+ loc.getBlockY() +","+ loc.getBlockZ();
    }
}