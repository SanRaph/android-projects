package com.phyte.sanraphindustries.viso.car.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class DataUtil {

    private DataUtil() { }

    /**
     * Check if the array is empty (remove the leading and trailing spaces)
     */
    public static boolean isEmpty(String string) {
        return (string == null || string.length() == 0 || string.trim().length() == 0);
    }

    /**
     *
     Check if the array is empty
     */
    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * Check if Collection is empty
     */
    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }

    /**
     *Check if List is empty
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    /**
     * Check if the Map is empty
     */
    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }
}
