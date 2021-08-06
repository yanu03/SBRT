package kr.tracom.bms.domain.PI0902;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0902.PI0902Mapper;
import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0902Service extends ServiceSupport{

	@Autowired
	private PI0902Mapper pi0902Mapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private FTPHandler ftpHandler;
	
	public List PI0902G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0902Mapper.PI0902G0R0(map);
	}
	
	public List PI0902SHI0() throws Exception {
		return pi0902Mapper.PI0902SHI0();
	}
	
	public List PI0902G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");		
		return pi0902Mapper.PI0902G1R0(map);
	}
	
	public Map PI0902G1K0() throws Exception {
		return pi0902Mapper.PI0902G1K0(); 
	}	
	
	public List PI0902G1R1() throws Exception {
		return pi0902Mapper.PI0902G1R1();
	}
	
	public String getTxtVal1(Map param) throws Exception {
		List<Map<String, Object>> list = commonMapper.selectCommonDtlList(param);
		return list.get(0).get("TXT_VAL1").toString();
	}
	
	//예약
	public Map PI0902G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		Map<String, Object> map = getSimpleDataMap("dma_BMS_ED_INFO");
		
		try {
			
			//예약 정보, 예약결과 정보 insert
			Map<String, Object> paramMap = new HashMap<String, Object>();
//			iCnt = pi0902Mapper.PI0902G1I0(paramMap);
			
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt = pi0902Mapper.PI0902G1I0(data);
					
					Map m = new HashMap();
					m.put("CO_CD", "CATEGORY");
					m.put("DL_CD", map.get("CATEGORY").toString());
					map.put("CATEGORY_VAL", getTxtVal1(m));
					
					m.replace("CO_CD", "FRAME");
					m.replace("DL_CD", map.get("FRAME").toString());
					map.put("FRAME_VAL", getTxtVal1(m));
					
					m.replace("CO_CD", "FONT");
					m.replace("DL_CD", map.get("FONT").toString());
					map.put("FONT_VAL", getTxtVal1(m));
					
					ftpHandler.reserveErm(data, map);//jh
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
	
	//예약 취소
	public Map PI0902G1U0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt += pi0902Mapper.PI0902G1U0(data);
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
	
	public List PI0902G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch2");		
		return pi0902Mapper.PI0902G2R0(map);
	}
}
