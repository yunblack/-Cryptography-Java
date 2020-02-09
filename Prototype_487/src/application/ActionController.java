package application;

/*
 * Hyongsok Sim
 * Partly with Juyoung and Jongsun
 */
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import application.Enc;
import application.GenerateRandom;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

public class ActionController implements Initializable {
	@FXML
	private Pane Mpane; // FXML Pane
	@FXML
	private HBox Encrypto, Decrypto; // FXML HBox
	@FXML
	private Label labela, labelb, labelc; // FXML Label
	@FXML
	private TextField PartN, addressEnter, DecEmail1, DecEmail2, DecEmail3, TauVal; // FXML TextField
	@FXML
	private ListView<String> addressList; // FXML ListView
	@FXML
	private Button addItem, EncStart, FileAdd, DecStart; // FXML Button
	@FXML
	private TextArea original, FileName; // FXML TextArea
	@FXML
	private ListView<File> FileList, DecFiles;

	Random rdg = new Random(); // Create Random object

	public BigInteger key;
	private static String path = "C:/temporary/";
	private ArrayList<BigInteger> shares = new ArrayList<BigInteger>();
	private String[] emails; // String array to store e-mails
	private String eText; // Encrypt Text(eTexT)
	private int count = 0, c = 0; // count for emails array's index, c is to pick a email in the emails array
	private ObservableList<String> Eaddress; // ObservableList to show users which email that he/she add
	private ObservableList<File> FList;
	private ObservableList<File> DFile;
	FileChooser fileChooser = new FileChooser(); // Create FileChooser object
	ArrayList<File> file = new ArrayList<File>();
	FTPmanager fp1 = new FTPmanager();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		Eaddress = FXCollections.observableArrayList(""); // Set observableArrayList Collections
		addressList.setItems(Eaddress); // Add FXCollection to the list
		FList = FXCollections.observableArrayList();
		FileList.setItems(FList);
		DFile = FXCollections.observableArrayList();
		DecFiles.setItems(DFile);

