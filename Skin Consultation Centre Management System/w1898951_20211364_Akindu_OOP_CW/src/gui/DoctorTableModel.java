package gui;

import console.Doctor;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Creating a custom table model for the doctor table.
 * Code adapted to suit the needs of the program from Week 07 of the lecture notes
 */
public class DoctorTableModel extends AbstractTableModel {
   private final String[] columnNames = {"Medical License Number", "First Name", "Last Name", "Specialization",
           "Mobile Number", "Date Of Birth" };
   private final ArrayList<Doctor> doctorsList;

   public DoctorTableModel(ArrayList<Doctor> listOfDoctors){
      doctorsList = listOfDoctors;
   }

   @Override
   public int getRowCount() {
      return doctorsList.size();
   }

   @Override
   public int getColumnCount() {
      return columnNames.length;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      Object temp = null;
      if(columnIndex == 0){
         temp = doctorsList.get(rowIndex).getMedicalLicenseNumber();
      }else if(columnIndex == 1){
         temp = doctorsList.get(rowIndex).getName();
      }else if(columnIndex == 2){
         temp = doctorsList.get(rowIndex).getSurname();
      }else if(columnIndex == 3){
         temp = doctorsList.get(rowIndex).getSpecialization();
      }else if(columnIndex == 4){
         temp = doctorsList.get(rowIndex).getMobileNumber();
      }else if(columnIndex == 5){
         temp = doctorsList.get(rowIndex).getDateOfBirth();
      }
      return temp;
   }

   public String getColumnName(int col){
      return columnNames[col];
   }

   public Class getColumnClass(int col){
      if(col == 0){
         return String.class;
      }else if(col == 1){
         return String.class;
      }else if(col == 2){
         return String.class;
      }else if(col == 3){
         return String.class;
      }else if(col == 4){
         return String.class;
      }else if(col == 5){
         return LocalDate.class;
      }
      else {
         return null;
      }
   }
}
