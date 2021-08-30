package kr.tracom.bms.domain.PI0501;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0501Service extends ServiceSupport {

	@Autowired
	private PI0501Mapper pi0501Mapper;
	
	public List PI0501G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List returnList = pi0501Mapper.PI0501G0R0(map);
		
		for(Object obj:returnList) {
			Map<String, Object> temp = (Map<String, Object>)obj;
			temp.put("FILE_PATH", "/fileUpload/video/"+temp.get("FILE_NM"));			
		}
		
		return returnList;
	}
	
	public List PI0501SHI0() throws Exception {
		return pi0501Mapper.PI0501SHI0();
	}
	
	public List PI0501P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0501Mapper.PI0501P0R0(map);
	}

	public Map PI0501G0K0() throws Exception {
		return pi0501Mapper.PI0501G0K0(); 
	}
	
	public Map PI0501G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VDO_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("VDO_ID").equals(data.get("FILE_NM"))==false))
						{
							doMoveFile("up/", "video/", data.get("FILE_NM").toString(), data.get("VDO_ID").toString()+ "."+ data.get("FILE_EXTENSION").toString());
							data.put("FILE_NM", data.get("VDO_ID").toString()+ "."+ data.get("FILE_EXTENSION").toString());
						}	
					iCnt += pi0501Mapper.PI0501G0I0(data);				
				} else if (rowStatus.equals("U")) {
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("VDO_ID").equals(data.get("FILE_NM"))==false)) 
						{
							doMoveFile("up/","video/",data.get("FILE_NM").toString(),data.get("VDO_ID").toString()+ "." + data.get("FILE_EXTENSION").toString());
							data.put("FILE_NM", data.get("VDO_ID").toString()+ "."+ data.get("FILE_EXTENSION").toString());
						}	
					uCnt += pi0501Mapper.PI0501G0U0(data);				
				} else if (rowStatus.equals("D")) {
					dCnt += pi0501Mapper.PI0501G0D0(data);
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
	
}
