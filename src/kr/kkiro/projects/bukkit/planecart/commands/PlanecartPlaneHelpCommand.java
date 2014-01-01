package kr.kkiro.projects.bukkit.planecart.commands;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;
import org.bukkit.command.CommandSender;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

public class PlanecartPlaneHelpCommand extends Command {

    @Override
    public String getCommand() {
        return "plane help";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getHelp() {
        return _("commandPlaneHelp");
    }

    @Override
    public boolean onCommand(Planecart plugin, CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(_("commandPlaneHelpTip2"));
            sender.sendMessage(_("commandPlaneHelpTip3"));
            sender.sendMessage(_("commandPlaneHelpTip4"));
            return true;
        } else {
            //TODO 주제가 생길때마다 Tip를 추가 해 주세요.
            //Switch문이 안되서 아주 슬프긴 하지만

            sender.sendMessage(_("commandPlaneHelpCmdTitle",args[0],(args.length < 2?"1":args[1])));
            //////////////////// craft
            if (args[0].toLowerCase().equals("craft")) {
                if (args.length < 2) {
                    //Page 1
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p1_Craft1"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p1_Craft2"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p1_Craft3"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p1_Craft4"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p1_Craft5"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p1_Craft6"));
                } else if (args[1].equals("2")) {
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p2_Craft1"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p2_Craft2"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p2_Craft3"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p2_Craft4"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p2_Craft5"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p2_Craft6"));
                } else if (args[1].equals("3")) {
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p3_Craft1"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p3_Craft2"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p3_Craft3"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p3_Craft4"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p3_Craft5"));
                }  else if (args[1].equals("4")) {
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p4_Craft1"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p4_Craft2"));
                    sender.sendMessage(_("commandPlaneHelpCmdTip_p4_Craft3"));
                } else {
                    sender.sendMessage(_("commandPlaneHelpUnknownPage"));
                    sender.sendMessage(_("commandPlaneHelpTip3"));
                    sender.sendMessage(_("commandPlaneHelpTip4"));
                }
               ////////////////////////////////////// fuel
            } else if (args[0].toLowerCase().equals("fuel")) {

            } else {
                //Unknown topic
                sender.sendMessage(_("commandPlaneHelpUnknownTopic"));
                sender.sendMessage(_("commandPlaneHelpTip3"));
                sender.sendMessage(_("commandPlaneHelpTip4"));
            }
        }
        return true;
    }

}
