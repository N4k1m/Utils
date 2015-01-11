package Utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author nakim
 */
public class MessageBoxes
{
    public static void ShowMessage(String Text)
    {
        ShowMessage(Text,"Message");
    }

    public static void ShowMessage(String Text,String Title)
    {
        ShowMessage(Text,Title,JOptionPane.NO_OPTION);
    }

    public static void ShowMessage(String Text,String Title,int messageType)
    {
        JOptionPane.showMessageDialog(null,Text,Title,messageType);
    }

    public static void ShowError(String Text,String Title)
    {
        ShowMessage(Text,Title,JOptionPane.ERROR_MESSAGE);
    }

    public static void ShowWarning(String Text,String Title)
    {
        ShowMessage(Text,Title,JOptionPane.WARNING_MESSAGE);
    }

    public static void ShowInfo(String Text,String Title)
    {
        ShowMessage(Text,Title,JOptionPane.INFORMATION_MESSAGE);
    }

    // Centered on parent frame
    public static void ShowMessage(Component parent, String Text)
    {
        ShowMessage(parent, Text, "Message");
    }

    public static void ShowMessage(Component parent, String Text,String Title)
    {
        ShowMessage(parent, Text, Title, JOptionPane.NO_OPTION);
    }

    public static void ShowMessage(Component parent,
                                   String Text,
                                   String Title,
                                   int messageType)
    {
        JOptionPane.showMessageDialog(parent, Text, Title, messageType);
    }

    public static void ShowError(Component parent, String Text,String Title)
    {
        ShowMessage(parent, Text, Title, JOptionPane.ERROR_MESSAGE);
    }

    public static void ShowWarning(Component parent, String Text, String Title)
    {
        ShowMessage(parent, Text, Title, JOptionPane.WARNING_MESSAGE);
    }

    public static void ShowInfo(Component parent, String Text, String Title)
    {
        ShowMessage(parent, Text, Title, JOptionPane.INFORMATION_MESSAGE);
    }
}
