package kr.tracom.cm.support;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {
		
	    public static String getEncrypt(String source, byte[] salt)
	    {
	        String result = "";
	        if( salt == null) salt = "".getBytes(); // salt값 없을 때  
	        try
	        {
	            byte[] a = source.getBytes();
	            byte[] bytes = new byte[a.length + salt.length];
	            System.arraycopy(a, 0, bytes, 0, a.length);
	            System.arraycopy(salt, 0, bytes, a.length, salt.length);
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            md.update(bytes);
	            byte[] byteData = md.digest();			// 한번만 수행함. 
	            
	            StringBuffer sb = new StringBuffer();
	            for (int i = 0; i < byteData.length; ++i)
	            {
	                sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
	            }
	            result = sb.toString();
	        } catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        return result;
	    }

	    public static String getHash(String source, byte[] salt, int len) throws NoSuchAlgorithmException
	    { // len 횟수만큼 digest 반복 실행하도록 함.  
	    	String result = ""; 
	    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    	digest.reset();
	    	digest.update(salt);
	    	byte[] input;
	    	StringBuffer sb = new StringBuffer();
			try
			{
				input = digest.digest(source.getBytes("UTF-8"));
		    	for (int i=0; i< len; i++) // 반복횟수만큼 실행 1000 이상의 값이 좋다.
		    	{
		    		digest.reset();
		    		input = digest.digest(input);
		    	}	

		    	
	            for (int i = 0; i < input.length; ++i) 
	            {
	                sb.append(Integer.toString((input[i] & 0xFF) + 256, 16).substring(1));
	            }
	            result = sb.toString();
	            
			}
			catch(UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result; 
	    }
	    

	    public static String getHashTwo(String source, byte[] salt, String userId)
	    { // len 대신 사용자별로 다른 반복 횟수 사용 
	    	String result = ""; 
	    	byte byteId[]; 
	    	StringBuffer buffId = new StringBuffer();
	    	int intId = 1000;
			try
			{
		    	byteId = userId.getBytes(); 
			    
		    	for (int i = 0; i < byteId.length; i++)
		    	{	
		    		if( i > 1) break; 
		    	    // byte 값을 Hex 값으로 바꾸기.
		    		buffId.append(String.format("%d",byteId[i]));
		    	}
		  	    intId = (int)Integer.valueOf( buffId.toString()); 
			    // 여기까지 반복 수행 횟수를 유저별로 고정된 숫자값 얻어냄.  					
				result = getHash(source, salt, (int)intId %2000 );
			}
			catch(NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				System.out.println(" userId : "+ userId+ " => "+ buffId.toString() + " => " + intId + " 반복횟수: "+ (int)intId %2000 + " result: " + result ); 
			}
			return result; 
	    }
	    
}
