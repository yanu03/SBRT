package kr.tracom.bms.domain.PI1001;

import java.util.List;
import java.util.Map;
import kr.tracom.cm.support.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Map PI1001G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_NOTICE_MST");
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");

			/*for (String key : data.keySet()) {
				if (data.get(key).equals("")) {
					data.put(key, null);
				}
			}*/
			if (rowStatus.equals("C"))
				iCnt += this.PI1001Mapper.PI1001G0I0(data);
			else if (rowStatus.equals("U"))
				uCnt += this.PI1001Mapper.PI1001G0U0(data);
			else if (rowStatus.equals("D")) {
				dCnt += this.PI1001Mapper.PI1001G0D0(data);
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