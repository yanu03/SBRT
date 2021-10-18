package kr.tracom.brt.domain.AL0202;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.CommonUtil;


@Service
public class AL0202Service extends ServiceSupport {

	@Autowired
	private AL0202Mapper al0304Mapper;
	
	
	public List AL0202G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List list = al0304Mapper.AL0202G0R0(map);
		
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
		return al0304Mapper.AL0202G1R0(param);
	}
	
	public List AL0202G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0304Mapper.AL0202G1CNT(param);
	}
	
	public List AL0202P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0304Mapper.AL0202P0R0(map);
	}
	
	public List AL0202P0R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_param_AL0202P0R1");
		return al0304Mapper.AL0202P1R0(map);
	}		
}