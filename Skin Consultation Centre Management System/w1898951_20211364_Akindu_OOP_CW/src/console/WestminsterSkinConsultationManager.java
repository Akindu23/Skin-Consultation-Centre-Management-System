package console;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * COPYRIGHT (C) 2023 Akindu Karunaratne (w1898951/20211364). All Rights Reserved.
 * Console Menu implementation of a Skin Consultation Centre.
 * Object-Oriented Programming (5COSC019.1) Coursework 1.
 *
 * @author Akindu Karunaratne
 * @version 1.0 2023-01-07.
 **/
public class WestminsterSkinConsultationManager implements SkinConsultationManager, Serializable {
   private static ArrayList<Doctor> doctorList = new ArrayList<>();
   public WestminsterSkinConsultationManager() {
   }
   public static ArrayList<Doctor> getDoctorList() {
      return doctorList;
   }

   /**
    * Method to add a doctor to the skin consultation centre.
    * regex patterns were referred from the following websites
    * <a href = "https://www.tutorialkart.com/java/how-to-check-if-string-contains-only-alphabets-in-java/">Alphabet only</a> <br>
    * <a href = "https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers">Mobile Number</a> <br>
    * <a href = "https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html">Java Documentation used for other validation</a>
    **/

   @Override
   public void addDoctor() {
      Scanner scanner = new Scanner(System.in);
      final int MAX_DOCTORS = 10;

      if (doctorList.size() < MAX_DOCTORS) {
         Doctor doctorObj = new Doctor();

         System.out.println("-------- ADD A DOCTOR --------\n");
         System.out.println("Enter 'R' in any of the prompts if you want to return back to the main menu. \n");

         String firstName = validateString("Enter doctor's first name: ",
                 "Doctor's first name should only contain letters and can not be empty! \n",
                 "[a-zA-Z]+");

         String surname = validateString("Enter doctor's surname: ",
                 "Doctor's surname should only contain letters and can not be empty! \n",
                 "[a-zA-Z]+");

         LocalDate dateOfBirth = validateDateOfBirth("Enter doctor's date of birth (YYYY-MM-DD): ",
                 "Doctor's date of birth can not be empty and should be in YYYY-MM-DD format! \n");

         String mobileNumber = validateString("Enter doctor's mobile number: ",
                 "Mobile number should have exactly 10 digits and can not be empty! \n",
                 "\\d{10}");

         String specialization = "";
         boolean validSpecialization = false;

         while (!validSpecialization) {
            System.out.println("\nSelect doctor's specialization from the options below: ");
            System.out.println("""
             \tC - Cosmetic Dermatology
             \tM - Medical Dermatology
             \tP - Paediatric Dermatology
             \tS - Surgical Dermatology
             \tO - Other
             """);
            System.out.print("Enter option: ");
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("C")){
               specialization = "Cosmetic Dermatology";
               validSpecialization = true;

            } else if (option.equalsIgnoreCase("M")) {
               specialization = "Medical Dermatology";
               validSpecialization = true;

            } else if (option.equalsIgnoreCase("P")) {
               specialization = "Paediatric Dermatology";
               validSpecialization = true;

            } else if (option.equalsIgnoreCase("S")) {
               specialization = "Surgical Dermatology";
               validSpecialization = true;

            }else if (option.equalsIgnoreCase("O")) {
               specialization = validateString("Enter specialization: ",
                       "Specialization should only contain letters and can not be empty! \n",
                       "[a-zA-Z ]+");
               validSpecialization = true;

            }else if (option.equalsIgnoreCase("R")) {
               return;
            } else {
               System.out.println("Invalid option! Please try again. \n");
            }
         }
         String medicalLicenseNumber = validateString("\nEnter doctor's medical license number: ",
                 "Medical license number can not be empty! \n", "[\\w ]+");

         //checking if medical license number already exists
         while (!checkUniqueMedicalLicenseNumber(medicalLicenseNumber)) {
            System.out.println("Medical license number already exists in the system. \n");
            medicalLicenseNumber = validateString("Enter doctor's medical license number: ",
                    "Medical license number can not be empty! \n", "[\\w ]+");
         }

         doctorObj.setDetails(firstName, surname, dateOfBirth, mobileNumber, specialization, medicalLicenseNumber);
         doctorList.add(doctorObj);

         System.out.println("\nDoctor " + firstName + " " + surname + " added to the skin consultation centre" +
                 " successfully!\n");
         System.out.println("Number of doctors in the skin consultation centre: " + doctorList.size() + "\n");
      } else {
         System.out.println("Maximum limit of doctors (" + MAX_DOCTORS + ") reached! Cannot add more doctors.\n");
      }
   }

   /**
    * Method to delete a doctor from the skin consultation centre.
    * Deletion is done by the medical license number of the doctor.
    **/

   @Override
   public void deleteDoctor() {
      if (doctorList.size() > 0) {
         System.out.println("-------- DELETE A DOCTOR --------\n");
         System.out.println("Enter 'R' instead of the medical license number if you want to return to the main menu.\n");

         System.out.println("-------Doctors currently added in the system------- ");
         for (Doctor doctor : doctorList) {
            System.out.println(doctor.getMedicalLicenseNumber() + " - " + doctor.getFullName());
         }
         System.out.println();

         String medicalLicenseNumber = validateString("Enter medical license number you want to delete: ",
                 "Medical license number can not be empty! \n", "[\\w ]+");

         //checking if medical license number is not in the system
         while (checkUniqueMedicalLicenseNumber(medicalLicenseNumber)) {
            System.out.println("\nDoctor not found! Please re-check existing medical license numbers and try again!\n");

            medicalLicenseNumber = validateString("Enter medical license number you want to delete: ",
                    "Medical license number can not be empty! \n", "[\\w ]+");
         }

         for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getMedicalLicenseNumber().equals(medicalLicenseNumber)) {
               System.out.println("\nDetails of doctor to be deleted: \n" + doctorList.get(i).showDetails());

               String confirmDelete = validateString("Are you sure you want to delete this doctor (Y/N)?: ",
                       "Please enter 'Y' or 'N'! \n", "[YyNn]");

               if (confirmDelete.equalsIgnoreCase("Y")) {
                  System.out.println("\nDoctor deleted.");
                  doctorList.remove(i);
                  System.out.println("Number of doctors in the skin consultation centre: " + doctorList.size() + "\n");

               } else {
                  System.out.println("\nDoctor not deleted. Returning to main menu...\n");
               }
               break;
            }
         }
      } else {
         System.out.println("No doctors are there in the skin consultation centre to remove!! \n");
      }
   }

   /**
    * Method to print all doctors in the skin consultation centre.
    * The doctors are sorted alphabetically by their surname.
    * If the surname is the same then the doctors are sorted by their first name.
    * <a href="https://howtodoinjava.com/java/sort/sort-on-multiple-fields/">
    * This website was used to refer and adapt the code to sort the doctors by their surname.
    * </a>
    **/
   @Override
   public void printDoctors() {
      if (doctorList.size() > 0) {
         Doctor[] sortedDoctors = doctorList.toArray(new Doctor[0]);

         Comparator<Doctor> compareBySurname = Comparator.comparing(Doctor::getSurname, String.CASE_INSENSITIVE_ORDER)
                 .thenComparing(Doctor::getName, String.CASE_INSENSITIVE_ORDER);

         Arrays.sort(sortedDoctors, compareBySurname);

         System.out.println("----------------- DOCTORS IN WESTMINSTER CONSULTATION CENTRE ------------------ \n");

         for (int i = 0; i < sortedDoctors.length; i++) {
            System.out.println("Doctor " + (i + 1));
            System.out.println(sortedDoctors[i].showDetails());
         }

      } else {
         System.out.println("No doctors are there in the skin consultation centre to print!! \n");
      }
   }

   /**
    * Method to save the progress of the skin consultation centre using serialization.
    * The doctors are saved in a file called "doctors.txt".
    * <a href="https://www.geeksforgeeks.org/serialization-in-java/">
    * Code for serialization adapted from this website.
    * </a>
    **/

   @Override
   public void saveProgress(String fileName) {
      try {
         FileOutputStream fileOutputStream = new FileOutputStream(fileName);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

         for (Doctor doctor : doctorList) {
            objectOutputStream.writeObject(doctor);
         }

         objectOutputStream.close();
         System.out.println("Progress saved successfully!\n");

      } catch (IOException e) {
         System.out.println("An error occurred while saving progress!!! \n" + e);
      }
   }

   /**
    * Method to load the progress of the skin consultation centre using deserialization.
    * The doctors are loaded from a file called "doctors.txt".
    * <a href="https://www.geeksforgeeks.org/serialization-in-java/">
    * Code for deserialization adapted from this website.
    * </a>
    **/

   @Override
   public void reloadProgress(String fileName) {
      doctorList.clear();
      try {
         FileInputStream fileInputStream = new FileInputStream(fileName);
         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

         while (fileInputStream.available() > 0) {
            doctorList.add((Doctor) objectInputStream.readObject());
         }

         objectInputStream.close();

      } catch (IOException | ClassNotFoundException e) {
         System.out.println(e);
      }
   }

   /**
    * Method to check if the medical license number of a doctor is unique.
    *
    * @param medicalLicenseNumber Medical license number of the doctor.
    * @return true if the medical license number is unique, false otherwise.
    */
   public boolean checkUniqueMedicalLicenseNumber(String medicalLicenseNumber) {
      for (Doctor doctor : doctorList) {
         if (doctor.getMedicalLicenseNumber().equals(medicalLicenseNumber)) { //medical license number already exists
            return false;  //not unique
         }
      }
      return true;
   }

   /**
    * Method to validate the input of a string. Also checks if the user wants to return to the main menu by entering 'M'
    *
    * @param displayMessage Message to be displayed to the user.
    * @param errorMessage   Error message to be displayed to the user.
    * @param format         regex Format of the string.
    * @return validated string.
    */
   public String validateString(String displayMessage, String errorMessage, String format) {
      Scanner input = new Scanner(System.in);
      String stringName = "";
      boolean validateString = true;

      while (validateString) {
         System.out.print(displayMessage);
         stringName = input.nextLine();

         if (stringName.equalsIgnoreCase("R")) {
            WestminsterSkinConsultationDriver.displayMenu();

         } else if (stringName.matches(format) && !(stringName.isEmpty())) {
            validateString = false;

         } else {
            System.out.println(errorMessage);
         }
      }
      return stringName;
   }

   /**
    * Method to validate Date of Birth of a doctor.
    *
    * @param displayMessage Message to be displayed to the user.
    * @param errorMessage   Error message to be displayed to the user.
    * @return validated Date of Birth.
    */
   public LocalDate validateDateOfBirth(String displayMessage, String errorMessage) {
      Scanner input = new Scanner(System.in);
      LocalDate dateOfBirth = null;
      boolean validateDateOfBirth = true;

      while (validateDateOfBirth) {
         try {
            System.out.print(displayMessage);
            dateOfBirth = LocalDate.parse(input.nextLine());

            if (dateOfBirth.isAfter(LocalDate.parse("1962-12-31")) &&
                    dateOfBirth.isBefore(LocalDate.parse("1999-01-01"))) {
               validateDateOfBirth = false;
            } else {
               System.out.println(errorMessage + " can only be between 1962-12-31 and 1999-01-01 due to minimum and " +
                       "maximum age requirements!! \n");
            }
         } catch (DateTimeParseException e1) {
            System.out.println("Please enter a valid date of birth in the format YYYY-MM-DD\n");
         }
      }
      return dateOfBirth;
   }
}
