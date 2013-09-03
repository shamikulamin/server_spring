package com.campusconnect.server.controller.helper;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;

import com.campusconnect.server.domain.Device;
import com.campusconnect.server.domain.IosDevice;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;

public class CampusConnectAndroidHelper {
	public static final String minSupportedVersion = "1.0.0";
	private static final boolean isLinux = false;
	private ServletContext servletContext;
	
	public CampusConnectAndroidHelper(ServletContext sc) {
		servletContext = sc;
	}
	
	public void registerDevice(String regID){
        DeviceHelper helper = new DeviceHelper(servletContext);
        helper.insertDevice(new Device(regID));
	}
	
	public void unregisterDevice(String regID) {
        DeviceHelper helper = new DeviceHelper(servletContext);
        helper.deleteById(regID);
	}
	
	public void registerIosDevice(String regID) {
		IosDeviceHelper helper = new IosDeviceHelper(servletContext);
		helper.insertDevice(new IosDevice(regID));
	}
	
	public void unregisterIosDevice(String regID) {
		IosDeviceHelper helper = new IosDeviceHelper(servletContext);
		helper.deleteById(regID);
	}
	
	public void registerIosDeviceIdChanged(String regID, String oldID) {
		IosDeviceHelper helper = new IosDeviceHelper(servletContext);
		helper.deleteById(oldID);
		helper.insertDevice(new IosDevice(regID));
	}

	public String loginAndroid(String netid_in, String pass_in, String version_in) {
		String plain_netid = "";
		String plain_pass = "";
		
		/* Decode from Base64 */
		byte[] enc_netid_bytes = Base64.decodeBase64(netid_in);
		byte[] enc_pass_bytes = Base64.decodeBase64(pass_in);
		
		String[] version_arr = version_in.split("_");
		String app_ver = version_arr[0];
		String os_type = version_arr[1];

		// 1.0.0_IOS
		// 1.0.0_ANDROID
		/* Get our PrivateKey and decrypt parameters */
		if( os_type.equals("ANDROID") ) {
			PrivateKey privateKey = getPrivKeyAndroid();
			plain_netid = decrypt_android(enc_netid_bytes, privateKey);
			plain_pass = decrypt_android(enc_pass_bytes, privateKey);
		} else {
			PrivateKey privateKey = getPrivKeyIos();
			plain_netid = decrypt_ios(enc_netid_bytes, privateKey);
			plain_pass = decrypt_ios(enc_pass_bytes, privateKey);
		}
		
		/* Not Authenticated */
		if( plain_netid == "" || plain_pass == "" )
			return "";

		/*
		//Remove later
		if( plain_netid.equals("test") && plain_pass.equals("123") ) {
			/* If app version is lower than min supported version then just return 
			if( !checkVersion(app_ver) )
				return "update";
			return "true";
		}*/
		
		if( !checkVersion(app_ver) )
			return "update";
		
		/* Check if credentials are valid
		 * - If not an exception is thrown and we return
		 * - Otherwise, the search was successful and we first write
		 * 		true to the output stream then return 
		 * */
		try {
			search(plain_netid,plain_pass);
		} catch (LDAPException e) {
			return "";
		} catch(GeneralSecurityException e){
            return "";
		}
		return "true"; //Successful LDAP query
	}
	
	
	 	/**
		 * Gets the current min supported version by returning an array where:
		 * 	 [0] is Major revision number
		 * 	 [1] is Minor revision number
		 * 	 [2] is Point revision number
		 * */
		private int[] getVersionArray( String ver ) {
			int[] intVerNums = new int[3];
			String[] verNums = ver.split("\\.");
			intVerNums[0] = Integer.parseInt(verNums[0]);
			intVerNums[1] = Integer.parseInt(verNums[1]);
			intVerNums[2] = Integer.parseInt(verNums[2]);
			return intVerNums;
		}
		
		private boolean checkVersion(String appVersion) {

		    int[] appVer = getVersionArray(appVersion);
		    int[] minVer = getVersionArray(minSupportedVersion);

		    for (int i = 0; i < minVer.length; i++)
		        if (appVer[i] != minVer[i])
		            return appVer[i] > minVer[i];

		    return true;
		}
	        
