package console;

import gui.*;
import java.io.File;
import java.util.Scanner;

//Driver class for the Westminster Skin Consultation System
public class WestminsterSkinConsultationDriver {
   public static void main(String[] args) {
      WestminsterSkinConsultationManager skinConsultationCentre = new WestminsterSkinConsultationManager();

      File temp = new File("src/console/doctors.txt"); //To check if data exists from a previous run
      if (temp.exists()) {
         System.out.println("\nATTENTION! Saved progress from a previous run has been found! The progress has been " +
                 "reloaded automatically.");
         skinConsultationCentre.reloadProgress("src/console/doctors.txt");
      }

      displayMenu();
   }

   /**
    * Displays the menu for the user to select an option.
    */
   public static void displayMenu() {
      WestminsterSkinConsultationManager skinConsultationCentre = new WestminsterSkinConsultationManager();

      Scanner input = new Scanner(System.in);
      while (true) {
         //Menu options to be displayed on the console
         System.out.println("""
                 
                 <---- WESTMINSTER SKIN CONSULTATION CENTRE ---->
                                  
                 ------------------- MENU ----------------------
                                 
                 \tEnter 1 or 'A' to add a new doctor.
                 \tEnter 2 or 'D' to delete a doctor.
                 \tEnter 3 or 'P' to print the list of doctors.
                 \tEnter 4 or 'S' to save progress in a File.
                 \tEnter 5 or 'G' to launch the GUI.
                 \tEnter 0 or 'X' to Exit.
                                  
                 -----------------------------------------------
                 """);

         System.out.print("Enter your option: "); //Taking user input for the menu option
         String option = input.nextLine();

         System.out.println();

         if (option.equals("1") || option.equalsIgnoreCase("A")) {
            skinConsultationCentre.addDoctor();   //To add a doctor

         } else if (option.equals("2") || option.equalsIgnoreCase("D")) {
            skinConsultationCentre.deleteDoctor();   //To delete a doctor

         } else if (option.equals("3") || option.equalsIgnoreCase("P")) {
            skinConsultationCentre.printDoctors();   //To print all doctors

         } else if (option.equals("4") || option.equalsIgnoreCase("S")) {
            skinConsultationCentre.saveProgress("src/console/doctors.txt");   //To save progress in a file

         } else if (option.equals("5") || option.equalsIgnoreCase("G")){
            skinConsultationCentre.saveProgress("src/gui/gui.txt"); //Saves current progress before opening the GUI
            AddConsultation.loadData();   //Loads the data from the file to the GUI

            new GUI(); //Launches the GUI

         }else if (option.equals("0") || option.equalsIgnoreCase("X")) {
            System.out.println("Exiting the Program...");
            System.exit(0); //Ending loop to exit program.

         } else {   //Checking if an option from the menu was entered. If not program will loop back to take user input.
            System.out.println("Invalid Option!!!\n");
         }
      }
   }
}
