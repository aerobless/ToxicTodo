package ch.theowinter.ToxicTodo.utilities;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionEngine {
	private final Cipher ecipher;
	private final Cipher dcipher;

	/**
	 * Creates a new instance of this class.
	 *
	 */
	public EncryptionEngine(String password) {
		super();
		Cipher[] generatedCiphers = generateCipher(password);
		ecipher = generatedCiphers[0];
		dcipher = generatedCiphers[1];
	}
	
	public Cipher[] generateCipher(String password){
		SecretKeyFactory factory;
		Cipher eCipher = null;
		Cipher dCipher = null;
		try {
			//Format password:
		    char[] passPherase = password.toCharArray();
			
			//Make salt:
			final Random r = new SecureRandom();
			byte[] salt = new byte[32];
			r.nextBytes(salt);
			
			//Generate cipher:
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(passPherase, salt, 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

			eCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			eCipher.init(Cipher.ENCRYPT_MODE, secret);
			
			dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dCipher.init(Cipher.DECRYPT_MODE, secret);
			
		} catch (NoSuchAlgorithmException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (InvalidKeySpecException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (NoSuchPaddingException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (InvalidKeyException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		}
		return new Cipher[]{eCipher, dCipher};
	}

	public SealedObject encrypt(Serializable input){
		SealedObject sealedObject  = null;
		try {
			sealedObject = new SealedObject(input, ecipher);
		} catch (IllegalBlockSizeException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (IOException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		}
		return sealedObject;
	}
	
	public Object decrypt(SealedObject input){
		Object output = null;
		try {
			output = input.getObject(dcipher);
		} catch (ClassNotFoundException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (IllegalBlockSizeException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (BadPaddingException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		} catch (IOException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		}
		return output;
	}
}
