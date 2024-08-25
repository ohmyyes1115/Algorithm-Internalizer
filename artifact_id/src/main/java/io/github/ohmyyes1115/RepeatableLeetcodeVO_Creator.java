package io.github.ohmyyes1115;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

class RepeatableLeetcodeVO_Creator {

    public static Repeatable_Leetcode_VO toVO(Local_Leetcode_Repeatable leetcodeProblem) {
        try {
            String title = leetcodeProblem.title;
            BufferedImage desc_img = ImageIO.read(new File(leetcodeProblem.filePath_desc));
            
            String url          = new String(Files.readAllBytes(Paths.get(leetcodeProblem.filePath_url)));
            String ans_str      = new String(Files.readAllBytes(Paths.get(leetcodeProblem.filePath_ans)));
            String template_str = new String(Files.readAllBytes(Paths.get(leetcodeProblem.filePath_template)));

            CustomProperties prop = new CustomProperties(leetcodeProblem.filePath_prop);
            RID rid = RID.of(prop.get("rid"));
            int iLevel = Integer.parseInt(prop.get("iLevel", "0"));

            LocalDateTime last_repeat_time = prop.contains("last_repeat_time") ?
                                                LocalDateTime.parse(prop.get("last_repeat_time")) :
                                                null;

            RSD rsd = prop.contains("rsd") ?
                                                RSD.fromString(prop.get("rsd")) :
                                                null;

            QD qd = prop.contains("QD") ?
                                                QD.fromString(prop.get("QD")) :
                                                null;

            return new Repeatable_Leetcode_VO()
                        .setTitle(title)
                        .setRid(rid)
                        .setUrl(url)
                        .setDescription(desc_img)
                        .setTemplate(template_str)
                        .setAnswer(ans_str)
                        .setILevel(iLevel)
                        .setLastRepeatTime(last_repeat_time)
                        .setRSD(rsd)
                        .setQD(qd)
            ;
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }
}