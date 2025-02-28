package com.educative.patterns.chapter1.window;

import java.util.Set;

public class Print {
    public static String printSetString(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (String s : set) {
            sb.append(s).append(", ");
        }
        sb.setLength(sb.length() - 2); // Remove last ", "
        sb.append("]");
        return sb.toString();
    }
    
    public static String repeat(String str, int count) {
        return new String(new char[count]).replace("\0", str);
    }

}