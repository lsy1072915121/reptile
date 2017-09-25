package cn.liushiyao.reptile.baidu;



import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class transURLtoINFO {
	/*
	 * 匹配消除html元素
	 */
	// 定义script的正则表达式
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
	// 定义style的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
	// 定义HTML标签的正则表达式
	private static final String regEx_html = "<[^>]+>";
	// 定义空格回车换行符
	private static final String regEx_space = "\\s*|\t|\r|\n";

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String temp = null;
		trans("http://www.baidu.com/", temp);
		System.out.println("over");
	}

	public static String trans(String url, String info) throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		ArrayList<String> hrefList = new ArrayList<String>();
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		try {
			HtmlPage page = null;
			try {
				page = (HtmlPage) webClient.getPage(url);
			} catch (ConnectException e) {
			}
			InputStream temp = new ByteArrayInputStream(page.asText().getBytes());
			InputStreamReader isr = new InputStreamReader(temp);
			BufferedReader br = new BufferedReader(isr);
			String str = null, rs = null;
			while ((str = br.readLine()) != null) {
				rs = str;
				if (rs != null)
					hrefList.add(rs);
			}
			System.out.println("从该网址" + url + "查找的可能相关文本如下：");
			for (int i = 0; i < hrefList.size(); i++) {
				String string = hrefList.get(i);
				string = getTextFromHtml(string);
				if (string.length() >= 50) {
					info += "\n" + string;
					System.out.println(string);
				}
			}
		} catch (IOException e) {
		}
		return info;
	}

	/*
	 * 从一行开始清除标签
	 * 
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static String getTextFromHtml(String htmlStr) {
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll(" ", "");
		return htmlStr;
	}
}