import java.util.Scanner;
public class Test_Q1 {
    
    public static boolean doLinesOverlap(int[] line1, int[] line2) {
        int x1 = line1[0];
        int x2 = line1[1];
        int x3 = line2[0];
        int x4 = line2[1];

        // Check if a line is a point
        if(x1==x2 || x3==x4)
        {
        	System.out.println("\nGiven Case is Invalid... One input is a point");
        	return false;
        }
        
        // Check if wrong coordinates are given
        if(x1>x2 || x3>x4)
        {
        	System.out.println("\nGiven Case is Invalid... Starting point is greater than ending point");
        	return false;
        }
        
        // Check if the lines overlap
        if (x1 < x4 && x2 > x3) {
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
        
        System.out.println("\nFollowing are some test cases\n");
        int[][] testCases = {
                {1, 5, 3, 7},      // Test Case 1: Overlapping lines
                {1, 3, 4, 6},      // Test Case 2: Non-overlapping lines
                {1, 5, 5, 7},      // Test Case 3: Meeting at endpoint
                {-3, 2, -1, 4},    // Test Case 4: Negative coordinates, overlap
                {1000000, 1000000000, 999999999, 1000000001}, // Test Case 5: Large coordinates, overlap
                {2, 2, 2, 2},      // Test Case 6: Same coordinates for both lines
                {1, 5, 5, 5},      // Test Case 7: Invalid input, same coordinate for different lines
              	{5, 1, 1, 5}       // Test Case 8: Invalid input, reversed coordinates for same line
            };

            for (int i = 0; i < testCases.length; i++) {
                int[] line11 = {testCases[i][0], testCases[i][1]};
                int[] line22 = {testCases[i][2], testCases[i][3]};
                boolean overlap = doLinesOverlap(line11, line22);
                System.out.println("Test Case " + (i + 1) + ": " + overlap);
            }
    }
}

