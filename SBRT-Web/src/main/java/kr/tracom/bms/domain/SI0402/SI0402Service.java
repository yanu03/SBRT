package kr.tracom.bms.domain.SI0402;

import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;

@Service
public class SI0402Service extends ServiceSupport {

	@Autowired
	private SI0402Mapper si0402Mapper;
	
	public List SI0402G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402G0R0(map);
	}

	public Map SI0402G0K0() throws Exception {
		return si0402Mapper.SI0402G0K0(); 
	}
	
	public List SI0402SHI0() throws Exception {
		return si0402Mapper.SI0402SHI0();
	}	
	
	public Map SI0402G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_NODE_CMPSTN");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String linkKey = null;
				
				String rowStatus = (String) data.get("rowStatus");
				
				if (rowStatus.equals("C")) {
					Map key = si0402Mapper.SI0402G1K0();
					if(Constants.NODE_TYPE_VERTEX.equals((String) data.get("NODE_TYPE"))==false
						&&Constants.NODE_TYPE_SOUND.equals((String) data.get("NODE_TYPE"))==false
					) {
						if(i<param.size()-1)
						{
							Map linkKeyMap = si0402Mapper.SI0402G1K1();
							linkKey = (String)linkKeyMap.get("SEQ");
						}
						data.put("LINK_NODE_YN","Y");
					}
					
					if(linkKey!=null) {
						data.put("LINK_ID",linkKey);	
					}
					data.put("NODE_ID",key.get("SEQ"));	
					iCnt += si0402Mapper.SI0402G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0402Mapper.SI0402G1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0402Mapper.SI0402G1D0(data);
				}
			}
			
			if(param.size()>0) {
				si0402Mapper.SI0402G1DA1(param.get(0));
				List<Map<String, Object>> routNodeList = si0402Mapper.SI0402G1R1(param.get(0));
				for (int i = 0; i < routNodeList.size()-1; i++) {
					Map data = routNodeList.get(i);
					data.put("LINK_SN",(i+1));
					data.put("ST_NODE_ID",param.get(i).get("NODE_ID"));
					data.put("ED_NODE_ID",param.get(i+1).get("NODE_ID"));
					String linkNm = param.get(i).get("NODE_NM") + "-" + param.get(i+1).get("NODE_NM");
					data.put("LINK_NM",linkNm);
					si0402Mapper.SI0402G1I1(data);
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
	
	public List SI0402G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return si0402Mapper.SI0402G1R0(param);
	}
	
	public List SI0402P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P0R0(map);
	}
	
	public List SI0402P1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P1R0(map);
	}
	
	public List SI0402P2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P2R0(map);
	}
	
	public List SI0402P3R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P3R0(map);
	}	
}
