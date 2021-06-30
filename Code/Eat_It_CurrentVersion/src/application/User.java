package application;

public class User {

	private String acc_id;
	private String username;
	private String password;
	
	public User(String acc_id, String username, String password)
	{
		this.acc_id = acc_id;
		this.username = username;
		this.password = password;
	}
	
	public User getUser()
	{
		return this;
	}
	
	public void setUserInfo(String acc_id, String username, String password)
	{
		this.acc_id = acc_id;
		this.username = username;
		this.password = password;
	}
	
	public String getAcc_id()
	{
		return acc_id;
	}
}
