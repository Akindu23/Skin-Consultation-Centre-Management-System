package gui;

import console.*;
import gui.CreateComponents.PTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class BookingConsultation extends JFrame implements Serializable {
   //instance variables
   private static final ArrayList<Consultation> assignedDoctorList = new ArrayList<>();
   private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
   private static JTextField date;
   private static PTextField startTime;
   private static PTextField endTime;
   private final JComboBox<Doctor> doctorComboList;
   private final JButton confirmDoctorButton;
   private final JButton backButton;
   private final Doctor[] doctorNames;

   // Constructor for GUI Frame to Book a Consultation.
   public BookingConsultation() {
      // CreateComponents object to create the common components for GUI
      CreateComponents createComponents = new CreateComponents();

      // Create WestminsterSkinConsultationManager object to access the methods
      WestminsterSkinConsultationManager skinConsultationCentre = new WestminsterSkinConsultationManager();
      int numberOfDoctors = WestminsterSkinConsultationManager.getDoctorList().size();

      // Create label for the background image
      JLabel background = new JLabel(new ImageIcon("images\\background.jpg"));
      background.setBounds(0, 0, 800, 700);

      // Create label for the main title banner
      JLabel header = new JLabel();
      header.setIcon(new ImageIcon("images\\bookingheader.png"));
      header.setBounds(65, 0, 650, 300);

      // Create label for logo.
      JLabel logo = createComponents.createLogo(700);

      // Create back button
      backButton = createComponents.createBackButton();
      this.add(backButton);

      // Creating the labels using createComponents object
      JLabel docListLabel = createComponents.createLabel("Select a Doctor for your Consultation", 50, 350,
              400, 30);
      this.add(docListLabel);


      // Add the doctor names to the combo box using an array of doctor names from the saved data.
      skinConsultationCentre.reloadProgress("src/gui/gui.txt");
      doctorNames = new Doctor[numberOfDoctors];
      for (int i = 0; i < numberOfDoctors; i++) {
         Doctor fullName = WestminsterSkinConsultationManager.getDoctorList().get(i);
         doctorNames[i] = fullName;
      }

      // Create the combo box for the doctor list and add the doctors to the combo box using the array of doctor names.
      doctorComboList = new JComboBox<>(doctorNames);
      doctorComboList.setBounds(475, 350, 275, 30);
      doctorComboList.setFocusable(false);
      doctorComboList.setFont(new Font("Dubai", Font.BOLD, 16));
      this.add(doctorComboList);

      // Create label for the date using createComponents object
      JLabel selectDateLabel = createComponents.createLabel("Enter a Date for your Consultation (YYYY-MM-DD)",
              50, 400, 500, 30);
      this.add(selectDateLabel);

      // Create a text field for the date and show the current date as a default.
      date = new JTextField(String.valueOf(LocalDate.now()));
      date.setBorder(new EmptyBorder(3, 3, 3, 3));
      date.setBounds(475, 400, 100, 30);
      date.setFont(new Font("Dubai", Font.PLAIN, 16));
      this.add(date);

      // Creating labels and text fields using createComponents object
      JLabel startTimeLabel = createComponents.createLabel("Enter Start Time for your Consultation (24h format HH:mm)",
              50, 450, 500, 30);
      this.add(startTimeLabel);

      startTime = createComponents.createTextField("09:00", 475, 450, 100, 30);
      this.add(startTime);

      JLabel endTimeLabel = createComponents.createLabel("Enter End Time for your Consultation (24h format HH:mm)",
              50, 500, 500, 30);
      this.add(endTimeLabel);

      endTime = createComponents.createTextField("17:00", 475, 500, 100, 30);
      this.add(endTime);

      // Create button to confirm the doctor selection
      confirmDoctorButton = createComponents.createButton("Confirm", 350, 575, 100, 35,
              "#92FE9D");
      this.add(confirmDoctorButton);

      // Creates BookDoctorHandler object to add event listeners to the buttons
      BookDoctorHandler handler = new BookDoctorHandler();
      backButton.addActionListener(handler);
      confirmDoctorButton.addActionListener(handler);

      // Add the components to the frame and set the frame properties.
      this.setTitle("Book Consultation");
      this.add(header);
      this.add(logo);
      this.add(background);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setLayout(null);
      this.setVisible(true);
      this.setSize(800, 700);
      this.setLocationRelativeTo(null);
      this.setResizable(false);
   }

   // Inner Class BookDoctorHandler to handle the events of the buttons.
   private class BookDoctorHandler implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         if (event.getSource() == backButton) {
            new GUI();
            dispose();
         } else if (event.getSource() == confirmDoctorButton) {
            validation();
         }
      }
   }

   // getters for text fields and assignedDoctorList
   public static JTextField getDate() {
      return date;
   }

   public static DateTimeFormatter getFormatter() {
      return formatter;
   }

   public static ArrayList<Consultation> getAssignedDoctorList() {
      return assignedDoctorList;
   }

   public static PTextField getStartTime() {
      return startTime;
   }

   public static PTextField getEndTime() {
      return endTime;
   }

   /**
    * Method to validate the user input.
    */
   public void validation() {
      // Creating boolean variables to check the validation of the user input.
      boolean checkDate = false;
      boolean checkStartTime = false;
      boolean checkEndTime = false;

      LocalDate today = LocalDate.now();
      LocalTime currentTime = LocalTime.now();
      LocalTime openingTime = LocalTime.of(9, 0);
      LocalTime closingTime = LocalTime.of(17, 0);

      // try-catch block to check the validation of the date.
      try {
         LocalDate selectedDate = LocalDate.parse(date.getText());

         // Check if the date is in the past.
         if (selectedDate.isBefore(today)) {
            JOptionPane.showMessageDialog(null, "Consultation Date can not be before today",
                    "Error", ERROR_MESSAGE);
         } else {
            checkDate = true;
         }
      } catch (DateTimeParseException e) {
         showMessageDialog(null, "Please enter date in YYYY-MM-DD format!",
                 "Error", ERROR_MESSAGE);
      }

      // if the date is valid, check the validation of the start time.
      if (checkDate) {
         // try-catch block to check the validation of the start time.
         try {
            LocalDate selectedDate = LocalDate.parse(date.getText());
            LocalTime sTime = LocalTime.parse(startTime.getText(), formatter);

            // Check if the start time is before the current time if the selected date is today.
            if (selectedDate.isEqual(today) && sTime.isBefore(currentTime)) {

               // Show error message if the start time is before the current time.
               showMessageDialog(null, "Consultation start time cannot be before current time",
                       "Error", ERROR_MESSAGE);
               return;

            }

            // Check if the start time is within working hours.
            if (sTime.isBefore(openingTime) || sTime.isAfter(closingTime)) {
               showMessageDialog(null, "Consultation start time cannot be before 9am or after 5pm!",
                       "Error", ERROR_MESSAGE);
            } else {
               checkStartTime = true;
            }

         } catch (DateTimeParseException e) {
            showMessageDialog(null, "Please enter start time in 24 hour format!",
                    "Error", ERROR_MESSAGE);
         }
      }

      // if the start time is valid, check the validation of the end time.
      if (checkStartTime) {
         // try-catch block to check the validation of the end time.
         try {
            LocalTime sTime = LocalTime.parse(startTime.getText(), formatter);
            LocalTime eTime = LocalTime.parse(endTime.getText(), formatter);

            // Check if end time is within the working hours.
            if (eTime.isBefore(openingTime) || eTime.isAfter(closingTime)) {
               showMessageDialog(null, "Consultation end time cannot be before 9am or after 5pm!",
                       "Error", ERROR_MESSAGE);
            }
            // Check if end time is before start time.
            else if (eTime.isBefore(sTime)) {
               showMessageDialog(null, "End time cannot be before start time!",
                       "Error", ERROR_MESSAGE);
            } else {
               checkEndTime = true;
            }

         } catch (DateTimeParseException e) {
            showMessageDialog(null, "Please enter end time in 24 hour format!",
                    "Error", ERROR_MESSAGE);
         }
      }

      // if the end time is valid.
      if (checkEndTime) {
         LocalTime sTime = LocalTime.parse(startTime.getText(), formatter);
         LocalTime eTime = LocalTime.parse(endTime.getText(), formatter);

         // Store duration of the consultation in a variable.
         Duration duration = Duration.between(sTime, eTime);
         double minutes = duration.toMinutes();
         int minimumTime = 10;
         int maximumTime = 120;

         // Check if the duration of the consultation is between 10 minutes and 2 hours.
         if (minutes < minimumTime || minutes > maximumTime) {
            showMessageDialog(null, "Consultation duration must be a minimum of 10 minutes " +
                            "and a maximum of 2 hours!",
                    "Error", ERROR_MESSAGE);

         } else {
            // Allocate the selected doctor to the consultation.
            if (doctorAllocation()) {

               // Take user to patient details page.
               new AddConsultation();
               dispose();
            }
         }
      }
   }

   /**
    * Method to allocate the selected doctor to the consultation if available.
    * If selected doctor is not available a doctor will randomly be allocated.
    * Code for setting size for JOptionPane referred from:
    * <a href = "https://stackoverflow.com/questions/14299741/setting-size-of-jpanel-or-joptionpane">Resize JOptionPane
    * </a>
    */
   public boolean doctorAllocation() {
      // Consultation object to store the doctor availability.
      Consultation doctorAvailability = new Consultation();
      boolean doctorAssigned = true;

      // Store the details of the selected doctor, consultation date, start time and end time in variables.
      Doctor doctor = (Doctor) doctorComboList.getSelectedItem();
      String selectedDoctorName = doctor.getFullName();
      String selectedMedicalLicenceNumber = doctor.getMedicalLicenseNumber();
      LocalDate selectedDate = LocalDate.parse(date.getText());
      LocalTime selectedStartTime = LocalTime.parse(startTime.getText(), formatter);
      LocalTime selectedEndTime = LocalTime.parse(endTime.getText(), formatter);

      // Check if the selected doctor is available at the given time slot
      if (isDoctorAvailable(selectedDoctorName, selectedMedicalLicenceNumber, selectedDate, selectedStartTime,
              selectedEndTime)) {

         // Store the details of the selected doctor (name and medical license number), consultation date,
         // start time and end time in a Consultation object.
         doctorAvailability.setDoctorAvailability(selectedDoctorName, selectedMedicalLicenceNumber, selectedDate,
                 selectedStartTime, selectedEndTime);

         // Add the Consultation object to the assignedDoctorList.
         assignedDoctorList.add(doctorAvailability);

         //Pop-up to show booking details
         UIManager.put("OptionPane.minimumSize", new Dimension(350, 100));
         showMessageDialog(null, "Booking Details \n\n" +
                 assignedDoctorList.get(assignedDoctorList.size() - 1).showBookingDetails());

      } else {
         // Doctor is not available, generate a random index from the list of doctor names and try to find a doctor
         ArrayList<Doctor> doctorNamesList = new ArrayList<>(Arrays.asList(doctorNames));
         Random random = new Random();

         while (doctorNamesList.size() > 0) {
            int index = random.nextInt(doctorNamesList.size());
            Doctor randomDoctor = doctorNamesList.get(index);
            String randomMedicalLicenseNumber = randomDoctor.getMedicalLicenseNumber();
            String randomDoctorName = randomDoctor.getFullName();

            // Available doctor found, assign the doctor to the user
            if (isDoctorAvailable(randomDoctorName, randomMedicalLicenseNumber, selectedDate, selectedStartTime,
                    selectedEndTime)) {

               // Store the details of the randomly assigned doctor (name and medical license number), consultation date,
               // start time and end time in a Consultation object.
               doctorAvailability.setDoctorAvailability(randomDoctorName, randomMedicalLicenseNumber, selectedDate,
                       selectedStartTime, selectedEndTime);

               // Add the Consultation object to the assignedDoctorList.
               assignedDoctorList.add(doctorAvailability);

               UIManager.put("OptionPane.minimumSize", new Dimension(350, 200));
               showMessageDialog(null, "Sorry, Dr. " + doctor + " is not available on the " +
                       "selected date and time slot \nDr. " + randomDoctor + " has been assigned instead.\n " +
                       "Please see the updated Booking Details below \n\n" +
                       assignedDoctorList.get(assignedDoctorList.size() - 1).showBookingDetails());
               break;
            }
            // Remove the doctor from the list to avoid selecting them again
            doctorNamesList.remove(index);
         }

         // If no doctor is available, show error message
         if (doctorNamesList.size() == 0) {
            showMessageDialog(null, "Sorry, no doctors are available on " +
                    selectedDate + " from " + selectedStartTime + " to " + selectedEndTime + ". " +
                    "\nPlease try booking a different time slot.");
            new BookingConsultation();
            dispose();
            doctorAssigned = false;
         }
      }
      return doctorAssigned;
   }

   /**
    * Method to check if the selected doctor is available at the given time slot.
    *
    * @param doctorName            Name of the doctor.
    * @param medicalLicenseNumber  Medical license number of the doctor.
    * @param date                  Date of the consultation.
    * @param startTime             Start time of the consultation.
    * @param endTime               End time of the consultation.
    *
    * @return true if the doctor is available, false otherwise.
    */
   public boolean isDoctorAvailable(String doctorName, String medicalLicenseNumber, LocalDate date, LocalTime startTime,
                                    LocalTime endTime) {

      // boolean variable to store the availability of the doctor.
      boolean isAvailable = true;

      // Check if the doctor is available at the given time slot.
      for (Consultation consultation : assignedDoctorList) {

         // Checking if doctor name, medical license number and date already exists in the assignedDoctorList.
         if (consultation.getDoctorName().equals(doctorName) && consultation.getConsultationDate().equals(date) &&
                 consultation.getDoctorLicenseNumber().equals(medicalLicenseNumber)) {

            // Checking if the entered time slot of the new consultation is between the start and end time of the current
            // consultations of the selected doctor.
            if (consultation.getStartTime().isBefore(endTime) && consultation.getEndTime().isAfter(startTime)) {
               isAvailable = false;
               break;
            }
         }
      }
      return isAvailable;
   }
}
