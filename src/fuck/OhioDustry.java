package fuck;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType.PlayerConnect;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Packets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OhioDustry extends Plugin {

    @Override
    public void init() {
        Events.on(PlayerConnect.class, event -> {
            Player player = event.player;
            int count = 0;
            String pattern = "(https?://)?(www\\.)?(discord\\.(gg|io|me|li)|discordapp\\.com/invite)/.+[a-z]";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(player.name);

            if (m.find() || player.name.contains("discord.gg") || player.name.contains("discordapp.com")) {
                try{
                player.con.kick("You have been kicked for having a discord link in your name.");
                Vars.net.handleServer(Packets.ConnectPacket.class, (con, packet) -> {
                    con.kicked = true;
                    con.close();
                });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            for (Player other : Groups.player) {
                if (other.con.address.equals(player.con.address)) {
                    count++;
                }
            }
            if (count >= 5) {
                try {
                    player.con.kick("You have been kicked for having too many connections.");
                    Vars.net.handleServer(Packets.ConnectPacket.class, (con, packet) -> {
                        con.kicked = true;
                        con.close();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}