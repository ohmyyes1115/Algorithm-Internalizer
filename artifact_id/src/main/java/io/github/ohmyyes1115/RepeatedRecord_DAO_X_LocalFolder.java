package io.github.ohmyyes1115;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import io.github.ohmyyes1115.CustomDuration.ETimeScale;

class RepeatedRecord_DAO_X_LocalFolder implements IRepeatedRecord_DAO {

    private String m_filePath;

    public RepeatedRecord_DAO_X_LocalFolder(String filePath) {
        m_filePath = filePath;
    }

    @Override  // IRepeatedRecord_DAO
    public boolean insert(RepeatedRecord_VO vo) {
        String SEPARATOR = "===================================================================================================================\n";
        List<String> attr_values = getAttrValuesToBeRecord(vo);
        String oneLine_str = constructOneLineStr(attr_values);
        String record_str = oneLine_str;

        if (vo.result == EResult.O) {
            record_str += SEPARATOR;
        }
        
        try {
            Files.writeString(Paths.get(m_filePath), record_str, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<String> getAttrValuesToBeRecord(RepeatedRecord_VO vo) {

        // 2022-11-27T10:24:45	Leetcode-BlindCurated75	5	O   31m	Longest Palindromic Substring
        return Arrays.asList(
            LocalDateTimeHelper.removeFloating(vo.dateTime).toString(),        // 2022-11-27T10:24:45
            vo.rid.get(),                                                      // Leetcode-BlindCurated75 - 5
            (vo.result == EResult.O) ? "O" : "X",                              // O
            formatCustomDuration(vo.rsd),                                      // 31m
            formatTitle(vo.title)                                              // Longest Palindromic Substring
        );
    }

    private String constructOneLineStr(List<String> attr_values) {
        if (attr_values.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder(attr_values.get(0));

        attr_values.stream()
                    .skip(1)
                    .forEach(value -> sb.append(/*"\t"*/ "    ").append(value));

        sb.append(System.lineSeparator());

        return sb.toString();
    }

    // pad to leng=7, since the longest case might be 10h:13m
    private String formatCustomDuration(RSD rsd) {
        final int PADDED_LEN = 7;  // longest case might be 10h:13m
        
        String without_padding = (rsd != null) ? rsd.toString(ETimeScale.MINUTE_SCALE) : "";
        int nSpaces = PADDED_LEN - without_padding.length();

        StringBuilder sb = new StringBuilder();
        if (nSpaces > 0) {
            IntStream.range(0, nSpaces).forEach(i -> sb.append(" "));
        }

        sb.append(without_padding);

        return sb.toString();
    }
    
    // [WA] hardcode to align "Leetcode-BlindCurated75-1"  &  "Leetcode-BlindCurated75-10"
    // If title is "Leetcode-BlindCurated75-1", append one more space to return "Leetcode-BlindCurated75-1 "
    private String formatTitle(String title) {
        final String RID_LEETCODE_BLINDCURATED_75 = "Leetcode-BlindCurated75-";  // leading format of Blind-Curated75's RID

        // make sure it's RID of Blind-Curated75
        if (title.startsWith(RID_LEETCODE_BLINDCURATED_75)) {
            if (title.length() == RID_LEETCODE_BLINDCURATED_75.length() + 1) {
                return " " + title;
            }
        }
        
        return title;
    }
}