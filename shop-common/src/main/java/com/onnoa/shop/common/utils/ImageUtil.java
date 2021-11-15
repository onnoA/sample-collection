package com.onnoa.shop.common.utils;

// import sun.misc.BASE64Decoder;
// import sun.misc.BASE64Encoder;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public final class ImageUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    private ImageUtil() {
    }

    /**
     * 根据两点坐标截取图片
     *
     * @param image
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     * @throws Exception
     */
    public static BufferedImage imageCaptureByPoint(BufferedImage image, Integer x1, Integer y1, Integer x2,
                                                    Integer y2) {
        Integer width = x2 - x1;
        Integer height = y2 - y1;
        return imageCapture(image, x1, y1, width, height);
    }

    /**
     * 图片截取
     *
     * @param image
     * @return
     */
    public static BufferedImage imageCapture(BufferedImage image, Integer x, Integer y, Integer width, Integer height) {
        // 设置截图图片的(x坐标,y坐标,width宽,height高)信息,并返回截切的新图片,存入缓存区
        /*
         * if (x > width || y > height) { return image; }
         */
        BufferedImage result = image.getSubimage(x, y, width, height);
        return result;
    }

    /**
     * 图片画检测框
     *
     * @param image
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static BufferedImage imageInspectionByPoint(BufferedImage image, Integer x1, Integer y1, Integer x2,
                                                       Integer y2) {
        Integer width = x2 - x1;
        Integer height = y2 - y1;
        return imageInspection(image, x1, y1, width, height);
    }

    /**
     * 图片画检测框
     *
     * @param image
     * @param x
     * @param y
     * @param width
     * @param heigth
     * @return
     */
    public static BufferedImage imageInspection(BufferedImage image, Integer x, Integer y, Integer width,
                                                Integer heigth) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(4.0f));
        g.setColor(Color.RED); // 画笔颜色
        g.drawRect(x, y, width, heigth); // 矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
        return image;
    }

    /**
     * 返回Base64
     *
     * @param imageByte
     * @return
     * @throws Exception
     */
    public static String getBase64(byte[] imageByte) {
        // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
        // BASE64Encoder base64Encoder = new BASE64Encoder();
        // return base64Encoder.encode(imageByte);
        return Base64.getEncoder().encodeToString(imageByte);
    }

    /**
     * byte转BufferedImage
     *
     * @param imageByte
     * @return
     * @throws Exception
     */
    public static BufferedImage byteToImage(byte[] imageByte) {
        ByteArrayInputStream in = null;
        BufferedImage image = null;
        try {
            in = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(in);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return image;
    }

    /**
     * 返回Base64
     *
     * @param jpegBuffer
     * @return
     * @throws IOException
     */
    public static String getBase64(BufferedImage jpegBuffer) {
        String base64 = "";
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(jpegBuffer, "jpeg", out);
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            // BASE64Encoder base64Encoder = new BASE64Encoder();
            // base64 = base64Encoder.encode(out.toByteArray());
            base64 = Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (IOException e) {
            LOGGER.error("图片转base64报错", e);
        } finally {
            // 关闭资源,否则会造成资源泄漏
            IOUtils.closeQuietly(out);
        }
        return base64;
    }

    /**
     * 返回Base64
     *
     * @param jpegBuffer
     * @return
     * @throws IOException
     */
    public static byte[] getByte(BufferedImage jpegBuffer) {
        byte[] bytes = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(jpegBuffer, "jpeg", out);
            bytes = out.toByteArray();
            return bytes;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            // 关闭资源,否则会造成资源泄漏
            IOUtils.closeQuietly(out);
        }
        return bytes;
    }

    /**
     * base64转byte[]
     *
     * @param base64
     * @return
     * @throws IOException
     */
    public static byte[] base64ToByte(String base64) throws IOException {
        // BASE64Decoder base64Decoder = new BASE64Decoder();
        // byte[] bytes = base64Decoder.decodeBuffer(base64);
        byte[] bytes = Base64.getDecoder().decode(base64);
        return bytes;
    }

    public static BufferedImage getBufferedImage(String base64) throws IOException {
        ByteArrayInputStream in = null;
        BufferedImage image = null;
        try {
            in = new ByteArrayInputStream(base64ToByte(base64));
            image = ImageIO.read(in);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return image;
    }

    public static InputStream getInputStream(BufferedImage bi) {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", os);
            is = new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(os);
        }
        return is;
    }

    /**
     * 上传图片
     *
     * @param sftFlag
     * @param ftpConfig
     * @param targetPath
     * @param fileName
     * @param byteArrInputGlobal
     * @throws IOException
     */
