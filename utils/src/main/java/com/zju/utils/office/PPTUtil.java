package com.zju.utils.office;

import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.xslf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by xujingfeng on 2017/10/5.
 */
public class PPTUtil {

    final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static String pptx2html(String docDir) {
        try {
            StringBuffer html = new StringBuffer();
            html.append("<html><head></head><body>");
            String uuid = UUID.randomUUID().toString();
            new File(HtmlUtil.FILE_DIR + uuid).mkdir();

            FileInputStream is = new FileInputStream(docDir);
            XMLSlideShow ppt = new XMLSlideShow(is);
            is.close();

            Dimension pgsize = ppt.getPageSize();

            for (int i = 0; i < ppt.getSlides().size(); i++) {
                try {
                    //防止中文乱码
                    for (XSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                        if (shape instanceof XSLFTextShape) {
                            XSLFTextShape tsh = (XSLFTextShape) shape;
                            for (XSLFTextParagraph p : tsh) {
                                for (XSLFTextRun r : p) {
                                    r.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    // clear the drawing area
                    graphics.setPaint(Color.white);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                    // render
                    ppt.getSlides().get(i).draw(graphics);

                    // save the output
                    String filename = HtmlUtil.FILE_DIR + uuid + "\\" + (i + 1) + ".jpg";
                    html
                            .append("<div style=\"text-align:center\"><img src=\"")
                            .append(filename)
                            .append("\"></div>");
                    FileOutputStream out = new FileOutputStream(filename);
                    ImageIO.write(img, "png", out);
                    out.close();
                } catch (Exception e) {
                    logger.error("第{}张ppt转换出错", i, e);
                }
            }
            html.append("</body></html>");
            return html.toString();
        } catch (Exception e) {
            logger.error("转换ppt异常", e);
            return null;
        }

    }

    public static String ppt2html(String docDir) {
        try {

            StringBuffer html = new StringBuffer();
            html.append("<html><head></head><body>");
            String uuid = UUID.randomUUID().toString();
            new File(HtmlUtil.FILE_DIR + uuid).mkdir();

            HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(docDir));

            Dimension pgsize = ppt.getPageSize();
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                //防止中文乱码
                for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape tsh = (HSLFTextShape) shape;
                        for (HSLFTextParagraph p : tsh) {
                            for (HSLFTextRun r : p) {
                                r.setFontFamily("宋体");
                            }
                        }
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                // render
                ppt.getSlides().get(i).draw(graphics);

                // save the output
                String filename = HtmlUtil.FILE_DIR + uuid + "\\" + (i + 1) + ".jpg";

                html
                        .append("<div style=\"text-align:center\"><img src=\"")
                        .append(filename)
                        .append("\"></div>");

                FileOutputStream out = new FileOutputStream(filename);
                ImageIO.write(img, "png", out);
                out.close();
            }
            html.append("</body></html>");
            return html.toString();
        } catch (Exception e) {
            logger.error("转换ppt异常", e);
            return null;
        }
    }

    /**
     * 调整图片大小
     *
     * @param srcImgPath  原图片路径
     * @param distImgPath 转换大小后图片路径
     * @param width       转换后图片宽度
     * @param height      转换后图片高度
     */
    public static void resizeImage(String srcImgPath, String distImgPath,
                                   int width, int height) throws IOException {

        File srcFile = new File(srcImgPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                0, null);

        ImageIO.write(buffImg, "JPEG", new File(distImgPath));

    }


}
