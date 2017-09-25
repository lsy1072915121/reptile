package cn.liushiyao.reptile.baidu.pojo;

import java.util.Map;

/**
 * 查询结果
 *
 */
public class KeyResult {

    //关键字
    private String keyWord;

    //搜索返回的词条以及链接
    private Map<String,String> queryResult;


    public String getKeyWord ( ) {
        return keyWord;
    }

    public void setKeyWord ( String keyWord ) {
        this.keyWord = keyWord;
    }

    public Map <String, String> getQueryResult ( ) {
        return queryResult;
    }

    public void setQueryResult ( Map <String, String> queryResult ) {
        this.queryResult = queryResult;
    }
}
