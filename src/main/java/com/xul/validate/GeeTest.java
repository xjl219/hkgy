package com.xul.validate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xujl.http.method.GetRequest;



public class GeeTest {
	public static final String CHALLENGE="challenge";
	public static final String GT="gt";
	int pianyiNum=2;
	final Map<String,String> header=new HashMap<String, String>();
	public static final String AJAX_URL="%sajax.php?gt=%s&challenge=%s&imgload=%s&passtime=%s&userresponse=%s&a=%s&callback=geetest_%s";
	static CookieStore cookieStore= new BasicCookieStore();
	public static CloseableHttpClient httpClient = HttpClientManager.getConnection(cookieStore);
	CloseableHttpClient httpClientGee = HttpClientManager.getConnection();
	
	TrajectoryHelper trajectoryHelper= new TrajectoryHelper();
	private static final Logger LOG = LoggerFactory.getLogger(GeeTest.class);
	static final Map<String,String> COOKIE=new HashMap<String, String>(){{
		
		put("Accept","*/*");
		put("Accept-Encoding","gzip, deflate, sdch");
		put("Accept-Language","zh-CN,zh;q=0.8");
		put("Connection","keep-alive");
//		put("Cookie","sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221593f0fc7923cb-0ad99b9339844-59123916-2073600-1593f0fc795218%22%7D; GeeTestUser=2ffa90cda51fbbf7bf99ea3733f5418c; Hm_lvt_25b04a5e7a64668b9b88e2711fb5f0c4=1485247903,1486179437,1486185760,1486199382; _ga=GA1.2.945726802.1482821716; _qddaz=QD.b6fjge.8s08d5.ix75smtk");
		put("Host","api.geetest.com");
		put("Referer","http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml");
		put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
//		put("Cookie","GeeTestUser=59ecccee40a0649014658cc2eaa7a8ef; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221593f0fc7923cb-0ad99b9339844-59123916-2073600-1593f0fc795218%22%7D; _ga=GA1.2.945726802.1482821716; Hm_lvt_25b04a5e7a64668b9b88e2711fb5f0c4=1482821716,1483514075,1485052077,1485247903; _qddaz=QD.b6fjge.8s08d5.ix75smtk");
//		put("Host","api.geetest.com");
//		put("Referer","http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml");
//		put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");
	}};
	private static final ArrayList<int[]> OFFSET_LIST = new ArrayList<int[]>() {
		private static final long serialVersionUID = -2536345131825887147L;

		{
			add(new int[] { -157, -58 });
			add(new int[] { -145, -58 });
			add(new int[] { -265, -58 });
			add(new int[] { -277, -58 });
			add(new int[] { -181, -58 });
			add(new int[] { -169, -58 });
			add(new int[] { -241, -58 });
			add(new int[] { -253, -58 });
			add(new int[] { -109, -58 });
			add(new int[] { -97, -58 });
			add(new int[] { -289, -58 });
			add(new int[] { -301, -58 });
			add(new int[] { -85, -58 });
			add(new int[] { -73, -58 });
			add(new int[] { -25, -58 });
			add(new int[] { -37, -58 });
			add(new int[] { -13, -58 });
			add(new int[] { -1, -58 });
			add(new int[] { -121, -58 });
			add(new int[] { -133, -58 });
			add(new int[] { -61, -58 });
			add(new int[] { -49, -58 });
			add(new int[] { -217, -58 });
			add(new int[] { -229, -58 });
			add(new int[] { -205, -58 });
			add(new int[] { -193, -58 });
			add(new int[] { -145, 0 });
			add(new int[] { -157, 0 });
			add(new int[] { -277, 0 });
			add(new int[] { -265, 0 });
			add(new int[] { -169, 0 });
			add(new int[] { -181, 0 });
			add(new int[] { -253, 0 });
			add(new int[] { -241, 0 });
			add(new int[] { -97, 0 });
			add(new int[] { -109, 0 });
			add(new int[] { -301, 0 });
			add(new int[] { -289, 0 });
			add(new int[] { -73, 0 });
			add(new int[] { -85, 0 });
			add(new int[] { -37, 0 });
			add(new int[] { -25, 0 });
			add(new int[] { -1, 0 });
			add(new int[] { -13, 0 });
			add(new int[] { -133, 0 });
			add(new int[] { -121, 0 });
			add(new int[] { -49, 0 });
			add(new int[] { -61, 0 });
			add(new int[] { -229, 0 });
			add(new int[] { -217, 0 });
			add(new int[] { -193, 0 });
			add(new int[] { -205, 0 });
		}
	};
	private static final int LENGTH = OFFSET_LIST.size();
	
