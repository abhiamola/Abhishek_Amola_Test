import java.util.Scanner;
public class Test_Q1 {
    
    public static boolean doLinesOverlap(int[] line1, int[] line2) {
        int x1 = line1[0];
        int x2 = line1[1];
        int x3 = line2[0];
        int x4 = line2[1];

        // Check if the lines overlap
        if (x1 <= x4 && x2 >= x3) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	int line1[] = new int[2];
    	int line2[] = new int[2];
		System.out.println("Enter coordinates of first line (Seperated by a space): ");
		line1[0] = sc.nextInt();
		line1[1] = sc.nextInt();
		System.out.println("Enter coordinates of second line: ");
		line2[0] = sc.nextInt();
		line2[1] = sc.nextInt();
        
        boolean overlap1 = doLinesOverlap(line1, line2);
        System.out.println(overlap1);
    }
}

