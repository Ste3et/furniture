package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.UUID;

public class Pixel {

    int x = 0;
    int z = 0;
    byte color = 0;
    @SuppressWarnings("unused")
    private UUID serialID = UUID.randomUUID();

    public Pixel(int x, int z, byte color) {
        this.x = x;
        this.z = z;
        this.color = color;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public byte getColor() {
        return this.color;
    }
}