	private JSONObject register ;
	private JSONObject gettype ;
	private JSONObject get ;
	public GeeTest(String host,String referer){
		header.put("Referer", referer);
		header.put("Host", host);
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");

	}
	public GeeTest(String host,String referer,String url){
		header.put("Referer", referer);
		header.put("Host", host);
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");
		
	}
	
	/**
	 * 合成指定的多张图片到一张图片
	 *
	 * @param imgSrcList
	 *            图片的地址列表
	 * @param topLeftPointList
	 *            每张小图片的偏移量
	 * @param countOfLine
	 *            每行的小图片个数
	 * @param cutWidth
	 *            每张小图片截取的宽度（像素）
	 * @param cutHeight
	 *            每张小图片截取的高度（像素）
	 * @param savePath
	 *            合并后图片的保存路径
	 * @param subfix
	 *            合并后图片的后缀
	 * @return 是否合并成功
	 */
	private  BufferedImage run(List<String> imgSrcList, int countOfLine, int cutWidth, int cutHeight) {
		if (imgSrcList == null)
			return null;
		BufferedImage lastImage = new BufferedImage(cutWidth * countOfLine,
				cutHeight * ((int) (Math.floor(imgSrcList.size() / countOfLine))), BufferedImage.TYPE_INT_RGB);
		String prevSrc = "";
		BufferedImage prevImage = null;
		try {
			for (int i = 0; i < imgSrcList.size(); i++) {
				String src = imgSrcList.get(i);
				BufferedImage image;
				if (src.equals(prevSrc))
					image = prevImage;
				else {
					if (src.trim().toLowerCase().startsWith("http"))
						image = ImageIO.read(new URL(src));
					else
						image = ImageIO.read(new File(src));
					prevSrc = src;
					prevImage = image;

				}
				if (image == null)
					continue;
				int[] topLeftPoint = OFFSET_LIST.get(i);
				int[] pixArray = image.getRGB(0 - topLeftPoint[0], 0 - topLeftPoint[1], cutWidth, cutHeight, null, 0,
						cutWidth);
				int startX = ((i) % countOfLine) * cutWidth;
				int startY = ((i) / countOfLine) * cutHeight;

				lastImage.setRGB(startX, startY, cutWidth, cutHeight, pixArray, 0, cutWidth);
			}
			return lastImage;
		} catch (Exception ex) {
			ex.printStackTrace();
			// return false;
		}
		return null;
	}

