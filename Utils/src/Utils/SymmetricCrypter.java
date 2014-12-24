package Utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Nakim
 */
public class SymmetricCrypter
{
    //<editor-fold defaultstate="collapsed" desc="Private variables">
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    private SecretKey secretKey;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public SymmetricCrypter(String transformation)
        throws NoSuchAlgorithmException, NoSuchPaddingException
    {
        this.encryptCipher = Cipher.getInstance(transformation);
        this.decryptCipher = Cipher.getInstance(transformation);
        this.secretKey = null;
    }

    public SymmetricCrypter(String transformation, SecretKey secretKey)
        throws NoSuchAlgorithmException,
               NoSuchPaddingException,
               InvalidKeyException
    {
        this(transformation);

        if (secretKey != null)
        {
            this.secretKey = secretKey;
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            this.decryptCipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter">
    public SecretKey getSecretKey()
    {
        return this.secretKey;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public methods">
    public void init(SecretKey secretKey) throws InvalidKeyException
    {
        if (secretKey == null)
            throw new InvalidKeyException("Invalid secret key : null");

        this.secretKey = secretKey;
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        this.decryptCipher.init(Cipher.DECRYPT_MODE, this.secretKey);
    }

    public void invalidate()
    {
        this.secretKey = null;
    }

    public boolean isValid()
    {
        return this.secretKey != null;
    }

    public byte[] encrypt(byte[] data) throws IllegalBlockSizeException,
                                              BadPaddingException,
                                              InvalidKeyException
    {
        if (!this.isValid())
            throw  new InvalidKeyException("SymmetricCrypter not initialized");

        return this.encryptCipher.doFinal(data);
    }

    public byte[] encrypt(String text) throws IllegalBlockSizeException,
                                              BadPaddingException,
                                              InvalidKeyException
    {
        return this.encrypt(text.getBytes());
    }

    public byte[] decryptByteArray(byte[] encryptedData)
        throws IllegalBlockSizeException,
               BadPaddingException,
               InvalidKeyException
    {
        if (!this.isValid())
            throw  new InvalidKeyException("SymmetricCrypter not initialized");

        return this.decryptCipher.doFinal(encryptedData);
    }

    public String decryptString(byte[] encryptedData)
        throws IllegalBlockSizeException,
               BadPaddingException,
               InvalidKeyException
    {
        return new String(this.decryptByteArray(encryptedData));
    }
    //</editor-fold>
}
