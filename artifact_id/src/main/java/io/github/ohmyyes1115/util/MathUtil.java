package io.github.ohmyyes1115.util;

class MathUtil {
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}