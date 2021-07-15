package kr.tracom.bms.domain.SI0701;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class SI0701Service extends ServiceSupport {
	@Autowired
	private SI0701Mapper si0701Mapper;

	/**
	 * 헤더메뉴, 사이드메뉴 조회 (로그인 사용자에게 권한이 있는 메뉴만 조회함)
	 * 
	 * @param param 사용자 로그인 ID가 저장된 맵 객체
	 */

	public List selectMenuList(Map param) throws Exception {
		return si0701Mapper.selectMenuList(param);
	}
	
	/**
	 * 사용자별 프로그램 권한 리스트 조회 (로그인 사용자에게 권한이 있는 프로그램 권한만 조회함)
	 * 
	 * @param param 사용자 로그인 ID가 저장된  맵 객체
	 */
	public List selectProgramAuthorityList(Map param) throws Exception {
		return si0701Mapper.selectProgramAuthorityList(param);
	}	
	

	/**
	 * 공통그룹 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectSI0701Co() throws Exception {
		return si0701Mapper.selectSI0701Co(getSimpleDataMap("dma_search"));
	}

	/**
	 * 모든 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectSI0701DtlAll() throws Exception {
		return si0701Mapper.selectSI0701Dtl();
	}

	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectSI0701DtlList() throws Exception {
		return si0701Mapper.selectSI0701DtlList(getSimpleDataMap("dma_si0701CO"));
	}

	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectCodeList() throws Exception {

		String[] selectCodeList;
		Map param = getSimpleDataMap("dma_si0701Dtl");
		String CO_CD = (String) param.get("CO_CD");


		selectCodeList = CO_CD.split(",");
		param.put("CODE", selectCodeList);
		return si0701Mapper.selectCodeList(param);
	}

	/**
	 * 시스템코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectSystemList() throws Exception {
		
		Map param = getSimpleDataMap("dma_systemCode");
		String systemCd = (String) param.get("SYSTEM_CD");

		String[] systemArr = systemCd.split(",");
		param.put("SYSTEM", systemArr);
		return si0701Mapper.selectSystemList(param);
	}
	
	
	/**
	 * 공통관리 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectSI0701SearchItem() throws Exception {
		return si0701Mapper.selectSI0701SearchItem();
	}

	/**
	 * 여러 건의 코드 그룹 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public Map saveCodeCoList() throws Exception {

		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_si0701Co");
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += si0701Mapper.insertSI0701Co(data);
			} else if (rowStatus.equals("U")) {
				uCnt += si0701Mapper.updateSI0701Co(data);
			} else if (rowStatus.equals("D")) {
				dCnt += si0701Mapper.deleteSI0701Co(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;

	}

	/**
	 * 여러 건의 코드 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public Map saveCodeDtlList() throws Exception {

		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_si0701Dtl");
		Map paramMap = getSimpleDataMap("dma_si0701CO");
		String CO_CD = (String) paramMap.get("CO_CD");
		
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			data.put("CO_CD", CO_CD);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += si0701Mapper.insertSI0701Dtl(data);
			} else if (rowStatus.equals("U")) {
				uCnt += si0701Mapper.updateSI0701Dtl(data);
			} else if (rowStatus.equals("D")) {
				dCnt += si0701Mapper.deleteSI0701Dtl(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;

	}

	/**
	 * 메뉴관리정보 삭제시 하위의 메뉴 접근 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 */

	public Map saveCodeCoListAll() throws Exception {

		int iCnt_grp = 0; // 등록한 그룹코드 건수
		int iCnt_code = 0; // 등록한 세부코드 건수
		int uCnt_grp = 0; // 수정한 그룹코드 건수
		int uCnt_code = 0; // 수정한 세부코드 건수
		int dCnt_grp = 0; // 삭제한 그룹코드 건수
		int dCnt_code = 0; // 삭제한 세부코드 건수
		List paramCodeCo = getSimpleList("dlt_si0701Co");
		List paramCodeDtl = getSimpleList("dlt_si0701Dtl");
		for (int i = 0; i < paramCodeCo.size(); i++) {
			Map dataGrp = (Map) paramCodeCo.get(i);
			String rowStatusGrp = (String) dataGrp.get("rowStatus");
			if (rowStatusGrp.equals("C")) {
				iCnt_grp += si0701Mapper.insertSI0701Co(dataGrp);

				for (int j = 0; j < paramCodeDtl.size(); j++) {
					Map dataGrpCode = (Map) paramCodeDtl.get(j);
					String rowStatusMenuAuth = (String) dataGrpCode.get("rowStatus");
					if (rowStatusMenuAuth.equals("C")) {
						iCnt_code += si0701Mapper.insertSI0701Dtl(dataGrpCode);
					}
				}
			} else if (rowStatusGrp.equals("U")) {
				for (int j = 0; j < paramCodeDtl.size(); j++) {
					Map dataGrpCode = (Map) paramCodeDtl.get(j);
					String rowStatusMenuAuth = (String) dataGrpCode.get("rowStatus");
					if (rowStatusMenuAuth.equals("C")) {
						iCnt_code += si0701Mapper.insertSI0701Dtl(dataGrpCode);
					} else if (rowStatusMenuAuth.equals("U")) {
						uCnt_code += si0701Mapper.updateSI0701Dtl(dataGrpCode);
					} else if (rowStatusMenuAuth.equals("D")) {
						dCnt_code += si0701Mapper.deleteSI0701Dtl(dataGrpCode);
					}
				}
				uCnt_grp += si0701Mapper.updateSI0701Co(dataGrp);
			} else if (rowStatusGrp.equals("D")) {
				si0701Mapper.deleteSI0701DtlAll(dataGrp); // 하위 코드 정보는 전체 삭제
				dCnt_grp += si0701Mapper.deleteSI0701Co(dataGrp);
			}
			for (int j = 0; j < paramCodeDtl.size(); j++) {
				Map dataGrpCode = (Map) paramCodeDtl.get(j);
				String rowStatusMenuAuth = (String) dataGrpCode.get("rowStatus");
				if (rowStatusMenuAuth.equals("D")) {
					dCnt_code += si0701Mapper.deleteSI0701Dtl(dataGrpCode);
				}
			}
		}
		
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT_GRP", String.valueOf(iCnt_grp));
		result.put("ICNT_CODE", String.valueOf(iCnt_code));
		result.put("UCNT_GRP", String.valueOf(uCnt_grp));
		result.put("UCNT_CODE", String.valueOf(uCnt_code));
		result.put("DCNT_GRP", String.valueOf(dCnt_grp));
		result.put("DCNT_CODE", String.valueOf(dCnt_code));
		return result;
	}

}