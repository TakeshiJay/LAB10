import java.util.*;

// Key: Pattern
// Value: Num Occurrences
/**
* CECS 277 Lab10 Mind Reader- Program guesses the users input based
* on whether the user has used the same inputs multiple of times or
* on previous attempts
*
*
* @author Blake Del Rey
* @author Jacob Sunia
*/
class Main 
{
  public static void main(String[] args) 
  {
    final char SENTINAL = 'Q';

    String pattern = "    ";
    int numSelected = 0;
    String choiceString = "";
    char choice = 'X';

    HashMap<String, Integer> map = new HashMap<String, Integer>();

    
    char prediction = makePrediction(map, pattern);
    choice = getInput();
    numSelected++;
    while (choice != SENTINAL)
    {
      System.out.println("Comp: " + prediction);
      pattern = Character.toString(pattern.charAt(1)) 
                + Character.toString(pattern.charAt(2)) 
                + Character.toString(pattern.charAt(3)) 
                + Character.toString(choice);
      
      if (numSelected >= 4)
      {
        storePattern(map, pattern);
      }

      choice = getInput();
      numSelected++;
      prediction = makePrediction(map, pattern);
      
    }

  }

  public static char makePrediction(HashMap<String, Integer> map, String pattern)
  {
    
    if (map.size() == 0)
    {
      Random rand = new Random();
      int randomGuessInt = rand.nextInt(2);
      if (randomGuessInt == 0)
      {
        return 'X';
      }
      if (randomGuessInt == 1)
      {
        return 'O';
      }
    }

    String xKeyCheck = pattern.substring(1,3) + "X";
    String oKeyCheck = pattern.substring(1,3) + "O";
    int xVal = 0;
    int oVal = 0;
    
    if (map.containsKey(xKeyCheck))
    {
      xVal = map.get(xKeyCheck); 
    }
    if (map.containsKey(oKeyCheck))
    {
      oVal = map.get(oKeyCheck);
    }
    System.out.println(xVal);
    System.out.println(oVal);
    if (xVal > oVal)
    {
      
      return 'X';
    }
    return 'O';
    

  }
  /** 
  * getInput takes user input and validates whether the input is true or false 
  */
  public static char getInput()
  {
    char choice = 'a';
    while (choice != 'X' && choice != 'O' && choice != 'Q')
    {
      System.out.println("Enter X or O, or Q to quit.");
      String choiceString = CheckInput.getString();
      choice = choiceString.charAt(0);
      choice = Character.toUpperCase(choice);
      if (choice != 'X' && choice != 'O' && choice != 'Q')
      {
        System.out.println("**ERROR NOT A VALID INPUT**");
      }
      
    }
    return choice;
  }
  /** 
  * storePattern checks if a specific pattern has been used while incrementing the usage value, otherwise it adds the String to the hashmap. 
  * @param map Passes in a empty HashMap and adds according to getInput
  * @param pattern is the pattern that the program detects and adds it to the hash map to determine whether the user has used the sequence before
  *
  */
  public static void storePattern(HashMap<String, Integer> map, String pattern)
  {
    if(map.containsKey(pattern))
    {
      int newVal = map.get(pattern);
      newVal++;
      map.put(pattern, newVal);
    } 
    else 
    {
      map.put(pattern, 1);
    }
  }
}