package application;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javafx.scene.control.ListView;

/*
 * Hyongsok Sim
 * Jongsun Park
 * Juyoung Yun
 */

public class Enc {

	// private static int index = 0;
	
	//Set the path
	private static String path = "C:/temporary/";

	public static String encrypt(String k, BigInteger key) throws Exception {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		byte[] saltBytes = bytes;

		// Password-Based Key Derivation function 2
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		// 70000번 해시하여 256 bit 길이의 키를 만든다.
		// Hasing 70,000 times and then make 256 bit length key
		PBEKeySpec spec = new PBEKeySpec(key.toString().toCharArray(), saltBytes, 70000, 256);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// 알고리즘/모드/패딩
		// Algorithm/Moding/Padding
		// CBC : Cipher Block Chaining Mode

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Algorithm : AES, Moding : CBC, Padding :
																	// PKCS5Padding
		cipher.init(Cipher.ENCRYPT_MODE, secret); // initialize cipher mode
		AlgorithmParameters params = cipher.getParameters();

		// Initial Vector(1단계 암호화 블록용)
		// Initial Vector(Step1 Encrypting Block)
		byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(k.getBytes("UTF-8"));
		byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];

		System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
		System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
		System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);

		return Base64.getEncoder().encodeToString(buffer);
	}

	public static String decrypt(String k, BigInteger key) throws Exception, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(k));

		byte[] saltBytes = new byte[20];
		buffer.get(saltBytes, 0, saltBytes.length);

		byte[] ivBytes = new byte[cipher.getBlockSize()];
		buffer.get(ivBytes, 0, ivBytes.length);

		byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
		buffer.get(encryoptedTextBytes);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(key.toString().toCharArray(), saltBytes, 70000, 256);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);

		return new String(decryptedTextBytes);
	}

	public static String GenerateKey(String msg) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());
		return Enc.byteToHexString(md.digest()); // return md(MessageDigest) as a HexString
	}

	public static String byteToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();

		for (byte b : data)
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

		return sb.toString();
	}

	public static void enc_file(ArrayList<File> files, BigInteger key, String name) throws IOException {
		// Create FTPmanager object
		FTPmanager fp1 = new FTPmanager();
		// Create File object with path
		File Folder = new File(path);
		// If Folder does not exist
		if (!Folder.exists()) {
			try {
				Folder.mkdir();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		try {
			// This is name and path of zip file to be created
			ZipFile zipFile = new ZipFile(path + name + ".zip");

			// Add files to be archived into zip file
			ArrayList<File> filesToAdd = files;

			// Initiate Zip Parameters which define various properties
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate

			// compression
			// DEFLATE_LEVEL_FASTEST - Lowest compression level but higher speed of
			// compression
			// DEFLATE_LEVEL_FAST - Low compression level but higher speed of compression
			// DEFLATE_LEVEL_NORMAL - Optimal balance between compression level/speed
			// DEFLATE_LEVEL_MAXIMUM - High compression level with a compromise of speed
			// DEFLATE_LEVEL_ULTRA - Highest compression level but low speed
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			// Set the encryption flag to true
			parameters.setEncryptFiles(true);

			// Set the encryption method to AES Zip Encryption
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			// AES_STRENGTH_128 - For both encryption and decryption
			// AES_STRENGTH_192 - For decryption only
			// AES_STRENGTH_256 - For both encryption and decryption
			// Key strength 192 cannot be used for encryption. But if a zip file already has
			// a file encrypted with key strength of 192, then Zip4j can decrypt this file
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			// Set password
			parameters.setPassword(key.toString());
			System.out.println(parameters.getPassword());
			// Now add files to the zip file
			zipFile.addFiles(filesToAdd, parameters);
			// Upload encrypted zipfile to the server
			fp1.sendFtpServer(new File(path + name + ".zip"));
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	public static void dec_file(File f, ArrayList<BigInteger> share, BigInteger Tau) throws NoSuchAlgorithmException {
		Collector c1 = new Collector(); // Call Collector
		BigInteger key = c1.reconstruct(Tau, share); // Reconstrcut key
		String source = f.toString();// "some/compressed/file.zip";
		String destination = path;// "some/destination/folder";
		String password = key.toString(); // set Password as a value of key

		try {
			ZipFile zipFile = new ZipFile(source); // get zipFile from the source
			if (zipFile.isEncrypted()) { // If zipFile is encrypted
				zipFile.setPassword(password); // Input password to the zipFile
			}
			zipFile.extractAll(destination); // Extract file from the zipFile
		} catch (ZipException e) {
			e.printStackTrace(); // If zipFile is not encrypted or password is not correct then print errors
		}
	}
}
