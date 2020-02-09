package application;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPmanager {
	/**
	 *
	 * @param ip       ftp서버ip => FTP Server's IP
	 * @param port     ftp서버port => FTP Server's Port
	 * @param id       사용자id => USER ID
	 * @param password 사용자password => USER Password
	 * @param folder   ftp서버에생성할폴더 => Folder to create on the FTP Server
	 * @param files    업로드list => Upload List
	 * @return
	 */
	private String address = "124.111.34.56", ID = "prunus", Password = "runya!canti@yuhan1688", folder = "Research";
	// address = Server Address, folder = Where to store
	private int port = 21;
	// Set FTP port as 21
	private FTPClient ftp = null;
	// Create FTPClient object

	public boolean sendFtpServer(File files) { // Send Encrypted file to FTP Server
		boolean isSuccess = false; // To determine whether file successfully sent to the server
		int reply; // store reply code from server
		try {
			ftp = new FTPClient(); // Create FTPClient object

			ftp.setControlEncoding("UTF-8"); // Set FTPClient's Encoding as UTF - 8
			ftp.connect(address, port); // Connect to the server
			System.out.println("Connected to " + address + " on " + ftp.getRemotePort());
			// Return connected statement		

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();
			// Set reply code
			if (!FTPReply.isPositiveCompletion(reply)) { // If Server not connected
				ftp.disconnect(); // disconnect from the server
				System.err.println("FTP server refused connection."); // Print Debug statement
				System.exit(1); // exit
			}

			if (!ftp.login(ID, Password)) { // If server not connected because of the ID or Password
				ftp.logout(); // logout from the server
				throw new Exception("Failed to connect FTP server"); // Print debug statement
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE); // Set File Type
			ftp.enterLocalPassiveMode(); // Change Mode

			System.out.println(ftp.printWorkingDirectory()); // Print FTP's current working directory
			try {
				ftp.makeDirectory(folder); // create Directory
			} catch (Exception e) {
				e.printStackTrace();
			}
			ftp.changeWorkingDirectory(folder); // Change working Directory
			System.out.println(ftp.printWorkingDirectory()); // Print FTP's current working directory

			File uploadFile = new File(files.toString()); // Set Upload File
			FileInputStream fis = null; // Create FileInputStream
			try {
				fis = new FileInputStream(uploadFile); // Input file to Input stream
				System.out.println(files.getName() + " : Sending = >"); // Print debug statement
				isSuccess = ftp.storeFile(files.getName(), fis); // Store file to the FTP server
				System.out.println(isSuccess); // Print is success or not
			} catch (IOException e) { // If file not exist
				e.printStackTrace(); // print errors
				isSuccess = false; // set isSuccess false
			} finally {
				if (fis != null) {
					try {
						fis.close(); // close file input stream
					} catch (IOException e) {
					}
				}
			} // end try

			ftp.logout(); // log out from the FTP server
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // Disconnect from the FTP server
				} catch (IOException e) {
				}
			}
		}
		return isSuccess; // return statement
	}

	public ArrayList<File> getList() throws IOException {
		ArrayList<File> files = new ArrayList<File>(); // Create Arraylist for Files from the FTP server
		int reply;
		try {
			ftp = new FTPClient();

			ftp.setControlEncoding("UTF-8");
			ftp.connect(address, port);
			System.out.println("Connected to " + address + " on " + ftp.getRemotePort());

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			if (!ftp.login(ID, Password)) {
				ftp.logout();
				throw new Exception("Failed to connect FTP server");
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

			System.out.println(ftp.printWorkingDirectory());
			ftp.changeWorkingDirectory(folder);
			System.out.println(ftp.printWorkingDirectory());
			FTPFile[] ftpfiles = ftp.listFiles(); // Get Files from the FTP server

			for (FTPFile ftpf : ftpfiles) // foreach files
				files.add(new File(ftpf.getName())); // add Files to the ArrayList
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
				}
			}
		}
		return files;
	}

	public void getFtpServer(String downloadFileName, String path) {
		int reply;
		try {
			ftp = new FTPClient();

			ftp.setControlEncoding("UTF-8");
			ftp.connect(address, port);
			System.out.println("Connected to " + address + " on " + ftp.getRemotePort());

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			if (!ftp.login(ID, Password)) {
				ftp.logout();
				throw new Exception("Failed to connect FTP server");
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

			System.out.println(ftp.printWorkingDirectory());
			ftp.changeWorkingDirectory(folder);
			System.out.println(ftp.printWorkingDirectory());

			// FileOutputStream out = null;
			String remoteFile1; // Filepath for FTP server
			File downloadFile1 = new File(path + downloadFileName); // Path for store files from the FTP server
			// InputStream in = null;
			folder += ("/" + downloadFileName); // Set File path 
			folder = "/" + folder; // Add / in front of the File path
			remoteFile1 = folder; // Set remote path as folder
			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
			boolean success = ftp.retrieveFile(remoteFile1, outputStream1);
			// Download File from FTP Server
			outputStream1.close();
			// Close Ouput Stream
			if (success)
				System.out.println("Download Complete");
				// Print Download Complete
		} catch (Exception e) {
		}
	}
}