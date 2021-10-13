package kr.tracom.bms.domain.PI0202;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;

@Service
public class PI0202Service extends ServiceSupport{

	@Autowired
	private PI0202Mapper pi0202Mapper;
	
	public List PI0202SHI0() throws Exception {
		return pi0202Mapper.PI0202SHI0();
	}	
	
	public Map PI0202G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_NODE_CMPSTN");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				
				String rowStatus = (String) data.get("rowStatus");
				
				if (rowStatus.equals("C")) {
					Map key = null;
					if(CommonUtil.empty(data.get("NODE_ID"))){
						key = pi0202Mapper.PI0202G1K0();
					}
					if(key!=null)data.put("NODE_ID",key.get("SEQ"));
					
					if(Constants.NODE_TYPE_VERTEX.equals((String) data.get("NODE_TYPE"))==false
						&&Constants.NODE_TYPE_SOUND.equals((String) data.get("NODE_TYPE"))==false
					) {
						data.put("LINK_NODE_YN","Y");
					}
					
					iCnt += pi0202Mapper.PI0202G1I0(data);
				} else if (rowStatus.equals("U")) {
					if(Constants.NODE_TYPE_VERTEX.equals((String) data.get("NODE_TYPE"))==false
						&&Constants.NODE_TYPE_SOUND.equals((String) data.get("NODE_TYPE"))==false
					) {
						data.put("LINK_NODE_YN","Y");
					}
					uCnt += pi0202Mapper.PI0202G1U0(data);
					
				} else if (rowStatus.equals("D")) {
					dCnt += pi0202Mapper.PI0202G1D0(data);
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

	public List PI0202G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return pi0202Mapper.PI0202G1R0(param);
	}

	public List<Map> PI0202P0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return pi0202Mapper.PI0202P0R0(param);
	}
}
