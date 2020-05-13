package me.codedred.xpbottles.commands.tabcompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class XpTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("xp")) {

			
			List<String> finalList = new ArrayList<String>();
			
			if (args.length == 1) {
				List<String> list = new ArrayList<String>();
				if (sender.hasPermission("xp.admin")) {
					list.add("give"); list.add("take");
					list.add("reset"); list.add("create");
					list.add("reload");
				}
				for (String s : list) 
					if (s.startsWith(args[0].toLowerCase()))
						finalList.add(s);
				return finalList;
			}
			
			if (args.length == 2) {
				List<String> secondList = new ArrayList<String>();
				if (!args[0].equalsIgnoreCase("reload"))
					secondList.add("<player>");
				for (String s : secondList) 
					if (s.startsWith(args[1].toLowerCase()))
						finalList.add(s);
				return finalList;
			}
			
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("create")) {
					List<String> thirdList = new ArrayList<String>();
					thirdList.add("<exp>");
					for (String s : thirdList) 
						if (s.startsWith(args[2].toLowerCase()))
							finalList.add(s);
					return finalList;
				}
			}
			
		}
		return null;
	}

}
