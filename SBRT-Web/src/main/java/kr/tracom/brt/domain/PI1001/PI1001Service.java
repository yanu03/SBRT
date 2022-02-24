package kr.tracom.brt.domain.PI1001;

import java.util.List;
import java.util.Map;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class PI1001Service extends ServiceSupport {

	@Autowired
	private PI1001Mapper PI1001Mapper;

	public List<Map> PI1001G0R0() throws Exception {
		Map param = getSimpleDataMap("dma_search");
		return this.PI1001Mapper.PI1001G0R0(param);
	}

	public Map PI1001G0K0() throws Exception {
		return this.PI1001Mapper.PI1001G0K0();
	}

	public List PI1001SHI0() throws Exception {
		return this.PI1001Mapper.PI1001SHI0();
	}
	
	public List PI1001SHI1() throws Exception {
		return this.PI1001Mapper.PI1001SHI1();
	}

	public List<Map> PI1001G1R0() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return this.PI1001Mapper.PI1001G1R0(param);
	}

	public List<Map> PI1001G2R0() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return this.PI1001Mapper.PI1001G2R0(param);
	}

	public List<Map> PI1001P0R0() throws Exception {
		Map param = getSimpleDataMap("dma_search");
		return this.PI1001Mapper.PI1001P0R0(param);
	}

	public List<Map> PI1001P1R0() throws Exception {
		Map param = getSimpleDataMap("dma_search");

		return this.PI1001Mapper.PI1001P1R0(param);
	}
	
	public List<Map> PI1001P2R0() throws Exception {
		Map param = getSimpleDataMap("dma_search");

		return this.PI1001Mapper.PI1001P2R0(param);
	}
	
	public List<Map> PI1001P3R0() throws Exception {
		Map param = getSimpleDataMap("dma_search");

		return this.PI1001Mapper.PI1001P3R0(param);
	}

	public Map PI1001G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_NOTICE_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C"))
					iCnt += this.PI1001Mapper.PI1001G0I0(data);
				else if (rowStatus.equals("U"))
					uCnt += this.PI1001Mapper.PI1001G0U0(data);
				else if (rowStatus.equals("D")) {
					dCnt += this.PI1001Mapper.PI1001G0D0(data);
				}
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "�ߺ��� Ű���� �����Ͱ� �����մϴ�.");
			}
			else
			{
				throw e;
			}		
		}

		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;
	}

	public Map PI1001G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_VHC_MST");
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C"))
				iCnt += this.PI1001Mapper.PI1001G1I0(data);
			else if (rowStatus.equals("D")) {
				dCnt += this.PI1001Mapper.PI1001G1D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}

	public Map PI1001G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_STTN_MST");
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C"))
				iCnt += this.PI1001Mapper.PI1001G2I0(data);
			else if (rowStatus.equals("D")) {
				dCnt += this.PI1001Mapper.PI1001G2D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
}