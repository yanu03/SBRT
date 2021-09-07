package kr.tracom.brt.domain.VI0100;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class VI0100Service extends ServiceSupport {

	@Autowired
	private VI0100Mapper vi0100Mapper;
	
	public List VI0100G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0100Mapper.VI0100G0R0(map);
	}
	
	public List VI0100P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0100Mapper.VI0100P0R0(map);
	}
	
	public List VI0100SHI0() throws Exception {
		return vi0100Mapper.VI0100SHI0();
		
	}

	public Map VI0100G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_VIOLT_HIS");
		Map map = getSimpleDataMap("dma_param_VI0100G1");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					
					data.put("OPER_DT",map.get("OPER_DT"));
					data.put("REP_ROUT_ID",map.get("REP_ROUT_ID"));
					data.put("ROUT_ID",map.get("ROUT_ID"));
					data.put("VHC_ID",map.get("VHC_ID"));
					data.put("VHC_NO",map.get("VHC_NO"));
					
					iCnt += vi0100Mapper.VI0100G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += vi0100Mapper.VI0100G1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += vi0100Mapper.VI0100G1D0(data);
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
	
	public List VI0100G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_VI0100G1");
		return vi0100Mapper.VI0100G1R0(param);
	}
	 	
}
