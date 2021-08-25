package kr.tracom.brt.domain.VH0300;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class VH0300Service extends ServiceSupport {

	@Autowired
	private VH0300Mapper vh0300Mapper;
	
	public List VH0300G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vh0300Mapper.VH0300G0R0(map);
	}
	
	public List VH0300SHI0() throws Exception {
		return vh0300Mapper.VH0300SHI0();
		
	}

	public Map VH0300G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_ACRT_LOC_STOP_LOG");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += vh0300Mapper.VH0300G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += vh0300Mapper.VH0300G1D0(data);
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
	
	public List VH0300G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_VH0300G1");
		return vh0300Mapper.VH0300G1R0(param);
	}
	 	
}
