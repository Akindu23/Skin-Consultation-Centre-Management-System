package console;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {
   WestminsterSkinConsultationManager skinConsultationManager = new WestminsterSkinConsultationManager();
   private Doctor testDoctor1 = new Doctor("Akindu","Karunaratne", LocalDate.of(1993,1,23),
           "0770457896", "Paediatric Dermatology", "p18989511");
   private Doctor testDoctor2 = new Doctor("Kimi","Raikkonen", LocalDate.of(1979,10,17),
           "0771173022", "Medical Dermatology", "m20071021");
   @Test
   void addDoctor() {
      WestminsterSkinConsultationManager.getDoctorList().add(testDoctor1);
      WestminsterSkinConsultationManager.getDoctorList().add(testDoctor2);

      assertEquals(2, WestminsterSkinConsultationManager.getDoctorList().size());
   }

   @Test
   void deleteDoctor() {
      WestminsterSkinConsultationManager.getDoctorList().add(testDoctor1);
      WestminsterSkinConsultationManager.getDoctorList().add(testDoctor2);

      WestminsterSkinConsultationManager.getDoctorList().remove(0);
      assertEquals(1, WestminsterSkinConsultationManager.getDoctorList().size());
   }

   @Test
   void printDoctors() {
      WestminsterSkinConsultationManager.getDoctorList().add(testDoctor1);
      WestminsterSkinConsultationManager.getDoctorList().add(testDoctor2);

      skinConsultationManager.printDoctors();
   }

   @Test
   void saveProgress() {
      skinConsultationManager.saveProgress("test/console/test.txt");
      File temp = new File("test/console/test.txt");
      assertTrue(temp.exists());
   }

   @Test
   void validateString() {
      String nameFormat = "[a-zA-Z]";
      String mobileFormat = "\\d{10}";
      String medicalLicenseNumberFormat = "[\\w ]";

      String testFirstName = "Akindu123";
      String testLastName = "@23Karu";
      String testMobileNumberFormat = "077a457896";
      String testMobileNumberLength = "012345678";
      String testMedicalLicenseNumber = "";

      assertFalse(testFirstName.matches(nameFormat), "Invalid First Name!");
      assertFalse(testLastName.matches(nameFormat), "Invalid Last Name!");
      assertFalse(testMobileNumberFormat.matches(mobileFormat), "Mobile number can only contain digits!");
      assertFalse(testMobileNumberLength.matches(mobileFormat), "Mobile number should be exactly 10 digits!");
      assertFalse(testMedicalLicenseNumber.matches(medicalLicenseNumberFormat), "Medical License Number can not be empty!");

   }
}