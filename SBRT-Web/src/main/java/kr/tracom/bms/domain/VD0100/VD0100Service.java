package kr.tracom.bms.domain.VD0100;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.VD0100.VD0100Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class VD0100Service extends ServiceSupport {
	
	@Autowired
	private VD0100Mapper vd0100Mapper;
	
	public List VD0100G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vd0100Mapper.VD0100G0R0(map);
	}

	public List VD0100SHI0() throws Exception {
		return vd0100Mapper.VD0100SHI0();
	}
	
	public List VD0100SHI1() throws Exception {
		return vd0100Mapper.VD0100SHI1();
	}
	
	public List VD0100G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch");
		return vd0100Mapper.VD0100G1R0(param);
	}

	public Map VD0100G1K0() throws Exception {
		return vd0100Mapper.VD0100G1K0(); 
	}
	
	public Map VD0100G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_DVC_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += vd0100Mapper.VD0100G1I0(data);
					if(data.getOrDefault("MNG_ID", "").toString().length() == 10) {
						//INSERT
						vd0100Mapper.VD0100G1I2(data);
					}
				} else if (rowStatus.equals("U")) {
					uCnt += vd0100Mapper.VD0100G1U0(data);
					if(data.getOrDefault("MNG_ID", "").toString().length() == 10) {
						//UPDATE
						vd0100Mapper.VD0100G1U2(data);
					}
				} else if (rowStatus.equals("D")) {
					dCnt += vd0100Mapper.VD0100G1D0(data);
					if(data.getOrDefault("MNG_ID", "").toString().length() == 10) {
						//DELETE
						vd0100Mapper.VD0100G1D2(data);
					}
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
