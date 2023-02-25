package console;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient extends Person implements Serializable {
   //instance variables
   private String patientID;
   private String nationalID;

   //default constructor
   public Patient() {
   }

   //getters and setters
   public String getPatientID() {
      return patientID;
   }

   public String getNationalID() {
      return nationalID;
   }

   //set all the attributes of the patient in one method
   public void setPatientDetails(String patientID, String nationalID, String name, String surname, LocalDate dateOfBirth,
                                 String mobileNumber)
   {
      this.patientID = patientID;
      this.nationalID = nationalID;
      setName(name);
      setSurname(surname);
      setDateOfBirth(dateOfBirth);
      setMobileNumber(mobileNumber);
   }
}
