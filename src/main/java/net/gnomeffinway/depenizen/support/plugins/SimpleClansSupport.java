package net.gnomeffinway.depenizen.support.plugins;

import net.aufdemrand.denizen.objects.dPlayer;
import net.aufdemrand.denizencore.objects.dList;
import net.aufdemrand.denizencore.tags.Attribute;
import net.gnomeffinway.depenizen.extensions.simpleclans.SimpleClansPlayerExtension;
import net.gnomeffinway.depenizen.objects.simpleclans.dClan;
import net.gnomeffinway.depenizen.support.Support;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

public class SimpleClansSupport extends Support {

    public SimpleClansSupport() {
        registerObjects(dClan.class);
        registerProperty(SimpleClansPlayerExtension.class, dPlayer.class);
        registerAdditionalTags("simpleclans");
    }

    public ClanPlayer getFrom(dPlayer p) {
        return new ClanPlayer(p.getOfflinePlayer().getUniqueId());
    }

    @Override
    public String additionalTags(Attribute attribute) {
        if (attribute.startsWith("simpleclans")) {
            attribute = attribute.fulfill(1);

            if (attribute.startsWith("list_clans")) {
                dList clans = new dList();
                for (Clan cl : SimpleClans.getInstance().getClanManager().getClans()) {
                    clans.add(new dClan(cl).identify());
                }
                return clans.getAttribute(attribute.fulfill(1));
            }

            else if(attribute.startsWith("list_unverified_clans")) {
                dList clans = new dList();
                for (Clan cl : SimpleClans.getInstance().getClanManager().getClans()) {
                    if (!cl.isVerified()) {
                        clans.add(new dClan(cl).identify());
                    }
                }
                return clans.getAttribute(attribute.fulfill(1));
            }
        }

        return null;
    }

}