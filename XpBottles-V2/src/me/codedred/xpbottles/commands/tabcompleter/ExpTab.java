package me.codedred.xpbottles.commands.tabcompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ExpTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("exp")) {

			
			List<String> finalList = new ArrayList<String>();
			
			if (args.length == 1) {
				List<String> list = new ArrayList<String>();
				if (sender.hasPermission("xp.withdraw"))
					list.add("withdraw");
				for (String s : list) 
					if (s.startsWith(args[0].toLowerCase()))
						finalList.add(s);
				return finalList;
			}
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("withdraw")) {
					List<String> secondList = new ArrayList<String>();
					secondList.add("<exp>");
					if (sender.hasPermission("xp.all"))
						secondList.add("all");
					if (sender.hasPermission("xp.give"))
						secondList.add("give");
					if (sender.hasPermission("xp.random"))
						secondList.add("random");
					for (String s : secondList) 
						if (s.startsWith(args[1].toLowerCase()))
							finalList.add(s);
					return finalList;
				}
			}
			
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("withdraw"))
					if (args[1].equalsIgnoreCase("give")) {
						List<String> thirdList = new ArrayList<String>();
						thirdList.add("<exp>");
						for (String s : thirdList) 
							if (s.startsWith(args[2].toLowerCase()))
								finalList.add(s);
						return finalList;
					}
			}
			
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("withdraw"))
					if (args[1].equalsIgnoreCase("give")) {
						List<String> thirdList = new ArrayList<String>();
						thirdList.add("<player>");
						for (String s : thirdList) 
							if (s.startsWith(args[3].toLowerCase()))
								finalList.add(s);
						return finalList;
					}
			}
			
		}
		return null;
	}

}
