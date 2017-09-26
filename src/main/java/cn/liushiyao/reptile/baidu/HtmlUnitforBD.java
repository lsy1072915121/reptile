package cn.liushiyao.reptile.baidu;

import cn.liushiyao.reptile.baidu.pojo.KeyResult;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

///////////////关于htmlunit的相关资料，在此站上有些资料，参考了一下：http://www.cnblogs.com/cation/p/3933408.html  

public class HtmlUnitforBD {
    private int N = 10;// 搜索页数(默认一页)
    private String keyW = "";
    private HtmlPage firstBaiduPage;        // 保存第一页搜索结果
    private String format = "";        // Baidu对应每个搜索结果的第一页第二页第三页等等其中包含“&pn=1”,“&pn=2”,“&pn=3”等等，提取该链接并处理可以获取到一个模板，用于定位某页搜索结果


    private KeyResult keyResult;

    public KeyResult getQueryResult(){
        return keyResult;
    }

    //===================构造函数======================//
    public HtmlUnitforBD ( ) {
        super ( );
    }

    public HtmlUnitforBD ( String keyW ) throws FailingHttpStatusCodeException, IOException {
        super ( );
        this.keyW = keyW;
        this.mainFunction ( N , keyW );
    }

    public void mainFunction ( final int n , final String keyWord )
            throws FailingHttpStatusCodeException, IOException {
        int x = n;// 页数
        keyResult = new KeyResult ();
        keyResult.setKeyWord ( keyWord );
        Map<String ,String > map  = new HashMap <> (  );
		/*
         * 1.获取并输出第一页百度查询内容
		 */
        Elements firstPageURL = null;
        try {
            firstPageURL = getFirstPage ( keyWord );
        } catch ( FailingHttpStatusCodeException | IOException e ) {
            e.printStackTrace ( );
        }
        // 定义firstPageURL作为第一个搜索页的元素集
        for ( Element newlink : firstPageURL ) {
            String linkHref = newlink.attr ( "href" );// 提取包含“href”的元素成分，JSoup实现内部具体过程
            String linkText = newlink.text ( );
            // 声明变量用于保存每个链接的摘要
            if ( linkHref.length ( ) > 14 & linkText.length ( ) > 2 ) {
                // 去除某些无效链接
                if ( ( ! linkText.equals ( "立即抢购" ) ) && ( ! linkText.equals ( "百度快照" ) ) && ( ! ( linkText.contains ( "条评价" ) ) ) && ( ! linkText.contains ( "好评" ) ) ) {

                    map.put ( linkText,linkHref);

                }

            }
        }

		/*
		 * 2.读取第二页及之后页面预处理
		 */
        nextHref ( firstBaiduPage );// 以firstBaiduPage作为参数，定义format，即网页格式。
		/*
		 * 3.获取百度第一页之后的搜索结果
		 */
        for ( int i = 1 ; i < x ; i++ ) {
            String tempURL = format.replaceAll ( "&pn=1" , "&pn=" + i + "" );// 根据已知格式修改生成新的一页的链接
            HtmlUnitforBD h = new HtmlUnitforBD ( );
            String htmls = h.getPageSource ( tempURL , "utf-8" );// 不知为何此处直接用JSoup的相关代码摘取网页内容会出现问题，所以采用新的编码来实现摘取网页源码
            Document doc = Jsoup.parse ( htmls );// 网页信息转换为jsoup可识别的doc模式
            Elements links = doc.select ( "a[data-click]" );// 摘取该页搜索链接
            for ( Element newlink : links ) {// 该处同上getFirstPage的相关实现
                String linkHref = newlink.attr ( "href" );
                String linkText = newlink.text ( );
                if ( linkHref.length ( ) > 14 & linkText.length ( ) > 2 ) {// 删除某些无效链接，查查看可发现有些无效链接是不包含信息文本的
//					System.out.println(linkHref + "\n\t\t摘要：" + linkText);
//                    eachurl.add ( linkHref );// 作为存储手段存储在arrayList里面
                    map.put ( linkText,linkHref);
                }
            }
        }
        keyResult.setQueryResult ( map );
    }



