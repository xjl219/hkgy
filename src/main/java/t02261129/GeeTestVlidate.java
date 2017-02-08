package t02261129;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xujl.http.method.GetRequest;
import com.xul.validate.StepsGX;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

@SuppressWarnings("restriction")
public class GeeTestVlidate {
	static Random rnd = new Random();
	static ScriptEngineManager manager = new ScriptEngineManager();
	static ScriptEngine engine = manager.getEngineByMimeType("application/javascript");
	@SuppressWarnings("restriction")
	static ScriptObjectMirror points;
	static ScriptObjectMirror na;
	static ScriptObjectMirror trailArray;
	static Invocable inv;
	static {
		try {
			engine.eval(new FileReader("D:/workspace-sts-3.8.3.RELEASE/geet/src/main/resources/path.js"));
			inv = (Invocable) engine;
			points = (ScriptObjectMirror) engine.get("point");
			na = (ScriptObjectMirror) engine.get("na");
			trailArray = (ScriptObjectMirror) engine.get("trailArray");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<int[]> getPoints(String length) {
		Object tmp = points.getMember(length);
	
		int parseInt = Integer.parseInt(length);
		for (int i = 1; i < 6&&tmp.toString()=="undefined"; i++) {
			tmp =  points.getMember((parseInt+i)+"");
			
		}
		for (int i = 1; i < 4&&tmp.toString()=="undefined"; i++) {
			tmp =  points.getMember((parseInt-i)+"");
			
		}
		ScriptObjectMirror member = (ScriptObjectMirror)tmp;
		int[][] integers = member.to((new int[0][0]).getClass());
		List<int[]> asList = Arrays.asList(integers);
		return asList;

	}
	public static List<int[]> getPoints2(String length) {
		Object tmp = trailArray.getMember(length);

		ScriptObjectMirror member = (ScriptObjectMirror)tmp;
		int[][] integers = member.to((new int[0][0]).getClass());
		List<int[]> asList = Arrays.asList(integers);
		return asList;
		
	}

	public static void main(String[] args) {
		System.out.println((getPoints("61")));
	}

	public static JSONObject GetValidate(int xpos, JSONObject config, Map<String,String> Cookie) {

		config.put("xpos", xpos);// ["xpos"] = xpos.ToString();
		ArrayList<JSONObject> actions = GetActions(xpos);

		for (JSONObject action : actions) {
			String challenge = config.getString("challenge");
			String gt = config.getString("gt");
			String response =getResponseStringByJs(xpos, challenge);// getResponseString(xpos, challenge);
			int passTime = action.getIntValue("passtime");
			String actString = action.getString("action");//"9/--.---,,,(!!Ewsstysstttytyyt(t((((y(yys(tsystsystsystvsystts)ts)t)tssss~sstssssss(!!($3Y11111111111111111111111111911111111111111191919M1191111111bUU$-Q1b$.Hb$*/$5Y";//action.getString("action");
			int imgLoadTime = next(50, 200)  ;
			try {
				Thread.sleep(passTime - imgLoadTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String ajaxUrl = String.format(
					"%sajax.php?gt=%s&challenge=%s&imgload=%s&passtime=%s&userresponse=%s&a=%s&callback=geetest_%s",
					config.getString("apiserver"), gt, challenge, imgLoadTime, passTime, response, actString,
					new Date().getTime());

			System.out.println(ajaxUrl);
			try {
				CloseableHttpResponse response2 = new GetRequest().request(StepsGX.httpClient, ajaxUrl, null, Cookie);
				String body = EntityUtils.toString(response2.getEntity());
				System.out.println(body);
				if (body.contains("DOCTYPE"))
					continue;
				JSONObject result = JSON.parseObject(body.substring(body.indexOf("{"), body.length() - 1));
				if (result.getIntValue("success") == 1) {
					System.out.println("success"+ xpos);
					result.put("challenge", challenge);
					// success
					return result;
				} else if (result.getString("message").contentEquals("abuse")) {
					// return abuse ,refresh config and try again
					System.out.println("abuse"+xpos);
					return result;
				} else {
					System.out.println("forbidden"+xpos);
					return result;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// return JSON.parseObject(body.substring(body.indexOf("{"),
			// body.length()-1));
		}
		/*
		 * var uri = new Uri(ajaxUrl); var hreq =
		 * (HttpWebRequest)HttpWebRequest.Create(uri); hreq.Timeout = 30000;
		 * hreq.Referer = referer; hreq.UserAgent = userAgent;
		 * hreq.CookieContainer = new CookieContainer();
		 * 
		 * hreq.CookieContainer.SetCookies(uri, cookies); var hres =
		 * (HttpWebResponse)hreq.GetResponse(); var js = ""; using (StreamReader
		 * sr = new StreamReader(hres.GetResponseStream())) js = sr.ReadToEnd();
		 * hres.Close(); var json = js.Substring(3, js.Length - 4);
		 * //Console.WriteLine(json); var result = JsonObject.Parse(json);
		 * result["challenge"] = challenge; if (result.Get<int>("success") == 1)
		 * { // success return result; } else if (result.Get("message") ==
		 * "abuse") { // return abuse ,refresh config and try again return
		 * result; } else if (result.Get("message") == "forbidden") { // try
		 * next action } }
		 */
		// failed
		return null;
	}
	static String getResponseStringByJs(int posx, String challenge) {
		try {
			Object userStr = inv.invokeMethod(na, "ra", posx,challenge);
		 return userStr.toString();
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
	static String getResponseString(int posx, String challenge) {
		String ct = challenge.substring(32);
		if (ct.length() < 2)
			return "";
		int[] d = new int[ct.length()];
		for (int e = 0; e < ct.length(); e++) {
			char f = ct.charAt(e);
			if (f > 57)
				d[e] = f - 87;
			else
				d[e] = f - 48;
		}
		int c = 36 * d[0] + d[1];
		int g = posx + c;
		ct = challenge.substring(0, 32);
		ArrayList<ArrayList<Character>> i = new ArrayList<ArrayList<Character>>(5);
		for (int ii = 0; ii < 5; ii++) {
			i.add(new ArrayList<Character>());
		}
		Map<Character, Integer> j = new HashMap<Character, Integer>();
		int k = 0;

		for (char h : ct.toCharArray()) {
			if (!j.containsKey(h) || j.get(h) != 1) {
				j.put(h, 1);
				i.get(k).add(h);
				k++;
				k %= 5;
			}

		}
		int n = g, o = 4;
		String p = "";
		List<Integer> q = new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(5);
				add(10);
				add(50);
			}
		};// Arrays.asList( 1, 2, 5, 10, 50) ;//new int[] { }.ToList(); ;
		while (n > 0) {
			if (n - q.get(o) >= 0) {
				int m = next(0, i.get(o).size());
				p += i.get(o).get(m);
				n -= q.get(o);
			} else {
				i.remove(o);
				q.remove(o);
				o--;
			}
		}
		return p;
	}

	public static ArrayList<JSONObject> GetActionsOld(Integer xpos) {
		ArrayList acts = new ArrayList<JSONObject>();

		for (int i = 0; i < 4; i++) {
			JSONObject act = new JSONObject();
			act.put("pos", xpos.toString());
			ArrayList<int[]> action = generate2(xpos);
			act.put("action", encrypt(action));
			int pt = 0;
			for (int[] a : action) {
				pt += a[2];
			}
			act.put("passtime", pt);
			acts.add(act);
		}
		return acts;

	}

	public static ArrayList<JSONObject> GetActions(Integer xpos) {
		ArrayList acts = new ArrayList<JSONObject>();

		// for (int i = 0; i < 4; i++) {
		JSONObject act = new JSONObject();
		act.put("pos", xpos.toString());
//		Test.randomPoints(xpos);//
		List<int[]> action =getPoints(xpos.toString());// 
		
		try {
			Object userStr = inv.invokeMethod(na, "qa", action);
			act.put("action", userStr.toString());
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}
		
//		act.put("action", encrypt(action));
		
		int pt = 0;
			pt = action.get(action.size()-1)[2] ;
		act.put("passtime", pt);
		acts.add(act);
		// }
		return acts;

	}

	public static String encrypt(List<int[]> action) {
		List<int[]> d = action;// diff(action);
		String dx = "", dy = "", dt = "";
		for (int j = 0; j < d.size(); j++) {

			Character b = replace(d.get(j));
			if (b != 0) {
				dy += b.toString();
			} else {
				dx += (encode(d.get(j)[0]));
				dy += (encode(d.get(j)[1]));
			}
			dt += (encode(d.get(j)[2]));
		}
		return dx + "!!" + dy + "!!" + dt;
	}

	static String encode(int n) {
		String b = "()*,-./0123456789:?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqr";
		int c = b.length();
		char d = (char) 0;
		int e = Math.abs(n);
		int f = e / c;
		if (f >= c)
			f = c - 1;
		if (f != 0) {
			d = b.charAt(f);
			e %= c;
		}
		String g = "";
		if (n < 0)
			g += "!";
		if (d != 0)
			g += "$";
		return g + (d == 0 ? "" : String.valueOf(d)) + b.charAt(e);
	}

	static Character replace(int[] a2) {
		int[][] b = new int[][] { new int[] { 1, 0 }, new int[] { 2, 0 }, new int[] { 1, -1 }, new int[] { 1, 1 },
				new int[] { 0, 1 }, new int[] { 0, -1 }, new int[] { 3, 0 }, new int[] { 2, -1 }, new int[] { 2, 1 } };
		String c = "stuvwxyz~";
		for (int d = 0; d < b.length; d++)
			if (a2[0] == b[d][0] && a2[1] == b[d][1])
				return c.charAt(d);
		return '\0';
	}

	public static int next(int min, int max) {

		return rnd.nextInt(max) % (max - min + 1) + min;
	}
	public static ArrayList<int[]> genPoints(int xpos){
		ArrayList<int[]> arr = new ArrayList<int[]>();
		int sx = next(15, 30);
		int sy = next(15, 30);
		int dt = 0;
		arr.add(new int[] { sx, sy, dt });
		
		
		return arr;
	}
	public static ArrayList<int[]> generate(int xpos)

	{
		int sx = next(15, 30);
		int sy = next(15, 30);
		ArrayList<int[]> arr = new ArrayList<int[]>();
		arr.add(new int[] { sx, sy, 0 });
		int maxCount = 100; // max len 100
		double x = 0;
		double lx = xpos - x;
		while (Math.abs(lx) > 0.8 && maxCount-- > 0) {
			double rn = rnd.nextDouble();

			double dx = rn * lx * 0.6;
			if (Math.abs(dx) < 0.5)
				continue;
			double dt = rnd.nextDouble() * (rn * 80 + 50) + 10;

			rn = rnd.nextDouble();
			double dy = 0;
			if (rn < 0.2 && dx > 10) //
			{
				dy = rn * 20.0;
				if (rn < 0.05)
					dy = -rn * 80;
			}

			x += dx;
			arr.add(new int[] { (int) (dx + 0.5), (int) (dy + 0.5), (int) (dt + 0.5) });
			lx = xpos - x;
		}
		double dtlast = 500.0 * rnd.nextDouble() + 100.0;
		arr.add(new int[] { 0, 0, (int) (dtlast) });
		return arr;
	}

	private static ArrayList<int[]> generate2(int xpos) {
		int sx = next(15, 30);
		int sy = next(15, 30);
		ArrayList<int[]> arr = new ArrayList<int[]>();
		arr.add(new int[] { sx, sy, 0 });

		int maxCount = 100; // max len 100
		double mds = 0.25;
		double speed = rnd.nextDouble() * 0.3 + 0.05;
		double ds = rnd.nextDouble() * 0.5 * mds;
		double dsign = 1;
		double x = 0;
		double lx = xpos - x;
		while (Math.abs(lx) > 1.0 && maxCount-- > 0) {
			double rn = rnd.nextDouble();
			double dt = rn * 100 + 10;
			if (rn < 0.2) {
				dt += rn * 200;
			}

			speed += ds * dsign;
			if (speed > 0.25)
				speed = 0.25;
			rn = rnd.nextDouble();

			if (rn < (speed / 0.25))
				dsign = -dsign;
			ds = rnd.nextDouble() * mds * 0.5;
			if (Math.abs(lx) < 10) {
				speed *= lx / 20;
			} else if (x < xpos / 3) {
				speed *= (x / xpos + 1.0);
			}

			if (speed < 0)
				speed = -speed;
			double dx = speed * dt;
			if (Math.abs(dx) < 0.6)
				continue;

			x += dx;
			if (x - xpos > 0 && dx > 0) {
				speed = -speed;
				x -= 2 * dx;
			}

			rn = rnd.nextDouble();
			double dy = 0;
			if (rn < 0.1 && dt > 70) {
				dy = rn * 30;
				if (rn < 0.05)
					dy = -rn * 60;
			}
			arr.add(new int[] { (int) (dx + 0.5), (int) (dy + 0.5), (int) (dt + 0.5) });
			lx = xpos - x;
		}
		double dtlast = 500.0 * rnd.nextDouble() + 100.0;
		arr.add(new int[] { 0, 0, (int) (dtlast) });
		return arr;
	}

	/*
	 * public JSONObject RefreshConfig(String gt, String challenge) {
	 * 
	 * String refreshUrl = String.format(
	 * "http://api.geetest.com/refresh.php?gt=%s&challenge=%s&callback=cb", gt,
	 * challenge); URI uri = new URI(refreshUrl); String js = ""; try {
	 * HttpWebRequest hreq = (HttpWebRequest)HttpWebRequest.Create(uri);
	 * hreq.Timeout = 10000; hreq.Referer = this.referer; hreq.CookieContainer =
	 * new CookieContainer(); var cookies = config.Get("cookie");
	 * hreq.CookieContainer.SetCookies(uri, cookies); HttpWebResponse hres =
	 * (HttpWebResponse)hreq.GetResponse(); using (StreamReader sr = new
	 * StreamReader(hres.GetResponseStream())) js = sr.ReadToEnd();
	 * hres.Close(); } catch(Exception ex) {
	 * //Console.WriteLine("refresh error: " + ex.Message); challenge =
	 * config["initchallenge"]; }
	 * 
	 * if (string.IsNullOrEmpty(js)) return false; var json = js.Substring(3,
	 * js.Length - 4);
	 * 
	 * var newParams = JsonObject.Parse(json); config["ypos"] =
	 * newParams["ypos"]; config["challenge"] = newParams["challenge"];
	 * config["bg"] = newParams["bg"]; config["fullbg"] = newParams["fullbg"];
	 * config["slice"] = newParams["slice"];
	 * //Console.WriteLine("config refreshed: " + config.Dump()); return true; }
	 */
	// int ypos = 0, int height = 52
	/*
	 * static Image AlignImage(Image img, int ypos, int height) { int width =
	 * 260; BufferedImage lastImage = new BufferedImage(width, height,
	 * BufferedImage.TYPE_INT_RGB); lastImage. Bitmap bmp = new Bitmap(width,
	 * height); int[] pos = new int[] {157, 145, 265, 277,181, 169, 241, 253,
	 * 109, 97, 289, 301, 85, 73, 25, 37, 13, 1, 121, 133, 61, 49, 217, 229,
	 * 205, 193, 145, 157, 277, 265, 169, 181, 253, 241, 97, 109, 301, 289, 73,
	 * 85, 37, 25, 1, 13, 133, 121, 49, 61, 229, 217, 193, 205}; int dx = 0, sy
	 * = 58, dy = 0; var g = Graphics.FromImage(bmp); for (int i = 0; i <
	 * pos.Length; i++) { g.DrawImage(img, new Rectangle(dx, dy - ypos, 10, 58),
	 * new Rectangle(pos[i], sy, 10, 58), GraphicsUnit.Pixel); dx += 10; if (dx
	 * == width) { dx = 0; dy = 58; sy = 0; } } g.Dispose(); return bmp; }
	 */
	public static int getLength(String g, String f) {
		String h = "";
		for (int i = 0; i < 9; i++)
			h += i % 2 == 0 ? f.charAt(i) : g.charAt(i);

		String a = h.substring(4);
		if (5 == a.length()) {
			int b = 200, c = Integer.parseInt(a, 16), d = c % b;
			if (d < 40)
				d = 40;
			return d;
		}
		return 0;
	}

}
