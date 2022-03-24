package io.wibdt.common.util;

public final class BinaryUtils {

    public static String byteToBinary(byte bt) {
        return String.format("%8s", Integer.toBinaryString(bt & 0xff)).replace(" ", "0");
    }

    public static String byteToHex(byte bt) {
        String hex = Integer.toHexString(bt & 0xff);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

}
