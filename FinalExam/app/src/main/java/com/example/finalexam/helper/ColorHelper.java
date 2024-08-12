package com.example.finalexam.helper;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class ColorHelper {

    public static String createColorHex(String seed) {
        long mSeed = ((long) seed.hashCode() << 32) | (seed.hashCode() & 0xFFFFFFFFL);
        return createColorHex(mSeed);
    }

    public static String createColorHex(long seed) {
        String hexString = "#FFFFFF";


        StringBuilder sb = new StringBuilder();
        sb.append("#");

        Random random = new Random(seed);
        int padding = 10;

        try {
            for (int i = 0; i < 3; i++) {
                int RGB = padding + random.nextInt(256 - padding * 2);
                sb.append(String.format("%02X", RGB));
            }
            hexString = sb.toString();
        } catch (Exception e) {
            Log.e("ColorHex", "createColorHex Exception", e);
        }

        return hexString;
    }

    public static boolean isBrightColor(int colorR, int colorG, int colorB) {
        int brightness = colorR + colorG + colorB;
        return brightness >= 550;
    }

    public static boolean isBrightColor(String RGBHex) {
        int color = Color.parseColor(RGBHex);
        return isBrightColor(color);
    }

    public static boolean isBrightColor(int color) {
        int colorR = Color.red(color);
        int colorG = Color.green(color);
        int colorB = Color.blue(color);
        return isBrightColor(colorR, colorG, colorB);
    }
}
