package com.xul.validate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;



public class CombineImages {
	/**
	 * �ϳ�ָ���Ķ���ͼƬ��һ��ͼƬ
	 *
	 * @param imgSrcList       ͼƬ�ĵ�ַ�б�
	 * @param topLeftPointList ÿ��СͼƬ��ƫ����
	 * @param countOfLine ÿ�е�СͼƬ����
	 * @param cutWidth         ÿ��СͼƬ��ȡ�Ŀ�ȣ����أ�
	 * @param cutHeight        ÿ��СͼƬ��ȡ�ĸ߶ȣ����أ�
	 * @param savePath         �ϲ���ͼƬ�ı���·��
	 * @param subfix         �ϲ���ͼƬ�ĺ�׺
	 * @return �Ƿ�ϲ��ɹ�
	 */
	public  static BufferedImage run(List<String> imgSrcList, List<String[]> topLeftPointList, int countOfLine, int cutWidth, int cutHeight) {
		if (imgSrcList == null) return null;
		BufferedImage lastImage = new BufferedImage(cutWidth * countOfLine, cutHeight * ((int) (Math.floor(imgSrcList.size() / countOfLine))), BufferedImage.TYPE_INT_RGB);
		String prevSrc = "";
		BufferedImage prevImage = null;
		try {
			for (int i = 0; i < imgSrcList.size(); i++) {
				String src = imgSrcList.get(i);
				BufferedImage image;
				if (src.equals(prevSrc)) image = prevImage;
				else {
					if (src.trim().toLowerCase().startsWith("http"))
						image = ImageIO.read(new URL(src));
					else
						image = ImageIO.read(new File(src));
					prevSrc = src;
					prevImage = image;

				}
				if (image == null) continue;
				String[] topLeftPoint = topLeftPointList.get(i);
				int[] pixArray = image.getRGB(0 - Integer.parseInt(topLeftPoint[0].trim()), 0 - Integer.parseInt(topLeftPoint[1].trim()), cutWidth, cutHeight, null, 0, cutWidth);
				int startX = ((i) % countOfLine) * cutWidth;
				int startY = ((i) / countOfLine) * cutHeight;

				lastImage.setRGB(startX, startY, cutWidth, cutHeight, pixArray, 0, cutWidth);
			}
			return lastImage;
		} catch (Exception ex) {
			ex.printStackTrace();
		//	return false;
		}
		return null;
	}
	
	
	public static List<BufferedImage> alignImage(String bg,String fullbg) {
//		String bg="http://static.geetest.com/pictures/gt/"+f+"/bg/"+g+".jpg";
//		String fullbg="http://static.geetest.com/pictures/gt/"+f+"/"+f+".jpg";
		String fullbgImg_=bg.split("gt/")[1].replaceAll("/", "_");
		String bgImg_=fullbg.split("gt/")[1].replaceAll("/", "_");
		List<BufferedImage> imgList=new ArrayList<BufferedImage>();
		List<String[]> topLeftPointList=new ArrayList<String[]>();
		List<String> fullbgList=new ArrayList<String>();
		List<String> bgList=new ArrayList<String>();
		String[] s1 = po.split("\t");
		//System.out.println(s1.length);
		for(String s:s1){
			topLeftPointList.add(s.split(" "));
			fullbgList.add(fullbg);
			bgList.add(bg);
		}
		try {
			
		
		//�ϳ�����ͼƬ
		BufferedImage fullbgImg = CombineImages.run(fullbgList, topLeftPointList, 26, 10, 58);
		imgList.add(fullbgImg);
		ImageIO.write(fullbgImg, "PNG", new File(fullbgImg_ + "_"+new Date().getTime()+".jpg"));
		BufferedImage bgImg = CombineImages.run(bgList, topLeftPointList, 26, 10, 58);
		imgList.add(bgImg);
		ImageIO.write(bgImg, "PNG", new File(bgImg_+"_"+new Date().getTime()+".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgList;
	
	}
	
	 static String po="-157 -58\t"+
				"-145 -58\t"+
				"-265 -58\t"+
				"-277 -58\t"+
				"-181 -58\t"+
				"-169 -58\t"+
				"-241 -58\t"+
				"-253 -58\t"+
				"-109 -58\t"+
				"-97 -58\t"+
				"-289 -58\t"+
				"-301 -58\t"+
				"-85 -58\t"+
				"-73 -58\t"+
				"-25 -58\t"+
				"-37 -58\t"+
				"-13 -58\t"+
				"-1 -58\t"+
				"-121 -58\t"+
				"-133 -58\t"+
				"-61 -58\t"+
				"-49 -58\t"+
				"-217 -58\t"+
				"-229 -58\t"+
				"-205 -58\t"+
				"-193 -58\t"+
				"-145 0\t"+
				"-157 0\t"+
				"-277 0\t"+
				"-265 0\t"+
				"-169 0\t"+
				"-181 0\t"+
				"-253 0\t"+
				"-241 0\t"+
				"-97 0\t"+
				"-109 0\t"+
				"-301 0\t"+
				"-289 0\t"+
				"-73 0\t"+
				"-85 0\t"+
				"-37 0\t"+
				"-25 0\t"+
				"-1 0\t"+
				"-13 0\t"+
				"-133 0\t"+
				"-121 0\t"+
				"-49 0\t"+
				"-61 0\t"+
				"-229 0\t"+
				"-217 0\t"+
				"-193 0\t"+
				"-205 0";
}

