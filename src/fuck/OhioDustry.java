package fuck;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType.PlayerConnect;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Packets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OhioDustry extends Plugin {
    private static final Pattern DISC = Pattern.compile("discord\\\\.gg/\\\\w+|<@!|&\\\\d{15,}>|@everyone|here", Pattern.CASE_INSENSITIVE);

    @Override
    public void init() {
        Events.on(PlayerConnect.class, event -> {
            Player player = event.player;
            int count = 0;
            Matcher m = DISC.matcher(player.name);

            if (m.find() || player.name.contains("discord.gg") || player.name.contains("discordapp.com")) {
                try{
                player.con.kick("You have been kicked for having a inappropriate name.");
                /*
                Vars.net.handleServer(Packets.ConnectPacket.class, (con, packet) -> {
                    con.kicked = true;
                    con.close();
                });*/
                } catch (Exception e){
                    Log.err("<OhioDustry> ", e);
                }
            }
            for (Player other : Groups.player) {
                if (other.con.address.equals(player.con.address)) {
                    count++;
                }
            }
            if (count >= 5) {
                count = 0;
                try {
                    player.con.kick("You have been kicked for having too many connections.");
                    Log.warn("Kicked " + player.name + " for possible botting.");
                    /*Vars.net.handleServer(Packets.ConnectPacket.class, (con, packet) -> {
                        con.kicked = true;
                        con.close();
                    });*/
                } catch (Exception e) {
                    Log.err("<OhioDustry> ", e);
                }
            }
        });
    }
}