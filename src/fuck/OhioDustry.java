package fuck;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.net.Administration.*;
import mindustry.world.blocks.storage.*;

public class OhioDustry extends Plugin {

    @Override
    public void init() {
        Events.on(PlayerConnect.class, event -> {
            Player player = event.player;
            int count = 0;
            for (Player other : Groups.player) {
                if (other.con.address.equals(player.con.address)) {
                    count++;
                }
            }
            if (count >= 5) {
                player.con.kick("You have been kicked for having too many accounts.");
                Log.info("Kicked player @ for having too many accounts.", player.name);
            }
        });
    }
}