package com.onnoa.shop.common.utils;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * pdf工具类，提取pdf中的身份证和签名图片
 * @author huangjiahui
 */
public class PdfUtil {

    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * 读取PDF每一页的图片内容
     *
     * @param path   pdf本地路径
     * @param base64   true返回 base64字符串， false返回 byte[]
     * @return
     */
    public static List<Object> readImage(String path, boolean base64) {
        final File file = new File(path);
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            return readImage(document, base64);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 读取PDF每一页的图片内容
     *
     * @param path  pdf的输入流
     * @param base64  true返回 base64字符串， false返回 byte[]
     * @return
     */
    public static List<Object> readImage(InputStream path, boolean base64) {
        PDDocument document = null;
        try {
            document = PDDocument.load(path);
            return readImage(document, base64);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 读取PDF每一页的图片内容
     *
     * @param document PDF doc
     * @param base64  true返回 base64字符串， false返回 byte[]
     * @return
     */
    public static List<Object> readImage(PDDocument document, boolean base64) {
        if (document == null) {
            return null;
        }
        List<Object> imageList = new ArrayList<>();
        try {
            // 图片内容
            PDPage page = null;
            PDResources resources = null;
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                page = document.getPage(i);
                resources = page.getResources();
                Iterable<COSName> cosNames = resources.getXObjectNames();
                if (cosNames != null) {
                    List<byte[]> images = analysePDF(cosNames, resources);
                    if (images != null && !images.isEmpty()) {
                        imageList.addAll(images);
                    }
                }
            }

            // 当没有图片，或返回类型为 BufferedImage 直接返回 imageList
            if (imageList.isEmpty() || !base64) {
                return imageList;
            }

            List<Object> base64List = new ArrayList<>();
            for (Object obj :imageList) {
                base64List.add(Base64.getEncoder().encodeToString((byte[]) obj));
            }
            return base64List;

                // 获取 签名 base64字符串
                /*
                if (signImage != null) {
                    signOS = new ByteArrayOutputStream();
                    // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
                    BufferedImage newBufferedImage = new BufferedImage(
                            signImage.getWidth(), signImage.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    // 将从pdf中解析出来的png 转为jpg
                    newBufferedImage.createGraphics().drawImage(signImage, 0, 0,
                            Color.WHITE, null);

                    ImageIO.write(newBufferedImage, "jpg", signOS);
                    if (base64) {
                        String signBase64 = ImageUtil.getBase64(signOS.toByteArray());
                        map.put("signContent", signBase64);


                    }
                    else {
                        map.put("signContent", signOS.toByteArray());
                    }
                }*/
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            try {
                if (document != null) {
                    document.close();
                }
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 解析PDF 获取 身份证 和 签名图片
     *
     * @param cosNames
     * @param resources
     * @return
     * @throws Exception
     */
    private static List<byte[]> analysePDF(Iterable<COSName> cosNames, PDResources resources) throws Exception {
        Iterator<COSName> cosNamesIter = cosNames.iterator();
        // List<PDImageXObject> imageList = new ArrayList<>();
        // List<Integer> sizeList = new ArrayList<>();
        List<byte[]> imageList = new ArrayList<>();
        ByteArrayOutputStream out = null;
        PDImageXObject ipdmage = null;
        // InputStream inputStream = null;
        // 从pdf中获取图片，多于两张图片报错
        try {
            while (cosNamesIter.hasNext()) {
                COSName cosName = cosNamesIter.next();
                if (resources.isImageXObject(cosName)) {
                    ipdmage = (PDImageXObject) resources.getXObject(cosName);
                    BufferedImage image = ipdmage.getImage();
                    out = new ByteArrayOutputStream();
                    ImageIO.write(image, "PNG", out);
                    imageList.add(out.toByteArray());
                    // sizeList.add(out.toByteArray().length);
                    // imageList.add(ipdmage);
                    out.close();
                }
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            out.close();
        }

        return imageList;
    }

    public static String readPdf(String fileStr) throws Exception {
        String pdfText = "";
        File file = new File(fileStr);
        // 是否排序
        boolean sort = false;
        // pdf文件名
        String pdfFile = file.getName();
        // 开始提取页数
        int startPage = 1;
        // 结束提取页数
        int endPage = Integer.MAX_VALUE;
        // 内存中存储的PDF Document
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            // PDFTextStripper来提取文本
            PDFTextStripper stripper = null;
            stripper = new PDFTextStripper();
            // 设置是否排序
            stripper.setSortByPosition(sort);
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            // 调用PDFTextStripper的writeText提取并输出文本
            pdfText = stripper.getText(document);
            System.out.println(pdfText);
        }
        finally {
            if (document != null) {
                // 关闭PDF Document
                document.close();
            }
        }
        return pdfText;
    }

    public static void main(String[] args) throws Exception {
        PDDocument document = PDDocument.load(new File("F:\\zteSoft\\ocr\\实名稽核样本\\cust_order_images_1000\\8731200922984924462.pdf"));
        List<Object> list = readImage(document, true);
        if (list != null && !list.isEmpty()) {
            for (Object obj : list) {
                System.out.println(obj.toString());
            }
        }
        /*
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );
        Rectangle rect = new Rectangle( 10, 400, 275, 100 );
        stripper.addRegion( "class1", rect );
        PDPageTree allPages = document.getDocumentCatalog().getPages();
        PDPage firstPage = (PDPage)allPages.get( 8 );
        stripper.extractRegions( firstPage );
        System.out.println( "Text in the area:" + rect );
        System.out.println( stripper.getTextForRegion( "class1" ) );*/
        /*readPdf("F:\\zteSoft\\ocr\\合同附件\\HNXTA2001826CGN00\\ITV测试1.pdf");
        String dir = "D:\\ZTEsoft\\OCR_\\pdf\\all";
        File file = new File(dir);
        File[] subFiles = file.listFiles();
        for (File subFile: subFiles) {
            List<Object> list = PdfUtil.readImage(subFile.getPath(), true);
            String outputStr = dir + "/" + subFile.getName().substring(0, subFile.getName().indexOf("."));
            File output = new File(outputStr);
            output.mkdirs();
            for (Object obj: list) {
                BufferedImage idcardImage = ImageUtil.getBufferedImage((String)obj);
                ImageIO.write(idcardImage, "jpg", new File(outputStr + "/" + System.currentTimeMillis() + ".jpg"));
            }
        }*/
    }


    /**
     * 读取PDF最后一页文本信息,返回String字符串
     *
     * @param path
     * @return private static String readText(String path) {
    PDDocument document = null;
    try {
    File file = new File(path);
    document = PDDocument.load(file);

    // 文本内容
    PDFTextStripper stripper = new PDFTextStripper();

    // 设置按顺序输出
    stripper.setSortByPosition(true);
    stripper.setStartPage(document.getNumberOfPages());
    stripper.setEndPage(document.getNumberOfPages());
    String text = stripper.getText(document);

    return text.trim();
    }
    catch (IOException e) {
    logger.error(e.getMessage(), e);
    }
    finally {
    try {
    if (document != null) {
    document.close();
    }
    }
    catch (IOException e) {
    logger.error(e.getMessage(), e);
    }
    }
    return null;
    }*/

    /**
     * 获取一个目录下面的所有的PDF文件
     *
     * @param dir
     * @return private static File[] getPdfFile(String dir) {
    File f = new File(dir);

    File[] allFiles = f.listFiles(new FileFilter() {
    // 过滤掉目录和非PDF文件
     @Override public boolean accept(File file) {
     String fileUrl = file.toString();
     if (file.isFile() && (fileUrl.endsWith(".pdf") || fileUrl.endsWith(".PDF"))) {
     return true;
     }
     else {
     return false;
     }
     }
     });
     return allFiles;
     } */

    /**
     * 获取一个目录下面的所有的PDF文件
     *
     * @param dir
     * @return private static List<FTPFile> getFtpPdfFile(FTPClient ftpClient, String dir) {
    List<FTPFile> result = new ArrayList<FTPFile>();

    try {
    //更换目录到当前目录
    ftpClient.changeWorkingDirectory(dir);
    FTPFile[] files = ftpClient.listFiles();

    for (FTPFile file : files) {
    if (file.isFile()) {
    String fileName = file.getName();
    if (fileName.endsWith(".pdf") || fileName.endsWith(".PDF")) {
    result.add(file);
    }
    }
    }
    }
    catch (IOException e) {
    logger.error(e.getMessage(), e);
    }
    return result;
    } */

    /**
     * 获取一个目录下面的所有的PDF文件，返回PDF文件目录
     *
     * @param dir
     * @return private static List<String> getPdfFilePath(String dir) {
    File f = new File(dir);

    File[] allFiles = f.listFiles(new FileFilter() {// 过滤掉目录和非PDF文件
     @Override public boolean accept(File file) {
     String fileUrl = file.toString();
     if (file.isFile() && (fileUrl.endsWith(".pdf") || fileUrl.endsWith(".PDF"))) {
     return true;
     }
     else {
     return false;
     }
     }
     });

     List<String> result = new ArrayList<String>();
     for (File file : allFiles) {
     String path = dir + "/" + file.getName();
     result.add(path);
     }

     return result;
     } */

    /**
     * 将一个文件移入到另外一个目录
     *
     * @param filePath
     * @param newPath

    private static void transferFile(String filePath, String newPath) {
    try {
    File afile = new File(filePath);
    if (!afile.exists()) {
    return;
    }
    else {
    if (afile.exists()) {
    afile.renameTo(new File(newPath));
    }
    }
    }
    catch (Exception e) {
    logger.error(e.getMessage(), e);
    }
    finally {

    }
    }*/

    /**
     * FTP中将一个文件移入到另外一个目录
     *
     * @param filePath
     * @param newPath

    private static void transferFile(FTPClient ftpClient, String filePath, String newPath) {
    try {
    if (ftpClient.changeWorkingDirectory(filePath)) {
    return;
    }
    else {
    ftpClient.rename(filePath, newPath);
    }
    }
    catch (Exception e) {
    logger.error(e.getMessage(), e);
    }
    } */

    /**
     * 删除FTP文件文件
     *
     * @param ftpClient ftp信息
     * @param pathname  FTP服务器保存目录 *
     * @param filename  要删除的文件名称 *
     * @return private static boolean deleteFile(FTPClient ftpClient, String pathname, String filename) throws Exception {
    boolean flag = false;
    try {
    //切换FTP目录
    ftpClient.changeWorkingDirectory(pathname);
    ftpClient.dele(filename);
    ftpClient.logout();
    flag = true;
    }
    catch (Exception e) {
    logger.error(e.getMessage(), e);
    throw e;
    }
    finally {
    if (ftpClient.isConnected()) {
    try {
    ftpClient.disconnect();
    }
    catch (IOException e) {
    logger.error(e.getMessage(), e);
    }
    }
    }
    return flag;
    } */

}
