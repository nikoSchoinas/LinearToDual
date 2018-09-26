# LinearToDual
Linear Programming with Java.

This java program reads from file a linear problem, parse it and finds its equal dual problem.
The problem has this form: 
min(or max) 3x1-4x2+5x3
s.t. x1 + x2 <= 15
3x3-2x2>=17 

Problem type: min (or max)
Objective function: 3x1-4x2+5x3
Restrictions: x1 + x2 <= 15, 3x3-2x2>17
Left sides (of restrictions): x1 + x2, 3x3-2x2
Right sides (of restrictions or B): 15, 17  
Operators:  <= , >=, =

About Parser:
The Parser turns a linear problem into a specific form, so it can be more manageable. Actually, it reads the linear problem from file and adds its elements to an ArrayList. Then it processes this ArrayList to fill the right static arrays. The code is inside LinearRead class. 
The program is functional under restrictions.

-”min” or “max” word need to be written lowercase (obviously in english).
-After min/max word it must be the objective function (e.g. max 2x1-x2) 
-All variables are symbolized with ‘x’ letter and their coefficients are integers.
-The restrictions starts with “s.t.” (s letter, dot, t letter, dot).
-Between variables and coefficients there are not exist any mathematical symbol that indicates multiplication (e.g. 3x1 + 2x2 and not  3*x1 + 2*x2).
-Symbols like ( ),{ },[ ] or any other symbol except from the basic ones (<,>,=,+,-,.) are not allowed.
-Operators can be <=, >=, =.
-Every term of the right side has only one sigh (e.g. -15x1 and not -+15x1)
-The file with the linear problem needs to be at the same directory with the java file. 

How it works:
Initially, the Parser reads all the characters from file and adds them to an ArrayList named charList. File’s name needs to be replaced from user in Main class (yourFile.txt, line 17). The program searches the words “min” or “max” and “s.t.” and fills the MinMax array. If those two words exist, the program continues normally, else it interrupts. Empty spaces and new line characters (‘\n’) are deleted. At the end of objective function and every restriction is placed a slash (‘/’), just to know where is the start and end of every restriction. When the program finds a right side (a number that it’s not stuck with the x variable) adds that slash.
If the program finds out that a <,>,= or right side is missing then it interrupts. It uses 6 variables to check that. 

-numOfSlashes
-numOfEqualOperators
-numOfExpectedB
-numOfRealB
-numOfExpectedEquals
-numOfRealEquals

When the program finds an equal operator (‘=’) the number of numOfEqualOperator, numOfExpectedB, numOfExpectedEquals, numOfRealEqual is increased. Also, if it finds an equal operator before a right side (B), numOfRealB variable is increased. If not, numOfSlashes, numOfEqualOperator variables are increased. It also checks if numOfRealB equals to numOfExpectedB and if numOfExpectedEquals equals to numOfRealEquals. If this check is faulty then an equal operator is missing. 

This check happens every time a right side is examined. If the file has not right sides but just operators , the program scans the ArrayList to find that error. This error is discovered by checking if the number of slashes equals to number of equal operators. This check is:
if(numOfSlashes-1 != numOfEqualOperator). We expect number of slashes to be higher by one unit. 

Then, the program “reads” the objective function to find the number of x variables and complete c and A arrays. It keeps the biggest index of variable x (e.g. if we have three x variables x1,x2,x3 then the biggest index is the number 3).

Every time the program finds an x variable takes its coefficient (with takeVariable method) and place it at the right position. The positions is indicating by the x variable’s index.

At the same time, the program fills A,b and Eqin arrays by reading every problems ristriction. 
“A” array is filled like “c” array. 
“B” array is filled when a slash symbol is found.
“Eqin” array is filled when < or > symbol is found. 

 Project classes:
-FileRW: This class is relevant with file reading and file writing. Initially, adds file characters into an ArrayList and at the end of process, writes the dual problem to file.  
-LinearRead: It contains the parser code that it’s described in the previous session. It also contains some getters and boolean variable to check the flow of the program.
-Main: The main class. Objects of the above classes are created in the main class. 

writeToFile is a method inside FileWR class. Takes as arguments: the name of output file, A,b,c,Eqin,MinMax arrays and numOfEqualOperators, numOfX variables. 

Firstly, checks the type of the problem (min or max), so to write the opposite type in file. (e.g. if the type problem was min then the dual problem’s type will be max).

Then, “reads” b array so to create the dual objective function. Checks every element if it’s positive or negative and writes it with the right sign. Next to it adds “w” variable with the right index. (e.g. if the first element in b array  (actually in position ‘0’) is 5, then writes to file +5w1. The index is occurred by adding one unit to array’s position. Here is zero (‘0’), so it will be one (‘1’).

Next, it writes every dual problem’s restrictions to file. It “reads” the right column of A array and next to every element writes “w” letter with the right index. After that, writes the appropriate operators (depending problem’s type) and the appropriate number from c array. The problem’s operators are <= or >=, so we need to know just it’s type to find the new ones. 
Finally, it writes the variable’s range (<= 0, >= 0 , free). 

In Main class we create a FileRW object to read from file and create the ArrayList. We create LinearRead object to make A,b,c,Eqin,MinMax arrays. We take the results with the appropriate getters and call writeToFile method to complete the output file.

The program is tested with yourFile.txt (rough form) and yourFile2.txt (normal form). User can make another file but keep in mind the file restrictions that are described in start. User can also change the name of output file. 
