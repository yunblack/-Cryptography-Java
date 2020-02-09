package application;

/*
 * Juyoung Yun, Daniel Yun
 */
public class AccessStructure {
	private static PI[] Access; //PI array
	
	public AccessStructure(int i) {
		Access = new PI[i]; // Create PI(Participants Info) Array to store Access Structure Temporarily
	}
	
	public PI[] getAccess() {
		return Access; // Get Access
	}
	
	public void setAccess(PI pi, int i) {
		Access[i] = pi; // Store pi at the index i of Array Access
	}
}
