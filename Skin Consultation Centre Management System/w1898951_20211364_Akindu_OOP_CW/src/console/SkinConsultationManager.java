package console;

public interface SkinConsultationManager {
   void addDoctor();
   void deleteDoctor();
   void printDoctors();
   void saveProgress(String fileName);
   void reloadProgress(String fileName);
}
