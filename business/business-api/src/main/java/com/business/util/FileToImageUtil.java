package com.business.util;

import com.aspose.cells.*;
import com.aspose.pdf.devices.JpegDevice;
import com.aspose.slides.ISlide;
import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2021/2/4
 * @time 创建时间: 11:23
 */
public class FileToImageUtil {

    /**
     * 需要签名，否则有水印
     *
     * @return
     */
    public static boolean getLicense(String type) throws Exception {
        boolean result = false;
        try {
            // 凭证
            String license = "<License>\n" +
                    "  <Data>\n" +
                    "    <Products>\n" +
                    "      <Product>Aspose.Total for Java</Product>\n" +
                    "      <Product>Aspose.Words for Java</Product>\n" +
                    "    </Products>\n" +
                    "    <EditionType>Enterprise</EditionType>\n" +
                    "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "  </Data>\n" +
                    "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                    "</License>";
            InputStream is = new ByteArrayInputStream(license.getBytes("UTF-8"));
            if (type.equals(".doc") || type.equals(".docx")) {
                License asposeLic = new License();
                asposeLic.setLicense(is);
                result = true;
            } else if (type.equals(".xls") || type.equals(".xlsx")) {
                com.aspose.cells.License asposeLic = new com.aspose.cells.License();
                asposeLic.setLicense(is);
                result = true;
            } else if (type.equals(".ppt") || type.equals(".pptx")) {
                com.aspose.slides.License asposeLic = new com.aspose.slides.License();
                asposeLic.setLicense(is);
                result = true;
            } else if (type.equals(".pdf")) {
                com.aspose.pdf.License asposeLic = new com.aspose.pdf.License();
                asposeLic.setLicense(is);
                result = true;
            }
            is.close();
        } catch (Exception e) {
            throw new Exception("aspose lic ERROR!" + e);
        }
        return result;
    }


    /**
     * @param inPath
     * @param outDir
     */
    public static List<String> turnMain(String suffix, String fileName, String inPath, String outDir) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            if (getLicense(suffix)) {
                long old = System.currentTimeMillis();
                if (suffix.equals(".doc") || suffix.equals(".docx")) {
                    list = wordToImg(fileName, inPath, outDir);
                } else if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
                    list = excelToImg(fileName, inPath, outDir);
                } else if (suffix.equals(".ppt") || suffix.equals(".pptx")) {
                    list = pptToImg(fileName, inPath, outDir);
                } else if (suffix.equals(".pdf")) {
                    list = pdfToImg(fileName, inPath, outDir);
                } else {
                    throw new Exception("格式错误");
                }
                long now = System.currentTimeMillis();
                System.out.println("convert OK! " + ((now - old) / 1000.0) + "秒");
            } else {
                throw new Exception("获取签名错误");
            }
        } catch (Exception e) {
            throw new Exception("turnMain Exception：" + e);
        }
        return list;
    }

    /**
     * word转图片
     *
     * @param inPath
     * @param outDir
     */
    private static List<String> wordToImg(String fileName, String inPath, String outDir) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            // word文档
            Document doc = new Document(inPath);
            // 支持RTF HTML,OpenDocument, PDF,EPUB, XPS转换
            //doc.save(outDir , SaveFormat.PDF);
            //ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
            ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
            int pageCount = doc.getPageCount();
            for (int i = 0; i < pageCount; i++) {
                String imgFileName = fileName + "_" + (i + 1) + ".png";
                File file = new File(outDir + File.separator + imgFileName);
                FileOutputStream os = new FileOutputStream(file);
                options.setPageIndex(i);
                doc.save(os, options);
                os.close();
                list.add(imgFileName);
            }
        } catch (Exception e) {
            throw new Exception("wordToImg Exception：" + e);
        }
        return list;
    }

    /**
     * excel转图片
     *
     * @param xlsPath
     * @param picPath
     */
    private static List<String> excelToImg(String fileName, String xlsPath, String picPath) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            Workbook workbook = new Workbook(xlsPath);// 原始excel路径
            //workbook.save(new FileOutputStream(new File(pdfPath)),SaveFormat.PDF);

            int cnt = workbook.getWorksheets().getCount();
            // Create an object for ImageOptions
            ImageOrPrintOptions imgOptions = new ImageOrPrintOptions();
            // Set the format type of the image
            imgOptions.setImageFormat(ImageFormat.getJpeg());
            CellsHelper.setFontDir("c:\\windows\\fonts");

            for (int i = 0; i < workbook.getWorksheets().getCount(); i++) {
                // 获取工作表
                Worksheet sheet = workbook.getWorksheets().get(i);
                // Create a SheetRender object with respect to your desired sheet
                SheetRender sr = new SheetRender(sheet, imgOptions);
                for (int j = 0; j < sr.getPageCount(); j++) {
                    String imgFileName = fileName + "_" + (j + 1) + ".jpg";
                    // Generate image(s) for the worksheet
                    sr.toImage(j, picPath + File.separator + imgFileName);
                    list.add(imgFileName);
                }
            }
        } catch (Exception e) {
            throw new Exception("excelToImg Exception：" + e);
        }
        return list;
    }

    /**
     * ppt转图片
     *
     * @param xlsPath
     * @param picPath
     */
    private static List<String> pptToImg(String fileName, String xlsPath, String picPath) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            com.aspose.slides.Presentation pres = new com.aspose.slides.Presentation(xlsPath);
            for (int i = 0; i < pres.getSlides().size(); i++) {
                ISlide slide = pres.getSlides().get_Item(i);
                int height = (int) (pres.getSlideSize().getSize().getHeight());
                int width = (int) (pres.getSlideSize().getSize().getWidth());
                BufferedImage image = slide.getThumbnail(new java.awt.Dimension(width, height));
                //每一页输出一张图片
                String imgFileName = fileName + "_" + (i + 1) + ".jpg";
                File outImage = new File(picPath + File.separator + imgFileName);
                ImageIO.write(image, "jpg", outImage);
                image.flush();
                list.add(imgFileName);
            }
        } catch (Exception e) {
            throw new Exception("pptToImg Exception：" + e);
        }
        return list;
    }

    /**
     * pdf转图片
     *
     * @param pdfPath
     * @param picPath
     */
    private static List<String> pdfToImg(String fileName, String pdfPath, String picPath) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(pdfPath);
            //Resolution resolution = new Resolution(960);
            JpegDevice jpegDevice = new JpegDevice();
            for (int index = 1; index <= pdfDocument.getPages().size(); index++) {
                String imgFileName = fileName + "_" + index + ".jpg";
                // 输出路径
                File file = new File(picPath + File.separator + imgFileName);
                FileOutputStream fileOs = new FileOutputStream(file);
                jpegDevice.process(pdfDocument.getPages().get_Item(index), fileOs);
                fileOs.close();
                list.add(imgFileName);
            }
        } catch (Exception e) {
            throw new Exception("pdfToImg Exception：" + e);
        }
        return list;
    }
}
