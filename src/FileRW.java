import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FileRW {
	
	public FileRW(String inputFile,ArrayList<Character> ch){
		    /*try catch block. Read characters from file and add them in ArrayList charList*/        
	try{
		int c;
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    	
    	while((c = reader.read()) != -1) {
	  		char character = (char) c;
	  		ch.add(character);
	  	}
	
		reader.close();
	} catch (IOException e) {
   
	}
	
	/*add symbol '*' that declares the end of ArrayList charList*/
	ch.add('*');
	}

	public void writeToFile(String outputFile,int [][]A, int []b, int [][]c, int []Eqin, int [] MinMax, int numOfEqualOperator, int numOfX){
		try {
		int z,i,j;
		PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
		if(MinMax[0] == -1)
			writer.print("max ");
		else if (MinMax[0] == 1)
			writer.print("min ");
		i =0;
		while(i<b.length){
			z=0;
			if(b[i]>=0){
				z=i+1;
				writer.print("+"+b[i]+"w"+ z);
			}	
			else if(b[i]<0){
				z=i+1;
				writer.print(b[i]+"w"+ z);
			}
			i++;	
		}
		writer.println();
		for(i=0;i<numOfX;i++){
			for(j=0;j<numOfEqualOperator;j++){
				z=0;
				if(A[j][i]>=0){
					z=j+1;
					writer.print("+"+A[j][i]+"w"+ z);
				}
				else if(A[j][i]<0){
					z=j+1;
					writer.print(A[j][i]+"w"+ z);
				}	
			}
			if(MinMax[0]== -1)
				writer.print(" <= " + c[0][i]);
			else if (MinMax[0]== 1)
				writer.print(" >= " + c[0][i]);
		writer.println();
		}
		for(i=0;i<numOfEqualOperator;i++){
			z=0;
			if(MinMax[0] == -1){
				if(Eqin[i] == -1){
					z=i+1;
					writer.print("w"+z+"<= 0, ");
				}
				else if(Eqin[i] == 1){
					z=i+1;
					writer.print("w"+z+">= 0, ");
				}
				else{
					z=i+1;
					writer.print("w"+z+"-free, ");
				}
			}
			else if(MinMax[0]==1){
				if(Eqin[i] == -1){
					z=i+1;
					writer.print("w"+z+">= 0, ");
				}
				else if(Eqin[i] == 1){
					z=i+1;
					writer.print("w"+z+"<= 0, ");
				}
				else{
					z=i+1;
					writer.print("w"+z+"-free, ");
				}
			}

			
		}
		writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
