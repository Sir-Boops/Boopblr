package me.boops.parsers;

public class UrlToUsable {
	
	public String user_name;
	public long pub_id;
	
	public UrlToUsable(String url){

		String user_url;
		long id;
		
		//Remove The Http(s)
		if(url.contains("https://")){
			
			//Remove The Https://
			user_url = url.replace("https://", "").replace("/post/", " ").split(" ")[0];
			id = Long.parseLong(url.replace("https://", "").replace("/post/", " ").split(" ")[1]);
		} else {
			
			//Remove The Http://
			user_url = url.replace("http://", "").replace("/post/", " ").split(" ")[0];
			id = Long.parseLong(url.replace("http://", "").replace("/post/", " ").split(" ")[1]);
		}
		
		//Set Them
		user_name = user_url;
		pub_id = id;
	}

}
