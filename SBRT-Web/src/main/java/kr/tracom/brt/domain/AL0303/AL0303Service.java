package kr.tracom.brt.domain.AL0303;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;

@Service
public class AL0303Service extends ServiceSupport{

	@Autowired
	private AL0303Mapper AL0303Mapper;
	
	public List AL0303G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		
		return AL0303Mapper.AL0303G0R0(map);
	}
	
	
	public List AL0303SHI0() throws Exception {
		return AL0303Mapper.AL0303SHI0();
	}
	
	public List AL0303SHI1() throws Exception {
		return AL0303Mapper.AL0303SHI1();
	}
	
	public Map AL0303G0K0() throws Exception {
		return AL0303Mapper.AL0303G0K0(); 
	}
	
	public Map AL0303G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BRT_RPC_VHC_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map<String, Object> data = param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if(rowStatus.equals("C")) {
					iCnt += AL0303Mapper.AL0303G0I0(data);
				}else if (rowStatus.equals("U")) {
					uCnt += AL0303Mapper.AL0303G0U0(data);
				}else if (rowStatus.equals("D")) { 
					dCnt += AL0303Mapper.AL0303G0D0(data); 
				}
				 
			}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			} else {
				throw e;
			}
		}

		Map result = saveResult(iCnt, uCnt, dCnt);

		return result;

	}
	
	public List AL0303P1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_VHC_INFO");
		return AL0303Mapper.AL0303P1R0(map);
	}
	
	public List AL0303P2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_BMS_DRV_MST");
		return AL0303Mapper.AL0303P2R0(map);
	}
	
	public List AL0303P3R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_VHC_INFO");
		return AL0303Mapper.AL0303P3R0(map);
	}
	
	public List AL0303P4R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_VHC_INFO");
		return AL0303Mapper.AL0303P4R0(map);
	}
	
	/*
	public List<Map> PI0205G0R0() throws Exception{
		Map<String, Object> param = getSimpleDataMap("dma_search");
		List returnList = PI0205Mapper.PI0205G0R0(param);
		
		Map<String, Object> AUDIO_INFO = getSimpleDataMap("dma_AUDIO_INFO");
		for(Object obj:returnList) {
			
			Map<String, Object> temp = (Map<String, Object>)obj;
			temp.put("VOC_PATH", "/fileUpload/audio/"+AUDIO_INFO.get("AUDIO_NM"));			
		}
		
		return returnList;
		
	}
	
	public Map PI0205G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_VOC_INFO");
        Map<String, Object> AUDIO_INFO = getSimpleDataMap("dma_AUDIO_INFO");
        
        try {
        	for (int i = 0; i < param.size(); i++) {
    			Map<String, Object> data = param.get(i);
    			String rowStatus = (String) data.get("rowStatus");
    			// 데이터베이스 date 타입일때 공백으로 들어가면 에러나는 사항 임시 수정
    			for (String key : data.keySet()) {
    				if (data.get(key).equals("")) {
    					data.put(key, null);
    				}
    			}			
    			if (rowStatus.equals("U")) {
    				if((data.get("VOC_ID") != null)&&(data.get("VOC_ID").toString().isEmpty()==false))
    					{
    						uCnt += PI0205Mapper.PI0205G0U0(data);
    					}
    				else if((data.get("VOC_ID") == null)||(data.get("VOC_ID").toString().isEmpty()==true)) 
    					{
    						iCnt += PI0205Mapper.PI0205G0I0(data);
    					}
    				
   
    				if((AUDIO_INFO.get("AUDIO_NM")!=null)&&(AUDIO_INFO.get("AUDIO_NM").toString().isEmpty()==false))
						{
    						doMoveFile("up/", "audio/", AUDIO_INFO.get("AUDIO_NM").toString(), AUDIO_INFO.get("AUDIO_NM").toString());
						}
                    
    				
    			} else if (rowStatus.equals("D")) {
    				dCnt += PI0205Mapper.PI0205G0D0(data);
    			}
    		}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			} else {
				throw e;
			}
		}
		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;
	}
	
	
	
	
	public Map PI0205G0K0() throws Exception {
		return PI0205Mapper.PI0205G0K0();
	}	*/
	
	
}
