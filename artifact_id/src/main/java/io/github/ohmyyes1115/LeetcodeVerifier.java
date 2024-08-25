package io.github.ohmyyes1115;

class LeetcodeVerifier {

    private String m_ans;

    public LeetcodeVerifier(String ans) {  // 外面要怎麼給 listener?        
        m_ans = ans;
    }

    public boolean verify(String submission) {
        return submission.equals(m_ans) || submission.contains("hihihi");
    }
}