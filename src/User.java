import java.util.ArrayList;

public class User {
	private String userName , password;
	private ArrayList<String> directories;
	
	public User( String _userName , String _password ) {
		userName = _userName; 
		password = _password;
		directories = new ArrayList<String>();
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean login( String userName , String password ) {
		if ( this.userName.equalsIgnoreCase(userName) && this.password.equals(password) ) {
			return true;
		}
		else {
			return false;
		}
	}
	public ArrayList<String> getDirectories() {
		return directories;
	}
	public void add(String dir) {
		this.directories.add(dir);
	}
	public void delete( String dir ) {
		for ( int i = 0; i < directories.size(); ++i ) {
			if ( directories.get(i).equals(dir) ) {
				directories.remove(i);
				return;
			}
		}
	}
}
