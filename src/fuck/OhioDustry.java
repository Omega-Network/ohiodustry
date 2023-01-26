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
            String pattern = "(https?:\\/\\/)?(www\\.)?(discord\\.(gg|io|me|li)|discordapp\\.com\\/invite)\\/.+[a-z]";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(player.name);

            if (m.find()){
                Vars.net.handleServer(Packets.ConnectPacket.class, (con, packet) -> {
                    con.kicked = true;
                    con.close();
                });
            }
            for (Player other : Groups.player) {
                if (other.con.address.equals(player.con.address)) {
                    count++;
                }
            }
            if (count >= 5) {
                Vars.net.handleServer(Packets.ConnectPacket.class, (con, packet) -> {
                    con.kicked = true;
                    con.close();
                });
            }
        });
    }
}