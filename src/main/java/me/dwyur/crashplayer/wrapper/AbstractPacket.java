package me.dwyur.crashplayer.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import org.bukkit.entity.Player;

public abstract class AbstractPacket {
    protected PacketContainer handle;

    protected AbstractPacket(PacketContainer handle, PacketType type) {
        if (handle == null) {
            throw new IllegalArgumentException("Packet handle cannot be NULL.");
        }
        if (!Objects.equal(handle.getType(), type)) {
            throw new IllegalArgumentException(handle.getHandle()
                    + " is not a packet of type " + type);
        }

        this.handle = handle;
    }

    public AbstractPacket(PacketType type) {
        this.handle = new PacketContainer(type);
        this.handle.getModifier().writeDefaults();
    }

    public PacketContainer getHandle() {
        return handle;
    }

    public void sendPacket(Player receiver) {
        ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, getHandle());
    }

    public void broadcastPacket() {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
    }

    public void receivePacket(Player sender) {
        ProtocolLibrary.getProtocolManager().receiveClientPacket(sender, getHandle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractPacket that = (AbstractPacket) o;

        return java.util.Objects.equals(handle, that.handle);
    }

    @Override
    public int hashCode() {
        return handle != null ? handle.hashCode() : 0;
    }
}