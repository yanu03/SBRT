package kr.tracom.bms.domain.SM0800;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;

@Service
public class SM0800Service extends ServiceSupport{
	
	private String salt = "tracom3452TRACOM#$%@";
	
	@Autowired
	private SM0800Mapper sm0800Mapper;
	
	public List SM0800G0R0() throws Exception{
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return sm0800Mapper.SM0800G0R0(map);
	}
	
	public List SM0800SHI0() throws Exception {
		return sm0800Mapper.SM0800SHI0();
	}
	
	public Map SM0800G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_API_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				
				String apiKey = makeKey(data);
				data.put("API_KEY", apiKey);
				
				if (rowStatus.equals("C")) {
					iCnt += sm0800Mapper.SM0800G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += sm0800Mapper.SM0800G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += sm0800Mapper.SM0800G0D0(data);
				} 
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}

		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
		
	}
	
	public Map SM0800G0K0() throws Exception {
		return sm0800Mapper.SM0800G0K0(); 
	}
	
	private String makeKey(Map data) {
		String ip = (String) data.get("ALLOWED_IP");
    	String endPoint = (String) data.get("API_END_POINT");
    	
    	return makeHash(ip, endPoint);
	}
	
	/**
	 * 키 생성 
	 * SHA256 해시
	 * ip
	 * salt = tracomKey & endPoint 
	 *  **/
	public String makeHash(String ip, String endPoint) {
		MessageDigest md = null;
		String salt2 = endPoint;
		String salt3 = String.valueOf(Math.random());
		String msg = ip + salt + salt2 + salt3;
		
		String result = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(msg.getBytes());
			byte msgByte[] = md.digest();
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < msgByte.length; i++) {
				sb.append(Integer.toString((msgByte[i]&0xff) + 0x100, 16).substring(1));
			}
			
			result = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			result = null;
		}
		
		return result;
	}
		
}
