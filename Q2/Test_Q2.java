import java.util.Scanner;

public class Test_Q2 {

	    public static String compareVersions(String version1, String version2) {
	        String[] v1 = version1.split("\\.");
	        String[] v2 = version2.split("\\.");

	        for (int i = 0; i < Math.max(v1.length, v2.length); i++) {
	            int num1 = 0;
	            int num2 = 0;

	            if (i < v1.length) {
	                num1 = parseComponent(v1[i]);
	            }
	            if (i < v2.length) {
	                num2 = parseComponent(v2[i]);
	            }

	            if (num1 > num2) {
	                return "Greater";
	            } else if (num1 < num2) {
	                return "Less";
	            }
	        }

	        return "Equal";
	    }

	    //Parsing components of versions
	    private static int parseComponent(String component) {
	        try {
	            return Integer.parseInt(component.replaceAll("[^0-9]", ""));
	        } catch (NumberFormatException e) {
	            throw new NumberFormatException("Invalid input.. It contains alphabet");
	        }
	    }


	public static void main(String args[]) {
		System.out.println("1.2 is "+ compareVersions("1.2", "1.1")+ " 1.1"); // Basic Case
        System.out.println("2.0 is "+ compareVersions("2.0", "2.0")+ " 2.0"); // Basic Case
        System.out.println("1.1 is "+ compareVersions("1.1", "1.2")+ " 1.2"); // Basic Case
        System.out.println("1.2.1 is "+ compareVersions("1.2.1", "1.2")+ " 1.2"); // Case with Multiple Digits
        System.out.println("1.2 is "+ compareVersions("1.2", "1.2.2")+ " 1.2.2"); // Case with Multiple Digits
        System.out.println("1.10 is "+ compareVersions("1.10", "1.9")+ " 1.9"); // Case with Leading Zeros
        System.out.println("1.02 is "+compareVersions("1.02", "1.2")+" 1.2"); // Case with Leading Zeros
        System.out.println("1000.1000.1000 is "+compareVersions("1000.1000.1000", "999.999.999")+" 999.999.999"); 
        System.out.println("1.5 is "+compareVersions("1.5", "1.2")+" 1.2"); // Basic Case
        System.out.println("1.2.3.4.5.6.7.8.9.10 is "+compareVersions("1.2.3.4.5.6.7.8.9.10", "1.2")+" 1.2"); // Long Version Case
        System.out.println("999999999.999999999 is "+compareVersions("999999999.999999999", "1.2")+" 1.2");
	}
}
