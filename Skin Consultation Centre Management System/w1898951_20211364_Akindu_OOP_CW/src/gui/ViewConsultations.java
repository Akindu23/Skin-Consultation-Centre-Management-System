package gui;

import console.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class ViewConsultations extends JFrame {
   // instance variables
   private final JButton backButton;
   private JLabel patientIDInfoLabel;
   private JLabel patientInfoLabel;
   private JLabel patientNationalIDInfoLabel;
   private JLabel patientMobileNumberInfoLabel;
   private JLabel patientDateOfBirthInfoLabel;
   private JPanel consultationDetails;
   private JPanel patientDetails;
   private JLabel consultationIDInfoLabel;
   private JLabel doctorInfoLabel;
   private JLabel costInfoLabel;
   private JLabel dateInfoLabel;
   private JLabel startTimeInfoLabel;
   private JLabel endTimeInfoLabel;
   private JLabel notesInfoLabel;
   private JLabel imageInfoLabel;
   private final JLabel infoLabel;
   private JLabel noImageLabel;
   private JComboBox<String> consultationIdComboBox;
   private final JButton selectButton;
   private final CreateComponents createComponents;

   // Constructor for View Consultations Page.
   public ViewConsultations() {
      // CreateComponents object to create the common components for GUI
      createComponents = new CreateComponents();

      // Create label for the background image
      JLabel background = new JLabel(new ImageIcon("images\\background.jpg"));
      background.setBounds(0, 0, 1350, 800);

      // Create label for the main title banner
      JLabel header = new JLabel();
      header.setIcon(new ImageIcon("images\\history.png"));
      header.setBounds(375, 0, 600, 150);

      // Create label for logo
      JLabel logo = createComponents.createLogo(1250);

      // Calling methods that add patient and consultation details panels to the frame.
      patientDetailsPanel();
      consultationDetailsPanel();

      // Create the back button using createComponents object
      backButton = createComponents.createBackButton();
      this.add(backButton);

      // Display all the available consultations in the combo box
      displayConsultationIDList();

      // Create the select button using createComponents object
      selectButton = createComponents.createButton("Select", 850, 175, 100, 30,
              "#92FE9D");
      this.add(selectButton);

      // Create label to display the information
      infoLabel = new JLabel();
      infoLabel.setIcon(new ImageIcon("images\\info.png"));
      infoLabel.setBounds(400,225,500,200);

      // Create DisplayConsultationHandler object to add action listeners to buttons.
      DisplayConsultationsHandler handler = new DisplayConsultationsHandler();
      backButton.addActionListener(handler);
      selectButton.addActionListener(handler);

      // Add the components to the frame and set the frame properties
      this.setTitle("View Consultations");
      this.add(logo);
      this.add(header);
      this.add(infoLabel);
      this.add(background);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setLayout(null);
      this.setBounds(50, 20, 1350, 500);
      this.setVisible(true);
      this.setResizable(false);
   }

   // Inner class DisplayConsultationsHandler to handle the events of the buttons
   private class DisplayConsultationsHandler implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         if (event.getSource() == backButton) {
            new GUI();
            dispose();
         } else if (event.getSource() == selectButton){
            displayConsultationDetails();
         }
      }
   }

   /**
    * Method to add the text fields and labels to the patient details panel.
    */
   public void patientDetailsPanel () {
      // Create the patient details panel using createComponents object
      patientDetails = createComponents.createDetailsPanel(50, 250, 500, 300,
              "Patient Details");
      this.add(patientDetails);

      // Create the labels and text fields for the patient details using createComponents object
      JLabel patientIDLabel = createComponents.createLabel("Patient ID ", 50, 50, 200, 30);
      patientDetails.add(patientIDLabel);

      patientIDInfoLabel = createComponents.createLabel("", 250, 50, 300, 30);
      patientDetails.add(patientIDInfoLabel);

      JLabel patientNameLabel = createComponents.createLabel("Patient Name ", 50, 100, 200,
              30);
      patientDetails.add(patientNameLabel);

      patientInfoLabel = createComponents.createLabel("", 250, 100, 300, 30);
      patientDetails.add(patientInfoLabel);

      JLabel patientNationalIDLabel = createComponents.createLabel("National ID ", 50, 150, 200,
              30);
      patientDetails.add(patientNationalIDLabel);

      patientNationalIDInfoLabel = createComponents.createLabel("", 250, 150, 300, 30);
      patientDetails.add(patientNationalIDInfoLabel);

      JLabel patientMobileNumberLabel = createComponents.createLabel("Mobile Number ", 50, 200, 200,
              30);
      patientDetails.add(patientMobileNumberLabel);

      patientMobileNumberInfoLabel = createComponents.createLabel("", 250, 200, 300, 30);
      patientDetails.add(patientMobileNumberInfoLabel);

      JLabel patientDateOfBirthLabel = createComponents.createLabel("Date of Birth ", 50, 250, 200,
              30);
      patientDetails.add(patientDateOfBirthLabel);

      patientDateOfBirthInfoLabel = createComponents.createLabel("", 250, 250, 300, 30);
      patientDetails.add(patientDateOfBirthInfoLabel);
   }

   /**
    * Method to add the text fields and labels to the consultation details panel.
    */
   public void consultationDetailsPanel() {
      // Create the consultation details panel using createComponents object
      consultationDetails = createComponents.createDetailsPanel(650, 250, 650, 510,
              "Consultation Details");
      this.add(consultationDetails);

      // Create the labels and text fields for the consultation details using createComponents object
      JLabel consultationIDLabel = createComponents.createLabel("Consultation ID", 150, 50, 400,
              30);
      consultationDetails.add(consultationIDLabel);

      consultationIDInfoLabel = createComponents.createLabel("", 350, 50, 400, 30);
      consultationDetails.add(consultationIDInfoLabel);

      JLabel doctorLabel = createComponents.createLabel("Doctor", 150, 100, 400, 30);
      consultationDetails.add(doctorLabel);

      doctorInfoLabel = createComponents.createLabel("", 350, 100, 400, 30);
      consultationDetails.add(doctorInfoLabel);

      JLabel costLabel = createComponents.createLabel("Cost", 150, 150, 400, 30);
      consultationDetails.add(costLabel);

      costInfoLabel = createComponents.createLabel("", 350, 150, 400, 30);
      consultationDetails.add(costInfoLabel);

      JLabel dateLabel = createComponents.createLabel("Date", 150, 200, 400, 30);
      consultationDetails.add(dateLabel);

      dateInfoLabel = createComponents.createLabel("", 350, 200, 400, 30);
      consultationDetails.add(dateInfoLabel);

      JLabel startTimeLabel = createComponents.createLabel("Start Time", 150, 250, 400, 30);
      consultationDetails.add(startTimeLabel);

      startTimeInfoLabel = createComponents.createLabel("", 350, 250, 400, 30);
      consultationDetails.add(startTimeInfoLabel);

      JLabel endTimeLabel = createComponents.createLabel("End Time", 150, 300, 400, 30);
      consultationDetails.add(endTimeLabel);

      endTimeInfoLabel = createComponents.createLabel("", 350, 300, 400, 30);
      consultationDetails.add(endTimeInfoLabel);

      JLabel notesLabel = createComponents.createLabel("Notes", 150, 350, 400, 30);
      consultationDetails.add(notesLabel);

      notesInfoLabel = createComponents.createLabel("", 350, 350, 400, 30);
      consultationDetails.add(notesInfoLabel);

      JLabel imagesLabel = createComponents.createLabel("Images", 150, 400, 400, 30);
      consultationDetails.add(imagesLabel);

      imageInfoLabel = createComponents.createLabel("", 350, 400, 100, 100);
      consultationDetails.add(imageInfoLabel);
   }

   /**
    * Method to display the available consultations in a ComboBox.
    */
   public void displayConsultationIDList() {
      JLabel infoLabel = createComponents.createLabel("Please Select a Consultation ID", 400, 175,
              400, 30);
      this.add(infoLabel);

      consultationIdComboBox = new JComboBox<>();

      for (int i = 0; i < AddConsultation.getConsultationList().size(); i++) {
            consultationIdComboBox.addItem(AddConsultation.getConsultationList().get(i).getConsultationID());
      }
      consultationIdComboBox.setFocusable(false);
      consultationIdComboBox.setBounds(670, 175, 125, 30);
      this.add(consultationIdComboBox);

   }

   /**
    * Method to display the details of the selected consultation.
    * Once the user selects a consultation ID from the ComboBox, the information label is hidden and the
    * patient details and consultation details panels are displayed.
    */
   public void displayConsultationDetails() {
      infoLabel.setVisible(false);
      consultationDetails.setVisible(true);
      patientDetails.setVisible(true);
      this.setBounds(50, 20, 1350, 820);

      // Get the selected consultation ID from the ComboBox
      String consultationID = (String) consultationIdComboBox.getSelectedItem();

      // Getting consultation and patient objects based on the selected consultation ID
      Consultation consultation = getConsultationById(consultationID);
      Patient patient = getPatientByConsultationId(consultationID);

      // Displaying the patient details and consultation details in the labels
      displayPatientDetails(patient);
      displayConsultationDetails(consultation);

      // Decrypting the notes and uploaded image and displaying them in the labels
      decrypt(consultationID, notesInfoLabel, imageInfoLabel);
   }

   /**
    * Method to get the consultation object based on the selected consultation ID.
    * @param consultationId The selected consultation ID
    * @return The consultation object for the selected consultation ID
    */
   private Consultation getConsultationById(String consultationId) {
      for (Consultation consultation : AddConsultation.getConsultationList()) {
         if (consultationId.equals(consultation.getConsultationID())) {
            return consultation;
         }
      }
      return null;
   }

   /**
    * Method to get the patient object based on the selected consultation ID.
    * @param consultationId The selected consultation ID
    * @return The patient object for the selected consultation ID
    */
   private Patient getPatientByConsultationId(String consultationId) {
      Consultation consultation = getConsultationById(consultationId);
      return consultation.getPatient();
   }

   /**
    * Method to display the patient details in the labels.
    * @param patient The patient object for the selected consultation ID
    */
   private void displayPatientDetails(Patient patient) {
      patientIDInfoLabel.setText(patient.getPatientID());
      patientInfoLabel.setText(patient.getFullName());
      patientNationalIDInfoLabel.setText(patient.getNationalID());
      patientMobileNumberInfoLabel.setText(patient.getMobileNumber());
      patientDateOfBirthInfoLabel.setText(String.valueOf(patient.getDateOfBirth()));
   }

   /**
    * Method to display the consultation details in the labels.
    * @param consultation The consultation object for the selected consultation ID
    */
   private void displayConsultationDetails(Consultation consultation) {
      // Storing the consultation details in variables
      String doctorName = consultation.getDoctorName();
      String medicalLicenseNumber = consultation.getDoctorLicenseNumber();
      String cost = String.format("%.2f", consultation.getCost());
      LocalDate consultationDate = consultation.getConsultationDate();
      LocalTime startTime = consultation.getStartTime();
      LocalTime endTime = consultation.getEndTime();

      // Displaying the consultation details in the labels
      consultationIDInfoLabel.setText(consultation.getConsultationID());
      doctorInfoLabel.setText(doctorName + " - " + medicalLicenseNumber);
      costInfoLabel.setText("£" + cost);
      dateInfoLabel.setText(String.valueOf(consultationDate));
      startTimeInfoLabel.setText(String.valueOf(startTime));
      endTimeInfoLabel.setText(String.valueOf(endTime));
   }

   /**
    * Method to decrypt the notes and uploaded image and display them in the labels.
    * The code for this method was referred from:
    * <a href = "https://www.baeldung.com/java-aes-encryption-decryption">Encrypting and Decrypting in Java</a>
    * <br><a href = "https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html">Java Documentation</a>
    *
    * @param consultationId The selected consultation ID
    * @param notesLabel The label to display the decrypted notes
    * @param imageLabel The label to display the decrypted image
    */
   public void decrypt(String consultationId, JLabel notesLabel, JLabel imageLabel) {
      try {
         // Getting the paths for the encrypted notes and image and the secret key
         String pathName = "./Encrypted Patient Data/" + consultationId;
         Path notesPath = Paths.get(pathName + "/notes.txt");
         Path imagePath = Paths.get(pathName + "/image.png");
         Path keyPath = Paths.get(pathName + "/key.aes");

         // Decrypting the secret key and making an exact copy of it
         byte[] key = Files.readAllBytes(keyPath);
         SecretKey secretKey = new SecretKeySpec(key, 0, key.length, "AES");

         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(Cipher.DECRYPT_MODE, secretKey);

         // If the notes file exists, decrypt the notes and display them in the label
         if (Files.exists(notesPath)) {
            byte[] encryptedNotes = Files.readAllBytes(notesPath);
            byte[] decryptedNotes = cipher.doFinal(encryptedNotes);
            String notes = new String(decryptedNotes);
            notesLabel.setText(notes);
            notesLabel.setForeground(Color.BLACK);
         } else {
            notesLabel.setText("No notes added");
            notesLabel.setForeground(Color.RED);
         }

         // If the image file exists, decrypt the image and display it in the label
         if (Files.exists(imagePath)) {
            byte[] encryptedImage = Files.readAllBytes(imagePath);
            byte[] decryptedImage = cipher.doFinal(encryptedImage);

            ImageIcon imageIcon = new ImageIcon(decryptedImage);
            Image uploadedImage = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(),
                    imageLabel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(uploadedImage));
         } else {
            noImageLabel = createComponents.createLabel("No image uploaded", 350, 400,
                    400, 30);
            noImageLabel.setForeground(Color.RED);
            consultationDetails.add(noImageLabel);
         }

      } catch (Exception e) {
         System.out.println(e);
      }
   }
}

