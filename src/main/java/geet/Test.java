package geet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;

public class Test {
	static Random rnd = new Random();
	public static void main(String[] args) {
		calcLength();
//		System.out.println(JSON.toJSONString(randomPoints(144)));
	}
	
	public static ArrayList<int[]> randomPoints(int posx){
		int x=randomX(15,30),y=Y(15, 30),t=0,i=0;
		ArrayList<int[]> points= new ArrayList<int[]>();
		points.add(new int[]{ -x,-y,t});
		points.add(new int[]{ 0,0,0});
		y=Y(-1, 3);
		while (x<posx) {
//			System.out.println(String.format("%d-> x:%d,y:%d,t:%d",i, x,y,t));
			int tx=randomX(1,3);
			 x+=tx;
			// y=(Y(-1, 3)+y)/2;
			 t+=randomX(5,15*(tx+3));
			 points.add(new int[]{ x,y,t});
			 ++i;
		}
		 t+=randomX(900,1502);
		 points.add(new int[]{ x,y,t});
		return points;
	}
	public static int randomY(int min, int max) {
		int nextInt = rnd.nextInt(max);
		int random = nextInt % (max - min + 1) + min;
		return rnd.nextInt() % 2 ==0 ?random:Math.abs(random);
	}
	public static int Y(int min, int max) {
		long randomNum = System.nanoTime();//currentTimeMillis();  
		long randomNumber =  randomNum%(max-min)+min; 
		return (int)randomNumber;
	}
	public static int randomX(int min, int max) {
		int nextInt = rnd.nextInt(max);
		int random = nextInt % (max - min + 1) + min;
		return Math.abs(random);
	}
	
	private static void calcLength() {
		String fullbg="http://static.geetest.com/pictures/gt/895656306/895656306.jpg";
    	String bg="http://static.geetest.com/pictures/gt/895656306/bg/dae907dc5.jpg";
    	String[] split = bg.split("gt/")[1].split("/bg/");
		//"9a1fa270bcff706fa94f72cb92e8f7fc5e" 177
	
		System.err.println("GeeTestVlidate "+GeeTestVlidate.getLength(split[0],split[1]));
		System.err.println("GeeTestVlidate "+GeeTestVlidate.getLength(split[1],split[0]));
		List<BufferedImage> imageList = CombineImages.alignImage(bg, fullbg);
		int posx = FindXDiffRectangeOfTwoImage.findXDiffRectangeOfTwoImage(imageList, 0);
		System.out.println("posx:"+posx);
	}

}
