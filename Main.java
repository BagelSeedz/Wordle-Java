import java.util.Scanner;
import java.util.ArrayList;

class Main {
  static WordFile fileObject = new WordFile();
  private static String listOfWords[] = fileObject.getWords();
  private static int minimumLetters = 2, maximumLetters = 10, maxGuesses = 6;
  private static Scanner mrScanner = new Scanner(System.in);
  
  public static String getRandomWord(int letters) {
    int listLength = listOfWords.length;
    String randomWord = "temp";
    while (true) {
      int tempRandomIndex = (int)(Math.random() * listLength);
      String tempWord = listOfWords[tempRandomIndex];
      if (tempWord.length() == letters) {
        randomWord = tempWord;
        break;
      }
    }
    return randomWord;
  }
  
  public static int askForDifficulty() {
    while (true) {
      System.out.print("Difficulty?: ");
      int difficulty = Integer.parseInt((mrScanner.nextLine()));
      if (!((difficulty >= minimumLetters) && (difficulty <= maximumLetters))) {
        System.out.println("Please insert a number between " + minimumLetters + " and " + maximumLetters);
      }
      else{
        return difficulty;
      }
    }
  }
  
  public static void main(String[] args) {
    int difficulty = askForDifficulty();
    String randomWord = getRandomWord(difficulty);
    
    String currentList = "";
    for (int i=0; i<difficulty; i++) {
      currentList = currentList + "_";
    }
    System.out.println("WORDLE! (HARD MODE) | " + difficulty + " letter word");
    System.out.println("----------------------");
    System.out.println(currentList);
    
    boolean beat = false;
    int guessCount = 1;
    while (true) {
      System.out.println("----------------------");
      System.out.print("Guess " + guessCount + "/" + maxGuesses + ": ");
      String test = mrScanner.nextLine();
      if (!(test.length() == difficulty)) {
        System.out.println("Please insert a " + difficulty + " letter word");
        continue;
      }

      ArrayList<String> alreadyFound = new ArrayList<String>();
      for (int i=0; i<difficulty; i++) {
        for (int a=0; a<randomWord.length(); a++) {
          if ((test.substring(i,i+1)).equals(randomWord.substring(a,a+1))) {
            boolean letterFound = false;
            for (String letter : alreadyFound) {
              if (letter == randomWord.substring(a,a+1)) {
                letterFound = true;
              }
            }
            if (letterFound == false) {
              System.out.println("Discovered Letter " + randomWord.substring(a,a+1));
              alreadyFound.add(randomWord.substring(a,a+1));
            }
            if ((test.substring(i,i+1)).equals(randomWord.substring(i,i+1))) {
              currentList = currentList.substring(0, i) + test.substring(i,i+1) + currentList.substring(i+1, difficulty);
            }
          }
        }
      }

      if (currentList.equals(randomWord)) {
        beat = true;
        break;
      } 
      else if (guessCount == maxGuesses) {
        break;
      }

      guessCount += 1;
      System.out.println("----------------------");
      System.out.println(currentList);
    }

    if (beat) {
      System.out.print("You won! the word was " + randomWord + ".");
    }
    else {
      System.out.print("You lost. the word was " + randomWord + ".");
    }
  }
}
