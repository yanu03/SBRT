package kr.tracom.bms.domain.PI0801;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0801.PI0801Mapper;
import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0801Service extends ServiceSupport {
	@Autowired
	private PI0801Mapper pi0801Mapper;
	
	@Autowired
	FTPHandler ftpHandler;
	
	public List PI0801G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List returnList = pi0801Mapper.PI0801G0R0(map);
		
		return returnList;
	}
	
	public List PI0801SHI0() throws Exception {
		return pi0801Mapper.PI0801SHI0();
	}

	public Map PI0801G0K0() throws Exception {
		return pi0801Mapper.PI0801G0K0();
	}
	
	public Map PI0801G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_INNER_LED_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					
					int retCnt = pi0801Mapper.PI0801G0I0(data);
					iCnt += retCnt;				
					
					if(retCnt > 0) {
						ftpHandler.makeIld(data);
						List<Map<String, Object>> ildList = pi0801Mapper.PI0801G0R0(null);
						ftpHandler.makeIldList(ildList);
					}
					
					
				} else if (rowStatus.equals("U")) {
					int retCnt = pi0801Mapper.PI0801G0U0(data);
					uCnt += retCnt;
					
					if(retCnt > 0) {
						ftpHandler.makeIld(data);
						List<Map<String, Object>> ildList = pi0801Mapper.PI0801G0R0(null);
						ftpHandler.makeIldList(ildList);
					}
					
				} else if (rowStatus.equals("D")) {
					int retCnt = pi0801Mapper.PI0801G0D0(data);
					dCnt += retCnt;
					
					if(retCnt > 0) {
						
						int resultCnt = 0;
						List<Map<String, Object>> ildList = pi0801Mapper.PI0801G0R0(null);
						
						for(int idx=0; idx<ildList.size(); idx++) {
							Map<String, Object> ild = ildList.get(idx);
							
							String seq = Integer.toString(idx+1);
							ild.put("SN", seq);
							
							if(pi0801Mapper.PI0801G0U1(ild) > 0) {
								resultCnt++;
							}
						}
						
						if(resultCnt == ildList.size()) {
							ftpHandler.makeIldList(ildList);
						}
						
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
