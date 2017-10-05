package com.zju.utils.office;

/**
 * Created by xujingfeng on 2017/10/5.
 */
public class HtmlUtil {

    final static String FILE_DIR = "C:\\Users\\xujingfeng\\Desktop\\";
    final static String DEFAULT_ENCODING = "utf-8";

    public static String doc2Html(String docDir) {
        return WordUtil.doc2Html(docDir);
    }

    public static String docx2Html(String docDir) {
        return WordUtil.docx2Html(docDir);
    }

    public static String excel2Html(String docDir) {
        return ExcelUtil.excel2Html(docDir);
    }

    public static String ppt2html(String docDir) {
        return PPTUtil.ppt2html(docDir);
    }

    public static String pptx2html(String docDir) {
        return PPTUtil.pptx2html(docDir);
    }

    public static String generateHTML(String docDir) {
        if (docDir.endsWith(".doc")) {
            return doc2Html(docDir);
        } else if (docDir.endsWith(".docx")) {
            return docx2Html(docDir);
        } else if (docDir.endsWith(".xlsx")) {
            return excel2Html(docDir);
        } else if (docDir.endsWith(".pptx")) {
            return pptx2html(docDir);
        } else if (docDir.endsWith(".ppt")) {
            return ppt2html(docDir);
        } else {
            return "<h1>对不起，该文档不支持预览<h1>";
        }
    }

}
