package dredsss.chatforbid;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	private boolean toInterceptMessages = true;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission("chatforbid.toggle")) {
			this.toInterceptMessages = !this.toInterceptMessages;
			this.getServer().broadcastMessage(this.toInterceptMessages ? this.getConfig().getString("chatEnable") : this.getConfig().getString("chatDisable"));
			return false;
		}
		sender.sendMessage(this.getConfig().getString("noPerm"));
		return true;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChatMessage(AsyncPlayerChatEvent e) {
		if (e.getPlayer().hasPermission("chatforbid.bypass"))
			return;
		e.setCancelled(this.toInterceptMessages);
	}
}
