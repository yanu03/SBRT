package kr.tracom.bms.domain.SI0501;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;

@Service
public class SI0501Service extends ServiceSupport{

	@Autowired
	private SI0501Mapper si0501Mapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	public List SI0501G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0501Mapper.SI0501G0R0(map);
	}

	public Map SI0501G0K0() throws Exception {
		return si0501Mapper.SI0501G0K0(); 
	}
	
	public List SI0501SHI0() throws Exception {
		return si0501Mapper.SI0501SHI0();
	}	
	
	public Map SI0501G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_STTN_MST");
		//Map coMap = new HashMap<String, Object>();
		//coMap.put("CO_CD", Constants.SYS_INFO);
		//coMap.put("DL_CD", Constants.SY012 );
		//List<Map<String, Object>>  list = commonMapper.selectCommonDtlList(coMap);
		//String averageSttnStopTm = (String) list.get(0).get("TXT_VAL1");
		
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				
				String rowStatus = (String) data.get("rowStatus");
				
				if (rowStatus.equals("D")==false) {
					if(CommonUtil.empty(data.get("STOP_TM_PEAK"))){
						data.put("STOP_TM_PEAK", null);
					}
					if(CommonUtil.empty(data.get("STOP_TM_NONE_PEAK"))){
						data.put("STOP_TM_NONE_PEAK", null);
					}
				}
				if (rowStatus.equals("C")) {
					iCnt += si0501Mapper.SI0501G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0501Mapper.SI0501G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0501Mapper.SI0501G0D0(data);
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
	
	public Map SI0501G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0501Mapper.SI0501G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0501Mapper.SI0501G1D0(data);
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
	
	public List SI0501G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return si0501Mapper.SI0501G1R0(param);
	}
	
	public List SI0501P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_ROUT_MST");
		return si0501Mapper.SI0501P0R0(map);
	}
	
	
	
}
