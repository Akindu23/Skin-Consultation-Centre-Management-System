package console;

import java.io.Serializable;
import java.time.LocalDate;

abstract class Person implements Serializable {
   //instance variables
   private String name;
   private String surname;
   private LocalDate dateOfBirth;
   private String mobileNumber;

   //default constructor
   public Person(){
   }

   //parameterized constructor
   public Person(String name, String surname, LocalDate dateOfBirth,String mobileNumber) {
      this.name = name;
      this.surname = surname;
      this.dateOfBirth = dateOfBirth;
      this.mobileNumber = mobileNumber;
   }

   //getters and setters
   public String getName() {
      return name;
   }

   public String getSurname() {
      return surname;
   }

   public String getFullName() {
      return name + " " + surname;
   }

   public LocalDate getDateOfBirth() {
      return dateOfBirth;
   }

   public String getMobileNumber() {
      return mobileNumber;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public void setDateOfBirth(LocalDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
   }

   public void setMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
   }

}
