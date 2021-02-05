package com.business.util;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2021/2/4
 * @time 创建时间: 11:23
 */
public class FileToImageUtil {

    //private static boolean getDocLicense() {
    //    boolean result = false;
    //    try {
    //        // 凭证
    //        String license =
    //                "<License>\n" +
    //                        "  <Data>\n" +
    //                        "    <Products>\n" +
    //                        "      <Product>Aspose.Total for Java</Product>\n" +
    //                        "      <Product>Aspose.Words for Java</Product>\n" +
    //                        "    </Products>\n" +
    //                        "    <EditionType>Enterprise</EditionType>\n" +
    //                        "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
    //                        "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
    //                        "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
    //                        "  </Data>\n" +
    //                        "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
    //                        "</License>";
    //        InputStream is = new ByteArrayInputStream(license.getBytes("UTF-8"));
    //        License asposeLic = new License();
    //        asposeLic.setLicense(is);
    //        result = true;
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    return result;
    //}
    //
    ///**
    // * 文档转图片
    // * [url=home.php?mod=space&uid=952169]@Param[/url]
    // * inPath 传入文档地址
    // *
    // * @param outDir 输出的图片文件夹地址
    // */
    //public static void docToImg(String inPath, String outDir) {
    //    try {
    //        if (!getDocLicense()) {
    //            throw new Exception("com.aspose.words lic ERROR!");
    //        }
    //        long old = System.currentTimeMillis();
    //        // word文档
    //        Document doc = new Document(inPath);
    //        // 支持RTF HTML,OpenDocument, PDF,EPUB, XPS转换
    //        //doc.save(outDir , SaveFormat.PDF);
    //        //ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
    //        ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
    //        int pageCount = doc.getPageCount();
    //        for (int i = 0; i < pageCount; i++) {
    //            File file = new File(outDir + "/" + i + ".png");
    //            System.out.println(outDir + "/" + i + ".png");
    //            FileOutputStream os = new FileOutputStream(file);
    //            options.setPageIndex(i);
    //            doc.save(os, options);
    //            os.close();
    //        }
    //        long now = System.currentTimeMillis();
    //        System.out.println("convert OK! " + ((now - old) / 1000.0) + "秒");
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}
}
