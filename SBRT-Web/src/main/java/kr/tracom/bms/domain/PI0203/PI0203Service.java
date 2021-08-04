package kr.tracom.bms.domain.PI0203;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0203.PI0203Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class PI0203Service extends ServiceSupport{

	@Autowired
	private PI0203Mapper PI0203Mapper;
		
	public List<Map> PI0203G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0203Mapper.PI0203G0R0(param);
	}
	
	public List PI0203SHI0() throws Exception {
		return PI0203Mapper.PI0203SHI0();
	}	
	
	public Map PI0203G0K0() throws Exception {
		return PI0203Mapper.PI0203G0K0();
	}	
	
	public Map PI0203G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_VOC_INFO");
        Map<String, Object> AUDIO_INFO = getSimpleDataMap("dma_AUDIO_INFO");
        
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			
			if (rowStatus.equals("C")) {
				iCnt += PI0203Mapper.PI0203G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += PI0203Mapper.PI0203G0U0(data);
				
                doMoveFile("up/", "audio/", AUDIO_INFO.get("AUDIO_NM").toString(), AUDIO_INFO.get("AUDIO_NM").toString());
			} else if (rowStatus.equals("D")) {
				dCnt += PI0203Mapper.PI0203G0D0(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}
}
