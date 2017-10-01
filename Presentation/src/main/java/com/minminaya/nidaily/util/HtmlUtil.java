package com.minminaya.nidaily.util;

import com.minminaya.data.http.model.content.ContentModel;

import java.util.List;

/**
 * Created by Niwa on 2017/10/02
 * <p>
 * 处理的html工具类
 */
public class HtmlUtil {

    //css样式,隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

    //css style tag,需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG
            = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";


    public static final String MIME_TYPE = "text/html; charset=utf-8";

    public static final String ENCODING = "utf-8";


    private HtmlUtil() {

    }


    /**
     * 根据css链接生成Link标签
     *
     * @param url String
     * @return String
     */
    public static String createCssTag(String url) {

        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }


    /**
     * 根据多个css链接生成Link标签
     *
     * @param urls List<String>
     * @return String
     */
    public static String createCssTag(List<String> urls) {

        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }


    /**
     * 根据样式标签,html字符串
     * 生成完整的HTML文档
     *
     * @param html string
     * @param css  string
     * @return string
     */
    private static String createHtmlData(String html, String css) {

        return css.concat(HIDE_HEADER_STYLE).concat(html);
    }


    /**
     * 生成完整的HTML文档
     *
     * @return String
     */
    public static String createHtmlData(ContentModel model) {

        final String css = HtmlUtil.createCssTag(model.getCss());
        return createHtmlData(model.getBody(), css);
    }
}
