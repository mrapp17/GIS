//    On my honor:
//   
//    - I have not discussed the Java language code in my program with
//      anyone other than my instructor or the teaching assistants
//      assigned to this course.
//   
//    - I have not used Java language code obtained from another student,
//      or any other unauthorized source, either modified or unmodified. 
//   
//    - If any Java language code or documentation used in my program
//      was obtained from another source, such as a text book or course
//      notes, that has been clearly noted with a proper citation in
//      the comments of my program.
//   
//    - I have not designed this program in such a way as to defeat or
//      interfere with the normal operation of the Curator System.
//
//    Michael S Rapp
// 	  14 April 2017

//	Authors notes regarding import behaviors:
//		The system in its current state will store every single element within the imported file into the data base
//	and all of them will also be indexed by name. However any objects with a latitude or longitude which is outside
//	the bounds specified by the world command or which are "Unknown" are no indexed into the coordinate Index.
//	This was designed to operate this way.
//
//	Michael S Rapp

 
import controller.Controller;

public class GIS {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		if(args.length != 3)
			System.out.println("java GIS <database file name> <command script file name> <log file name>\n");
		Controller commandController = new Controller(args[0], args[1], args[2]);
	}

}
