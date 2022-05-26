
/*
 * Driver program for ExpTree (binary expression tree)
 * @author Peter DiPalma
 * @version 1.1
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Driver {
	/*
	 * main method uses a buffered reader to read testcases.txt line by line, then
	 * uses the line to construct an exptree and run tests
	 */
	public static void main(String[] args) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("testcases.txt"));
			String line = reader.readLine();
			while (line != null) {
				testTree(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExpTree test = new ExpTree("1 + 2 * 3 - 4 / 5 + 6 * 7 - 8 / 9 + 10 * 11");
		ExpTree testt = new ExpTree("1 + 2 * 3 - 4 / 5 + 6 * 7 - 8 / 9 + 10 * 111");
		ExpTree testtt = new ExpTree("(1) + (2) * 3 - 4 / 5 + 6 * 7 - 8 / 9 + (10 * 11)");

		// quick test for equivalence
		System.out.println(
				test.getInfixString() + " equals " + testt.getInfixString() + "?\n" + test.equals(testt) + "\n\n");
		System.out.println(test.getInfixString() + " equals " + testtt.getInfixString() + "?\n" + test.equals(testtt));
	}

	/*
	 * method that does tree testing, called from inside for loop in main
	 */
	public static void testTree(String line) {
		ExpTree test = new ExpTree(line);
		System.out.println("Testing: " + line);
		System.out.println("Preorder:" + test.getPrefixString());
		System.out.println("Inorder:" + test.getInorderString());
		System.out.println("Postorder:" + test.getPostfixString());
		System.out.println("\ntoString method:");
		System.out.println(test.toString());
		System.out.println("done...");
		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");

	}

}
