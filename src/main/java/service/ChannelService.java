package service;

import client.Client;
import client.inventory.InventoryType;
import database.character.CharacterSaver;
import net.server.Server;
import server.maps.FieldLimit;
import server.maps.MiniDungeonInfo;
import tools.PacketCreator;

import java.io.IOException;
import java.net.InetAddress;

public class ChannelService {
    private final Server server = Server.getInstance();
    private final CharacterSaver chrSaver;

    public ChannelService(CharacterSaver characterSaver) {
        this.chrSaver = characterSaver;
    }

    public void changeChannel(Client c, int channel) {
        var chr = c.getPlayer();
        if (chr.isBanned()) {
            c.disconnect(false, false);
            return;
        }

        if (!chr.isAlive() || FieldLimit.CANNOTMIGRATE.check(chr.getMap().getFieldLimit())) {
            c.sendPacket(PacketCreator.enableActions());
            return;
        }

        if (MiniDungeonInfo.isDungeonMap(chr.getMapId())) {
            c.sendPacket(PacketCreator.serverNotice(5, "Changing channels or entering Cash Shop or MTS are disabled when inside a Mini-Dungeon."));
            c.sendPacket(PacketCreator.enableActions());
            return;
        }

        String[] socket = Server.getInstance().getInetSocket(c, c.getWorld(), channel);
        if (socket == null) {
            c.sendPacket(PacketCreator.serverNotice(1, "Channel " + channel + " is currently disabled. Try another channel."));
            c.sendPacket(PacketCreator.enableActions());
            return;
        }

        chr.closePlayerInteractions();
        chr.closePartySearchInteractions();

        chr.unregisterChairBuff();
        server.getPlayerBuffStorage().addBuffsToStorage(chr.getId(), chr.getAllBuffs());
        server.getPlayerBuffStorage().addDiseasesToStorage(chr.getId(), chr.getAllDiseases());
        chr.setDisconnectedFromChannelWorld();
        chr.notifyMapTransferToPartner(-1);
        chr.removeIncomingInvites();
        chr.cancelAllBuffs(true);
        chr.cancelAllDebuffs();
        chr.cancelBuffExpireTask();
        chr.cancelDiseaseExpireTask();
        chr.cancelSkillCooldownTask();
        chr.cancelQuestExpirationTask();
        //Cancelling magicdoor? Nope
        //Cancelling mounts? Noty

        chr.getInventory(InventoryType.EQUIPPED).checked(false); //test
        chr.getMap().removePlayer(chr);
        chr.clearBanishPlayerData();
        c.getChannelServer().removePlayer(chr);

        chrSaver.save(chr);

        chr.setSessionTransitionState();
        try {
            c.sendPacket(PacketCreator.getChannelChange(InetAddress.getByName(socket[0]), Integer.parseInt(socket[1])));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