    /**
     * 获取百度搜索第一页内容
     * @param w
     * @return
     * @throws FailingHttpStatusCodeException
     * @throws MalformedURLException
     * @throws IOException
     */
    public Elements getFirstPage ( String w ) throws FailingHttpStatusCodeException, IOException {
        String word = w;
        WebClient webClient = new WebClient ( BrowserVersion.CHROME );
        webClient.getOptions ( ).setJavaScriptEnabled ( false );// HtmlUnit对JavaScript的支持不好，关闭之
        webClient.getOptions ( ).setCssEnabled ( false );// HtmlUnit对CSS的支持不好，关闭之
        HtmlPage page = ( HtmlPage ) webClient.getPage ( "http://www.baidu.com/" );// 百度搜索首页页面
        HtmlInput input = ( HtmlInput ) page.getHtmlElementById ( "kw" );// 获取搜索输入框并提交搜索内容（查看源码获取元素名称）
        input.setValueAttribute ( word );// 将搜索词模拟填进百度输入框（元素ID如上）
        // 获取搜索按钮并点击
        HtmlInput btn = ( HtmlInput ) page.getHtmlElementById ( "su" );
        // 模拟搜索按钮事件
        firstBaiduPage = btn.click ( );
        // 将获取到的百度搜索的第一页信息输出
        String WebString = firstBaiduPage.asXml ( ).toString ( );
        Document doc = Jsoup.parse ( WebString );// 转换为Jsoup识别的doc格式
        System.out.println ( "************百度搜索“" + word + "”第1页结果************" );// 输出第一页结果
        Elements links = doc.select ( "a[data-click]" );// 返回包含类似<a......data-click=" "......>等的元素，详查JsoupAPI
        return links;// 返回此类链接，即第一页的百度搜素链接
    }

    /*
     * 获取下一页地址
     */
    public void nextHref ( HtmlPage p ) {
        // 输入：HtmlPage格式变量，第一页的网页内容；
        // 输出：format的模板
        WebClient webClient = new WebClient ( BrowserVersion.CHROME );
        webClient.getOptions ( ).setJavaScriptEnabled ( false );
        webClient.getOptions ( ).setCssEnabled ( false );
        p = firstBaiduPage;
        String morelinks = p.getElementById ( "page" ).asXml ( );// 获取到百度第一页搜索的底端的页码的html代码
        org.jsoup.nodes.Document doc = Jsoup.parse ( morelinks );// 转换为Jsoup识别的doc格式
        Elements links = doc.select ( "a[href]" );// 提取这个html中的包含<a href=""....>的部分
        boolean getFormat = true;// 设置只取一次每页链接的模板格式
        for ( Element newlink : links ) {
            String linkHref = newlink.attr ( "href" );// 将提取出来的<a>标签中的链接取出
            if ( getFormat ) {
                format = "http://www.baidu.com" + linkHref;// 补全模板格式
                getFormat = false;
            }
        }
    }

    public String getPageSource ( String pageUrl , String encoding ) {
        // 输入：url链接&编码格式
        // 输出：该网页内容
        StringBuffer sb = new StringBuffer ( );
        try {
            URL url = new URL ( pageUrl );// 构建一URL对象
            BufferedReader in = new BufferedReader ( new InputStreamReader ( url.openStream ( ) , encoding ) );// 使用openStream得到一输入流并由此构造一个BufferedReader对象
            String line;
            while ( ( line = in.readLine ( ) ) != null ) {
                sb.append ( line );
                sb.append ( "\n" );
            }
            in.close ( );
        } catch ( Exception ex ) {
            //		System.err.println(ex);
        }
        return sb.toString ( );
    }
}