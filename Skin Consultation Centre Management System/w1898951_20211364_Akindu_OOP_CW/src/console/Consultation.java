package console;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
public class Consultation implements Serializable {
   //instance variables
   private String consultationID;
   private Patient patient;
   private String doctorName;
   private String doctorLicenseNumber;
   private LocalDate consultationDate;
   private LocalTime startTime;
   private LocalTime endTime;
   private double cost;
   private String notes;

   //default constructor
   public Consultation() {
   }

   //getters and setters
   public String getConsultationID() {
      return consultationID;
   }
   public Patient getPatient() {
      return patient;
   }

   public String getDoctorName() {
      return doctorName;
   }

   public String getDoctorLicenseNumber() {
      return doctorLicenseNumber;
   }

   public LocalDate getConsultationDate() {
      return consultationDate;
   }

   public LocalTime getStartTime() {
      return startTime;
   }

   public LocalTime getEndTime() {
      return endTime;
   }

   public double getCost() {
      return cost;
   }

   //to set the details of the doctor booked for the consultation
   public void setDoctorAvailability(String doctorName, String doctorMedicalLicenseNumber, LocalDate consultationDate,
                                     LocalTime startTime, LocalTime endTime) {
      this.doctorName = doctorName;
      this.doctorLicenseNumber = doctorMedicalLicenseNumber;
      this.consultationDate = consultationDate;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   //set all the attributes of the consultation in one method
   public void setConsultationDetails(String consultationID, Patient patient, String doctorName, String doctorNumber,
                                      LocalDate date, LocalTime startTime, LocalTime endTime, double cost, String notes)
   {
      this.consultationID = consultationID;
      this.patient = patient;
      this.doctorName = doctorName;
      this.doctorLicenseNumber = doctorNumber;
      this.consultationDate = date;
      this.startTime = startTime;
      this.endTime = endTime;
      this.cost = cost;
      this.notes = notes;
   }

   //Override toString method to display the details of the consultation
   public String toString(){
      return "Patient ID                             : " + patient.getPatientID() + "\n" +
              "Consultation ID                   : " + consultationID + "\n" +
              "Consultation Cost               : £" + getCost() + "\n" +
              "Name                                    : " + patient.getFullName() + "\n" +
              "National ID                           : " + patient.getNationalID() + "\n" +
              "Mobile number                    : " + patient.getMobileNumber() + "\n" +
              "Date of Birth                        : " + patient.getDateOfBirth() + "\n" +
              "Doctor                                  : " + getDoctorName() + "\n" +
              "Consultation Date               : " + getConsultationDate() + "\n" +
              "Consultation Start Time      : " + getStartTime() + "\n" +
              "Consultation End Time       : " + getEndTime() + "\n";
   }

   public String showBookingDetails () {
      return "Doctor                                  : " + getDoctorName() + " (" + getDoctorLicenseNumber() + ")\n" +
              "Consultation Date               : " + getConsultationDate() + "\n" +
              "Consultation Start Time      : " + getStartTime() + "\n" +
              "Consultation End Time       : " + getEndTime() + "\n";
   }
}