package io.github.ohmyyes1115;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

class CustomProperties_Loader {

    public static InnerLoader loadProperties(String local_prob_dir_root) {
        return new InnerLoader(local_prob_dir_root);
    }

    public static class InnerLoader {

        private static final String LOCAL_PROPERTY_FILE_NAME = "properties.txt";

        private String m_local_prob_dir_root;

        public InnerLoader(String local_prob_dir_root) {
            m_local_prob_dir_root = local_prob_dir_root;
        }

        public CustomProperties fromRid(RID rid) throws IOException {

            Optional<File> targetDir_opt = new LocalLeetcodeRepeatable_Helper(m_local_prob_dir_root).getDir_FromRid(rid);
            if (!targetDir_opt.isPresent()) {
                throw new IllegalArgumentException("Can't get directory for id = " + rid);
            }

            File dir = targetDir_opt.get();
            String filePath_prop = dir.getPath() + "\\" + LOCAL_PROPERTY_FILE_NAME;
            return new CustomProperties(filePath_prop);
        }
    }
}