package geet;

import java.util.Base64;

public class B64 {

	public static void main(String[] args) {

		byte[] decode = Base64.getDecoder().decode("43nAqvyfY1M3C2gp7Uu4Dqg5H-83Y6mp3fPk8sTwccSLq6Kb7EAYcYcvp2J96-jVD1Gz0Tn9rsggfVp-MZpi0waxggf45upgc03WBPX8BaUJNdhUpx3v65e3IbW46LF8dAp7ylZpLXvtUQQaN2fIYA");
System.out.println(new String(decode));
	}

}