		// Add a ChangeListener to TextField to look for change in focus
		// Not Needed yet - Toward a Better Function (Add, Delete, or Edit)
		/*
		 * addressEnter.focusedProperty().addListener(new ChangeListener<Boolean>() {
		 * public void changed(ObservableValue<? extends Boolean> observable, Boolean
		 * oldValue, Boolean newValue) { if (addressEnter.isFocused()) {
		 * addItem.setDisable(false); } } });
		 * 
		 * // Add a ChangeListener to ListView to look for change in focus
		 * addressList.focusedProperty().addListener(new ChangeListener<Boolean>() {
		 * public void changed(ObservableValue<? extends Boolean> observable, Boolean
		 * oldValue, Boolean newValue) { if (addressList.isFocused()) {
		 * addItem.setDisable(false); } } });
		 */

	}

	public void encryptoP(ActionEvent event) {
		Encrypto.setVisible(true); // Set HBox's visible true
									// When you first click Start Sharing you can see this page.
	}

	public void decryptoP(ActionEvent event) {
		Decrypto.setVisible(true);
	}

	/*
	 * When you click Set button which is located at the next to the TextField It
	 * will set the number of participants
	 */
	public void encset(ActionEvent event) {
		if (!CheckN(PartN.getText())) { // If PartN(TextField)'s text is not numeric
			Alert a = new Alert(AlertType.CONFIRMATION); // Create Alert
			a.setTitle("Input the Number"); // Set Alert's title "Input the Number"
			a.setHeaderText("Number space Error"); // Set Alert's Header Text "Number space Error"
			a.setContentText("Please input the number"); // Set Alert's Content Text "Please input the number"
			a.showAndWait(); // Show Alert and wait until user click the confirm button.
		} else {
			emails = new String[Integer.parseInt(PartN.getText())]; // Set Email array with size PartN.getText()
			// System.out.println(PartN.getText()); // Debug Statement
			addressList.setVisible(true); // Set addressList(ListView)'s visibility true
			addressEnter.setVisible(true); // Set addressEnter(TextField)'s visibility true
			addressEnter.requestFocus();
			addItem.setVisible(true); // Set addItem(Button)'s visibility true
			// original.setDisable(false); // Set original's enable false
			// original is the TextArea to input Plain Text from the user

			FileName.setDisable(false); // Set FileName's enable false
			// FileName is for the Zip File's name which is secured by password

			// EncStart.setDisable(false); // Set EncStart's enable false
			// EncStart is the button to start encryption
		}
	}

	public void DecSet(ActionEvent event) throws IOException {
		if (DecEmail1.getText().isBlank() && DecEmail2.getText().isBlank() && DecEmail3.getText().isBlank()) {
			Alert a = new Alert(AlertType.CONFIRMATION); // Create Alert
			a.setTitle("Insert shares"); // Set Alert's title "Input the Number"
			a.setHeaderText("Share is not found"); // Set Alert's Header Text "Number space Error"
			a.setContentText("Please input your shares"); // Set Alert's Content Text "Please input the number"
			a.showAndWait(); // Show Alert and wait until user click the confirm button.
		} else {
			ArrayList<File> fL = fp1.getList();
			DFile.addAll(fL);
		}
	}

	public void AddEmail(ActionEvent event) {
		int k = Integer.parseInt(PartN.getText()); // Set k as a number from PartN

		if (count < k) { // While k is less than count
			Eaddress.add(count, addressEnter.getText()); // ListView.add(index, Text) so it will contain email address
															// // from index of 0
			emails[count] = addressEnter.getText(); // Store email to emails array from index of 0
			addressEnter.requestFocus(); // To make sure that user can input email conveniently, Move focus to //
											// addressEnter(TextField)
			addressEnter.clear(); // Set addressEnter as blank
			count++; // increase count
		}
		if (count == k) { // If count equals to k
			addItem.setDisable(true); // Set addItem's(Button) enable false
			addressEnter.setDisable(true); // Set addressEnter's(TextField) enable false
			FileAdd.setDisable(false);
			file.addAll(fileChooser.showOpenMultipleDialog(null)); // Get File from the filechooser
			FList.addAll(file);
		}
	}

	/*
	 * Function to check if the str is numeric or not.
	 */
	public boolean CheckN(String str) {
		if (str.isEmpty()) // If str is empty
			return false;
		else if (str.isBlank()) // If srt is blank
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < 48 || str.charAt(i) > 58) // If str's ASCII code is not equals to numeric ASCII code
				return false;
		}

		return true; // Else cases are numeric cases so it returns true
	}

	public void startE(ActionEvent event) throws Exception {

	}

	public void FileEnc(ActionEvent event) throws Exception {
		if (FileName.getText().isBlank() || FileName.getText().isEmpty() || FileName.getText().equals("File Name")) {
			Alert a = new Alert(AlertType.CONFIRMATION); // Create Alert
			a.setTitle("File Name Required"); // Set Alert's title "Input the Number"
			a.setContentText("Please input the File Name"); // Set Alert's Content Text "Please input the number"
			a.showAndWait(); // Show Alert and wait until user click the confirm button.
		} else {
			key = BigInteger.valueOf(rdg.nextLong());
			Enc.enc_file(file, key, FileName.getText());
			int size = Integer.parseInt(PartN.getText()), as = size; // Set size as a number from the PartN's Text, as
																		// is
																		// equal to size
			DBmanager db = new DBmanager(); // Create DBmanager Object
			AccessStructure[] a = new AccessStructure[(int) Math.sqrt(as)]; // Create AccessStructure Array to store
																			// AccessStructrue group
			BigInteger Tau; // Tau is BigInteger

			for (int i = 0; i < a.length; i++) {
				if (i < a.length - 1) { // If it is not the last AccessStructure group
					a[i] = new AccessStructure((int) Math.sqrt(size)); // Call the Constructor of AccessStructure to set
																		// PI's size
					as -= (int) Math.sqrt(size); // Subtract the sqrt of size from as
					/*
					 * Why we subtract sqrt(size) from as is because that when we create
					 * AccessStructure with 10 participants the size of AccessStructure Array is
					 * 3(because (int)Math.sqrt(10)) is 3 so both of two AccessStructure (First and
					 * Second) have 3 Participants as a same group of AccessStructure and the last
					 * one group of AccessStructure will has 4 Participants. So if there are lots of
					 * people who participated in the group, such as 104 user, it will divide them
					 * to ten group of AccessStructure and each of them will have 10, 10, 10, 10,
					 * 10, 10, 10, 10, 10, 14 participants as a member of each AccessStructure
					 */
				} else {
					a[i] = new AccessStructure((as)); // Set Access Structure's size with the remaining members
				}
				for (int k = 0; k < a[i].getAccess().length; k++) {
					PI pi = GenerateRandom.GenerateRandomShares(size, emails[rdg.nextInt(emails.length)]); // Set
																											// Participants
																											// info with
																											// size and
					// emails[c]
					a[i].setAccess(pi, k); // Add Participants info to the AccessStructure index of k
				}
				Tau = GenerateRandom.TauValue(key, a[i].getAccess()); // Calculate Tau Value
				// System.out.println("Tay : " + Tau); // Debug Statement
				for (PI p : a[i].getAccess())
					db.uploadE(i, p.PN, p.share.toString(), Tau.toString());
				// Upload information of AccessStructure number, Participants email & share, and
				// Tau value
				// eText = Enc.encrypt(original.getText(), key); // Set eText as a Encrypted
				// Text
				// enctext.setText(eText); // Show the result of Encrypting Plain Text
				for (PI p : a[i].getAccess())
					SendEmail.navermail(p.PN, "The RandomShare is " + p.share); // Send Email to Participants
			}
			Encrypto.setVisible(false);
			Alert k = new Alert(AlertType.CONFIRMATION); // Create Alert
			k.setTitle("Encrypt Complete!"); // Set Alert's title "Encrypt Complete!"
			k.setHeaderText("Complete Encrypting"); // Set Alert's Header Text "Complete Encrypting"
			k.setContentText("Your file has been Encrypted."); // Set Alert's Text "Your file has been encrypted"
			k.showAndWait(); // Show Alert and wait until user click the confirm button.
		}
	}

	public void FileDec(ActionEvent event) throws Exception {
		// If File selected and none of shares are empty
		if (DecFiles.getSelectionModel().getSelectedItem() != null
				&& !(DecEmail1.getText().isBlank() && DecEmail2.getText().isBlank() && DecEmail3.getText().isBlank())) {

			// Get selected file from the server
			fp1.getFtpServer(DecFiles.getSelectionModel().getSelectedItem().getName(), path);

			
			// Add Shares to the ArrayList to send Array to the Enc Class
			shares.add(BigInteger.valueOf(Long.parseLong(DecEmail1.getText())));
			shares.add(BigInteger.valueOf(Long.parseLong(DecEmail2.getText())));
			shares.add(BigInteger.valueOf(Long.parseLong(DecEmail3.getText())));
			
			// Decrypting File(Decompression Zip with the Encrypting Key)
			Enc.dec_file(new File(path + DecFiles.getSelectionModel().getSelectedItem().getName()), shares,
					new BigInteger(TauVal.getText()));
			
		} else { //Else cases
			Alert a = new Alert(AlertType.CONFIRMATION); // Create Alert
			a.setTitle("Shares or File Error"); // Set Alert's title "Shares or File Error"
			a.setHeaderText("Something went wrong"); // Set Alert's Header Text "Something went wrong"
			a.setContentText("Please input the shares or Choose the file"); // Set Alert's Content Text "Please input the shares
																			// or Choose the file"
			a.showAndWait(); // Show Alert and wait until user click the confirm button.
		}
	}
}
