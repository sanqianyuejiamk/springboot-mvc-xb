package com.mengka.springboot.img_Base64_01;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 *  Convert Image File to Base64 String
 *
 * @author mengka
 * @version 2020/11/26
 * @since
 */
public class img_base64_01 {

    public static void main(String... args)throws IOException {
        String filePath = "/Users/hyy044101331/work_sanqianyuejia/springboot-mvc-xb/src/main/java/com/mengka/springboot/img_Base64_01/111.png";

        /**
         *  Convert Image File to Base64 String
         *
         */
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        System.out.println("encodedString = "+encodedString);


        /**
         *  Convert Base64 String to Image File
         */
        String outputFileName = "/Users/hyy044101331/work_sanqianyuejia/springboot-mvc-xb/src/main/java/com/mengka/springboot/img_Base64_01/out.png";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        FileUtils.writeByteArrayToFile(new File(outputFileName), decodedBytes);

    }
}