		private PrivateKey getPrivKeyAndroid() {
			ObjectInputStream inputStream;
			PrivateKey pk = null;
			
			try {
				if( isLinux ) {	// Linux
					inputStream = new ObjectInputStream(new FileInputStream("/usr/key/private.key"));
				} else {		// Windows
					inputStream = new ObjectInputStream(new FileInputStream("C:/private.key"));
				}
				pk = (PrivateKey) inputStream.readObject();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	                catch(ClassNotFoundException e){
	                    e.printStackTrace();
	                }
			
			return pk;
		}
		
		/**
		 * Decrypt text using private key.
		 * 
		 * @param text
		 *          :encrypted text
		 * @param key
		 *          :The private key
		 * @return plain text
		 * @throws java.lang.Exception
		 */
		private static String decrypt_android(byte[] text, PrivateKey key) {
			byte[] dectyptedText = null;
			try {
				final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");	// Get an RSA cipher object and print the provider

				/* Decrypt the text using the private key */
				cipher.init(Cipher.DECRYPT_MODE, key);	
				dectyptedText = cipher.doFinal(text);
			} catch (Exception ex) {
				ex.printStackTrace();
				return "";
			}
			return new String(dectyptedText);
		}
		
		private PrivateKey getPrivKeyIos() {
			try {
				File f = null;
				if( isLinux ) {	// Linux
					f = new File("/usr/key/private_key.der");
				} else {		// Windows
					f = new File("C:/private_key.der");
				}
				FileInputStream fis = new FileInputStream(f);
				DataInputStream dis = new DataInputStream(fis);
				byte[] keyBytes = new byte[(int)f.length()];
				dis.readFully(keyBytes);
				dis.close();
				
				PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
				KeyFactory kf = KeyFactory.getInstance("RSA");
				return kf.generatePrivate(spec);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Decrypt text using private key.
		 * 
		 * @param text
		 *          :encrypted text
		 * @param key
		 *          :The private key
		 * @return plain text
		 * @throws UnsupportedEncodingException 
		 * @throws java.lang.Exception
		 */
		private static String decrypt_ios(byte[] text, PrivateKey key) {
			byte[] dectyptedText = null;
			try {
				final Cipher cipher = Cipher.getInstance("RSA");	// Get an RSA cipher object and print the provider

				/* Decrypt the text using the private key */
				cipher.init(Cipher.DECRYPT_MODE, key);	
				dectyptedText = cipher.doFinal(text);
				return new String(dectyptedText, "UTF8");
			} catch (Exception ex) {
				ex.printStackTrace();
				return "";
			}
		}
		
		public SearchResultEntry getDetailsLDAP(String enc_user, String enc_pass, String os_type) throws GeneralSecurityException, LDAPException {
			String plain_netid = "", plain_pass = "";
			
			/* Decode from Base64 */
			byte[] enc_netid_bytes = Base64.decodeBase64(enc_user);
			byte[] enc_pass_bytes = Base64.decodeBase64(enc_pass);
			
			if( os_type.equals("ANDROID") ) {
				PrivateKey privateKey = getPrivKeyAndroid();
				plain_netid = decrypt_android(enc_netid_bytes, privateKey);
				plain_pass = decrypt_android(enc_pass_bytes, privateKey);
			} else {
				PrivateKey privateKey = getPrivKeyIos();
				plain_netid = decrypt_ios(enc_netid_bytes, privateKey);
				plain_pass = decrypt_ios(enc_pass_bytes, privateKey);
			}
			
			String[] attributes = new String[] { "cn", "uid", "utaEmplID", "homePostalAddress", "homePhone" };	// the attributes to search for
	        
	        /* Connect to LDAP */
	        SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
	        SSLSocketFactory socketFactory = sslUtil.createSSLSocketFactory();
	        LDAPConnection ldap = new LDAPConnection(socketFactory, 
	                					"ldap.cedar.uta.edu",
	                					636,
	                					"uid=" + plain_netid + ",cn=accounts,dc=uta,dc=edu",
	                					plain_pass
	        );
	        
	        /* Search LDAP */
	        SearchResult sr = ldap.search(
	                "cn=accounts,dc=uta,dc=edu", 
	                SearchScope.SUB,
	                "(uid=" + plain_netid + ")",
	                attributes
	        );
	        List<SearchResultEntry> srelist = sr.getSearchEntries();
	        return srelist.get(0);
		}
		
	    private static boolean search(String username, String password) throws GeneralSecurityException, LDAPException {
	        
	        String[] attributes = new String[] { "cn", "uid", "utaEmplID", "homePostalAddress", "homePhone" };	// the attributes to search for
	        
	        /* Connect to LDAP */
	        SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
	        SSLSocketFactory socketFactory = sslUtil.createSSLSocketFactory();
	        LDAPConnection ldap = new LDAPConnection(socketFactory, 
	                					"ldap.cedar.uta.edu",
	                					636,
	                					"uid=" + username + ",cn=accounts,dc=uta,dc=edu",
	                					password
	        );
	        
	        /* Search LDAP */
	        SearchResult sr = ldap.search(
	                "cn=accounts,dc=uta,dc=edu", 
	                SearchScope.SUB,
	                "(uid=" + username + ")",
	                attributes
	        );
	        
	        if (sr.getEntryCount() == 0)
	            return false;
	        return true;
	    }
}
