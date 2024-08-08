package com.example.finalexam.helper;

import java.util.Random;

public class ColorHexHelper {

    public static String createColorHexString(String seed) {
        long mSeed = ((long) seed.hashCode() << 32) | (seed.hashCode() & 0xFFFFFFFFL);
        return createColorHexString(mSeed);
    }

    public static String createColorHexString(long seed) {
        String hexString = "#FFFFFF";


        StringBuilder sb = new StringBuilder();
        sb.append("#");

        Random random = new Random(seed);

        try {
            for (int i = 0; i < 3; i++) {
                int RBG = random.nextInt(256);
                sb.append(Integer.toHexString(RBG));
            }
            hexString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hexString;
    }
}
