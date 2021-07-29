package application;

/**
 * This is a class is for the user object.
 * It holds the credential info for when a
 * user logs in.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class User {
	private String acc_id;
	private String username;
	private String password;
	
	/**
	 * This is the constructor for the User class that accepts
	 * an account id number, username, and password.
	 * @param acc_id Account id number of the user.
	 * @param username Username of the user.
	 * @param password Password of the user.
	 */
	public User(String acc_id, String username, String password)
	{
		this.acc_id = acc_id;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * This method returns the instance of user.
	 * @return A User instance.
	 */
	public User getUser()
	{
		return this;
	}
	
	/**
	 * This method is used to set the User's members
	 * with the give parameters.
	 * @param acc_id Account id number of the user.
	 * @param username Username of the user.
	 * @param password Password of the user.
	 */
	public void setUserInfo(String acc_id, String username, String password)
	{
		this.acc_id = acc_id;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * This method returns the account number of the User instance.
	 * @return A String for the account number.
	 */
	public String getAcc_id()
	{
		return acc_id;
	}

	/**
	 * This method returns the username of the User instance.
	 * @return A String containing the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method is used to set the User's username member
	 * with the give parameter.
	 * @param username A String containing the username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method returns the password of the User instance.
	 * @return A String containing the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method is used to set the User's password member
	 * with the give parameter.
	 * @param password A String containing the password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method is used to set the User's acc_id member
	 * with the give parameter.
	 * @param acc_id A String containing the account id.
	 */
	public void setAcc_id(String acc_id) {
		this.acc_id = acc_id;
	}
	
	
}