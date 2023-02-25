package console;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

   private Patient testPatient = new Patient();
   private ArrayList<Patient> testPatientList = new ArrayList<>();

   @Test
   void setPatientDetails() {
      String testPatientID = "P001";
      String testNationalID = "200302301355";
      String testName = "Akindu";
      String testSurname = "Karunaratne";
      LocalDate testDOB = LocalDate.of(2003,1,23);
      String testMobileNumber = "0770457896";

      testPatient.setPatientDetails(testPatientID, testNationalID, testName, testSurname, testDOB, testMobileNumber);
      testPatientList.add(testPatient);

      assertEquals("Akindu Karunaratne", testPatientList.get(testPatientList.size() - 1).getFullName());
      assertTrue(testPatientList.contains(testPatient));
   }
}