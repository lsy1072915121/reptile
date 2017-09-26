package cn.liushiyao.reptile.search.vo;

import cn.liushiyao.reptile.search.constant.BaiduSearchConstant;

/**
 * 百度搜索
 */

/*
wd-查询的关键字
pn-已显示的结果条数（用于定位），即当前页从pn条记录开始显示，该值为10的倍数
(舍)cl-搜索类型：0-搜索所有结果；3-网页搜索；2-图片搜索或者新闻搜索
rn--搜索结果中每页显示的条数，取值范围在10、20、50、100，缺省设置rn=10
ie--查询输入文字的编码，缺省设置ie=gb2312，即为简体中文。
tn-- 提交搜索请求的来源站点。
ct--语言，0-所有语言，1-简体中文网页，2-繁体中文网页；默认值为0。
 */

public class BaiduSearchVo {


    private String keyWord;
    private byte pointNumber;


    public BaiduSearchVo ( String keyWord , byte pointNumber ) {
        this.keyWord = keyWord;
        this.pointNumber = pointNumber;
        showNumber= BaiduSearchConstant.ShowNumber.N10;
        enCode=BaiduSearchConstant.Encode.UTF8;
        searchWeb=BaiduSearchConstant.RequestWeb.baidu;
        language=BaiduSearchConstant.Language.all;
    }

    private byte showNumber;
    private String enCode;
    private String searchWeb;
    private byte language;

    public String getKeyWord ( ) {
        return keyWord;
    }

    public void setKeyWord ( String keyWord ) {
        this.keyWord = keyWord;
    }

    public byte getPointNumber ( ) {
        return pointNumber;
    }

    public void setPointNumber ( byte pointNumber ) {
        this.pointNumber = pointNumber;
    }

    public byte getShowNumber ( ) {
        return showNumber;
    }

    public void setShowNumber ( byte showNumber ) {
        this.showNumber = showNumber;
    }

    public String getEnCode ( ) {
        return enCode;
    }

    public void setEnCode ( String enCode ) {
        this.enCode = enCode;
    }

    public String getSearchWeb ( ) {
        return searchWeb;
    }

    public void setSearchWeb ( String searchWeb ) {
        this.searchWeb = searchWeb;
    }

    public byte getLanguage ( ) {
        return language;
    }

    public void setLanguage ( byte language ) {
        this.language = language;
    }
}
