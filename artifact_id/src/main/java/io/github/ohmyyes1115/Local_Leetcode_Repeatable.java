package io.github.ohmyyes1115;

import java.io.File;
import java.util.function.Predicate;

class Local_Leetcode_Repeatable {

    public String title;
    public String filePath_url;
    public String filePath_desc;
    public String filePath_ans;
    public String filePath_template;
    public String filePath_prop;

    public static Local_Leetcode_Repeatable createFromDir(String dir, String title) {
        return createFromDir(new File(dir), title);
    }

    public static Local_Leetcode_Repeatable createFromDir(File dir, String title) {

        String path_url         = dir.getPath() + "\\" + "url.txt";
        String path_desc        = dir.getPath() + "\\" + "description.png";
        String path_template    = dir.getPath() + "\\" + "template.txt";
        String path_ans         = dir.getPath() + "\\" + "answer.txt";
        String path_prop        = dir.getPath() + "\\" + "properties.txt";

        Predicate<File> isProblemDirValid = prob_dir -> {
            return new File(path_url     ).exists()
                && new File(path_desc    ).exists()
                && new File(path_template).exists()
                && new File(path_ans     ).exists()
                && new File(path_prop    ).exists();
        };

        if (dir.isDirectory() && !isProblemDirValid.test(dir)) {
            throw new IllegalArgumentException("dir is not valid: " + dir.getPath());
        }

        return new Local_Leetcode_Repeatable()
                    .setTitle(title)
                    .setFilePath_url(path_url)
                    .setFilePath_description(path_desc)
                    .setFilePath_template(path_template)
                    .setFilePath_answer(path_ans)
                    .setFilePath_properties(path_prop);
    }

    private Local_Leetcode_Repeatable setTitle(String title) {
        this.title = title;
        return this;
    }

    private Local_Leetcode_Repeatable setFilePath_url(String filePath_url) {
        this.filePath_url = filePath_url;
        return this;
    }

    private Local_Leetcode_Repeatable setFilePath_description(String filePath_desc) {
        this.filePath_desc = filePath_desc;
        return this;
    }

    private Local_Leetcode_Repeatable setFilePath_template(String filePath_template) {
        this.filePath_template = filePath_template;
        return this;
    }

    private Local_Leetcode_Repeatable setFilePath_answer(String filePath_ans) {
        this.filePath_ans = filePath_ans;
        return this;
    }

    private Local_Leetcode_Repeatable setFilePath_properties(String filePath_prop) {
        this.filePath_prop = filePath_prop;
        return this;
    }
}