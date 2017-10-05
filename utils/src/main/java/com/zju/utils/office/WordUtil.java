package com.zju.utils.office;

import fr.opensagres.xdocreport.core.io.internal.StringBuilderOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by xujingfeng on 2017/10/5.
 */
public class WordUtil {

    final static Logger logger = LoggerFactory.getLogger(WordUtil.class);

    public static String doc2Html(String docDir) {
        HWPFDocument wordDocument;
        String uuid = UUID.randomUUID().toString();
        new File(HtmlUtil.FILE_DIR + uuid).mkdir();
        try {
            //字节码输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //根据输入文件路径与名称读取文件流
            InputStream in = new FileInputStream(docDir);
            //把文件流转化为输入wordDom对象
            wordDocument = new HWPFDocument(in);
            //通过反射构建dom创建者工厂
            DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
            //生成dom创建者
            DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
            //生成dom对象
            Document dom = domBuilder.newDocument();
            //生成针对Dom对象的转化器
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(dom);
            //转化器重写内部方法
            //word中有图片，存放在指定的目录下
            /**
             * content: 字节数组，图片的真实内容
             * suggestedName: 生成的图片名称
             */
            wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> {
                String resultName = HtmlUtil.FILE_DIR + uuid + "\\" + suggestedName;
                generatePicture(content, resultName);
                return resultName;
            });
            //转化器开始转化接收到的dom对象
            wordToHtmlConverter.processDocument(wordDocument);
            //保存文档中的图片
            /*    List<?> pics=wordDocument.getPicturesTable().getAllPictures();
                if(pics!=null){
                    for(int i=0;i<pics.size();i++){
                        Picture pic = (Picture)pics.get(i);
                        try {
                            pic.writeImageContent(new FileOutputStream("E:/test/"+ pic.suggestFullFileName()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            } */
            //从加载了输入文件中的转换器中提取DOM节点
            Document htmlDocument = wordToHtmlConverter.getDocument();
            //从提取的DOM节点中获得内容
            DOMSource domSource = new DOMSource(htmlDocument);
            //输出流的源头
            StreamResult streamResult = new StreamResult(out);
            //转化工厂生成序列转化器
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            //设置序列化内容格式
            serializer.setOutputProperty(OutputKeys.ENCODING, HtmlUtil.DEFAULT_ENCODING);
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            //生成文件方法
            String htmlStr = new String(out.toByteArray(), Charset.forName(HtmlUtil.DEFAULT_ENCODING));
            out.close();
            return htmlStr;
        } catch (Exception e) {
            logger.error("转换doc异常", e);
            return null;
        }
    }

    private static File generatePicture(byte[] b, String fileDir) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(fileDir);
            file.createNewFile();
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            logger.error("生成图片异常", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    logger.error("关闭输出流异常", e);
                }
            }
        }
        return file;
    }

    public static String docx2Html(String docDir) {
        String sourceFileName = docDir;
        String uuid = UUID.randomUUID().toString();
        new File(HtmlUtil.FILE_DIR + uuid).mkdir();
        String imagePathStr = HtmlUtil.FILE_DIR + uuid;
        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(sourceFileName));
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagePathStr)));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver(imagePathStr));
            StringBuilderOutputStream html = new StringBuilderOutputStream();
            outputStreamWriter = new OutputStreamWriter(html, HtmlUtil.DEFAULT_ENCODING);
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
            return html.toString();
        } catch (Exception e) {
            logger.error("转换docx异常", e);
            return null;
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    logger.error("关闭输出流异常", e);
                }

            }
        }
    }

}
