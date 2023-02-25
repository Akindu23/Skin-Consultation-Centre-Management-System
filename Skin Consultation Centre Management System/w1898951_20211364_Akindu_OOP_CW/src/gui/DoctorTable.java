package gui;

import console.WestminsterSkinConsultationManager;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorTable extends JFrame {
   private JTable doctorTable;
   private final JButton backButton;
   private final JButton sortByFirstNameBtn;
   private final JButton sortBySurnameBtn;
   private final JButton defaultTableBtn;
   private final JButton consultDoctorBtn;

   public DoctorTable() {
      // CreateComponents object to create the common components for GUI
      CreateComponents createComponents = new CreateComponents();

      // Create label for the background image
      JLabel background = new JLabel(new ImageIcon("images\\background.jpg"));
      background.setBounds(0, 0, 1400, 675);

      // Create label for the main page title
      JLabel header = new JLabel();
      header.setIcon(new ImageIcon("images\\doctorheader.png"));
      header.setBounds(480, 10, 450, 70);

      // Create logo for the frame
      JLabel logo = createComponents.createLogo(1300);

      //Creating the buttons using createComponents object
      backButton = createComponents.createBackButton();
      this.add(backButton);

      sortByFirstNameBtn = createComponents.doctorTableFrameButton("       Sort Doctors by First Name",
              100, 575, 300, 35, "#92FE9D", "images\\sort.png");
      this.add(sortByFirstNameBtn);

      sortBySurnameBtn = createComponents.doctorTableFrameButton("         Sort Doctors by Surname",
              450, 575, 300, 35, "#92FE9D", "images\\sort.png");
      this.add(sortBySurnameBtn);

      defaultTableBtn = createComponents.doctorTableFrameButton("        Default Table", 800, 575, 200,
              35, "#ffa252", "images\\refresh.png");
      this.add(defaultTableBtn);

      consultDoctorBtn = createComponents.doctorTableFrameButton("         Consult a Doctor", 1050, 575,
              250, 35, "#92FE9D", "images\\consult.png");
      this.add(consultDoctorBtn);

      // Create DoctorTableHandler object to add action listeners to buttons
      DoctorTable.DoctorTableHandler handler = new DoctorTable.DoctorTableHandler();
      backButton.addActionListener(handler);
      sortByFirstNameBtn.addActionListener(handler);
      sortBySurnameBtn.addActionListener(handler);
      defaultTableBtn.addActionListener(handler);
      consultDoctorBtn.addActionListener(handler);

      // Add the components to the frame and set the frame properties
      this.setTitle("Doctors in Westminster Skin Consultation Centre");
      this.add(logo);
      this.add(table());
      this.add(header);
      this.add(background);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setLayout(null);
      this.setVisible(true);
      this.setSize(1400, 675);
      this.setLocationRelativeTo(null);
      this.setResizable(false);
   }

   // Inner class DoctorTableHandler to handle the events of the buttons.
   private class DoctorTableHandler implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         if (event.getSource() == backButton) {
            new GUI();
            dispose();

         } else if (event.getSource() == sortByFirstNameBtn) {
            doctorTable.getRowSorter().toggleSortOrder(1);

         } else if (event.getSource() == sortBySurnameBtn) {
            doctorTable.getRowSorter().toggleSortOrder(2);

         } else if (event.getSource() == defaultTableBtn) {
            new DoctorTable();
            dispose();

         } else if (event.getSource() == consultDoctorBtn){
            new BookingConsultation();
            dispose();
         }
      }
   }

   /**
    * Method to create the table of doctors information.
    * Table created using custom table model.
    * Aligning the table data to the center code taken from:
    *  <a href = "https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value">Center Align Cell
    *  Data </a>
    *
    * @return JScrollPane containing the doctor information table.
    */
   private JScrollPane table(){
      //Creating an object of custom table model class
      //Populating the table with the doctors list from WestminsterSkinConsultationManager class
      DoctorTableModel model = new DoctorTableModel(WestminsterSkinConsultationManager.getDoctorList());

      //Defining the table
      doctorTable = new JTable(model);
      doctorTable.getTableHeader().setFont(new Font("Dubai", Font.BOLD,18));
      doctorTable.getTableHeader().setPreferredSize(new Dimension(70,40)); //Setting the header width & height
      doctorTable.getTableHeader().setBackground(Color.decode("#92FE9D"));
      doctorTable.getTableHeader().setReorderingAllowed(false);
      doctorTable.setFont(new Font("Dubai", Font.PLAIN,14));
      doctorTable.setRowHeight(40);  //Setting the row height
      doctorTable.setBackground(Color.decode("#A5F2F3"));
      doctorTable.setAutoCreateRowSorter(true); //Enabling the sorting of the table

      // Setting the alignment of the table cells
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

      for (int columnIndex = 0; columnIndex < doctorTable.getColumnCount(); columnIndex++)
      {
         doctorTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
      }

      JScrollPane scrollPane = new JScrollPane(doctorTable);
      scrollPane.setBounds(0, 100, 1400, 625);
      scrollPane.getViewport().setBackground(Color.decode("#ADF1FD"));
      return scrollPane;
   }
}
