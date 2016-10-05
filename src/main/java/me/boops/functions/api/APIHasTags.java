package me.boops.functions.api;

public class APIHasTags {

	public boolean hasTags = false;

	public APIHasTags(long id, String blog_name) throws Exception {

		APIGetPost GetPost = new APIGetPost();
		GetPost.Get(id, blog_name);

		if (!GetPost.getPost().getJSONArray("posts").getJSONObject(0).isNull("tags")) {
			if (GetPost.getPost().getJSONArray("posts").getJSONObject(0).getJSONArray("tags").length() > 0) {
				hasTags = true;
			} else {
				hasTags = false;
			}
		} else {
			hasTags = false;
		}
	}
}
