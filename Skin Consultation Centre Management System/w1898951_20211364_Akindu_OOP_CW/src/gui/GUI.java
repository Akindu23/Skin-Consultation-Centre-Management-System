package gui;

import console.WestminsterSkinConsultationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * COPYRIGHT (C) 2023 Akindu Karunaratne (w1898951/20211364). All Rights Reserved.
 * Graphical User Interface (GUI) implementation of a Skin Consultation Centre.
 * Object-Oriented Programming (5COSC019.1) Coursework 1.
 *
 * @author Akindu Karunaratne
 * @version 1.0 2023-01-07.
 **/
public class GUI extends JFrame {
   //instance variables
   private final JButton viewDoctorsButton;
   private final JButton addConsultationButton;
   private final JButton viewConsultationsButton;
   private final JButton exitButton;

   // Constructor for GUI Home Page. Contains buttons to navigate to other pages.
   public GUI() {
      // CreateComponents object to create the common components for GUI
      CreateComponents createComponents = new CreateComponents();

      // Create label for the background image
      JLabel background = new JLabel(new ImageIcon("images\\background.jpg"));
      background.setBounds(0, 0, 800, 600);

      // Create label for the main title banner
      JLabel banner = new JLabel(new ImageIcon("images\\wscm.png"));
      banner.setBounds(200, 0, 400, 175);

      //Creating the buttons using createComponents object
      viewDoctorsButton = createComponents.createButton("View All Doctors", 300,220, 200, 50,
              "#92FE9D");
      this.add(viewDoctorsButton);

      addConsultationButton = createComponents.createButton("Add Consultation", 300,300, 200, 50,
              "#92FE9D");
      this.add(addConsultationButton);

      viewConsultationsButton = createComponents.createButton("View Consultations",300, 380, 200,
              50, "#92FE9D");
      this.add(viewConsultationsButton);

      exitButton = createComponents.createButton("Exit", 300,460, 200, 50, "#ffa252");
      this.add(exitButton);

      //Creates GUIHandler object to add action listeners to buttons
      GUIHandler handler = new GUIHandler();
      viewDoctorsButton.addActionListener(handler);
      addConsultationButton.addActionListener(handler);
      viewConsultationsButton.addActionListener(handler);
      exitButton.addActionListener(handler);

      // Add the components to the frame and set the frame properties
      this.setTitle("Westminster Skin Consultation Manager");
      this.setLayout(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
      this.setSize(800, 600);
      this.setLocationRelativeTo(null); //Used to set the location of the frame to the centre of the screen.
      this.add(banner);
      this.add(background);
      this.setResizable(false);
   }

   // Inner Class GUIHandler to handle the events of the buttons in the GUI Home Page.
   private class GUIHandler implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         if (event.getSource() == viewDoctorsButton) {

            // Checks if there are doctors in the system.
            if (WestminsterSkinConsultationManager.getDoctorList().isEmpty()) {

               //Displays a message if there are no doctors in the system.
               JOptionPane.showMessageDialog(null, "There are no doctors available at the moment.");

            } else {
               //If there are doctors in the system, calls the DoctorTable class to display the doctors.
               new DoctorTable();
               dispose(); //Disposes the current frame.
            }
         } else if (event.getSource() == addConsultationButton) {
            if (WestminsterSkinConsultationManager.getDoctorList().isEmpty()) {
               JOptionPane.showMessageDialog(null, "There are no doctors available at the moment.");
            } else {
               //If there are doctors in the system, calls the BookingConsultation class which has the GUI Frame
               // to book a consultation with a doctor.
               new BookingConsultation();
               dispose();
            }
         }else if (event.getSource() == viewConsultationsButton) {
            if (AddConsultation.getConsultationList().isEmpty()) {
               JOptionPane.showMessageDialog(null, "There are no consultations available at the moment.");
            } else {
               //If there are consultations in the system, calls the ViewConsultations class to display the consultations.
               new ViewConsultations();
               dispose();
            }
         } else if (event.getSource() == exitButton) {

            //Returns to the console menu.
            dispose();
         }
      }
   }
}
