package cn.liushiyao.reptile.spider.test;

import cn.liushiyao.reptile.spider.bean.LinkTypeData;
import cn.liushiyao.reptile.spider.core.ExtractService;
import cn.liushiyao.reptile.spider.rule.Rule;

import java.util.List;



public class Test
{
	@org.junit.Test
	public void getDatasByClass()
	{
		Rule rule = new Rule(
				"http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
		new String[] { "query.enterprisename","query.registationnumber" }, new String[] { "兴网","" },
				"cont_right", Rule.CLASS, Rule.POST);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	@org.junit.Test
	public void getDatasByCssQuery()
	{
		Rule rule = new Rule("http://www.11315.com/search",
				new String[] { "name" }, new String[] { "兴网" },
				"div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	public void printf(List<LinkTypeData> datas)
	{
		for (LinkTypeData data : datas)
		{
			System.out.println(data.getLinkText());
			System.out.println(data.getLinkHref());
			System.out.println("***********************************");
		}

	}
	/**
	 * 使用百度新闻，只设置url和关键字与返回类型
	 */
	@org.junit.Test
	public void getDatasByCssQueryUserBaidu()
	{
		Rule rule = new Rule("http://news.baidu.com/ns",
				new String[] { "word" }, new String[] { "支付宝" },
				null, -1, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}
}
