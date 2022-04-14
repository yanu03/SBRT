package kr.tracom.cm.domain.Common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;


@Service
public class CommonService extends ServiceSupport {

	@Autowired
	private CommonMapper commonMapper;

	@Autowired
	FTPHandler ftpHandler;
	/**
	 * 헤더메뉴, 사이드메뉴 조회 (로그인 사용자에게 권한이 있는 메뉴만 조회함)
	 * 
	 * @param param 사용자 로그인 ID가 저장된 맵 객체
	 */

	public List selectMenuList(Map param) throws Exception {
		return commonMapper.selectMenuList(param);
	}
	
	/**
	 * 사용자별 프로그램 권한 리스트 조회 (로그인 사용자에게 권한이 있는 프로그램 권한만 조회함)
	 * 
	 * @param param 사용자 로그인 ID가 저장된  맵 객체
	 */
	public List selectProgramAuthorityList(Map param) throws Exception {
		return commonMapper.selectProgramAuthorityList(param);
	}	
	

	/**
	 * 공통그룹 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectCommonCo() throws Exception {
		return commonMapper.selectCommonCo(getSimpleDataMap("dma_search"));
	}

	/**
	 * 모든 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectCommonDtlAll() throws Exception {
		return commonMapper.selectCommonDtl();
	}

	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectCommonDtlList() throws Exception {
		return commonMapper.selectCommonDtlList(getSimpleDataMap("dma_commonCO"));
	}
	
	public List selectCommonDtlImg() throws Exception {
		return commonMapper.selectCommonDtlImg(getSimpleDataMap("dma_search"));
	}

	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectCodeList() throws Exception {

		String[] selectCodeList;
		Map param = getSimpleDataMap("dma_commonDtl");
		String CO_CD = (String) param.get("CO_CD");


		selectCodeList = CO_CD.split(",");
		param.put("CODE", selectCodeList);
		return commonMapper.selectCodeList(param);
	}
	
	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectCodeList2() throws Exception {

		String[] selectCodeList;
		String[] selectCodeList2;
		Map param = getSimpleDataMap("dma_commonDtl");
		String CO_CD = (String) param.get("CO_CD");
		String DL_CD = (String) param.get("DL_CD");


		selectCodeList = CO_CD.split(",");
		selectCodeList2 = DL_CD.split(",");
		param.put("CODE", selectCodeList);
		param.put("DLCD", selectCodeList2);
		return commonMapper.selectCodeList2(param);
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
		return commonMapper.selectSystemList(param);
	}
	
	
	/**
	 * 공통관리 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List selectCommonSearchItem() throws Exception {
		return commonMapper.selectCommonSearchItem();
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
		List param = getSimpleList("dlt_commonCo");
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += commonMapper.insertCommonCo(data);
			} else if (rowStatus.equals("U")) {
				uCnt += commonMapper.updateCommonCo(data);
			} else if (rowStatus.equals("D")) {
				dCnt += commonMapper.deleteCommonCo(data);
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
		List param = getSimpleList("dlt_commonDtl");
		Map paramMap = getSimpleDataMap("dma_commonCO");
		String CO_CD = (String) paramMap.get("CO_CD");
		
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			data.put("CO_CD", CO_CD);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				String imgPathNm = data.get("IMG_PATH").toString()+data.get("IMG_NM").toString();
				data.put("IMG_PATH", imgPathNm);
				iCnt += commonMapper.insertCommonDtl(data);
				if((data.get("IMG_NM")!=null)&&(data.get("IMG_NM").toString().isEmpty()==false)) {
					
					//doMoveFile("up/","common/code/",data.get("IMG_NM").toString(),data.get("CO_CD").toString()+data.get("DL_CD").toString()+data.get("IMG_NM").toString()+".png");
					doMoveFile("up/","common/code/",data.get("IMG_NM").toString(),data.get("CO_CD").toString()+data.get("DL_CD").toString()+data.get("IMG_NM").toString());
					commonMapper.insertCommonDtlImgPath(data);
					
		    		/*  2020-09-29 추가
		    		 *  설명: .jpg 이미지와 CERTI 이미지가 없을 경우 운전자 단말기에서 로그인이 되지 않음. 따라서 아래 코드 추가함
		    		 */
					/*String imgFileName = data.get("DRV_ID").toString();
					String pngExtName = ".png";
					doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+".jpg");
					doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+"_CERTI.jpg");*/
					
