package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class BridgeUserImpl extends GameUserImpl implements BridgeUser {

    private Team<BridgeUser> team;
    private TheBridgeKits kit;

    public BridgeUserImpl(String name, UUID uuid) {
        super(name, uuid);

    }

    @Override
    public void loadUser() {

        DatabaseManager.getSQLUtils().executeQuery( "INSERT INTO thebridge_users (id, uuid) VALUES(?, ?) ON CONFLICT (uuid) DO NOTHING RETURNING *", (ps -> {
            ps.setInt(1, getId());
            ps.setString(2, getUUID().toString());
        }), (rs) -> {
            if(rs.next()) {
                TheBridgeKits kit = TheBridgeKits.from(rs.getString("equipped_kit"));
                if(kit == null) kit = TheBridgeKits.WARRIOR;
                setKit(kit);
            }
            return rs;
        });
    }

}
