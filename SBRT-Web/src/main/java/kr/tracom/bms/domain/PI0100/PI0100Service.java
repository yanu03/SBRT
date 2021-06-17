package kr.tracom.bms.domain.PI0100;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.DateUtil;

@Service
public class PI0100Service extends ServiceSupport{

	@Autowired
	private PI0100Mapper pI0100Mapper;
		
	public List<Map> PI0100G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_BMS_NOTICE_MST");
		return pI0100Mapper.PI0100G0R0(param);
	}
	
	public Map PI0100G0K0() throws Exception {
		return pI0100Mapper.PI0100G0K0();
	}	
	
	public List<Map> PI0100G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return pI0100Mapper.PI0100G1R0(param);
	}
	
	public List<Map> PI0100G2R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return pI0100Mapper.PI0100G2R0(param);
	}
	
	public List<Map> PI0100P0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return pI0100Mapper.PI0100P0R0(param);
	}
	
	public List<Map> PI0100P1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		String temp[] = param.get("STTN_RCPT_IDS").toString().split(",");
		param.put("STTN_RCPT_IDS", temp);
		return pI0100Mapper.PI0100P1R0(param);
	}
	
	public Map PI0100G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_NOTICE_MST");
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			// 데이터베이스 date 타입일때 공백으로 들어가면 에러나는 사항 임시 수정
			for (String key : data.keySet()) {
				if (data.get(key).equals("")) {
					data.put(key, null);
				}
			}			
			if (rowStatus.equals("C")) {
				iCnt += pI0100Mapper.PI0100G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += pI0100Mapper.PI0100G0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += pI0100Mapper.PI0100G0D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
	
	public Map PI0100G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_VHC_MST");
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");		
			if (rowStatus.equals("C")) {
				iCnt += pI0100Mapper.PI0100G1I0(data);
			}else if (rowStatus.equals("D")) {
				dCnt += pI0100Mapper.PI0100G1D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);	
		return result;
	}

	public Map PI0100G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_STTN_MST");
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");		
			if (rowStatus.equals("C")) {
				iCnt += pI0100Mapper.PI0100G2I0(data);
			}else if (rowStatus.equals("D")) {
				dCnt += pI0100Mapper.PI0100G2D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
}
