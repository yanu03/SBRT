package kr.tracom.cm.domain.Program;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class ProgramService extends ServiceSupport{

	@Autowired
	private ProgramMapper programMapper;

	/**
	 * 메뉴관리 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */

	public List<Map> selectProgram() throws Exception {
		Map param = getSimpleDataMap("dma_search");
		return programMapper.selectProgram(param);
	}
	
	/**
	 * 사용자별 권한 프로그램 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */
	public List<Map> searchUserAuthProgram() throws Exception {
		Map param = getSimpleDataMap("dma_program");
		return programMapper.searchUserAuthProgram(param);
	}
	
	/**
	 * 조직별 권한 프로그램 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */
	public List<Map> searchOrgAuthProgram() throws Exception {
		Map param = getSimpleDataMap("dma_program");
		return programMapper.searchOrgAuthProgram(param);
	}

	/**
	 * 메뉴별 접근 메뉴 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */

	public List<Map> selectProgramAuthority() throws Exception {
		Map param = getSimpleDataMap("dma_program");
		return programMapper.selectProgramAuthority(param);
	}

	/**
	 * 메뉴별 접근 메뉴 등록
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */

	public List<Map> excludeSelectProgramAuthority() throws Exception {
		Map param = getSimpleDataMap("dma_search");
		return programMapper.excludeSelectProgramAuthority(param);
	}

	/**
	 * 여러 건의 메뉴관리(개인기본정보) 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 * @throws Exception 
	 */

	public Map saveProgram() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List param = getSimpleList("dlt_program");
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += programMapper.insertProgram(data);
			} else if (rowStatus.equals("U")) {
				uCnt += programMapper.updateProgram(data);
			} else if (rowStatus.equals("D")) {
				dCnt += programMapper.deleteProgram(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
	
	/**
	 * 사용자 권한 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 * @throws Exception 
	 */
	public Map saveUserAuthProgram() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List param = getSimpleList("dlt_userAuthProgram");
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += programMapper.insertUserAuthProgram(data);
			} else if (rowStatus.equals("U")) {
				uCnt += programMapper.updateUserAuthProgram(data);
			} else if (rowStatus.equals("D")) {
				dCnt += programMapper.deleteUserAuthProgram(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
	
	/**
	 * 조직 권한 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 * @throws Exception 
	 */
	public Map saveOrgAuthProgram() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List param = getSimpleList("dlt_orgAuthProgram");
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += programMapper.insertOrgAuthProgram(data);
			} else if (rowStatus.equals("U")) {
				uCnt += programMapper.updateOrgAuthProgram(data);
			} else if (rowStatus.equals("D")) {
				dCnt += programMapper.deleteOrgAuthProgram(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}

	/**
	 * 여러 건의 메뉴별 접근 메뉴 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 * @throws Exception 
	 */

	public Map saveProgramAuthority() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		
		List param = getSimpleList("dlt_programAuthority");
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += programMapper.insertProgramAuthority(data);
			} else if (rowStatus.equals("U")) {
				dCnt += programMapper.updateProgramAuthority(data);
			} else if (rowStatus.equals("D")) {
				dCnt += programMapper.deleteProgramAuthority(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}

	/**
	 * 메뉴정보 삭제시 하위의 메뉴별 접근권한 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 * @throws Exception 
	 */

	public Map saveProgramAll() throws Exception {
		int iCnt_menu = 0; // 등록한 메뉴 건수
		int iCnt_access = 0; // 등록한 메뉴별 접근권한 건수
		int uCnt_menu = 0; // 수정한 메뉴 건수
		int uCnt_access = 0; // 수정한 메뉴별 접근권한 건수
		int dCnt_menu = 0; // 삭제한 메뉴 건수
		int dCnt_access = 0; // 삭제한 메뉴별 접근권한 건수

		List paramProgram = getSimpleList("dlt_program");
		List paramProgramAcess = getSimpleList("dlt_programAuthority");
		
		for (int i = 0; i < paramProgram.size(); i++) {
			Map dataProgram = (Map) paramProgram.get(i);
			String rowStatusProgram = (String) dataProgram.get("rowStatus");
			if (rowStatusProgram.equals("C")) {
				iCnt_menu += programMapper.insertProgram(dataProgram);

				for (int j = 0; j < paramProgramAcess.size(); j++) {
					Map dataProgramAcess = (Map) paramProgramAcess.get(j);
					String rowStatusProgramAuthority = (String) dataProgramAcess.get("rowStatus");
					if (rowStatusProgramAuthority.equals("C")) {
						iCnt_access += programMapper.insertProgramAuthority(dataProgramAcess);
					}
				}
			} else if (rowStatusProgram.equals("U")) {
				for (int j = 0; j < paramProgramAcess.size(); j++) {
					Map dataProgramAcess = (Map) paramProgramAcess.get(j);
					String rowStatusProgramAuthority = (String) dataProgramAcess.get("rowStatus");
					if (rowStatusProgramAuthority.equals("C")) {
						iCnt_access += programMapper.insertProgramAuthority(dataProgramAcess);
					} else if (rowStatusProgramAuthority.equals("D")) {
						dCnt_access += programMapper.deleteProgramAuthority(dataProgramAcess);
					}
				}
				uCnt_menu += programMapper.updateProgram(dataProgram);
				// 상위 메뉴가 삭제이면 하위메뉴별 접근권한은 모두 삭제
			} else if (rowStatusProgram.equals("D")) {
				programMapper.deleteProgramAuthority(dataProgram); // 하위 코드 정보는 전체 삭제
				dCnt_menu += programMapper.deleteProgram(dataProgram);
			}

		}
		
		if(paramProgram.size()==0) {
			for (int j = 0; j < paramProgramAcess.size(); j++) {
				Map dataProgramAcess = (Map) paramProgramAcess.get(j);
				String rowStatusProgramAuthority = (String) dataProgramAcess.get("rowStatus");
				if (rowStatusProgramAuthority.equals("C")) {
					iCnt_access += programMapper.insertProgramAuthority(dataProgramAcess);
				} else if (rowStatusProgramAuthority.equals("D")) {
					dCnt_access += programMapper.deleteProgramAuthority(dataProgramAcess);
				}
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT_MENU", String.valueOf(iCnt_menu));
		result.put("ICNT_ACCESS", String.valueOf(iCnt_access));
		result.put("UCNT_MENU", String.valueOf(uCnt_menu));
		result.put("UCNT_ACCESS", String.valueOf(uCnt_access));
		result.put("DCNT_MENU", String.valueOf(dCnt_menu));
		result.put("DCNT_ACCESS", String.valueOf(dCnt_access));
		return result;
	}
}
