package com.xul.validate;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.script.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xujl.http.method.PostRequest;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.JSObject; 
public class JavaInvJs {
	static  ScriptEngineManager manager = new ScriptEngineManager();    
	static ScriptEngine engine = manager.getEngineByMimeType("application/javascript");  
	static Random random= new Random();
	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		
		ra(0,"f393c4f4b7e2eef1ef35a741f3e91950cc");
	}
	private static void gx() {
		try {
			 JSONObject one = Steps. one();
			String id= one.getString(Steps.ID);
			
			String challenge = one.getString(StepsGX.CHALLENGE);
			 Invocable inv =	getInvocable();
			Object c = engine.get("c");
			ScriptObjectMirror Q = (ScriptObjectMirror)engine.get("Q");
			ScriptObjectMirror b=(ScriptObjectMirror)Q.get("b");
			ScriptObjectMirror invokeMethod2 = (ScriptObjectMirror)inv.invokeMethod(c, "Qz", id,"{id:"+id+"}");
			
			ScriptObjectMirror cc=(ScriptObjectMirror)inv.invokeMethod(c, "c", id);
			System.out.println(cc.getMember("x_pos"));
//			cc.forEach((k,v)->System.out.println(k));
			String ccjson = JSON.toJSONString(cc);
			System.out.println(ccjson);
			Double x_pos = (Double)cc.getMember("x_pos");
			int intx_pos = x_pos.intValue();
			int times = random.nextInt(2000);
			ScriptObjectMirror ajax=(ScriptObjectMirror)inv.invokeMethod(c, "ajax", intx_pos,times,challenge,id);
			String jsonString = JSON.toJSONString(ajax);
			String validate=ajax.getMember("validate").toString();
			String validate2 = StepsGX.validate(challenge, validate);
			System.out.println(validate2);
			String entlist = StepsGX.entlist(challenge,  "中国移动");
			System.out.println(entlist);
//			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void bj() {
		try {
			JSONObject one = Steps. one();
			String id= one.getString(Steps.ID);
			
			String challenge = one.getString(Steps.CHALLENGE);
			Invocable inv =	getInvocable();
			Object c = engine.get("c");
			ScriptObjectMirror Q = (ScriptObjectMirror)engine.get("Q");
			ScriptObjectMirror b=(ScriptObjectMirror)Q.get("b");
			ScriptObjectMirror invokeMethod2 = (ScriptObjectMirror)inv.invokeMethod(c, "Qz", id,"{id:"+id+"}");
			
			ScriptObjectMirror cc=(ScriptObjectMirror)inv.invokeMethod(c, "c", id);
			System.out.println(cc.getMember("x_pos"));
//			cc.forEach((k,v)->System.out.println(k));
			String ccjson = JSON.toJSONString(cc);
			System.out.println(ccjson);
			Double x_pos = (Double)cc.getMember("x_pos");
			int intx_pos = x_pos.intValue();
			int times = random.nextInt(2000);
			ScriptObjectMirror ajax=(ScriptObjectMirror)inv.invokeMethod(c, "ajax", intx_pos,times,challenge,id);
			String jsonString = JSON.toJSONString(ajax);
			String validate=ajax.getMember("validate").toString();
			String validate2 = Steps.validate(challenge, validate);
			System.out.println(validate2);
			String entlist = Steps.entlist(challenge,  "中国移动");
			System.out.println(entlist);
//			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String ra(int a,String b){
		try {
			
			Invocable inv =	getInvocable();
			Object c = engine.get("c");
			Object p = inv.invokeMethod(c, "ra", a,b);
			System.out.println(p.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Invocable getInvocable() throws ScriptException, FileNotFoundException {
		engine.eval(new FileReader("D:/workspace-sts-3.8.3.RELEASE/geet/src/main/resources/gee.js"));
		
		 Invocable inv = (Invocable) engine;
		 return inv;
	}

}
