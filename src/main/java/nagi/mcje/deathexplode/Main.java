package nagi.mcje.deathexplode;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
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
    public boolean player, mob;

    public Configuration config;

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this,this);
        getCommand("de").setExecutor(this);
        saveDefaultConfig();
        config = getConfig();
        power = Float.parseFloat(config.getString("power"));
        fire = config.getBoolean("fire");
        breakblock = config.getBoolean("breakblock");
        player = config.getBoolean("player");
        mob = config.getBoolean("mob");
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        Boolean explode = false;
        if(entity instanceof Player && player) explode = true;
        else if(entity instanceof Mob && mob) explode = true;
        if(explode) loc.getWorld().createExplosion(loc, power, fire, breakblock);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("de")){
            if(args.length == 0){
                sendCommandHelp(sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("power")){
                if(args.length == 1){
                    sender.sendMessage("値を入力して下さい");
                    return true;
                }
                try{
                    float value = Float.parseFloat(args[1]);
                    config.set("power", value);
                    saveConfig();
                    power = value;
                    sender.sendMessage("爆発の強さを"+power+"に変更しました");
                }catch(NumberFormatException e){
                    sender.sendMessage("有効な値を入力して下さい(小数可)");
                }
                return true;
            }else if(args[0].equalsIgnoreCase("fire")
                || args[0].equalsIgnoreCase("break")
                || args[0].equalsIgnoreCase("player")
                || args[0].equalsIgnoreCase("mob")){
                if(args.length == 1){
                    sender.sendMessage("値を入力して下さい");
                    return true;
                }
                try{
                    boolean value = Boolean.parseBoolean(args[1]);
                    String v = value ? "有効" : "無効";
                    if(args[0].equalsIgnoreCase("fire")){
                        config.set("fire", value);
                        saveConfig();
                        fire = value;
                        sender.sendMessage("爆発時の炎上を"+v+"にしました");
                    }else if(args[0].equalsIgnoreCase("break")){
                        config.set("breakblock", value);
                        saveConfig();
                        breakblock = value;
                        sender.sendMessage("爆発時のブロック破壊を"+v+"にしました");
                    }else if(args[0].equalsIgnoreCase("player")){
                        config.set("player", value);
                        saveConfig();
                        player = value;
                        sender.sendMessage("プレイヤー死亡時の爆発を"+v+"にしました");
                    }else if(args[0].equalsIgnoreCase("mob")){
                        config.set("mob", value);
                        saveConfig();
                        mob = value;
                        sender.sendMessage("Mob死亡時の爆発を"+v+"にしました");
                    }
                }catch(NumberFormatException e){
                    sender.sendMessage("有効な値を入力して下さい(trueまたはfalse)");
                }
                return true;
            }else if(args[0].equalsIgnoreCase("settings")){
                sendSettings(sender);
            }else{
                sendCommandHelp(sender);
                return true;
            }
        }
        return true;
    }

    public void sendCommandHelp(CommandSender sender){
        sender.sendMessage("DeathExplodeコマンド");
        sender.sendMessage("/de power <数値> : 爆発の強さを変更します(小数可)");
        sender.sendMessage("/de fire <true|false> : 爆発時の炎上の有無を変更します");
        sender.sendMessage("/de break <true|false> : 爆発時のブロック破壊の有無を変更します");
        sender.sendMessage("/de player <true|false> : プレイヤー死亡時の爆発の有無を変更します");
        sender.sendMessage("/de mob <true|false> : Mob死亡時の爆発の有無を変更します");
        sender.sendMessage("/de settings : 現在の設定を表示します");
    }

    public void sendSettings(CommandSender sender){
        sender.sendMessage("DeathExplode 現在の設定");
        sender.sendMessage("爆発の強さ : "+power);
        sender.sendMessage("爆発時の炎上 : "+ (fire ? "有効" : "無効"));
        sender.sendMessage("爆発時のブロック破壊 : "+ (breakblock ? "有効" : "無効"));
        sender.sendMessage("プレイヤー死亡時の爆発 : "+ (player ? "有効" : "無効"));
        sender.sendMessage("Mob死亡時の爆発 : "+ (mob ? "有効" : "無効"));
    }
}
