package es.obsilion.freezer.configuration;

import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfHeader;

@ConfHeader({
        "",
        "This is the main configuration for the ObsilionFreeze plugin.",
        ""
})
public interface MainConfig {

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cThis command can only be run by players.")
    String onlyPlayer();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cYou need to supply a player: /freeze <player>")
    String commandUsage();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cThe supplied player isn't online.")
    String offlinePlayer();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cYou can't freeze staff.")
    String cantFreezeStaff();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cThe player you are trying to freeze is already frozen.")
    String alreadyFrozen();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &aYou have frozen the player!")
    String youHaveFrozenPlayer();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &aYou have unfrozen the player!")
    String youHaveUnFrozenPlayer();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cYou have been frozen! &aAnswer the questions of the staff.")
    String youHaveBeenFrozen();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &aYou have been unfrozen!")
    String youHaveBeenUnFrozen();

    @ConfDefault.DefaultString("&b&lFREEZER &7» &cYou can't do that while you are frozen.")
    String cantDoThat();
}
