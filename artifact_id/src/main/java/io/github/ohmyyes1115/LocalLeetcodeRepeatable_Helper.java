package io.github.ohmyyes1115;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class LocalLeetcodeRepeatable_Helper {

    private String m_local_prob_dir_root;

    public LocalLeetcodeRepeatable_Helper(String local_prob_dir_root) {
        m_local_prob_dir_root = local_prob_dir_root;
    }

    public Optional<File> getDir_FromRid(RID rid) {  // Leetcode-BlindCurated75-2
        String[] splitted = rid.get().split("-");

        if (splitted == null) {
            return Optional.empty();
        }

        String target_id_num = splitted[splitted.length - 1];  // 2

        // ref: https://stackoverflow.com/questions/3003310/how-can-i-match-on-but-exclude-a-regex-pattern
        // match with: "leads with number", followed by a dot
        Pattern pattern =  Pattern.compile("^(\\d+)\\..*");

        Predicate<File> is_target_dir = dir -> {
            
            Matcher matcher = pattern.matcher(dir.getName());
            if (matcher.find()) {
                String leading_number = matcher.group(1);  // only get the group inside the 1st parentheses (e.g., 2)
                return leading_number.equals(target_id_num);
            }
            return false;
        };

        return getLocalLeetcodeProblemDirs()
                    .stream()
                    .filter(is_target_dir)
                    .findAny();
    }

    public List<Local_Leetcode_Repeatable> getLocalLeetcodeProblems() {
        // "3. Longest Palindromic Substring"    ->    "Longest Palindromic Substring"
        UnaryOperator<String> toTitle = folder_name -> folder_name.substring(folder_name.indexOf(" ") + 1);

        return getLocalLeetcodeProblemDirs()
                    .stream()
                    .map(dir -> Local_Leetcode_Repeatable.createFromDir(dir, toTitle.apply(dir.getName())))
                    .collect(Collectors.toList());
    }

    // ------------------------- private function -------------------------

    private List<File> getLocalLeetcodeProblemDirs() {
        Pattern pattern =  Pattern.compile("(\\d)+.*");  // leads with number

        Predicate<File> is_dir_contains_id = dir -> {
            return pattern.matcher(dir.getName()).matches();
        };

        File root_problem_dir = new File(m_local_prob_dir_root);
        return Arrays.stream(root_problem_dir.listFiles(File::isDirectory))
                        .filter(is_dir_contains_id)
                        .collect(Collectors.toList());
    }
}