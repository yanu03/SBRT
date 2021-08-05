package kr.tracom.bms.domain.PI0206;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0702.PI0702Mapper;
import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0206Service extends ServiceSupport {

	@Autowired
	private PI0206Mapper pi0206Mapper;
	
	@Autowired
	private FTPHandler ftpHandler;
	
	public List PI0206G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0206Mapper.PI0206G0R0(map);
	}
	
	public List PI0206SHI0() throws Exception {
		return pi0206Mapper.PI0206SHI0();
	}
	
	public List PI0206G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");		
		return pi0206Mapper.PI0206G1R0(map);
	}
	
	public Map PI0206G1K0() throws Exception {
		return pi0206Mapper.PI0206G1K0(); 
	}	
	
	public List PI0206G1R1() throws Exception {
		return pi0206Mapper.PI0206G1R1();
	}
	
	//예약
	public Map PI0206G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		//체크한 노선 리스트 (list_param)
		List<Map<String, Object>> list_param = getSimpleList("dlt_BIT_ROUT_MST");
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		try {
			
			//예약 정보, 예약결과 정보 insert
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					
					data.put("ROUTE_ID",map.get("ROUTE_ID"));
					iCnt = pi0206Mapper.PI0206G1I0(data);
					
					//차량별 장치코드리스트
					String vhcId = String.valueOf(data.get("VHC_ID"));
					List<Map<String, Object>> dlist = pi0206Mapper.selectDvcCd(vhcId);
					
					//예약해야할노선리스트			
					for(Map<String, Object> route : list_param) {
						
						String routeId = String.valueOf(map.get("ROUTE_ID"));
						Map<String, Object> routeInfo= pi0206Mapper.selectRouteInfo(routeId);
						route.put( "TXT_VAL1", String.valueOf(routeInfo.get("TXT_VAL1")) ); //U or D //list_param 의 값을 변경

						//local temp -> local vehicle 폴더로 복사
						ftpHandler.reserveDst(routeInfo, dlist);
		    		}
					
				} 
				else if (rowStatus.equals("D")) {
					dCnt += pi0206Mapper.PI0206G1D0(data);
				} 
			}			
			
			//list.csv파일 생성 및 ftp 싱크
			ftpHandler.makeDstConfig(param, list_param);
			
			
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
	public Map PI0206G1U0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt += pi0206Mapper.PI0206G1U0(data);
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
	
	public List PI0206G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch2");		
		return pi0206Mapper.PI0206G2R0(map);
	}
}
