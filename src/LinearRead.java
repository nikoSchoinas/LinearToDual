import java.util.ArrayList;

public class LinearRead {
	
	private int[][] A;
	private int[] b;
	private int[][] c;
	private int[] Eqin;
	private int [] MinMax;
	private int numOfEqualOperator,numOfX;
	private boolean flag;
	

	public int[] getMinMax() {
			return MinMax;
		}
	public int getNumOfEqualOperator() {
		return numOfEqualOperator;
	}
	public int getNumOfX() {
		return numOfX;
	}
	public int[][] getA() {
		return A;
	}
	public int[] getB() {
		return b;
	}
	public int[][] getC() {
		return c;
	}
	public int[] getEqin() {
		return Eqin;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public LinearRead(ArrayList<Character> charList){
	/*numOfSlashes: counts the number of slashes that have been added at the end of a restriction*/
	/*numOfEqualOperator: counts the number of equals operator*/
	/*numOfX: counts the number of variables in function */
	/*numOfExpectedB: counts the number of expected b variables if an equal operator is met*/
	/*numOfRealB: counts the actual number of b variables*/
	/*numOfExpectedEquals: counts the number of expected equals operator if a b variable is met*/
	/*numOfRealEquals: counts the actual number of equals operator*/
	/*numberLength: counts the digits of one number  */
	/*charListElement is the selected element of ArrayList charList */
	/*notError is a boolean variable that will be false if something is wrong with the linear problem*/
	
	int numOfSlashes,numOfExpectedB,numOfRealB,numOfExpectedEquals,numOfRealEquals,numberLength;
	numOfSlashes = numOfEqualOperator = numOfX = numOfExpectedB = numOfRealB = numOfExpectedEquals = numOfRealEquals = 0;
	char charListElement;
	boolean notError = true;
	
	/*Check for min or max word existence in file (actually in ArrayList charList) and complete MinMax array*/
    MinMax = new int[1];
    boolean minOrMaxExistence=true;
    String minOrMax = "";
    int j=0;
    while(j<charList.size() && minOrMax.length()<3){
    	if(charList.get(j) == 'm' || charList.get(j) == 'i' || charList.get(j) == 'n' || charList.get(j) == 'a' || charList.get(j) == 'x')
    		minOrMax+=charList.get(j);
    	j++;
    }
    	
      if(minOrMax.equals("min")){
      	System.out.println("min word exists in file\n");
      	MinMax[0] = -1;
      }
      else if(minOrMax.equals("max")){
      	System.out.println("max word exists in file\n");
      	MinMax[0] = 1;
      }
      else{
      	System.out.println("min or max word does not exist in file\n");
      	minOrMaxExistence = false;
      }
      
      /*Check for s.t. word existence in file (actually in ArrayList charList)*/
      String st = "";
      boolean stExistence = true;
      j=0;
      while(j<charList.size() && st.length()<4){
    	if(charList.get(j) == 's' || charList.get(j) == 't' || charList.get(j) == '.')
    		st+=charList.get(j);
    	j++;
    }
	
      if(st.equals("s.t."))
      	System.out.println("s.t. word exists in file\n");
      else{
      	System.out.println("s.t. word does not exist in file\n ");
      	stExistence = false;
      }
	notError = minOrMaxExistence && stExistence;
	
	for(int i=0; i<charList.size() && notError; i++){
		charListElement = charList.get(i);
		/*remove spaces,line feeds and carriage returns*/
		if(charListElement==' ' || charListElement=='\n' || charListElement==13){ 
			charList.remove(i);
			i=i-1;
		}
		/*locates a b variable*/
		else if(charListElement>47 && charListElement<58 && charList.get(i-1)!='x'){
			numberLength = 0;
			/*counts digit of number (numberLength -1 is the actual digit number)*/
			while(charListElement > 47 && charListElement<58){
				charListElement = charList.get(i++);
				numberLength++;
			}
			/*this if-check ensures that the number is a b variable*/
			if(charList.get(i-1)!='x'){
				charList.add(i-1, '/');
				/*this if-check ensures that before the b variable there is an equal operator so the restriction has right form */
				if(charList.get(i-numberLength-1) == '=' || charList.get(i-numberLength -2) == '='){
					numOfRealB++;	
				}
				/*else before b variable there is not an equal operator*/
				else{
					numOfRealB++;
					numOfExpectedB++;
					numOfExpectedEquals++;
				}

				numOfSlashes++;
				
				/*this if-check ensures that restriction has right form*/
				if(numOfExpectedB != numOfRealB || numOfExpectedEquals != numOfRealEquals)
					notError = false;
			}
		}
		/* when an equal operator is met we need to increase value of some variable.   */
		else if(charListElement == '=' && charList.get(i-1)!='='){
			numOfEqualOperator++;
			numOfRealEquals++;
			numOfExpectedEquals++;
			numOfExpectedB++;
		}
		/*add a slash '/' before s. End of function */
		else if(charListElement == 's'){ 
			charList.add(i, '/');
			i=i+2;
			numOfSlashes++;
			
		}		 
	}
	      	
      int i;
      /*The above part of coding counts the number of 'x' variables (1,2,3 etc)*/
      if(notError){
      	i=3;
     	 int maxVariables = 0;
     	 while(charList.get(i)!='/'){
      		if(charList.get(i)=='x')
      			maxVariables = Character.getNumericValue(charList.get(i+1));
      			if(maxVariables>numOfX)
      				numOfX =maxVariables;
      		i++;	
      	}
     }

      /*check if everything is fine with the number of restrictions and equals operators*/
      boolean correctForm = true;
      if(numOfSlashes-1 != numOfEqualOperator){
      	correctForm = false;
      }
   
      
      /*if the above checks are fine then flag variable is true*/
      flag = correctForm && stExistence && minOrMaxExistence && notError;

      /*Complete c array */
      c = new int [1][numOfX];
      i=0;
      boolean minusExistence = false;
      while(charList.get(i)!='/' && flag){
      	if(charList.get(i)=='-'){
      		minusExistence = true;
      	}
      	if(charList.get(i)=='x' && (charList.get(i-1)>47 && charList.get(i-1)<58)){
      		int digitCounter=0;
      		i--;
      		while(charList.get(i)>47 && charList.get(i)<58){
      			digitCounter++;
      			i--;
      		}
      		if(charList.get(i)=='-')
      			minusExistence = true;
      		int cVariable=0;
      		cVariable = takeVariable(charList,digitCounter,i);
      		i=i+digitCounter+2;
      		
      		if(minusExistence){
      			c[0][Character.getNumericValue(charList.get(i))-1] = -cVariable;
      			minusExistence = false;
      		}
      			
      		else{
      			c[0][Character.getNumericValue(charList.get(i))-1] = cVariable;
      			minusExistence = false;
      		}
      			
      		
      	}
      	else if(charList.get(i)=='x' && charList.get(i-1)== '-'){
      		c[0][Character.getNumericValue(charList.get(i+1))-1] = -1;
      		minusExistence = false;
      	}
      	else if(charList.get(i)=='x' && (charList.get(i-1)== '+' || charList.get(i-1)!='a')){
      		if(minusExistence)
      			c[0][Character.getNumericValue(charList.get(i+1))-1] = -1;
      		else 
      			c[0][Character.getNumericValue(charList.get(i+1))-1] = 1;
      		minusExistence = false;
      	}
      	i++;
      }
      
	
	/*Add elements to A,b and Eqin array*/
	A = new int[numOfEqualOperator][numOfX];
	b = new int[numOfEqualOperator];
	Eqin = new int[numOfEqualOperator];
	int m=0;
	minusExistence = false;
	for(i=i+1;i<charList.size() && flag ;i++){
		if(charList.get(i)=='-'){
			minusExistence =true;
		}
		if(charList.get(i)=='x' && (charList.get(i-1)>47 && charList.get(i-1)<58)){
      		int digitCounter=0;
      		i--;
      		while(charList.get(i)>47 && charList.get(i)<58){
      			digitCounter++;
      			i--;
      		}
      		if(charList.get(i)=='-')
      			minusExistence = true;
      		int AVariable=0;
      		AVariable = takeVariable(charList,digitCounter,i);
      		i=i+digitCounter+2;
      		
      		if(minusExistence){
      			A[m][Character.getNumericValue(charList.get(i))-1] = -AVariable;
      			minusExistence = false;
      		}
			else{
				A[m][Character.getNumericValue(charList.get(i))-1] = AVariable;
				minusExistence = false;
			}
      			
      	}
      	else if(charList.get(i)=='x' && charList.get(i-1)== '-'){
      		A[m][Character.getNumericValue(charList.get(i+1))-1] = -1;
      		minusExistence = false;
      	}
      	else if(charList.get(i)=='x' && (charList.get(i-1)== '+' || charList.get(i-1)!='a')){
      		if(minusExistence)
      			A[m][Character.getNumericValue(charList.get(i+1))-1] = -1;
      		else
      			A[m][Character.getNumericValue(charList.get(i+1))-1] = 1;
      		minusExistence = false;
      	}
      	else if(charList.get(i)=='<' && charList.get(i+1)=='=' ){
      		Eqin[m] = -1;
      	}
      	else if(charList.get(i)=='>' && charList.get(i+1)=='='){
      		Eqin[m] = 1;
      	}
      	else if(charList.get(i)=='/'){
      		minusExistence = false;
      		int digitCounter=0;
      		i--;
      		while(charList.get(i)>47 && charList.get(i)<58){
      			digitCounter++;
      			i--;
      		}
      		if(charList.get(i)=='-')
      			minusExistence = true;
      		int bVariable=0;
      		bVariable = takeVariable(charList,digitCounter,i);
      		i=i+digitCounter+1;
      		
      		if(minusExistence)
      			b[m] = -bVariable;
      		else
      			b[m] = bVariable;
      			
      		m++;
      		
      	}
      	
			
	}
	if(!flag){
		System.out.println("Something is wrong with the linear problem...\nCheck if 'b' variable or equal operator is missing");
		System.out.println("Check if min(or max)and s.t. words are missing\n");
	} 
	else
		System.out.println("Everything seems to be fine\n");   
	
	}

	/*takeVariable function. Convert a number (digitNumber) of characters from ArrayList (list) that start from a 
	specific position (loopNumber) into an integer (integerNumber)
	and returns that integer. */
	public static int takeVariable(ArrayList<Character> list,int digitNumber,int loopNumber){
		int integerNumber=0;
		switch(digitNumber){
      		case 1: integerNumber = Character.getNumericValue(list.get(++loopNumber));
      		break;
      		case 2: integerNumber = Character.getNumericValue(list.get(++loopNumber))*10 + Character.getNumericValue(list.get(++loopNumber));
      		break;
      		case 3: integerNumber = Character.getNumericValue(list.get(++loopNumber))*100 + Character.getNumericValue(list.get(++loopNumber))*10
      		+ Character.getNumericValue(list.get(++loopNumber));
      		break;
      		case 4: integerNumber = Character.getNumericValue(list.get(++loopNumber))*1000 + Character.getNumericValue(list.get(++loopNumber))*100
      		+ Character.getNumericValue(list.get(++loopNumber))*10 + Character.getNumericValue(list.get(++loopNumber));
      		break;
      		case 5: integerNumber = Character.getNumericValue(list.get(++loopNumber))*10000 + Character.getNumericValue(list.get(++loopNumber))*1000
      		+ Character.getNumericValue(list.get(++loopNumber))*100 + Character.getNumericValue(list.get(++loopNumber)*10 + Character.getNumericValue(list.get(++loopNumber)));
      		break;
      		}
      	return integerNumber;
	}
	
}
