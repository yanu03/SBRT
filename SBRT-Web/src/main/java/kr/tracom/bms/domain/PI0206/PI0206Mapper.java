package kr.tracom.bms.domain.PI0206;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0206Mapper {

	public List PI0206G0R0(Map param);
	
	public List PI0206SHI0();
	
	public Map PI0206G1K0();
	
	public List PI0206G1R0(Map param);
	
	public int PI0206G1I0(Map param);
	
	public int PI0206G1U0(Map param);
	
	public int PI0206G1D0(Map param);
	
	public List PI0206G2R0(Map param);
	
	public List PI0206G1R1(); 
	
	
	//차량별 장치정보 
	List<Map<String, Object>> selectDvcCd(String vhcId);
	//노선정보
	Map<String, Object> selectRouteInfo(String routId);
	
	/** 코스정보 jh **/
	public Map<String, Object> selectCourseInfo(String courseId);
	
	/** 노선리스트 jh **/
	public List<Map<String, Object>> selectRoutList(Map param);
	
	/** 노드리스트 jh **/
	public List<Map<String, Object>> selectAllNodeList();
	
	/** 정류장 & 음성노드 리스트 JH **/
	public List<Map<String, Object>> selectStnAudioList();
	
	/** 노선별 노드 리스트 (순서별) jh **/
	public List<Map<String, Object>> selectRoutNodeList(String routId);
	
	/**노선별 링크 리스트 jh **/
	public List<Map<String, Object>> selectRoutLinkList(String routId);
	
	/** 노선정보리스트 jh **/
	public List<Map<String, Object>> selectRoutInfoList(Map param);
	
	/** routlist에 들어갈 노선 정보 jh **/
	public Map<String, Object> makeRoutNewRow(String routId);

	/** 노선별 음성편성 리스트 jh **/
	public List<Map<String, Object>> selectRoutOrgaList(String routId);
	
	/** 음성편성별 음성정보 리스트 jh **/
	public List<Map<String, Object>> selectOrgaVocList(String orgaId);
	
	/** 실내 LED 대본이 있는 음성인지 질의 jh **/
	public String isExistIld(String vocId);
	
	/** 코스 정보 리스트 jh **/
	public List<Map<String, Object>> selectCourseInfoList(Map param);
	
	/** 코스별 노선 리스트 JH **/
	public List<Map<String, Object>> selectCourseRoutList(String courseId);
	
	/** 카카오 API 키 JH **/
	public String selectAPIKey();
}
