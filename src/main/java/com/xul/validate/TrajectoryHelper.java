package com.xul.validate;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.alibaba.fastjson.JSONObject;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class TrajectoryHelper {
	public static final String PASSTIME = "passtime";
	public static final  String RESPONSE = "response";
	public static final String ACTION = "action";
	public static final String POS = "pos";
	public static final String TMP = "tmp";
	public static final String Y_POS = "ypos";
	static ScriptEngineManager manager = new ScriptEngineManager();
	static ScriptEngine engine = manager.getEngineByMimeType("application/javascript");
	@SuppressWarnings("restriction")
	static ScriptObjectMirror points;
	static ScriptObjectMirror na;
	static ScriptObjectMirror trailArray;
	static Invocable inv;
	static ArrayList<Integer> lengths = new ArrayList<Integer>();
	static {
		try {
			InputStream resourceStream = TrajectoryHelper.class.getResourceAsStream("/path.js");
			engine.eval(new InputStreamReader(resourceStream));
			inv = (Invocable) engine;
			points = (ScriptObjectMirror) engine.get("point");
			na = (ScriptObjectMirror) engine.get("na");
			trailArray = (ScriptObjectMirror) engine.get("trailArray");

			points.keySet().forEach(k -> lengths.add(Integer.parseInt(k)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public ArrayList<JSONObject> getActions(Integer xpos,String challenge) {
		ArrayList<JSONObject> acts = new ArrayList<JSONObject>();

		List<int[][] > actions = getPoints(xpos);//
		actions.forEach(action -> {
			JSONObject act = new JSONObject();
			
			act.put(POS, xpos);
			act.put(TMP, action[action.length-1][0]);
			
//			act.put(RESPONSE, encodeResponse(xpos, challenge));
			act.put(RESPONSE, encodeResponse(action[action.length-1][0], challenge));

			try {
				Object userStr = inv.invokeMethod(na, "qa", action);
				
				act.put(ACTION, userStr.toString());
			} catch (NoSuchMethodException | ScriptException e) {
				e.printStackTrace();
			}

			int pt = 0;
			pt =  action[action.length - 1][2];
			
			act.put(PASSTIME, pt);
			acts.add(act);
		});
		return acts;

	}
	  List<int[][]> getPoints(Integer length) {
		ArrayList<int[][]> acts = new ArrayList<int[][]>();
		final int t =length;
		lengths.stream().sorted((x, y) -> Math.abs(x - t) - Math.abs(y - t)).limit(3).forEach(i -> {
			Object tmp = points.getMember(i.toString());
			ScriptObjectMirror member = (ScriptObjectMirror) tmp;
			int[][] integers = member.to((new int[0][0]).getClass());
			acts.add(integers);

		});

		return acts;

	}
	 String encodeResponse(int posx, String challenge) {
		try {
			Object userStr = inv.invokeMethod(na, "ra", posx, challenge);
			return userStr.toString();
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
	 static Random rnd = new Random();
		public static int next(int min, int max) {

			return rnd.nextInt(max) % (max - min + 1) + min;
		}
	public static void main(String[] args) {
		points.forEach((k,v)->{System.out.println(k+v);
			ScriptObjectMirror member = (ScriptObjectMirror)v;
			int[][] integers = member.to((new int[0][0]).getClass());
			final int j = integers[integers.length-1][0];
			if(!k.contentEquals(""+j) )
				System.err.println(k+":"+j);
			else
			System.out.println(k+":"+j);
		}  );
		/*for (Integer i : lengths) {
			System.err.println(i.toString());
			ScriptObjectMirror member = (ScriptObjectMirror) points.getMember(i.toString());;
			int[][] integers = member.to((new int[0][0]).getClass());
			
			final int j = integers[integers.length-1][0];
			if(i.intValue() != j)
				System.err.println(i+":"+j);
			else
			System.out.println(i+":"+j);
		}*/
	
			
	}
}
