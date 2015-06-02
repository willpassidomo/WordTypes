package Contradition.TextProcessing.Dictionary;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.PatternSyntaxException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Collections;
/*
 * The ParseDict class is a class for parsing a spceific dictionary (the file called "dictionary.txt")
 * There are 3 uses of this class as follows;
 *  1) CommandLine IO tool
 *      - run this program from the command line with the name of the dictionary text file as the sole parameter and a
 *      file called "good.txt" will be outputed with a list of all parsable words and their types. This use is primarily
 *      debugging purposes, as another file called "bad.txt" will be outputted with recognized words for which word types
 *      were unable to be parsed
 *  2) Command Line Lookup tool:
 *    - run from the command line with the name of the dictionary text file as the first parameter and "lookup" as the
 *    second parameter. The program will parse the dictionary and load it into memory and enter lookup mode. In lookup
 *    mode, the User may enter a search term, a word, and the program will respond the the word's type. exit with ctr-C
 *  3) Class
 *    - in a program, instantiate and instance of the class and call the "parse" method, passing in the path to the dictionary
 *    file as the parameter, this will return a HashMap containing the dictionary words as keys and lists of types as values
 *
 */

public class ParseDict {

  private boolean toFile = false;
  private PrintWriter awriter = null;
  private PrintWriter bwriter = null;

  public ParseDict(boolean toFile) {
    this.toFile = toFile;
  }

  public static void main(String[] args) throws PatternSyntaxException, FileNotFoundException {
    if (args.length > 3 || args.length < 0) {
      System.out.println("useage: java ParseDict [txt file] {\"lookup\"}");
    }
    if (args.length > 1 && args[1].equalsIgnoreCase("lookup")) {
      ParseDict pd = new ParseDict(false);
      System.out.println("processing...");
      Map<String, ArrayList<String>> dict = pd.parse(args[0]);
      System.out.println("done, enter search term:");
      String input;
      while(true) {
        Scanner scan = new Scanner(System.in);
        input = scan.nextLine();
        if (input.equals("dump")) {pd.dump(dict);}
        System.out.print(input + " type- ");
        pd.print(dict.get(input));
        System.out.print("\n");
      }
    } else {
      ParseDict pd = new ParseDict(true);
      pd.parse(args[0]);
    }
  }

  public Map<String, ArrayList<String>> parse(String fileName) throws FileNotFoundException{
    HashMap<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
    Scanner scan = null;
    if (toFile) {
      awriter = new PrintWriter("results/good.txt");
      bwriter = new PrintWriter("results/bad.txt");
    }
    int counta = 0, countb = 0;

    try {
      File file = new File(fileName);
      scan = new Scanner(file);
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    String line;
    while(scan.hasNext()) {
      line = scan.nextLine();
      int len = line.length();
      if(len == 1 || (len > 1 && isADictWord(line))) {
          //look for word type and store word
          if(scan.hasNext()) {
            String nline = scan.nextLine();
            String[] type = nline.split(", ",2);
              if (type.length > 1) {
                type = type[1].split("\\.",2);
                type = type[0].split(",",2);
                type = type[0].split("Etym",2);
                if (toFile) {
                    awriter.println(type[0] +"- " + line);
                    counta++;
                    } else {
                    put(dict, line,type[0]);
                    }
                } else {
                  if (toFile) {
                    bwriter.println("NO TYPE - " + line);
                    countb++;
                  }
                }
              }
          }
        }
    if (toFile) {
      System.out.println("good- "+counta);
      System.out.println("bad- " + countb);
      System.out.println("done");
    }
    return dict;
  }

  public static boolean isADictWord(String line) {
    for (int i = 0; i < line.length(); i++) {
      if(!Character.isUpperCase(line.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  private void put(Map<String, ArrayList<String>> dict, String key, String value) {
    ArrayList<String> types = null;
    if(dict.containsKey(key)) {
        types = dict.get(key);
      }else {
        types = new ArrayList<>();
      }
      if (!types.contains(value)) {
        types.add(value);
      }
      dict.put(key, types);
  }

  private void print(ArrayList<String> li) {
    if(li != null) {
      for(String s: li) {
        System.out.print(s +": ");
      }
    }
  }

  private void dump(Map<String, ArrayList<String>> dict) {
    Map<String,Integer> types = new HashMap<>();
    for (ArrayList<String> v: dict.values()) {
      for(String s: v) {
        if(types.containsKey(s)) {
          int i = types.get(s);
          types.put(s,++i);
        } else {
          types.put(s,1);
        }
      }
    }
    System.out.print("\n");
    Set<String> keys = types.keySet();
    keys = new TreeSet<>(keys);
    Iterator<String> it = keys.iterator();
    String k;
    while(it.hasNext()) {
      k = it.next();
      System.out.println(k + " #"+types.get(k));
    }
  }
}
