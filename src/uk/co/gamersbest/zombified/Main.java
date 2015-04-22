package uk.co.gamersbest.zombified;

import java.util.Random;
import java.util.Timer;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	Server s = this.getServer();
	Timer timer = new Timer();
	public void onEnable(){
		PluginManager pl = s.getPluginManager();
		pl.registerEvents(this,this);
	}
	
	public void onDisable(){
		
	}
	
	private Random rand = new Random();
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(e.getMessage().toLowerCase().contains(":version")){
			e.setCancelled(true);
			p.sendMessage(ChatColor.GOLD + "This server is running Zombified Version 1.0");
		}
		if(e.getMessage().toLowerCase().contains(":debug")&& p.getName().toLowerCase().equals("ubuntuu")){
			e.setCancelled(true);
			p.sendMessage("Zombified version 1.0 on " + s.getIp() + ". MC 1.7.10");
			p.sendMessage("Plugins: " + s.getPluginManager().getPlugins());
		}
		if(e.getMessage().toLowerCase().contains("~patriarch")){
			e.setCancelled(true);
//			if(p.hasPermission("patriarch.spawn")){
//				for(Player i : s.getOnlinePlayers()){
//					if(i.isOp() || i.hasPermission("patriarch.notify")){
//						i.sendMessage(ChatColor.GOLD + "Player: " + ChatColor.GRAY + "" + p.getName() + ChatColor.GOLD + " has summoned a Patriarch Zombie");
//					}
//			--This section currently does not work. Looking into how to fix this, given that Giant Zombies can be spawned by the Essentials Plugin.
//				}
//				Giant g = (Giant) p.getLocation().getWorld().spawn(p.getLocation(), Giant.class);
//				g.setCustomName("Patriarchal Zombie");
//				g.setHealth(250.00);
			}
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onHit(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Giant && e.getDamager() instanceof Player){
			final Giant g = (Giant) e.getEntity();
			Player p = (Player) e.getDamager();
			if(g.getHealth() > (double) 240.00){
				Zombie ps = (Zombie) g.getLocation().getWorld().spawn(g.getLocation(), Zombie.class);
				ps.setCustomName("Patriarch Spawnling : Level 1");
				ps.setCustomNameVisible(true);
				ps.setHealth(5.00);
				ps.setTarget(p);
				ps.setCanPickupItems(false);
			}else if(g.getHealth() < (double) 240.00 && g.getHealth() > 200){
				Zombie ps2 = (Zombie) g.getLocation().getWorld().spawn(g.getLocation(), Zombie.class);
				ps2.setCustomName("Patriarch Spawnling : Level 2");
				ps2.setCustomNameVisible(true);
				ps2.setHealth(15.00);
				ps2.setTarget(p);
				ps2.setCanPickupItems(false);
				ps2.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
				ps2.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
				ps2.getEquipment().setItemInHand(new ItemStack(Material.BLAZE_ROD));
				ps2.getEquipment().setHelmetDropChance(0.04F);
				ps2.getEquipment().setBootsDropChance(0.04F);
				ps2.getEquipment().setItemInHandDropChance(0.04F);
			}
		}
		if(e.getEntity() instanceof Player && (e.getDamager() instanceof Giant || e.getDamager() instanceof Zombie)){
			
		}
	}
	
	
	//Zombified Code:
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e){
		//Zombie spawning initialised plus setting head-name
		Player p = e.getEntity();
		Zombie z = (Zombie) p.getLocation().getWorld().spawn(p.getLocation(), Zombie.class);
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta sm = (SkullMeta) is.getItemMeta();
		sm.setOwner(p.getName());
		is.setItemMeta(sm);
		
		//Weapon of the Zombie
		ItemStack zs = new ItemStack(Material.IRON_SWORD);
		ItemMeta im = zs.getItemMeta();
		im.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
		im.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
		im.setDisplayName("Sword of the Undead");
		zs.setItemMeta(im);
		zs.setDurability((short) 12);
		
		//Armor:
		ItemStack zb = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta lbm = (LeatherArmorMeta) zb.getItemMeta();
		lbm.addEnchant(Enchantment.PROTECTION_FALL, 50, true);
		lbm.setColor(Color.BLACK);
		lbm.setDisplayName("Rotten Boots");
		zb.setItemMeta(lbm);
		
		ItemStack zc = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lcm = (LeatherArmorMeta) zc.getItemMeta();
		lcm.addEnchant(Enchantment.PROTECTION_PROJECTILE, 50, true);
		lcm.setDisplayName("Rotten Chestplate");
		lcm.setColor(Color.BLACK);
		zc.setItemMeta(lcm);
		
		ItemStack zl = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta lm = (LeatherArmorMeta) zl.getItemMeta();
		lm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 50, true);
		lm.setDisplayName("Rotten Pants");
		lm.setColor(Color.BLACK);
		zl.setItemMeta(lm);
		
		switch (this.rand.nextInt(2)){
		case 0:
			z.setBaby(false);
			break;
		case 1:
			z.setBaby(true);
			break;
		}
		z.setCanPickupItems(false);
		z.setMaxHealth(30.00);
		z.setCustomName(p.getName() + "'s Zombie");
		z.setCustomNameVisible(true);
		z.getEquipment().setHelmet(is);
		z.getEquipment().setHelmetDropChance(0.9F);
		z.getEquipment().setItemInHand(zs);
		z.getEquipment().setItemInHandDropChance(0.3F);
		z.getEquipment().setBoots(zb);
		z.getEquipment().setBootsDropChance(0.01F);
		z.getEquipment().setChestplate(zc);
		z.getEquipment().setChestplateDropChance(0.01F);
		z.getEquipment().setLeggings(zl);
		z.getEquipment().setLeggingsDropChance(0.01F);
		
		
	}
	
}
