package console;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
   private Doctor testDoctor = new Doctor("Akindu","Karunaratne", LocalDate.of(1993,1,23),
           "0770457896", "Paediatric Dermatology", "p18989511");

   @Test
   void getMedicalLicenseNumber() {
      assertEquals("p18989511", testDoctor.getMedicalLicenseNumber());
   }
   @Test
   void getSpecialization() {
      assertEquals("Paediatric Dermatology", testDoctor.getSpecialization());
   }
   @Test
   void setDetails() {
      String testFirstName = "Kimi";
      String testLastName = "Raikkonen";
      LocalDate testDOB = LocalDate.of(1979,10,17);
      String testMobileNumber = "0771173022";
      String testSpecialization = "Medical Dermatology";
      String testMedicalLicenseNumber = "m20071017";

      Doctor doctor = new Doctor();
      doctor.setDetails(testFirstName, testLastName, testDOB, testMobileNumber, testSpecialization, testMedicalLicenseNumber);

      assertEquals("Kimi Raikkonen", doctor.getFullName());
      assertEquals(testDOB, doctor.getDateOfBirth());
      assertEquals("0771173022", doctor.getMobileNumber());
   }
}