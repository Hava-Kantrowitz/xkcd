import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generator {

  public static void main(String[] args) throws FileNotFoundException {
    int wordsNum = 4;
    int capsNum = 0;
    int numsNum = 0;
    int symsNum = 0;

    //Getting symbols together
    List<String> symbols = Arrays.asList("~", "!", "@", "#", "$", "%", "^", "&", "*", ".", ":", ";");

    //Getting numbers together
    List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    ArrayList<String> pswdWords = new ArrayList<>();
    ArrayList<String> pswdSymbols = new ArrayList<>();
    ArrayList<String> pswdNumbers = new ArrayList<>();

    String password = "";

    Random randy = new Random();

    //Parsing
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-h") || args[i].equals("--help")) {
        System.out.println("This program will make you a CorrectHorseBatteryStaple program. If " +
                "you're confused, just run ./xkcdpwgen to get your password.");
        return;
      }

      if (args[i].equals("-w") || args[i].equals("--words")) {
        wordsNum = Integer.parseInt(args[i+1]);
      }

      if (args[i].equals("-c") || args[i].equals("--caps")) {
        capsNum = Integer.parseInt(args[i+1]);
        if (capsNum > wordsNum) {
          System.out.println("Can't capitalize words that don't exist. Please try again.");
          return;
        }
      }

      if (args[i].equals("-s") || args[i].equals("--symbols")) {
        symsNum = Integer.parseInt(args[i+1]);
      }

      if (args[i].equals("-n") || args[i].equals("--numbers")) {
        numsNum = Integer.parseInt(args[i+1]);
      }
    }

    File file = new File("C:\\Users\\havak\\IdeaProjects\\xkcdpwgen\\src\\words.txt");
    String word;

    //get the words
    for (int i = 0; i < wordsNum; i++) {
      int randNum = randy.nextInt(102305);//the number of lines in file, hardcoded
      int index = 0;
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        index++;
        word = sc.nextLine();
        if (randNum == index) {
          pswdWords.add(word.toLowerCase());
        }
      }
    }

    //get the symbols
    for (int i = 0; i < symsNum; i++) {
      int index = randy.nextInt(symbols.size());
      pswdSymbols.add(symbols.get(index));
    }

    //get the numbers
    for (int i = 0; i < numsNum; i++) {
      int index = randy.nextInt(numbers.size());
      pswdNumbers.add(numbers.get(index));
    }

    //capitalize
    for (int i = 0; i < capsNum; i++) {
      makeCapital(pswdWords);
    }

    //combine together
    for (String pswdWord : pswdWords) {
      int determinator = randy.nextInt(10);
      if (determinator < 5) {
        //potentially add symbol
        if (pswdSymbols.size() != 0) {
          int randSymb = randy.nextInt(pswdSymbols.size());
          password = password.concat(pswdSymbols.get(randSymb));
          pswdSymbols.remove(randSymb);
        }
      }
      else {
        //potentially add num
        if (pswdNumbers.size() != 0) {
          int randNum = randy.nextInt(pswdNumbers.size());
          password = password.concat(pswdNumbers.get(randNum));
          pswdNumbers.remove(randNum);
        }
      }
      password = password.concat(pswdWord);

      if (determinator < 5) {
        //potentially add num
        if (pswdNumbers.size() != 0) {
          int randNum = randy.nextInt(pswdNumbers.size());
          password = password.concat(pswdNumbers.get(randNum));
          pswdNumbers.remove(randNum);
        }
      }
      else {
        //potentially add symbol
        if (pswdSymbols.size() != 0) {
          int randSymb = randy.nextInt(pswdSymbols.size());
          password = password.concat(pswdSymbols.get(randSymb));
          pswdSymbols.remove(randSymb);
        }
      }
    }

    int origNumSize = pswdNumbers.size();
    for (int i = 0; i < origNumSize; i++) {
      int determinator = randy.nextInt(10);
      if (determinator < 5) {
        if (pswdSymbols.size() != 0) {
          int randSymb = randy.nextInt(pswdSymbols.size());
          password = password.concat(pswdSymbols.get(randSymb));
          pswdSymbols.remove(randSymb);
        }
      }

      int randyNum = randy.nextInt(pswdNumbers.size());
      password = password.concat(pswdNumbers.get(randyNum));
      pswdNumbers.remove(randyNum);
    }

    int origSymbSize = pswdSymbols.size();
    for (int i = 0; i < origSymbSize; i++) {
      int randySymb = randy.nextInt(pswdSymbols.size());
      password = password.concat(pswdSymbols.get(randySymb));
      pswdSymbols.remove(randySymb);
    }

    //Print out
    System.out.println("We are done. We have " + wordsNum + " words made out of " + capsNum + " capitals, " +
            symsNum + " symbols, and " + numsNum + " numbers.");
    System.out.println("Your password is " + password);
  }

  private static void makeCapital(ArrayList<String> pswdWords) {
    Random randy = new Random();
    int randyNum = randy.nextInt(pswdWords.size());
    String tryWord = pswdWords.get(randyNum);
    if (!isCapital(tryWord)) {
      pswdWords.remove(randyNum);
      pswdWords.add(randyNum, capitalize(tryWord));
    }
    else {
      makeCapital(pswdWords);
    }
  }

  private static boolean isCapital(String tryWord) {
    return Character.isUpperCase(tryWord.charAt(0));
  }

  public static String capitalize(String word) {
    return word.substring(0,1).toUpperCase() + word.substring(1);
  }

}
