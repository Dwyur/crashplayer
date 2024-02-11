package me.dwyur.crashplayer.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerExplosion extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.EXPLOSION;

    public WrapperPlayServerExplosion() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public void setX(double value) {
        handle.getDoubles().write(0, value);
    }

    public void setY(double value) {
        handle.getDoubles().write(1, value);
    }

    public void setZ(double value) {
        handle.getDoubles().write(2, value);
    }

    public void setRadius(float value) {
        handle.getFloat().write(0, value);
    }

    public void setPlayerVelocityX(float value) {
        handle.getFloat().write(0, value);
    }

    public void setPlayerVelocityY(float value) {
        handle.getFloat().write(1, value);
    }
}