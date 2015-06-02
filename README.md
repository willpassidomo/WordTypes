# WordTypes
a parser which loads the dictionary.txt or any similarly formatted dictionary into memory, with the purpose of serving the type of a given word (noun, verb, prop, adj). The purpose of this project is to serve as a module in a Natural Speech Processing project

The ParseDict class is a class for parsing a spceific dictionary (the file called "dictionary.txt")
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
