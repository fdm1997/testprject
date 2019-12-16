package com.fdm.testpoject.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * @author fdm
 * @date 2019/10/29 18:05
 * @Description: 二维码生成工具类
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    public static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    /**
     * 生成二维码
     * @param content      二维码内容
     * @param logo     logo图片字节数组
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content, byte[] logo, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logo == null ) {
            return image;
        }
        // 插入图片
        QrCodeUtils.insertImage(image, logo, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     * @param source       二维码图片
     * @param logo     LOGO图片字节数组
     * @param needCompress 是否压缩
     * @throws IOException
     */
    private static void insertImage(BufferedImage source, byte[] logo, boolean needCompress) throws IOException {
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(logo);
            Image src = ImageIO.read(inputStream);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            // 压缩LOGO
            if (needCompress) {
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                // 绘制缩小后的图
                g.drawImage(image, 0, 0, null);
                g.dispose();
                src = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(src, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


    /**
     * 生成二维码（带logo,并且写入指定的本地地址）
     * @param content 内容
     * @param logo LOGO输入流
     * @param needCompress 是否压缩LOGO
     * @param path  将二维码文件写入本地指定地址
     * @return
     * @throws Exception
     */
    public static void encode(String content, byte[] logo, boolean needCompress,String path)
            throws Exception {
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        BufferedImage image = QrCodeUtils.createImage(content, logo, needCompress);
        ImageIO.write(image, FORMAT, outputStream);
    }

    /**
     *生成二维码（带logo，返回二维码图片字节数组）
     * @param content 内容
     * @param logo LOGO输入流
     * @param needCompress 是否压缩LOGO
     * @return 将二维码文件写入本地指定地址
     * @throws Exception
     */
    public static byte[] encode(String content, byte[] logo, boolean needCompress)
            throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = QrCodeUtils.createImage(content, logo, needCompress);
        ImageIO.write(image, FORMAT, outputStream);
        byte[] data = outputStream.toByteArray();
        return data;
    }

    /**
     *生成二维码（不带logo）
     * @param content 内容
     * @return
     * @throws Exception
     */
    public static byte[] encode(String content)
            throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = QrCodeUtils.createImage(content, null, false);
        ImageIO.write(image, FORMAT, outputStream);
        byte[] data = outputStream.toByteArray();
        return data;
    }
}
