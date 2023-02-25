package console;

import java.io.Serializable;
import java.time.LocalDate;

public class Doctor extends Person implements Serializable {
   //instance variables
   private String medicalLicenseNumber;
   private String specialization;

   //default constructor
   public Doctor() {
   }

   //parameterized constructor
   public Doctor(String name, String surname, LocalDate dateOfBirth, String mobileNumber,
                 String specialization, String medicalLicenseNumber) {
      super(name, surname, dateOfBirth, mobileNumber);
      this.specialization = specialization;
      this.medicalLicenseNumber = medicalLicenseNumber;
   }

   //getters and setters
   public String getMedicalLicenseNumber() {
      return medicalLicenseNumber;
   }

   public String getSpecialization() {
      return specialization;
   }

   //set details of the doctor
   public void setDetails(String name, String surname, LocalDate dateOfBirth, String mobileNumber,
                          String specialization, String medicalLicenseNumber) {
      super.setName(name);
      super.setSurname(surname);
      super.setDateOfBirth(dateOfBirth);
      super.setMobileNumber(mobileNumber);
      this.specialization = specialization;
      this.medicalLicenseNumber = medicalLicenseNumber;
   }

   //Override toString method to display doctor name and medical license number in the GUI ComboBox.
   public String toString() {
      return getFullName() + "   (" + getMedicalLicenseNumber() + ")";
   }

   //show doctor details when printing doctor arraylist.
   public String showDetails() {
      return "\tName                    : " + getFullName() + "\n" +
              "\tMedical License Number  : " + getMedicalLicenseNumber() + "\n" +
              "\tSpecialization          : " + getSpecialization() + "\n" +
              "\tMobile Number           : " + getMobileNumber() + "\n" +
              "\tDate of Birth           : " + getDateOfBirth() + "\n";
   }
}
