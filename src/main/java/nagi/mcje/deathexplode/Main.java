package nagi.mcje.deathexplode;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener{

    public float power; //爆発の強さ
    public boolean fire;    //爆発時の着火
    public boolean breakblock;  //爆発時のブロック破壊
    public boolean player, mob, item;

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this,this);
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        power = Float.parseFloat(config.getString("power"));
        fire = config.getBoolean("fire");
        breakblock = config.getBoolean("breakblock");
        player = config.getBoolean("player");
        mob = config.getBoolean("mob");
        item = config.getBoolean("item");
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        Boolean explosion = false;
        if(entity instanceof Player && player) explosion = true;
        else if(entity instanceof Mob && mob) explosion = true;
        else if(entity instanceof Item && item) explosion = true;
        if(explosion) loc.getWorld().createExplosion(loc, power, fire, breakblock);
    }
}