	private List<BufferedImage> alignImage(String bg, String fullbg) {
		List<BufferedImage> imgList = new ArrayList<BufferedImage>();
		List<String> fullbgList = new ArrayList<String>();
		List<String> bgList = new ArrayList<String>();
		for (int i = 0; i < LENGTH; i++) {
			fullbgList.add(fullbg);
			bgList.add(bg);
		}
		try {

			// 合成完整图片
			BufferedImage fullbgImg = run(fullbgList, 26, 10, 58);
			imgList.add(fullbgImg);
			ImageIO.write(fullbgImg, "PNG", new File(fullbg.substring(39).replaceAll("/", "-")));
			BufferedImage bgImg = run(bgList, 26, 10, 58);
			imgList.add(bgImg);
			ImageIO.write(bgImg, "PNG", new File(bg.substring(39).replaceAll("/", "-")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgList;

	}
	public int calcDistance(String bg, String fullbg,int y) {
        try {
        	List<BufferedImage> imageList = alignImage(bg,fullbg);
            BufferedImage image1 = imageList.get(0);
            BufferedImage image2 = imageList.get(1);
            int width1 = image1.getWidth();
            int height1 = image1.getHeight();
            int width2 = image2.getWidth();
            int height2 = image2.getHeight();

            if (width1 != width2) return -1;
            if (height1 != height2) return -1;

            int left = 0;
            /**
             * 从左至右扫描
             */
            boolean flag = false;
            for (int i = 0; i < width1; i++) {
                for (int j = y; j < height1; j++)
                    if (isPixelNotEqual(image1, image2, i, j)) {
                        left = i;
                        flag = true;
                        break;
                    }
                if (flag) break;
            }
            int distance = left-pianyiNum;
            LOG.debug("ypos:{},left:{},distance :{}",y,left,distance);
			return distance;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
	public  JSONObject validate(String url){
		try {
			 new GetRequest().request(httpClient, "http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml", null, null);
		
			CloseableHttpResponse response = new GetRequest().request(httpClient, url, null, null);
			String body = 	EntityUtils.toString(response.getEntity());
			LOG.debug("register: "+body);
			register = JSON.parseObject(body);
			gettype();
			get();
			JSONObject ajax = ajax();
			return ajax;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public  JSONObject gettype(){
		String url="http://api.geetest.com/gettype.php?gt=" + register.getString(GT) + "&callback=geetest_" 
			    + random();

		
		try {
			CloseableHttpResponse response = new GetRequest().request(httpClientGee, url, null, COOKIE);
			
			
			String body = 	EntityUtils.toString(response.getEntity());
			LOG.debug("gettype: "+body);
			return register;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public  JSONObject get(){
		JSONObject  config =register;
		String url="http://api.geetest.com/get.php?gt=" + config.getString(GT) + 
				"&challenge="+config.getString(CHALLENGE) +"&product=popup&offline=true&protocol=&path=/static/js/geetest.5.10.0.js&type=slide&callback=geetest_" 
				+ random();
		
//		System.out.println(url);
		try {
			while(true){
			CloseableHttpResponse response = new GetRequest().request(httpClientGee, url, null, COOKIE);
			
			
			String body = 	EntityUtils.toString(response.getEntity());
			LOG.debug("get: "+body);
			if(body.contains("DOCTYPE"))
				continue;
			get=JSON.parseObject(body.substring(body.indexOf("{"), body.length()-1));
			return get;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	JSONObject refresh(){
		String challenge = get.getString(CHALLENGE);
		String gt = get.getString(GT);
		String url = String.format("http://api.geetest.com/refresh.php?challenge=%s&gt=%s&callback=geetest_%s",
				challenge,gt,random());
		
		try {
			while(true){
			CloseableHttpResponse response = new GetRequest().request(httpClientGee, url, null, null);
			
			
			String body = 	EntityUtils.toString(response.getEntity());
			LOG.debug("refresh: "+body);
			if(body.contains("DOCTYPE"))
				continue;
			return JSON.parseObject(body.substring(body.indexOf("{"), body.length()-1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	JSONObject  ajax(){
		
    	String staticservers="http://static.geetest.com/";
    	String bg=staticservers+get.getString("bg");
    	String fullbg=staticservers+get.getString("fullbg");
    	int ypos=get.getIntValue(TrajectoryHelper.Y_POS);
		LOG.debug("bg:{},fullbg:{}",bg,fullbg);
		int xpos=calcDistance(bg, fullbg,ypos);
		
		String challenge = get.getString(CHALLENGE);
		String gt = get.getString(GT);
		
		ArrayList<JSONObject> actions = trajectoryHelper.getActions(xpos, challenge);
		int retry=0;
		again:while(true)
		for (int i=0;i< actions.size();i++) {
//			for (JSONObject action : actions) {
			JSONObject action=actions.get(i);
			String response = action.getString(TrajectoryHelper.RESPONSE);// getResponseString(xpos,
			xpos=		action.getIntValue(TrajectoryHelper.POS);												// challenge);
			int passTime = action.getIntValue(TrajectoryHelper.PASSTIME);
			String actString = action.getString(TrajectoryHelper.ACTION);// //action.getString("action");
			int imgLoadTime = TrajectoryHelper.next(80, 200);
			try {
				int sleep = passTime - imgLoadTime;
				sleep=sleep < 10 ? TrajectoryHelper.next(500, 590) : sleep;
				Thread.sleep(Math.abs(sleep));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		String ajaxUrl = String.format(
				"%sajax.php?gt=%s&challenge=%s&imgload=%s&passtime=%s&userresponse=%s&a=%s&callback=geetest_%s",
				get.getString("apiserver"), gt, challenge, imgLoadTime, passTime, response, actString,
				random());

		LOG.debug("ajaxUrl:{}",ajaxUrl);
		try {
			CloseableHttpResponse response2 = new GetRequest().request(StepsGX.httpClientGee, ajaxUrl, null,
					COOKIE);
			String body = EntityUtils.toString(response2.getEntity());
			System.out.println(body);
			if (body.contains("DOCTYPE"))
				continue;
			JSONObject result = JSON.parseObject(body.substring(body.indexOf("{"), body.length() - 1));
			System.out.println("message:"+result.getString("message"));
			if (result.getIntValue("success") == 1) {
				LOG.debug("success xpos:{} ,real xpos:{}" , xpos,action.getIntValue(TrajectoryHelper.TMP));
				result.put("challenge", challenge);
				// success
				return result;
			} else if (result.getString("message").contentEquals("abuse")) {
				// return abuse ,refresh config and try again
				LOG.debug("abuse xpos:{},realXpos:{}",  xpos,action.getIntValue(TrajectoryHelper.TMP));
				if(retry > 4)break again;
				retry++;
			actions= again();
			LOG.debug("again " + xpos);
			continue again;
			
			}else if (result.getString("message").contentEquals("fail")) {
				// return abuse ,refresh config and try again
				LOG.debug("fail xpos:{},realXpos:{}",  xpos,action.getIntValue(TrajectoryHelper.TMP));
				continue;
			} else {
				LOG.debug("forbidden xpos:{},realXpos:{}",  xpos,action.getIntValue(TrajectoryHelper.TMP));
				return result;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return null;
	}
	
	ArrayList<JSONObject> again(){
		JSONObject refresh = refresh();
		String staticservers="http://static.geetest.com/";
    	String bg=staticservers+refresh.getString("bg");
    	String fullbg=staticservers+refresh.getString("fullbg");
    	int ypos=get.getIntValue(TrajectoryHelper.Y_POS);
		LOG.debug("bg:{},fullbg:{}",bg,fullbg);
		int xpos=calcDistance(bg, fullbg,ypos);
		
		String challenge = get.getString(CHALLENGE);
		ArrayList<JSONObject> actions = trajectoryHelper.getActions(xpos, challenge);
		return actions;
	}
	
    private  boolean isPixelNotEqual(BufferedImage image1, BufferedImage image2, int i, int j) {
        int pixel1 = image1.getRGB(i, j);
        int pixel2 = image2.getRGB(i, j);

        int[] rgb1 = new int[3];
        rgb1[0] = (pixel1 & 0xff0000) >> 16;
        rgb1[1] = (pixel1 & 0xff00) >> 8;
        rgb1[2] = (pixel1 & 0xff);

        int[] rgb2 = new int[3];
        rgb2[0] = (pixel2 & 0xff0000) >> 16;
        rgb2[1] = (pixel2 & 0xff00) >> 8;
        rgb2[2] = (pixel2 & 0xff);

        for (int k = 0; k < 3; k++)
            if (Math.abs(rgb1[k] - rgb2[k]) > 50)
                return true;

        return false;
    }
	public static void main(String[] args) {
		String ref= "http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml";
	
		String host="gx.gsxt.gov.cn";
		String url= "http://gx.gsxt.gov.cn/pc-geetest/register?t="+new Date().getTime();
	
		GeeTest geet= new GeeTest(host,ref);
	

	}
     String random()
    {
    	
        String random = String.valueOf(new Date().getTime()-Double.valueOf(10000*Math.random()).intValue());
//        String random = String.valueOf(Double.valueOf(10000*Math.random()).intValue()+ new Date().getTime());
		return random;
    }
}