					//ftp sync
					//ftpHandler.uploadSI0300();
					
				}
			} else if (rowStatus.equals("U")) {
				String imgPathNm = data.get("IMG_PATH").toString()+data.get("IMG_NM").toString();
				data.put("IMG_PATH", imgPathNm);
				uCnt += commonMapper.updateCommonDtl(data);
				if((data.get("IMG_NM")!=null)&&(data.get("IMG_NM").toString().isEmpty()==false)) {
					commonMapper.insertCommonDtlImgPath(data);
					
					if(data.get("IMG_PATH").equals(data.get("IMG_PATH_ORI")) == false) {
						doMoveFile("up/","common/code/",data.get("IMG_NM").toString(),data.get("CO_CD").toString()+data.get("DL_CD").toString()+data.get("IMG_NM").toString());
						//doMoveFile("up/","common/code/",data.get("IMG_NM").toString(),data.get("CO_CD").toString()+data.get("DL_CD").toString()+data.get("IMG_NM").toString()+".png");
						commonMapper.updateCommonDtlImgPath(data);
					}
					
					
					/*  2020-09-29 추가
		    		 *  설명: .jpg 이미지와 CERTI 이미지가 없을 경우 운전자 단말기에서 로그인이 되지 않음. 따라서 아래 코드 추가함
		    		 */
					/*String imgFileName = data.get("DRV_ID").toString();
					String pngExtName = ".png";
					doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+".jpg");
					doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+"_CERTI.jpg");*/
					
					//ftp sync
					//ftpHandler.uploadSI0300();
					
				}
				
			} else if (rowStatus.equals("D")) {
				dCnt += commonMapper.deleteCommonDtl(data);
				
				/*if((data.get("IMG_PATH")!=null)&&(data.get("IMG_PATH").toString().isEmpty()==false)) {
					Files.delete((Path) data.get("IMG_PATH"));
				}*/
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
		List paramCodeCo = getSimpleList("dlt_commonCo");
		List paramCodeDtl = getSimpleList("dlt_commonDtl");
		for (int i = 0; i < paramCodeCo.size(); i++) {
			Map dataGrp = (Map) paramCodeCo.get(i);
			String rowStatusGrp = (String) dataGrp.get("rowStatus");
			if (rowStatusGrp.equals("C")) {
				iCnt_grp += commonMapper.insertCommonCo(dataGrp);

				for (int j = 0; j < paramCodeDtl.size(); j++) {
					Map dataGrpCode = (Map) paramCodeDtl.get(j);
					String rowStatusMenuAuth = (String) dataGrpCode.get("rowStatus");
					if (rowStatusMenuAuth.equals("C")) {
						iCnt_code += commonMapper.insertCommonDtl(dataGrpCode);
					}
				}
			} else if (rowStatusGrp.equals("U")) {
				for (int j = 0; j < paramCodeDtl.size(); j++) {
					Map dataGrpCode = (Map) paramCodeDtl.get(j);
					String rowStatusMenuAuth = (String) dataGrpCode.get("rowStatus");
					if (rowStatusMenuAuth.equals("C")) {
						iCnt_code += commonMapper.insertCommonDtl(dataGrpCode);
					} else if (rowStatusMenuAuth.equals("U")) {
						uCnt_code += commonMapper.updateCommonDtl(dataGrpCode);
					} else if (rowStatusMenuAuth.equals("D")) {
						dCnt_code += commonMapper.deleteCommonDtl(dataGrpCode);
					}
				}
				uCnt_grp += commonMapper.updateCommonCo(dataGrp);
			} else if (rowStatusGrp.equals("D")) {
				commonMapper.deleteCommonDtlAll(dataGrp); // 하위 코드 정보는 전체 삭제
				dCnt_grp += commonMapper.deleteCommonCo(dataGrp);
			}
			for (int j = 0; j < paramCodeDtl.size(); j++) {
				Map dataGrpCode = (Map) paramCodeDtl.get(j);
				String rowStatusMenuAuth = (String) dataGrpCode.get("rowStatus");
				if (rowStatusMenuAuth.equals("D")) {
					dCnt_code += commonMapper.deleteCommonDtl(dataGrpCode);
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
