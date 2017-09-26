package cn.liushiyao.reptile.search.constant;



/*
wd-查询的关键字
pn-已显示的结果条数（用于定位），即当前页从pn条记录开始显示，该值为10的倍数
cl-搜索类型：0-搜索所有结果；3-网页搜索；2-图片搜索或者新闻搜索
rn--搜索结果中每页显示的条数，取值范围在10、20、50、100，缺省设置rn=10
ie--查询输入文字的编码，缺省设置ie=gb2312，即为简体中文。
tn-- 提交搜索请求的来源站点。
ct--语言，0-所有语言，1-简体中文网页，2-繁体中文网页；默认值为0。
 */
public class BaiduSearchConstant {


    
    public static class ShowNumber{
        public static byte N10=10;
        public static byte N20=20;
        public static byte N50=50;
        public static byte N100=100;
    }
    public static class Encode{
        public static String UTF8="utf-8";
        public static String GB2312="gb2312";
    }
    public static class RequestWeb{
        public static String baidu="baidu";
    }
    public static class Language{
        public static byte all=0;
        public static byte cs=1;
        public static byte ct=2;

    }


}
