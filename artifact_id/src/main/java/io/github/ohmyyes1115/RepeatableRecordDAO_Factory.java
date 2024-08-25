package io.github.ohmyyes1115;

class RepeatableRecordDAO_Factory {

    public static final String LOCAL_RES_ROOT = "artifact_id\\res";

    public static IRepeatedRecord_DAO create() {
        return new RepeatedRecord_DAO_X_LocalFolder(
            LOCAL_RES_ROOT + "\\" /*System.lineSeparator()*/ + "repeated.txt");
    }
}