//    public static void uploadImage(String sftFlag, FtpConfig ftpConfig, String targetPath, String fileName,
//                                   InputStream byteArrInputGlobal) throws IOException {
//        FTPClient ftpClient = null;
//        SftpUtil sftpUtil = null;
//        try {
//            if (FtpConfig.FTP_FLAG.equals(sftFlag)) {
//                ftpClient = FtpUtil.getFTPClient(ftpConfig.getFtpHost(), Integer.parseInt(ftpConfig.getFtpPort()),
//                        ftpConfig.getFtpAccount(), ftpConfig.getFtpPassword());
//                FtpUtil.createDir(ftpClient, targetPath);
//                FtpUtil.uploadFile(ftpClient, targetPath, fileName, byteArrInputGlobal);
//                ftpClient.disconnect();
//            }
//            // 将图片上传sftp
//            else if (FtpConfig.SFTP_FLAG.equals(sftFlag)) {
//                sftpUtil = new SftpUtil(ftpConfig.getFtpAccount(), ftpConfig.getFtpPassword(), ftpConfig.getFtpHost(),
//                        Integer.parseInt(ftpConfig.getFtpPort()));
//                sftpUtil.uploadFile(targetPath, fileName, byteArrInputGlobal);
//                sftpUtil.disconnect();
//            }
//        }
//        catch (IOException e) {
//            LOGGER.info(e.getMessage(), e);
//        }
//        finally {
//            FtpUtil.disconnect(ftpClient);
//            SftpUtil.disconnect(sftpUtil);
//        }
//    }
    public static byte[] getByte(InputStream is) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream outSteam = null;
        try {
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            bytes = outSteam.toByteArray();
        } finally {
            IOUtils.closeQuietly(outSteam);
        }
        return bytes;
    }

    public static String getBase64(InputStream is) throws IOException {
        ByteArrayOutputStream outSteam = null;
        byte[] bytes = null;
        try {
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            bytes = outSteam.toByteArray();
        } finally {
            IOUtils.closeQuietly(outSteam);
        }
        return getBase64(bytes);
    }

    /**
     * ftp下载图片
     *
     * @param sftFlag
     * @param ftpConfig
     * @param targetPath
     * @return
     * @throws RuntimeException
     */
//    public static byte[] downloadImage(String sftFlag, FtpConfig ftpConfig, String targetPath) {
//        FTPClient ftpClient = null;
//        SftpUtil sftpUtil = null;
//        InputStream is = null;
//        ByteArrayOutputStream outSteam = null;
//        byte[] bytes = null;
//        try {
//
//            if (FtpConfig.FTP_FLAG.equals(sftFlag)) {
//                ftpClient = FtpUtil.getFTPClient(ftpConfig.getFtpHost(), Integer.parseInt(ftpConfig.getFtpPort()),
//                        ftpConfig.getFtpAccount(), ftpConfig.getFtpPassword());
//                is = FtpUtil.downloadFile(ftpClient, targetPath);
//            }
//            else if (FtpConfig.SFTP_FLAG.equals(sftFlag)) {
//                String imagePath = targetPath.substring(0, targetPath.lastIndexOf("/"));
//                String imageName = targetPath.substring((targetPath.lastIndexOf("/") + 1));
//                sftpUtil = new SftpUtil(ftpConfig.getFtpAccount(), ftpConfig.getFtpPassword(), ftpConfig.getFtpHost(),
//                        Integer.parseInt(ftpConfig.getFtpPort()));
//                ChannelSftp channelSftp = sftpUtil.connect();
//                is = sftpUtil.downloadFile(channelSftp, imagePath, imageName);
//            }
//            if (is == null) {
//                throw new IOException();
//            }
//
//            outSteam = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = -1;
//            while ((len = is.read(buffer)) != -1) {
//                outSteam.write(buffer, 0, len);
//            }
//            bytes = outSteam.toByteArray();
//            return bytes;
//        }
//        catch (IOException e) {
//            LOGGER.info(e.getMessage(), e);
//        }
//        finally {
//            IOUtils.closeQuietly(outSteam);
//            IOUtils.closeQuietly(is);
//            FtpUtil.disconnect(ftpClient);
//            SftpUtil.disconnect(sftpUtil);
//        }
//        return bytes;
//    }

    /**
     * fastDFS 下载图片
     *
     * @param fastDFSConfig
     * @param fileUrl
     * @return
     */
//    public static byte[] downloadImage(FastDFSConfig fastDFSConfig, String fileUrl) {
//        FastDFSUpUtil fastDFSUpUtil = new FastDFSUpUtil();
//        byte[] bytes = null;
//        try {
//            bytes = fastDFSUpUtil.downloadFile(fastDFSConfig, fileUrl);
//            if (bytes == null) {
//                bytes = fastDFSUpUtil.downloadFile(fastDFSConfig, fileUrl);
//            }
//        }
//        catch (Exception e) {
//            LOGGER.info(e.getMessage(), e);
//        }
//        return bytes;
//    }

    /**
     * fastDFS 上传图片
     *
     * @param fastDFSConfig
     * @param bytes
     * @return
     */
//    public static String uploadImage(FastDFSConfig fastDFSConfig, byte[] bytes) {
//        FastDFSUpUtil fastDFSUpUtil = new FastDFSUpUtil();
//        String filePath = "";
//        try {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append(System.currentTimeMillis());
//            stringBuffer.append(".jpg");
//
//            String uploadFileName = stringBuffer.toString();
//            filePath = fastDFSUpUtil.send(fastDFSConfig, uploadFileName, bytes);
//        }
//        catch (Exception e) {
//            LOGGER.info(e.getMessage(), e);
//        }
//        return filePath;
//    }
    public static String downloadFromUrl(String urlStr) {
        String base64 = "";
        byte[] bytes = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outSteam = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            inputStream = conn.getInputStream();
            if (inputStream == null) {
                throw new IOException();
            }

            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            bytes = outSteam.toByteArray();
            base64 = getBase64(bytes);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            //throw new OcrRunTimeException(ErrorCodeEnum.HTTP_GET_ERROR, "URL下载文件报错", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outSteam);
        }
        return base64;
    }


    /**
     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), (double) src_width / 2, (double) src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    /*
     *
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 != 0) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

}