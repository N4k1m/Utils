package Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author nakim
 */
public class BytesConverter
{
    /**
     * Convert a byte array into string
     * @param array the byte array to convert into string
     * @return string build from byte array
     */
    public static String byteArrayToString(byte[] array)
    {
        try
        {
            return new String(array, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) 
        {
            return "UNSUPPORTED_ENCODING";
        } 
        catch (NullPointerException ex) 
        {
            return "NULL_VALUE";
        }
    }
    
    /**
     * Convert a byte array to int
     * @param array the byte array to convert into int
     * @return int build from byte array
     */
    public static int byteArrayToInt(byte[] array) 
    {
        return   array[3] & 0xFF        |
                (array[2] & 0xFF) << 8  |
                (array[1] & 0xFF) << 16 |
                (array[0] & 0xFF) << 24;
    }
    
    /**
     * Convert an object into a byte array
     * @param object the object to convert into byte array
     * @return byte array build from object
     */
    public static byte[] toByteArray(Object object)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        
        try
        {
            out = new ObjectOutputStream(bos);   
            out.writeObject(object);
            return bos.toByteArray();
        }
        catch (IOException ex)
        {
            System.err.println("toByteArray failed : " + ex);
            return null;
        }
    }
    
    /**
     * Convert an integer into a byte array
     * @param integer the integer to convert into byte array
     * @return byte array build from integer
     */
    public static byte[] toByteArray(final int integer)
    {  	
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        try
        {
            dos.writeInt(integer);
            dos.flush();
            return bos.toByteArray();
        }
        catch(IOException ex)
        {
            System.err.println("toByteArray failed : " + ex);
            return null;
        }        
    }
    
    /**
     * Convert a byte array into an object
     * @param byteArray the byte array to convert into object
     * @return object build from byte array
     */
    public static Object fromByteArray(byte[] byteArray)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in;
        
        try
        {
            in = new ObjectInputStream(bis);
            return in.readObject(); 
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.err.println("fromByteArray failed : " + ex);
            return null;
        }
    }
}
