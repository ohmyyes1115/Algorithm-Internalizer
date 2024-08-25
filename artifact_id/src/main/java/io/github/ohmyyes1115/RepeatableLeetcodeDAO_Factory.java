package io.github.ohmyyes1115;

import javax.annotation.Nonnull;

class RepeatableLeetcodeDAO_Factory {

    public static final String LOCAL_PROB_DIR_ROOT = "artifact_id\\res\\Leetcode\\Blind_Curated_75";

    public static @Nonnull IRepeatable_Leetcode_DAO create() {
        if (Feature_OnOff.LEETCODE_REPEATABLE_RECORD_EDITABLE) {
            return new Repeatable_Leetcode_DAO_X_LocalFolder(LOCAL_PROB_DIR_ROOT);
        }
        else {
            IRepeatable_Leetcode_DAO decoratee = new Repeatable_Leetcode_DAO_X_LocalFolder(LOCAL_PROB_DIR_ROOT);
            IRepeatable_Leetcode_DAO decorator = new Repeatable_Leetcode_DAO_X_Record_Immutable(decoratee);
            return decorator;
        }
    }
}