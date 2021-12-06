package kr.tracom.brt.domain.AL0203;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0103.AL0103Mapper;
import kr.tracom.cm.domain.OperPlan.OperPlanService;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0203Service extends ServiceSupport {

	@Autowired
	OperPlanService operPlanService;
	
	@Autowired
	private AL0203Mapper al0203Mapper;
	
	public List AL0203G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0203Mapper.AL0203G0R0(map);
	}
	
	
	public List AL0203SHI0() throws Exception {
		return al0203Mapper.AL0203SHI0();
	}
	
	public List AL0203G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return al0203Mapper.AL0203G1R0(param);
	}
	
	public List AL0203G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0203Mapper.AL0203G1CNT(param);
	}
	
	
	public Map AL0203G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_ALLOC_PL_NODE_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
				} else if (rowStatus.equals("U")) {					
					operPlanService.changeOperAllocPlNodeInfo(data); //노드별 출도착시각 변경		
					uCnt += al0203Mapper.AL0203G0U0(data);
				} else if (rowStatus.equals("D")) {
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
	
	//운행계획(정류소) 배포
	public Map AL0203P0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_ALLOC_PL_NODE_INFO");
		Map<String, Object> map = getSimpleDataMap("dma_relDt");
		String temp[] = map.get("relDt").toString().replace("[","").replace("]","").replace(" ","").split(",");
		
		for(int j=0; j<temp.length; j++) {
			try {
				for (int i = 0; i < param.size(); i++) {
					Map data = (Map) param.get(i);
					data.put("OPER_DT", temp[j]);
					iCnt += al0203Mapper.AL0203P0I0(data);
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
			
		}
		

		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
	}
	
	public List AL0203P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_searchItem");
		return al0203Mapper.AL0203P0R0(map);
	}
	
}
