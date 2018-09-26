import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Character> charList = new ArrayList<Character>();
		int [][]A;
		int []b;
		int [][]c;
		int []Eqin;
		int [] MinMax;
		int numOfEqualOperator;
		int numOfX;
		boolean isRight;
		
		FileRW file = new FileRW("yourFile.txt",charList);
		LinearRead linear = new LinearRead(charList);
		A = linear.getA();
		b = linear.getB();
		c = linear.getC();
		Eqin = linear.getEqin();
		MinMax = linear.getMinMax();
		numOfEqualOperator = linear.getNumOfEqualOperator();
		numOfX = linear.getNumOfX();
		
		isRight=linear.isFlag();
		if(isRight)
			file.writeToFile("results.txt", A, b, c, Eqin,MinMax,numOfEqualOperator, numOfX);
		else
			System.out.println("Sorry the program can't convert this linear problem");
	}

}
