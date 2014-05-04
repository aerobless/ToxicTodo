package ch.theowinter.toxictodo.sharedobjects;

import java.io.IOException;
import java.io.Serializable;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionEngine {
	private Cipher cipher;
	private Cipher dcipher;	

	/**
	 * Creates a new instance of this class.
	 * @throws Exception 
	 *
	 */
	public EncryptionEngine(String password) throws Exception {
		super();
         char[] password1 = password.toCharArray();
         byte[] salt = "ds8f982fn29w".getBytes(); 

        // Create key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password1, salt, 1024, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Init ciphers
        cipher = Cipher.getInstance("AES");
        dcipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        dcipher.init(Cipher.DECRYPT_MODE, secret);
	}
	
	public SealedObject enc(Serializable input) throws Exception{
        SealedObject so = new SealedObject(input, cipher);
      return so;
    }
	
	public Object dec(SealedObject input) throws ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, IOException {
       Object decryptedPacket = (Object) input.getObject(dcipher);
      return decryptedPacket;
    }
}
