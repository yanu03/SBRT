package kr.tracom.bms.domain.PI0601;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0601.PI0601Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0601Service extends ServiceSupport  {

	@Autowired
	private PI0601Mapper pi0601Mapper;
	
	public List PI0601G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List returnList = pi0601Mapper.PI0601G0R0(map);
		
		for(Object obj:returnList) {
			Map<String, Object> temp = (Map<String, Object>)obj;
			temp.put("FILE_PATH", "/fileUpload/PI0601/"+temp.get("VDO_ID"));			
		}
		
		return returnList;
	}
	
	public List PI0601SHI0() throws Exception {
		return pi0601Mapper.PI0601SHI0();
	}
	
	public List PI0601P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0601Mapper.PI0601P0R0(map);
	}

	public Map PI0601G0K0() throws Exception {
		return pi0601Mapper.PI0601G0K0(); 
	}
	
	public Map PI0601G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BIT_VDO_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += pi0601Mapper.PI0601G0I0(data);
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("VDO_ID").equals(data.get("FILE_NM"))==false))
						{
							doMoveFile("up/","PI0601/",data.get("FILE_NM").toString(),data.get("VDO_ID").toString());
						}					
				} else if (rowStatus.equals("U")) {
					uCnt += pi0601Mapper.PI0601G0U0(data);
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("VDO_ID").equals(data.get("FILE_NM"))==false)) 
						{
							doMoveFile("up/","PI0601/",data.get("FILE_NM").toString(),data.get("VDO_ID").toString());
						}					
				} else if (rowStatus.equals("D")) {
					dCnt += pi0601Mapper.PI0601G0D0(data);
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
