package kr.tracom.bms.domain.SI0102;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.SI0101.SI0101Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class SI0102Service extends ServiceSupport {
	
	@Autowired
	private SI0102Mapper si0102Mapper;
	
	public List SI0102G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0102Mapper.SI0102G0R0(map);
	}

	public Map SI0102G0K0() throws Exception {
		return si0102Mapper.SI0102G0K0(); 
	}
	
	public List SI0102SHI0() throws Exception {
		return si0102Mapper.SI0102SHI0();
	}	
	
/*	public Map SI0102G0S0() throws Exception {
		int iCnt_grd0 = 0;
		int iCnt_grd1 = 0;
		int uCnt_grd0 = 0;
		int uCnt_grd1 = 0;
		int dCnt_grd0 = 0;		
		int dCnt_grd1 = 0;		
		
		List<Map<String, Object>> param_grd0 = getSimpleList("dlt_BMS_TRANSCOMP_MST");
		List<Map<String, Object>> param_grd1 = getSimpleList("dlt_BMS_GRG_COMP_CMPSTN");
		
		try {
			for (int i = 0; i < param_grd0.size(); i++) {
				Map data_grd0 = (Map) param_grd0.get(i);
				String rowStatus_grd0 = (String) data_grd0.get("rowStatus");
				if (rowStatus_grd0.equals("C")) {
					iCnt_grd0 += si0102Mapper.SI0102G0I0(data_grd0);
					
					for (int j = 0; j < param_grd1.size(); j++) {
						Map data_grd1 = (Map) param_grd1.get(j);
						String rowStatus_grd1 = (String) data_grd1.get("rowStatus");
						if (rowStatus_grd1.equals("C")) {
							iCnt_grd1 += si0102Mapper.SI0102G1I0(data_grd1);
						}	
					}
				} else if (rowStatus_grd0.equals("U")) {
					for (int j = 0; j < param_grd0.size(); j++) {
						Map data_grd1 = (Map) param_grd1.get(j);
						String rowStatus_grd1 = (String) data_grd1.get("rowStatus");
						if (rowStatus_grd1.equals("C")) {
							iCnt_grd1 += si0102Mapper.SI0102G1I0(data_grd1);
						} else if (rowStatus_grd1.equals("D")) {
							dCnt_grd1 += si0102Mapper.SI0102G1D0(data_grd1);
						}
					}
					uCnt_grd0 += si0102Mapper.SI0102G0U0(data_grd0);
				} else if (rowStatus_grd0.equals("D")) {
					si0102Mapper.SI0102G1D1(data_grd0);
					dCnt_grd0 += si0102Mapper.SI0102G0D0(data_grd0);
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

		
		Map result = saveResult(iCnt_grd0, iCnt_grd1, uCnt_grd0, uCnt_grd1, dCnt_grd0, dCnt_grd1);
		
		return result;		
		
		
	}*/
	
	public Map SI0102G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_TRANSCOMP_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0102Mapper.SI0102G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0102Mapper.SI0102G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0102Mapper.SI0102G0D0(data);
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
	
	public List SI0102G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_COMPID");
		return si0102Mapper.SI0102G1R0(param);
	}
	
	public List SI0102P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_GRG_MST");
		return si0102Mapper.SI0102P0R0(map);
	}

	public Map SI0102G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_GRG_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0102Mapper.SI0102G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0102Mapper.SI0102G1D0(data);
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
