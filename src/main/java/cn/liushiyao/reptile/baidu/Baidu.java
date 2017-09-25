package cn.liushiyao.reptile.baidu;


import cn.liushiyao.reptile.baidu.pojo.KeyResult;

import java.util.Iterator;
import java.util.Map;

/**
 * baidu 搜索关键字词条爬虫
 * 目的：爬去百度关键字的以及链接
 *
 *
 */


public class Baidu
{
	
	public static void main(String[] args) throws Exception {
		HtmlUnitforBD htmlUnitforBD = new HtmlUnitforBD ("小米");
		KeyResult keyResult = htmlUnitforBD.getQueryResult ();
		Map<String ,String > map =  keyResult.getQueryResult ();
		Iterator iterator = map.entrySet ().iterator ();
		for (String key:map.keySet () ){
			System.out.println ("key"+key+"==>"+map.get ( key ) );
		}
	}

}
