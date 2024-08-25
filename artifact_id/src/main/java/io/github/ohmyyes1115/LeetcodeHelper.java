package io.github.ohmyyes1115;

import java.util.Map;
import java.util.TreeMap;

class LeetcodeHelper {

    @Deprecated
    static class Converter_Id_Description {

        private static Map<String, String> MAP_x_ID_TO_DESC = new TreeMap<>();

        static {
            MAP_x_ID_TO_DESC.put("1", "Two Sum");
            MAP_x_ID_TO_DESC.put("2", "Longest Substring Without Repeating Characters");
            MAP_x_ID_TO_DESC.put("5", "Longest Palindromic Substring");
        }

        private String m_id;

        public Converter_Id_Description(String id) {
            m_id = id;
        }

        public String toTitle() {
            if (MAP_x_ID_TO_DESC.containsKey(m_id)) {
                return MAP_x_ID_TO_DESC.get(m_id);
            }

            String class_name = LeetcodeHelper.class.getSimpleName() + "." + this.getClass().getSimpleName();
            return "[*] Add mapping for id=" + m_id + "\tin [" + class_name + "]";
        }
    }

    public static Converter_Id_Description fromId(String id) {
        return new Converter_Id_Description(id);
    }
}