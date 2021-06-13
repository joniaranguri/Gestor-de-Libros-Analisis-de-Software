package helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * encriptado/desencriptado con algoritmo RSA. Se comenta tambien como guardar
 * las claves en fichero y recuperarlas después.
 * 
 */
public class RSAAsymetricCrypto {
	private static Cipher rsa;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private static String PUBLIC_KEY_PATH = "../files/publickey.dat";
	private static String PRIVATE_KEY_PATH = "../files/privatekey.dat";

	public RSAAsymetricCrypto() {

	}

	public void generateKeys() throws NoSuchAlgorithmException {

		// Generar el par de claves
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey= keyPair.getPrivate();
		// Se guarda y recupera de fichero la clave publica
		try {
			saveKey(publicKey, PUBLIC_KEY_PATH);

			publicKey = loadPublicKey(PUBLIC_KEY_PATH);

			// Se guarda y recupera de fichero la clave privada
			saveKey(privateKey, PRIVATE_KEY_PATH);
			privateKey = loadPrivateKey(PRIVATE_KEY_PATH);

			// Obtener la clase para encriptar/desencriptar
			rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String Encrypt(String string) {

		// Se encripta el texto
		String encryptedText = "";
		try {
			this.rsa.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encriptado;
			encriptado = rsa.doFinal(string.getBytes());
			for (byte b : encriptado)
				encryptedText = encryptedText + Integer.toHexString(0xFF & b);

			return encryptedText;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedText;

	}

	public String Decrypt(String string) {

		// Se desencripta el texto
		String decryptedText = "";
		byte[] bytesDesencriptados = null;
		try {
			rsa.init(Cipher.DECRYPT_MODE, privateKey);
			bytesDesencriptados = rsa.doFinal(string.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decryptedText = new String(bytesDesencriptados);
		return decryptedText;
	}

	private static PublicKey loadPublicKey(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		int numBtyes = fis.available();
		byte[] bytes = new byte[numBtyes];
		fis.read(bytes);
		fis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec keySpec = new X509EncodedKeySpec(bytes);
		PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
		return keyFromBytes;
	}

	private static PrivateKey loadPrivateKey(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		int numBtyes = fis.available();
		byte[] bytes = new byte[numBtyes];
		fis.read(bytes);
		fis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
		return keyFromBytes;
	}

	private static void saveKey(Key key, String fileName) throws Exception {
		byte[] publicKeyBytes = key.getEncoded();
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(publicKeyBytes);
		fos.close();
	}
}