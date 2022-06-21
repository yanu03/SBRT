package kr.tracom.brt.domain.AL0202;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0201.AL0201Mapper;
import kr.tracom.cm.domain.OperPlan.OperPlanMapper;
import kr.tracom.cm.domain.OperPlan.OperPlanService;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.tims.TimsService;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Result;


@Service
public class AL0202Service extends ServiceSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceSupport.class);
	

	@Autowired
	private OperPlanService operPlanService;
	
	@Autowired
	private AL0202Mapper al0202Mapper;
	
	@Autowired
	private OperPlanMapper operPlanMapper;
	
	@Autowired
	private AL0201Mapper al0201Mapper;
	
	
	@Autowired
	TimsService TimsService;
	
	
	public List AL0202G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List list = al0202Mapper.AL0202G0R0(map);
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> data = (Map<String, Object>)list.get(i);
			
			String wayAscNm = CommonUtil.objectToString(data.get("WAY_ASC_NM"));
			String wayDescNm = CommonUtil.objectToString(data.get("WAY_DESC_NM"));
			String stSttnNm = CommonUtil.objectToString(data.get("ST_STTN_NM"));
			String edSttnNm = CommonUtil.objectToString(data.get("ED_STTN_NM"));
			if(CommonUtil.notEmpty(stSttnNm)&&CommonUtil.notEmpty(edSttnNm)) {
				data.put("WAY_ASC_STR", stSttnNm+" → "+edSttnNm);
				data.put("WAY_DESC_STR", edSttnNm+" → "+stSttnNm);
			}
		}

		 
		 return list;
	}
	
	public List AL0202G1R0() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0202Mapper.AL0202G1R0(param);
	}
	
	public List AL0202G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0202Mapper.AL0202G1CNT(param);
	}
	
	public List AL0202P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0202Mapper.AL0202P0R0(map);
	}
	
	public List AL0202P0R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_param_AL0202P0R1");
		return al0202Mapper.AL0202P1R0(map);
	}
	
	public List selectCorCnt() throws Exception {
		return al0202Mapper.selectCorCnt();
	}
	
	public Map AL0202G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		Map type = getSimpleDataMap("dma_save");
		
		List<Map<String, Object>> param = getSimpleList("dlt_OPER_ALLOC_PL_ROUT_INFO");
		List<Map<String, Object>> param2 = getSimpleList("dlt_BRT_OPER_ALLOC_PL_COR_INFO");
		List<Map<String, Object>> param3 = getSimpleList("dlt_BRT_OPER_PL_MST");
		try {
			
			if(param.size()>0&&"ALL".equals(type.get("TYPE"))){
				al0202Mapper.AL0202G1DA0(param.get(0));
			}
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				if(CommonUtil.empty(data.get("ROUT_ID")))break;
				
				String rowStatus = (String) data.get("rowStatus");
					
				String routId = data.get("ROUT_ID").toString();
				String dayDiv = data.get("DAY_DIV").toString();
				if(CommonUtil.empty(data.get("OPER_SN")))break;
				int operSn = Integer.valueOf(data.get("OPER_SN").toString());
				
				if (rowStatus.equals("C")) {
					iCnt += al0202Mapper.AL0202G1I0(data);
					al0201Mapper.AL0201G1I0(data);
					operPlanService.makeOperAllocPlNodeInfo(routId, dayDiv, operSn, true);
				} else if (rowStatus.equals("U")) {
					uCnt += al0202Mapper.AL0202G1U0(data);
					al0201Mapper.AL0201G1I0(data);
					operPlanService.makeOperAllocPlNodeInfo(routId, dayDiv, operSn, true);
				} else if (rowStatus.equals("D")) {
					dCnt += al0202Mapper.AL0202G1D0(data);
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
		
		try {
			if(param2.size()>0&&"ALL".equals(type.get("TYPE"))){
				al0202Mapper.AL0202G1DA1(param2.get(0));
			}
			for (int i = 0; i < param2.size(); i++) {
				Map data = (Map) param2.get(i);
				if(CommonUtil.empty(data.get("ROUT_ID")) || CommonUtil.empty(data.get("REP_ROUT_ID")) || CommonUtil.empty(data.get("ALLOC_NO")))break;
				
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					
					al0202Mapper.AL0202G1I1(data);
				} else if (rowStatus.equals("U")) {
					al0202Mapper.AL0202G1U1(data);
				} else if (rowStatus.equals("D")) {
					al0202Mapper.AL0202G1D1(data);
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
		
		try {
			for (int i = 0; i < param3.size(); i++) {
				Map data = (Map) param3.get(i);
				
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					al0201Mapper.AL0201G0I0(data);
				} else if (rowStatus.equals("U")) {
					al0201Mapper.AL0201G0U0(data);
				} else if (rowStatus.equals("D")) {
					al0201Mapper.AL0201G0D0(data);
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
		
		//운행계획 생성되었다고 BRT 서비스에 알림
		//TimsService.notifyOperAllocCompleted();
		
		return result;		
	}
}
