package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * This class was made to create the components needed such as JButtons, JLabels, JTextFields etc. to avoid code
 * redundancies.
 */
public class CreateComponents {

   /**
    * Creates the Helper method for the JButtons used in the GUI.
    * Contains the common elements such as the cursor to be a hand, the font and not to be focusable.
    *
    * @param text The text to display on the button
    * @param colorHexCode The hex code for the background color of the button
    * @return The created JButton
    */
   private JButton createButtonHelper(String text, String colorHexCode) {
      JButton button = new JButton(text);
      button.setBackground(Color.decode(colorHexCode));
      button.setCursor(new Cursor(Cursor.HAND_CURSOR));
      button.setFont(new Font("Dubai", Font.BOLD, 16));
      button.setFocusable(false);
      return button;
   }

   /**
    * Using the button helper method, creates the JButtons used in the GUI.
    * The bounds (location) of the button is the common element that is set here using the parameters.
    *
    * @param text The text to display on the button
    * @param x The x coordinate of the top left corner of the button
    * @param y The y coordinate of the top left corner of the button
    * @param width The width of the button
    * @param height The height of the button
    * @param colorHexCode The hex code for the background color of the button
    * @return The created JButton
    */
   public JButton createButton(String text, int x, int y, int width, int height, String colorHexCode) {
      JButton button = createButtonHelper(text, colorHexCode);
      button.setBounds(x, y, width, height);
      return button;
   }

   /**
    * This method is used to create the JButtons used in the Doctor Table frame of the GUI.
    * Created using the help of the button helper method and icons are set based on the image path given in the
    * parameters.
    *
    * @param text The text to display on the button
    * @param x The x coordinate of the top left corner of the button
    * @param y The y coordinate of the top left corner of the button
    * @param width The width of the button
    * @param height The height of the button
    * @param colorHexCode The hex code for the background color of the button
    * @param imagePath The path to the image to display as an icon on the button
    @return The created JButton
    */
   public JButton doctorTableFrameButton(String text, int x, int y, int width, int height, String colorHexCode,
                                         String imagePath) {
      JButton button = createButtonHelper(text, colorHexCode);
      button.setBounds(x, y, width, height);
      button.setIcon(new ImageIcon(imagePath));
      button.setHorizontalAlignment(SwingConstants.LEFT);
      return button;
   }

   /**
    * This method is used to create the common back button in all the frames of the GUI.
    * The button is only an image. It is done by setting the icon of the button using the image path given and
    * setting the opacity to false, content area filled to false as well as the border to null.
    *
    * @return The created JButton
    */
   public JButton createBackButton() {
      JButton backButton = new JButton();
      backButton.setBounds(30, 20, 35, 35);
      backButton.setIcon(new ImageIcon("images\\back.png"));
      backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      backButton.setOpaque(false);
      backButton.setContentAreaFilled(false);
      backButton.setBorderPainted(false);
      backButton.setFocusable(false);

      return backButton;
   }

   /**
    * Creates all the JLabels used in the GUI with the given text, dimensions, and font.
    *
    * @param labelText The text to display on the label
    * @param x The x coordinate of the top left corner of the label
    * @param y The y coordinate of the top left corner of the label
    * @param width The width of the label
    * @param height The height of the label
    * @return The created JLabel
    */
   public JLabel createLabel(String labelText, int x, int y, int width, int height) {
      JLabel label = new JLabel(labelText);
      label.setBounds(x, y , width, height);
      label.setFont(new Font("Dubai", Font.BOLD, 16));

      return label;
   }

   /**
    * Creates JLabel for the logo used in all the frames other than the main home page.
    *
    * @param x The x coordinate of the top left corner of the label. This will always be a value which aligns the button
    *          to the top right corner of the frame.
    * @return The created JLabel for the logo.
    */
   public JLabel createLogo (int x) {
      JLabel logo = new JLabel();
      logo.setIcon(new ImageIcon("images\\logo.png"));
      logo.setBounds(x, 10, 70, 70);

      return logo;
   }

   /**
    * Creates a text field with the given placeholder text, dimensions, and font.
    * PTextField is a custom class which extends JTextField and is used to create the text fields with placeholder text
    * used in the GUI. The class can be seen at the end of this class.
    *
    * @param placeholder The placeholder text to display in the text field when it is empty
    * @param x The x coordinate of the top left corner of the text field
    * @param y The y coordinate of the top left corner of the text field
    * @param width The width of the text field
    * @param height The height of the text field
    * @return The created PTextField
    */
   public PTextField createTextField(String placeholder, int x, int y, int width, int height) {
      PTextField field = new PTextField(placeholder);
      field.setBounds(x, y , width, height);
      field.setBorder(new EmptyBorder(3, 3, 3, 3));
      field.setFont(new Font("Dubai", Font.PLAIN, 16));

      return field;
   }

   /**
    * Creates the panels needed in the ViewConsultation frame of the GUI.
    * Panels will contain a title border.
    * Initially the panels will not be visible
    * @param x The x coordinate of the top left corner of the panel
    * @param y The y coordinate of the top left corner of the panel
    * @param width The width of the panel
    * @param height The height of the panel
    * @param details The title of the border for the panel
    * @return The created panel
    * */
   public JPanel createDetailsPanel(int x, int y, int width, int height, String details) {
      JPanel detailsPanel = new JPanel();
      TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
              details);
      border.setTitleFont(new Font("Dubai", Font.BOLD, 20));
      border.setTitleColor(Color.BLACK);
      detailsPanel.setBorder(border);
      detailsPanel.setOpaque(false);
      detailsPanel.setBounds(x,y,width,height);
      detailsPanel.setLayout(null);
      detailsPanel.setVisible(false);

      return detailsPanel;
   }

   /**
    * This class was created to make JTextFields have placeholder values to improve user-friendliness.
    * Done by extending the JTextField class and overriding the paintComponent method.
    * The code was adapted from the following source:
    * <a href = "https://stackoverflow.com/questions/13033600/java-placeholder-on-textfield"> JTextField Placeholder Text</a>
    */

   public class PTextField extends JTextField {
      private final String placeHolder;

      public PTextField(String placeHolder) {
         this.placeHolder = placeHolder;
      }

      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);

         if (getText().isEmpty()){
            g.setColor(Color.GRAY);
            g.drawString(placeHolder, 3, 20);
         }
      }

   }
}


