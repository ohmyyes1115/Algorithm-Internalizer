package io.github.ohmyyes1115;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.ohmyyes1115.CustomDuration.ETimeScale;

class Repeatable_Leetcode_DAO_X_LocalFolder implements IRepeatable_Leetcode_DAO {

    private String m_local_prob_dir_root;
    LocalLeetcodeRepeatable_Helper m_localLeetcodeRepeatable_Helper;

    public Repeatable_Leetcode_DAO_X_LocalFolder(String local_prob_dir_root) {
        m_local_prob_dir_root = local_prob_dir_root;
        m_localLeetcodeRepeatable_Helper = new LocalLeetcodeRepeatable_Helper(local_prob_dir_root);
    }

    @Override  // IRepeatable_Leetcode_DAO
    public List<Repeatable_Leetcode_VO> loadAll() {
        return m_localLeetcodeRepeatable_Helper.getLocalLeetcodeProblems().stream()
                                                                            .map(RepeatableLeetcodeVO_Creator::toVO)
                                                                            .collect(Collectors.toList());
    }

    @Override  // IRepeatable_Leetcode_DAO
    public boolean updateILevel(RID rid, int iLevel) {
        try {
            CustomProperties prop = CustomProperties_Loader.loadProperties(m_local_prob_dir_root).fromRid(rid);
            prop.set("iLevel", String.valueOf(iLevel));

            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    @Override  // IRepeatable_Leetcode_DAO
    public boolean updateLastRepeatTime(RID rid, LocalDateTime last_repeat_time) {
        try {
            CustomProperties prop = CustomProperties_Loader.loadProperties(m_local_prob_dir_root).fromRid(rid);
            prop.set("last_repeat_time", LocalDateTimeHelper.removeFloating(last_repeat_time).toString());

            return true;
        }
        catch (IOException e) {}

        return false;
    }

    @Override  // IRepeatable_Leetcode_DAO
    public Optional<LocalDateTime> readLastRepeatTime(RID rid) {
        try {
            CustomProperties prop = CustomProperties_Loader.loadProperties(m_local_prob_dir_root).fromRid(rid);
            String last_repeat_time = prop.get("last_repeat_time", "");

            if (!last_repeat_time.isEmpty()) {
                return Optional.of(LocalDateTime.parse(last_repeat_time));
            }
        }
        catch (IOException e) {}
        
        return Optional.empty();
    }

    @Override  // IRepeatable_Leetcode_DAO
    public boolean updateRSD(RID rid, RSD rsd) {
        try {
            CustomProperties prop = CustomProperties_Loader.loadProperties(m_local_prob_dir_root).fromRid(rid);
            prop.set("rsd", rsd.toString(ETimeScale.MINUTE_SCALE));

            return true;
        }
        catch (IOException e) {}

        return false;
    }

    @Override  // IRepeatable_Leetcode_DAO
    public Optional<RSD> readRSD(RID rid) {
        try {
            CustomProperties prop = CustomProperties_Loader.loadProperties(m_local_prob_dir_root).fromRid(rid);
            String rsd_std = prop.get("rsd", "");

            if (!rsd_std.isEmpty()) {
                return Optional.of(RSD.fromString(rsd_std));
            }
        }
        catch (IOException e) {}

        return Optional.empty();
    }

    @Override  // IRepeatable_Leetcode_DAO
    public Optional<QD> readQD(RID rid) {
        try {
            CustomProperties prop = CustomProperties_Loader.loadProperties(m_local_prob_dir_root).fromRid(rid);
            String qd_str = prop.get("QD", "");

            if (!qd_str.isEmpty()) {
                return Optional.of(QD.fromString(qd_str));
            }
        }
        catch (IOException e) {}

        return Optional.empty();
    }
    
}