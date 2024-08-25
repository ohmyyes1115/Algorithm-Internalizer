package io.github.ohmyyes1115;

public class Leetcode_Problem {
    private int m_problem_num;
    public String m_desc_file_path = null;
    public String m_answ_file_path = null;

    public Leetcode_Problem(int problem_num, String file_path) {
        m_problem_num = problem_num;
        m_desc_file_path = file_path;
    }

    public int getProblemNum() {
        return m_problem_num;
    }

    public String getFilePath_description() {
        return m_desc_file_path;
    }

    public String getFilePath_answer() {
        return m_answ_file_path;
    }
}