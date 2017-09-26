package cn.liushiyao.reptile.search;


import cn.liushiyao.reptile.search.vo.BaiduSearchVo;
import cn.liushiyao.reptile.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络搜索（目前百度）
 */
/*
wd-查询的关键字
pn-已显示的结果条数（用于定位），即当前页从pn条记录开始显示，该值为10的倍数
cl-搜索类型：0-搜索所有结果；3-网页搜索；2-图片搜索或者新闻搜索
rn--搜索结果中每页显示的条数，取值范围在10、20、50、100，缺省设置rn=10
ie--查询输入文字的编码，缺省设置ie=gb2312，即为简体中文。
tn-- 提交搜索请求的来源站点。
ct--语言，0-所有语言，1-简体中文网页，2-繁体中文网页；默认值为0。
 */
public class SearchUtil {

    /**
     * @return 词条+url
     */
    public static Map <String, String> queryResult ( BaiduSearchVo baiduSearchVo ) {

        Map <String, String> map = new HashMap <> ( );
        map.put ( "wd" , baiduSearchVo.getKeyWord ( ) );
        map.put ( "pn" , String.valueOf ( baiduSearchVo.getPointNumber ( ) ) );
        map.put ( "rn" , String.valueOf ( baiduSearchVo.getShowNumber ( ) ) );
        map.put ( "ie" , baiduSearchVo.getEnCode ( ) );
        map.put ( "tn" , baiduSearchVo.getSearchWeb ( ) );
        map.put ( "ct" , String.valueOf ( baiduSearchVo.getLanguage ( ) ) );
        String result = HttpUtils.URLGet ( "http://www.baidu.com/s" , map , HttpUtils.URL_PARAM_DECODECHARSET_UTF8 );
        Document doc = Jsoup.parse(result);
        Element div = doc.getElementById ( "4" );
        Element div2 = doc.getElementById ( "6" );
        Elements h3 = doc.getElementsByTag ( "h3" );
        Elements string  = h3.select ( "a" );

//        System.out.println (div.toString () );
        System.out.println (string.toString ());
        System.out.println ("===============================" );

        return null;
    }

    public static void main ( String[] a ) {

        SearchUtil.queryResult ( new BaiduSearchVo ( "旅游" , (byte)0 ) );


    }
    public static void demo1(){
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
        System.out.println (doc.getElementsByTag ( "body" ).toString ());
    }


}
