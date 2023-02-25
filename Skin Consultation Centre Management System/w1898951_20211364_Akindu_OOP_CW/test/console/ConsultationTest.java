package console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTest {
   Patient testPatient = new Patient();
   Consultation testConsultation = new Consultation();

   @BeforeEach
   void setUp() {
      String testPatientID = "P001";
      String testNationalID = "200302301355";
      String testName = "Akindu";
      String testSurname = "Karunaratne";
      LocalDate testDOB = LocalDate.of(2003,1,23);
      String testMobileNumber = "0770457896";

      String testConsultationID = "C001";
      String testDoctor = "Kimi Raikkonen";
      String testMLN = "m20071021";
      LocalDate testDate = LocalDate.of(2022,1,8);
      LocalTime testStartTime = LocalTime.of(10,0);
      LocalTime testEndTime = LocalTime.of(11,0);
      double testCost = 15.0;
      String testNotes = "test1";

      testPatient.setPatientDetails(testPatientID, testNationalID, testName, testSurname, testDOB, testMobileNumber);
      testConsultation.setConsultationDetails(testConsultationID, testPatient, testDoctor, testMLN, testDate,
              testStartTime, testEndTime, testCost, testNotes);
   }

   @Test
   void getConsultationID() {
      assertEquals("C001", testConsultation.getConsultationID());
   }

   @Test
   void getPatient() {
      assertEquals(testPatient, testConsultation.getPatient());
      assertEquals("Akindu Karunaratne", testConsultation.getPatient().getFullName());
   }
   @Test
   void getDoctorName() {
      assertEquals("Kimi Raikkonen", testConsultation.getDoctorName());
   }
   @Test
   void getDoctorLicenseNumber() {
      assertEquals("m20071021", testConsultation.getDoctorLicenseNumber());
   }
   @Test
   void getConsultationDate() {
      assertEquals(LocalDate.of(2022,1,8), testConsultation.getConsultationDate());
   }
   @Test
   void getStartTime() {
      assertEquals(LocalTime.of(10,0), testConsultation.getStartTime());
   }
   @Test
   void getEndTime() {
      assertEquals(LocalTime.of(11,0), testConsultation.getEndTime());
   }
   @Test
   void getCost() {
      assertEquals(15.0, testConsultation.getCost());
   }
}