package com.itktp.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.logging.log4j.message.MultiformatMessage;
import sun.applet.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ktp
 * @date: 2022/3/30
 */
public class ZXing {

    /**
     * 二维码上传位置
     **/
    private static String codePath = "src/";

    /**
     * 二维码宽度
     **/
    private static Integer width = 150;

    /**
     * 二维码高度
     **/
    private static Integer height = 150;

    /**
     * @param name 文件名称
     * @return 解析二维码
     * @throws IOException
     * @throws NotFoundException
     */
    public static String decode(String name) throws IOException, NotFoundException {
        //文件路径
        String filePath = codePath + name + ".png";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filePath));
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);
        HashMap<DecodeHintType, Object> decodeHints = new HashMap<>();
        decodeHints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(bitmap, decodeHints);
        return result.getText();
    }

    /**
     * @param content 二维码内容
     * @param name    文件名称
     * @return 生成二维码
     * @throws WriterException
     * @throws IOException
     */
    public static String encode(String content, String name) throws WriterException, IOException {
        Map<EncodeHintType, Object> encodeHints = new HashMap<>();
        encodeHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, encodeHints);
        String filepath = codePath + name + ".png";
        Path path = FileSystems.getDefault().getPath(filepath);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
        return codePath + name + ".png";
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        //生成二维码 内容为abc 名称为test
        //ZXing.encode("abc", "test");
        //解析二维码
        String test = ZXing.decode("test");
        System.out.println(test);
    }
}
