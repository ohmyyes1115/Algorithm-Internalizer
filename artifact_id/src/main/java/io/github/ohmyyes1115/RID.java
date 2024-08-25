package io.github.ohmyyes1115;

class RID {

    private String m_rid_str;

    public static RID of(String rid_str) {
        return new RID(rid_str);
    }

    public String get() {
        return m_rid_str;
    }

    @Override
    public String toString() {
        return m_rid_str.toString();
    }

    private RID(String rid_str) {
        m_rid_str = rid_str;
    }
}