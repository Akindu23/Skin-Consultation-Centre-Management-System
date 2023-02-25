package gui;

import console.*;
import gui.CreateComponents.PTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.crypto.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class AddConsultation extends JFrame implements Serializable {
   //instance variables
   private static int nextPatientId = 1;
   private static int nextConsultationId = 1;
   private static ArrayList<Patient> patientList = new ArrayList<>();
   private static ArrayList<Consultation> consultationList = new ArrayList<>();
   private static ArrayList<Consultation> assignedDoctorsList = new ArrayList<>();
   private final JButton backButton;
   private final PTextField name;
   private final PTextField surname;
   private final PTextField dateOfBirth;
   private final PTextField mobileNumber;
   private final PTextField nationalID;
   private final JTextArea notes;
   private final JLabel imageLabel;
   private final JButton uploadImage;
   private String filePath;
   private final JButton addConsultation;
   private final JButton resetButton;
   private final int lastDoctorIndex = BookingConsultation.getAssignedDoctorList().size() - 1;

   // Constructor for AddConsultation class. Contains the frame components for adding patient details and
   // consultation notes to a consultation.
   public AddConsultation() {
      // CreateComponents object to create the common components for GUI
      CreateComponents createComponents = new CreateComponents();

      // Create label for the background image
      JLabel background = new JLabel(new ImageIcon("images\\background.jpg"));
      background.setBounds(0, 0, 800, 800);

      // Create label for the main title banner
      JLabel header = new JLabel(new ImageIcon("images\\patientheader.png"));
      header.setBounds(90, 0, 600, 250);

      // Create label for the logo
      JLabel logo = createComponents.createLogo(700);

      // Creating the back button using createComponents object
      backButton = createComponents.createBackButton();
      this.add(backButton);

      // Creating the labels and text fields using createComponents object
      JLabel nameLabel = createComponents.createLabel("Enter your Name", 125, 275, 400, 30);
      this.add(nameLabel);

      name = createComponents.createTextField("First Name", 400, 275, 300, 30);
      this.add(name);

      JLabel surnameLabel = createComponents.createLabel("Enter your Surname", 125, 325, 400,
              30);
      this.add(surnameLabel);

      surname = createComponents.createTextField("Surname", 400, 325, 300, 30);
      this.add(surname);

      JLabel nationalIDLabel = createComponents.createLabel("Enter your National ID", 125, 375, 400,
              30);
      this.add(nationalIDLabel);

      nationalID = createComponents.createTextField("National ID (12 Digits)", 400, 375, 300,
              30);
      this.add(nationalID);

      JLabel dateOfBirthLabel = createComponents.createLabel("Enter your Date of Birth", 125, 425,
              400, 30);
      this.add(dateOfBirthLabel);

      dateOfBirth = createComponents.createTextField("YYYY-MM-DD", 400, 425, 300, 30);
      this.add(dateOfBirth);

      JLabel mobileNumberLabel = createComponents.createLabel("Enter your Mobile Number", 125, 475,
              400, 30);
      this.add(mobileNumberLabel);

      mobileNumber = createComponents.createTextField("Mobile Number (10 Digits)", 400, 475, 300,
              30);
      this.add(mobileNumber);

      JLabel noteLabel = createComponents.createLabel("Notes", 125, 525, 400, 30);
      this.add(noteLabel);

      // Create text area for the notes
      notes = new JTextArea();
      notes.setBounds(400, 525, 300, 50);
      notes.setBorder(new EmptyBorder(3, 3, 3, 3));
      notes.setFont(new Font("Dubai", Font.PLAIN, 16));
      this.add(notes);

      JLabel imageLabelText = createComponents.createLabel("Add an image (Maximum 1)", 125, 600,
              400, 30);
      this.add(imageLabelText);

      imageLabel = createComponents.createLabel("", 400, 600, 100, 100);
      this.add(imageLabel);

      // Create button to upload an image
      uploadImage = createComponents.createButton("Upload Image", 130, 630, 150, 30,
              "#ffa252");
      this.add(uploadImage);

      // Create button to reset the text fields
      resetButton = createComponents.createButton("Reset", 125, 700, 150, 30,
              "#ffa252");
      this.add(resetButton);

      // Create button to submit the patient details and consultation notes
      addConsultation = createComponents.createButton("Submit", 550, 700, 150, 30,
              "#92FE9D");
      this.add(addConsultation);

      // Creates ConsultationHandler object to add event listeners to the buttons.
      ConsultationHandler handler = new ConsultationHandler();
      backButton.addActionListener(handler);
      addConsultation.addActionListener(handler);
      resetButton.addActionListener(handler);
      uploadImage.addActionListener(handler);

      // Add the components to the frame and set the frame properties
      this.setTitle("Add Consultation");
      this.add(header);
      this.add(logo);
      this.add(background);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setLayout(null);
      this.setVisible(true);
      this.setSize(800, 800);
      this.setLocationRelativeTo(null);
      this.setResizable(false);
   }

   // Inner Class ConsultationHandler to handle the events for the buttons in the AddConsultation class
   private class ConsultationHandler implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         if (event.getSource() == backButton) {
            // If the back button is clicked, the selected doctor is removed from the assignedDoctorsList and the user
            // is taken back to the Booking Consultation page and this frame is disposed.

            BookingConsultation.getAssignedDoctorList().remove(lastDoctorIndex);
            new BookingConsultation();
            dispose();

         } else if (event.getSource() == uploadImage) {
            // If the upload image button is clicked, the user is prompted to select an image file from their computer.
            uploadImage();

         } else if (event.getSource() == resetButton) {
            // If the reset button is clicked, the text fields are reset to their default values.
            resetDetails();

         } else if (event.getSource() == addConsultation) {
            // If the add consultation button is clicked, the patient details are validated and added to the patientList.
            // and consultationList. The notes are added to the consultationList as well.
            // User is taken back to the GUI Home page.

            if (validateInput()) {
               confirmConsultation();
               new GUI();
               dispose();
            }
         }
      }
   }

   // getter for the consultationList
   public static ArrayList<Consultation> getConsultationList() {
      return consultationList;
   }

   /**
    * This method is used to get the details of the patient from the text fields and add them to the patientList.
    * A patient object is created to store the details of the patient.
    * The patient object is then added to the consultationList which has the consultation details taken from the
    * BookingConsultation class.
    */
   public void confirmConsultation() {
      // Creating Patient Object and Consultation Object to store the details of the patient and consultation.
      Patient patientObj = new Patient();
      Consultation consultationObj = new Consultation();

      // Getting the details of the doctor and selected date and time for the consultation from the BookingConsultation
      // class.
      // Getting the details of the patient from the text fields.
      String doctorName = BookingConsultation.getAssignedDoctorList().get(lastDoctorIndex).getDoctorName();
      String medicalLicense = BookingConsultation.getAssignedDoctorList().get(lastDoctorIndex).getDoctorLicenseNumber();
      LocalDate selectedDate = LocalDate.parse(BookingConsultation.getDate().getText());
      LocalTime selectedStartTime = LocalTime.parse(BookingConsultation.getStartTime().getText(), BookingConsultation.getFormatter());
      LocalTime selectedEndTime = LocalTime.parse(BookingConsultation.getEndTime().getText(), BookingConsultation.getFormatter());
      String patientFirstName = name.getText();
      String patientSurname = surname.getText();
      String nationalId = nationalID.getText();
      String patientMobileNumber = mobileNumber.getText();
      String patientDateOfBirth = dateOfBirth.getText();
      String patientNotes = notes.getText();
      String patientId = patientIdGenerator(nationalId);
      String consultationId = consultationIdGenerator();
      double patientCost = costCalculator(patientId);

      // Encrypting the notes and image added by the user.
      encryptData(consultationId);

      // Setting the details of the patient to the patient object.
      patientObj.setPatientDetails(patientId, nationalId, patientFirstName, patientSurname,
              LocalDate.parse(patientDateOfBirth), patientMobileNumber);

      // Adding the patient object to the patientList if the patient is not already in the list.
      if (patientList.isEmpty()) {
         patientList.add(patientObj);
      } else {
         for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getNationalID().equals(nationalId)) {
               break;
            } else if (i == patientList.size() - 1) {
               patientList.add(patientObj);
            }
         }
      }

      // Setting the details of the consultation to the consultation object.
      consultationObj.setConsultationDetails(consultationId, patientObj, doctorName, medicalLicense, selectedDate,
              selectedStartTime, selectedEndTime, patientCost, patientNotes);

      // Adding the consultation object to the consultationList.
      consultationList.add(consultationObj);

      // Copying the assigned doctors from the BookingConsultation class to the assignedDoctorsList.
      assignedDoctorsList.addAll(BookingConsultation.getAssignedDoctorList());

      // Saving the data to the files.
      saveData();

      // Pop up message to confirm the consultation which shows the details of the consultation.
      UIManager.put("OptionPane.minimumSize", new Dimension(275, 200));
      showMessageDialog(null, "Consultation Successfully Added!\n\n" +
              "Details of Consultation: \n\n" + consultationList.get(consultationList.size() - 1));
   }

   /**
    * This method is used to validate the input of the patient details.
    * @return boolean value to check if the input is valid. If the input is valid, the method returns true.
    */
   public boolean validateInput() {
      // Store the booking details of the consultation in variables.
      LocalDate selectedDate = LocalDate.parse(BookingConsultation.getDate().getText());
      LocalTime selectedStartTime = LocalTime.parse(BookingConsultation.getStartTime().getText(), BookingConsultation.getFormatter());
      LocalTime selectedEndTime = LocalTime.parse(BookingConsultation.getEndTime().getText(), BookingConsultation.getFormatter());

      // boolean variable to check if the input is valid.
      boolean valid = true;
      // StringBuilder used to store the error messages.
      StringBuilder errorMessage = new StringBuilder();

      // Checking if the name field is empty or only contains letters.
      if (!name.getText().matches("[a-zA-Z]+") || name.getText().isEmpty()) {
         errorMessage.append("First name is empty or in an incorrect format! \n");
         valid = false;
      }

      // Checking if the surname field is empty or only contains letters.
      if (!surname.getText().matches("[a-zA-Z]+") || surname.getText().isEmpty()) {
         errorMessage.append("Surname is empty or in an incorrect format! \n");
         valid = false;
      }

      // Checking if the national ID field is empty or only contains numbers and is exactly 12 digits.
      if (!nationalID.getText().matches("\\d{12}") || nationalID.getText().isEmpty()) {
         errorMessage.append("National ID number can not be empty and should contain exactly 12 digits! \n");
         valid = false;
      }

      // try-catch block to check if the date of birth is in the correct format (YYYY-MM-DD).
      try {
         LocalDate enteredDateOfBirth = LocalDate.parse(dateOfBirth.getText());
         if (enteredDateOfBirth.isBefore(LocalDate.parse("1920-01-01")) ||
                 enteredDateOfBirth.isAfter(LocalDate.now())) {
            errorMessage.append("Date of Birth can not be before 1920-01-01 and can not be today or after today.\n");
            valid = false;
         }
      } catch (DateTimeParseException e) {
         errorMessage.append("Please enter your date of birth in the format YYYY-MM-DD.\n");
         valid = false;
      }

      // Checking if the mobile number field is empty or only contains numbers and is exactly 10 digits.
      if (!mobileNumber.getText().matches("\\d{10}") || mobileNumber.getText().isEmpty()) {
         errorMessage.append("Mobile number can not be empty and should contain exactly 10 digits! \n");
         valid = false;
      }

      // Checking if patient has already booked a consultation on the same date and time.
      for (Consultation consultation : consultationList) {
         if (consultation.getPatient().getNationalID().equals(nationalID.getText())) {
            if (consultation.getConsultationDate().equals(selectedDate)) {
               if (consultation.getStartTime().isBefore(selectedEndTime) &&
                       consultation.getEndTime().isAfter(selectedStartTime))
               {
                  errorMessage.append("""
                          You already have a consultation in this time slot!
                           Please enter another time for the consultation!
                          """);
                  valid = false;
                  BookingConsultation.getAssignedDoctorList().remove(lastDoctorIndex);
                  new BookingConsultation();
                  dispose();
               }
            }
         }
      }

      // If the input is not valid, a pop-up message will be displayed with ALL the error messages.
      if (!valid) {
         showMessageDialog(null, errorMessage.toString(), "Error", ERROR_MESSAGE);
      }
      return valid;
   }

   /**
    * This method is used to calculate the cost of the consultation.
    * If it is a new patient, the cost is £15 per hour.
    * If it is an existing patient, the cost is £25 per hour.
    *
    * @param patientId The patient ID of the patient.
    * @return The cost rounded to 2 decimal places.
    */

   public double costCalculator(String patientId) {
      // Calculate the duration of the consultation in minutes.
      LocalTime startTime = LocalTime.parse(BookingConsultation.getStartTime().getText());
      LocalTime endTime = LocalTime.parse(BookingConsultation.getEndTime().getText());
      Duration duration = Duration.between(startTime, endTime);
      double minutes = duration.toMinutes();

      // Cost of the consultation is set to £15 per hour.
      double costPerHour = 15.0;

      // If the patient is an existing patient, the cost is set to £25 per hour.
      for (Consultation consultation : consultationList) {
         String id = consultation.getPatient().getPatientID();
         if (id.equals(patientId)) {
            costPerHour = 25.0;
            break;
         }
      }
      // Calculate the cost of the consultation based on the duration and cost per hour.
      double cost = (minutes / 60) * costPerHour;
      return Math.round(cost * 100.00) / 100.00;
   }

   /**
    * This method is used to generate a patient ID for the user.
    * @param nationalId The national ID of the patient.
    * @return The patient ID.
    */
   public String patientIdGenerator(String nationalId) {
      // If the patient is an existing patient, assign the same patient ID.
      for (Patient patient : patientList) {
         if (patient.getNationalID().equals(nationalId)) {
            return patient.getPatientID();
         }
      }

      // If the patient is a new patient, generate a new patient ID. String.format is used to add leading zeros.
      String id = "P" + String.format("%03d", nextPatientId);
      nextPatientId++;
      return id;
   }

   /**
    * This method is used to generate a consultation ID for the user.
    * String.format is used to add leading zeros to the consultation ID.
    *
    * @return The consultation ID.
    */
   public String consultationIdGenerator() {
      String id = "C" + String.format("%03d", nextConsultationId);
      nextConsultationId++;
      return id;
   }

   /**
    * This method is used to allow users to upload an image as additional information.
    * JFileChooser is used to allow the user to select an image from their computer.
    * The file path is then stored in the filePath variable.
    * The selected image is then displayed in the imageLabel.
    * The code for this method was referred from:
    * <a href = "https://youtu.be/YZ_tQFTMYoQ">Using JFileChooser to upload an image</a>
    */
   public void uploadImage() {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File("."));
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "jpeg");
      fileChooser.setFileFilter(filter);
      int result = fileChooser.showOpenDialog(null);

      if (result == JFileChooser.APPROVE_OPTION) {
         File selectedImage = fileChooser.getSelectedFile();
         filePath = selectedImage.getAbsolutePath();
         ImageIcon imageIcon = new ImageIcon(filePath);
         imageIcon.setDescription(filePath);
         Image selectedImageIcon = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(),
                 imageLabel.getHeight(), Image.SCALE_SMOOTH);
         imageLabel.setIcon(new ImageIcon(selectedImageIcon));
      }
   }

   /**
    * This method is used to reset the text fields and image label.
    */
   public void resetDetails() {
      name.setText("");
      surname.setText("");
      nationalID.setText("");
      dateOfBirth.setText("");
      mobileNumber.setText("");
      notes.setText("");
      imageLabel.setIcon(null);
   }

   /**
    * This method is used to encrypt the patient's notes and uploaded image.
    * The encrypted notes and image is stored in a folder inside the "Encrypted Patient Details" folder in the project
    * directory.
    * The folder name is the Consultation ID and the notes is encrypted as "notes.txt", image as "image.png" and the
    * secret key for each consultation is stored in "key.aes".
    * The code for this method was referred from:
    * <a href = "https://www.baeldung.com/java-aes-encryption-decryption">Encrypting and Decrypting a String in Java</a>
    * <br><a href = "https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html">Java Documentation</a>
    *
    * @param consultationId The consultation ID of the consultation.
    */
   public void encryptData(String consultationId) {
      try {
         // Generate a secret key for the consultation.
         KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(128);
         SecretKey secretKey = keyGenerator.generateKey();

         // Cipher is used to encrypt the notes and image.
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);

         // Checks if there are any notes to encrypt.
         if (!notes.getText().isEmpty()) {
            // Encrypt the notes.
            byte[] notesBytes = notes.getText().getBytes();
            byte[] encryptedNotes = cipher.doFinal(notesBytes);
            String pathName = "./Encrypted Patient Data/" + consultationId;
            File directory = new File(pathName);
            directory.mkdir();

            // Directory is created to store the encrypted notes
            FileOutputStream fosNotes = new FileOutputStream(directory.getPath() + "/notes.txt");
            fosNotes.write(encryptedNotes);
            fosNotes.close();
         }

         // Checks if there is an image to encrypt.
         if (filePath != null && !filePath.isEmpty()) {
            // Reads the image file path and encrypts the image.
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            byte[] encryptedImage = cipher.doFinal(imageBytes);
            String pathName = "./Encrypted Patient Data/" + consultationId;
            File directory = new File(pathName);
            directory.mkdir();

            // Directory is created to store the encrypted image.
            FileOutputStream fosImages = new FileOutputStream(directory.getPath() + "/image.png");
            fosImages.write(encryptedImage);
            fosImages.close();
         }

         // Writes the secret key to a file.
         String pathName = "./Encrypted Patient Data/" + consultationId;
         File directory = new File(pathName);
         directory.mkdir();
         FileOutputStream fosKey = new FileOutputStream(directory.getPath() + "/key.aes");
         fosKey.write(secretKey.getEncoded());
         fosKey.close();

      } catch (Exception e) {
         System.out.println(e);
      }
   }

   /**
    * Method used to save the consultation details to a file when submitting the patient details.
    */
   public void saveData() {
      try (FileOutputStream fileOut = new FileOutputStream("src/gui/consultation_data.txt");
           ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
         out.writeObject(patientList);
         out.writeObject(consultationList);
         out.writeObject(assignedDoctorsList);
      } catch (IOException e) {
         System.out.println(e);
      }

      try (FileWriter fileWriter = new FileWriter("src/gui/ID_data.txt")) {
         fileWriter.write(nextPatientId + "\n");
         fileWriter.write(String.valueOf(nextConsultationId));
      } catch (IOException e) {
         System.out.println(e);
      }
   }

   /**
    * This method is used to load the consultation details from a file when the GUI is launched from the console menu.
    */
   public static void loadData() {
      try (FileInputStream fileIn = new FileInputStream("src/gui/consultation_data.txt");
           ObjectInputStream in = new ObjectInputStream(fileIn)) {
         patientList = (ArrayList<Patient>) in.readObject();
         consultationList = (ArrayList<Consultation>) in.readObject();
         assignedDoctorsList = (ArrayList<Consultation>) in.readObject();
         BookingConsultation.getAssignedDoctorList().addAll(assignedDoctorsList);
      } catch (IOException | ClassNotFoundException e) {
         System.out.println(e);
         return;
      }

      File nextPatientIdFile = new File("src/gui/ID_data.txt");
      if (nextPatientIdFile.exists()) {
         try (Scanner scanner = new Scanner(nextPatientIdFile)) {
            nextPatientId = Integer.parseInt(scanner.nextLine());
            nextConsultationId = Integer.parseInt(scanner.nextLine());
         } catch (FileNotFoundException e) {
            System.out.println(e);
         }
      }
   }
}
