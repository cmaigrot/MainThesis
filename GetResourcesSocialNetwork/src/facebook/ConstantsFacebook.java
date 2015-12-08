package facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

public class ConstantsFacebook {
	// Tokens for identification
	public static final String APP_ID = "1024002420955057";
	public static final String APP_SECRET = "6f6f3a38df7928c6ef34168177cb880a";

	public static final String ACCESS_TOKEN_STRING = "CAACEdEose0cBAKFQrCnyZAJglYZBh046q1BZBawzWb1majnmofBOJmmL86gEqHBqGSnuj6QxCqx6tGqa92SvUsFZBPUn2ylXD2lKhGbChz5FUagX5e7qUGpqXMTdeLvoDSOcbMf6kv6ybMhkprZC1m408NMEMnDpEKcFD2kAUkep48borNOBuLTKZAf6ZCtQ5KCSu9OZCQwkDxSrKySdwqw8";

	public static String getPageName(String name) throws FacebookException {
		Facebook facebook;
		// Generate facebook instance.
		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(APP_ID, APP_SECRET);
		// Set access token.
		AccessToken token = new AccessToken(ACCESS_TOKEN_STRING);
		facebook.setOAuthAccessToken(token);
		return facebook.getPage(name).getName();
	}
}
