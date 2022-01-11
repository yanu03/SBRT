package kr.tracom.cm.domain.OperPlan;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.Constants.OperPlanCalc;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;


@Service
public class OperPlanService extends ServiceSupport {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private OperPlanMapper operPlanMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	public void insertSimpleOperPlan(Map data) throws Exception {
		String wayYn = CommonUtil.notEmpty(data.get("WAY_YN"))?(String)data.get("WAY_YN"):"";
		String holiYn = CommonUtil.notEmpty(data.get("HOLI_YN"))?(String)data.get("HOLI_YN"):"";
		
		if("Y".equals(wayYn)) {				
			data.put("WAY_DIV", Constants.WayDivs.WD001); //상행
			data.put("DAY_DIV", Constants.DayDivs.DY001); //평일
			
			operPlanMapper.insertSimpleOperPlan(data);
			
			if("Y".equals(holiYn)) {
				data.put("DAY_DIV", Constants.DayDivs.DY002); //휴일
				operPlanMapper.insertSimpleOperPlan(data);
			}
			
			data.put("WAY_DIV", Constants.WayDivs.WD002); //하행
			data.put("DAY_DIV", Constants.DayDivs.DY001); //평일

			//하행일때 정류소 위치 변경
			String stSttnId = (String)data.get("ST_STTN_ID");
			//String stSttnNm = (String)data.get("ST_STTN_NM");
			String edSttnId = (String)data.get("ED_STTN_ID");
			//String edSttnNm = (String)data.get("ED_STTN_NM");
			data.put("ST_STTN_ID", edSttnId); 
			//data.put("ST_STTN_NM", edSttnNm);
			data.put("ED_STTN_ID", stSttnId);
			//data.put("ED_STTN_NM", stSttnNm);
			
			operPlanMapper.insertSimpleOperPlan(data);
			
			if("Y".equals(holiYn)) {
				data.put("DAY_DIV", Constants.DayDivs.DY002); //휴일
				operPlanMapper.insertSimpleOperPlan(data);
			}
		}
		else {
			data.put("DAY_DIV", Constants.DayDivs.DY001); //평일
			data.put("WAY_DIV", Constants.WayDivs.WD005); //없음
			operPlanMapper.insertSimpleOperPlan(data);
			
			
			if("Y".equals(holiYn)) {
				data.put("DAY_DIV", Constants.DayDivs.DY002); //휴일
				operPlanMapper.insertSimpleOperPlan(data);
			}
		}
	}
	
	public List<Map<String, Object>> selectOperPlanRoutList() throws Exception {
		
		Map<String, Object> param = getSimpleDataMap("dma_sub_search");
		
		Map<String, Object> operPlanMst = getSimpleDataMap("dma_BRT_OPER_PL_MST");
		
		List<Map<String, Object>> returnList = new ArrayList<>();
		
		param.put("CO_CD", Constants.SYS_INFO);
		param.put("DL_CD", Constants.SY012 );
		List<Map<String, Object>>  list = commonMapper.selectCommonDtlList(param);

		int averageSttnStopTm = Integer.parseInt((String) list.get(0).get("TXT_VAL1"));

		//Map<String, Object> operPlanMst = operPlanMapper.selectOperPlanMst(param);
		List<Map<String, Object>> sttnPeakInfoList = operPlanMapper.selectSttnPeakInfoOfOperPlan( param);
		List<Map<String, Object>> routOperPlan = new ArrayList<>();
		String curRoutId = "";
		String totalStopTmPeak = "00:00:00";
		String totalStopTmNonePeak = "00:00:00";
		//double totalRoutLen = 0;
		double routLen = 0;
		int routRunSec = 0;
		for(int i = 0; i < sttnPeakInfoList.size()-1; i++) {
			if(i==0) {
				routLen = CommonUtil.decimalToDouble(sttnPeakInfoList.get(i).get("ROUT_LEN"));
				continue;
			}
			/*if(curRoutId.equals((String)sttnPeakInfoList.get(i).get("ROUT_ID"))==false){
				routLen = CommonUtil.decimalToDouble(sttnPeakInfoList.get(i).get("ROUT_LEN"));
				totalRoutLen += routLen;
				Map<String, Object> map = new HashMap<String, Object>();
				
				curRoutId = (String)sttnPeakInfoList.get(i).get("ROUT_ID");
				map.put("ROUT_ID", curRoutId);
				map.put("TOTAL_STOP_TM_PEAK", totalStopTmPeak);
				map.put("TOTAL_STOP_TM_NONE_PEAK", totalStopTmNonePeak);
				totalStopTmPeak = "00:00:00";
				totalStopTmNonePeak = "00:00:00";
			}
			*/
			
			int stopTmPeak = CommonUtil.empty(sttnPeakInfoList.get(i).get("STOP_TM_PEAK"))?averageSttnStopTm:CommonUtil.decimalToInt(sttnPeakInfoList.get(i).get("STOP_TM_PEAK"));
			int stopTmNonePeak = CommonUtil.empty(sttnPeakInfoList.get(i).get("STOP_TM_NONE_PEAK"))?averageSttnStopTm:CommonUtil.decimalToInt(sttnPeakInfoList.get(i).get("STOP_TM_NONE_PEAK"));
			totalStopTmPeak = DateUtil.addSeconds(totalStopTmPeak, "HH:mm:ss", stopTmPeak);
			totalStopTmNonePeak = DateUtil.addSeconds(totalStopTmNonePeak, "HH:mm:ss", stopTmNonePeak);
		}
		routRunSec = (int)(routLen/(Constants.VHC_MAX_SPEED*1000/3600)); //노선 주행 시간
		
		int operSn = 1;
		
		//Date routStTm = DateUtil.getDate((String)operPlanMst.get("FST_TM")+":00","HH:mm:ss");	
		//Date amPeakStTm = DateUtil.getDate((String)operPlanMst.get("AM_PEAK_ST_TM")+":00","HH:mm:ss");	
		//Date pmPeakStTm = DateUtil.getDate((String)operPlanMst.get("PM_PEAK_ST_TM")+":00","HH:mm:ss");	
		String routStTm = CommonUtil.empty(operPlanMst.get("FST_TM"))?"00:00:00":(String)operPlanMst.get("FST_TM")+":00";
		String routLstTm = CommonUtil.empty(operPlanMst.get("LST_TM"))?"00:00:00":(String)operPlanMst.get("LST_TM")+":00";
		String amPeakStTm = CommonUtil.empty(operPlanMst.get("AM_PEAK_ST_TM"))?"00:00:00":(String)operPlanMst.get("AM_PEAK_ST_TM")+":00";
		String amPeakEdTm = CommonUtil.empty(operPlanMst.get("AM_PEAK_ED_TM"))?"00:00:00":(String)operPlanMst.get("AM_PEAK_ED_TM")+":00";
		String pmPeakStTm = CommonUtil.empty(operPlanMst.get("PM_PEAK_ST_TM"))?"00:00:00":(String)operPlanMst.get("PM_PEAK_ST_TM")+":00";
		String pmPeakEdTm = CommonUtil.empty(operPlanMst.get("PM_PEAK_ED_TM"))?"00:00:00":(String)operPlanMst.get("PM_PEAK_ED_TM")+":00";
		int amPeak = (int) (CommonUtil.empty(operPlanMst.get("AM_PEAK"))?0:DateUtil.minuteToSecond((String)operPlanMst.get("AM_PEAK")));
		int pmPeak = (int) (CommonUtil.empty(operPlanMst.get("PM_PEAK"))?0:DateUtil.minuteToSecond((String)operPlanMst.get("PM_PEAK")));
		int nonePeak = (int) (CommonUtil.empty(operPlanMst.get("NONE_PEAK"))?0:DateUtil.minuteToSecond((String)operPlanMst.get("NONE_PEAK")));
		
		int operIntvSec = 0; //운행간격시간(초)
		int minStopSec = 0; //최소정차시간(초)
		String routEdTm = "00:00:00";
		
		while(operSn<1000) {
			
			if(operSn==1) {
				minStopSec = (int) (DateUtil.timeToSecond(totalStopTmPeak));
			}
			else if(DateUtil.diffSeconds(routStTm, amPeakStTm,"HH:mm:ss")>=0 && DateUtil.diffSeconds(routStTm, amPeakEdTm,"HH:mm:ss")<=0) {
				operIntvSec = amPeak; 
				minStopSec = (int) (DateUtil.timeToSecond(totalStopTmPeak));
			}
			else if(DateUtil.diffSeconds(routStTm, pmPeakStTm,"HH:mm:ss")>=0 && DateUtil.diffSeconds(routStTm, pmPeakEdTm,"HH:mm:ss")<=0) {
				operIntvSec = pmPeak; 
				minStopSec = (int) (DateUtil.timeToSecond(totalStopTmNonePeak));
			}
			else {
				operIntvSec = nonePeak; 
				minStopSec = (int) (DateUtil.timeToSecond(totalStopTmPeak));
			}
			
			routStTm = DateUtil.addSeconds2(routStTm, "HH:mm:ss", operIntvSec); 
			
			int routOperSec = routRunSec + minStopSec;
			routEdTm = DateUtil.addSeconds2(routStTm, "HH:mm:ss", routOperSec); 
			
			if(DateUtil.diffSeconds(routStTm, routLstTm,"HH:mm:ss")>=0) {
				break;
			}
			Map<String, Object> reuturnMap = new HashMap<String, Object>();
			reuturnMap.put("REP_ROUT_ID", param.get("REP_ROUT_ID"));
			reuturnMap.put("DAY_DIV", param.get("DAY_DIV"));
			reuturnMap.put("WAY_DIV", param.get("WAY_DIV"));
			reuturnMap.put("OPER_SN",operSn);
			reuturnMap.put("ROUT_ST_TM",routStTm.substring(0, 5));
			reuturnMap.put("ROUT_ED_TM",routEdTm.substring(0, 5));
			//reuturnMap.put("OPER_INTV", DateUtil.secondToTime(operIntvSec).substring(0, 5));
			//reuturnMap.put("OPER_TM", DateUtil.secondToTime(routOperSec).substring(0, 5)); 
			returnList.add(reuturnMap);
			operSn ++;
			
		}
		
		return returnList;
	}
	
	
	
	/**
	 * 운행배차계획노드정보 생성
	 * @param data
	 * @throws Exception
	 */
	private List makeOperAllocPlNodeInfo(String routId, String dayDiv, int operSn, boolean bSave
										,int stNodeSn //첫 노드 순번
										,int edNodeSn //마지막 노드 순번
										,int offsetTm //도착 예정시간과의 차이(초)
										,String timeMin
										,String timeMax
										,String stNodeArrvTm
										,String stNodeDprtTm
										,String edNodeArrvTm
										,String edNodeDprtTm
										,int chgType //변경타입 //0:없음, 1:변경운행, 2:단순 수정
	) throws Exception {

		final String TIME_PATTERN = "HH:mm:ss";
		
		
		float MAX_SPEED_DEFAULT = OperPlanCalc.MAX_SPEED_DEFAULT; //50.0f;
    	float MAX_SPEED_LIMIT = OperPlanCalc.MAX_SPEED_LIMIT;//59.0f;
    	float MIN_SPEED_LIMIT = OperPlanCalc.MIN_SPEED_LIMIT;//35.0f;
    	int LIMIT_DIFF_SEC = OperPlanCalc.LIMIT_DIFF_SEC;//35;
    	int MAX_DELAY_SEC = OperPlanCalc.MAX_DELAY_SEC;//20;
    	float STD_AAC = OperPlanCalc.STD_AAC;//1.67f; //기준가속(1.67)
    	float STD_DEC = OperPlanCalc.STD_DEC;//-2.5f; //기준감속(-2.5)
    	
    	//기준값 가져오기
    	Map<String, Object> param = new HashMap<>();
		param.put("CO_CD", Constants.SYS_INFO);
		List<Map<String, Object>> cdList = commonMapper.selectCommonDtlList(param);
		
		for(Map<String, Object> cd : cdList) {
			
			
			try {
				String dlCd =  String.valueOf(cd.get("DL_CD"));
				String val =  String.valueOf(cd.get("TXT_VAL1"));
				
				if(OperPlanCalc.DL_CD_MAX_SPEED_DEFAULT.equals(dlCd)) {
					MAX_SPEED_DEFAULT = Float.valueOf(val);
				} else if(OperPlanCalc.DL_CD_MAX_SPEED_LIMIT.equals(dlCd)) {
					MAX_SPEED_LIMIT = Float.valueOf(val);
				} else if(OperPlanCalc.DL_CD_MIN_SPEED_LIMIT.equals(dlCd)) {
					MIN_SPEED_LIMIT = Float.valueOf(val);
				} else if(OperPlanCalc.DL_CD_LIMIT_DIFF_SEC.equals(dlCd)) {
					LIMIT_DIFF_SEC = Integer.valueOf(val);
				} else if(OperPlanCalc.DL_CD_MAX_DELAY_SEC.equals(dlCd)) {
					MAX_DELAY_SEC = Integer.valueOf(val);
				} else if(OperPlanCalc.DL_CD_STD_AAC.equals(dlCd)) {
					STD_AAC = Float.valueOf(val);
				} else if(OperPlanCalc.DL_CD_STD_DEC.equals(dlCd)) {
					STD_DEC = Float.valueOf(val);
				}
				
			} catch (Exception e) {
				logger.error("{}", e);
			}
			
		}
    	
		

		//정류장 정보
		List<Map<String, Object>> sttnList = null;
		Map<String, Object> sttnMap = null;

		//교차로 정보
		List<Map<String, Object>> crsList = null;
		Map<String, Object> crsMap = null;

		// 노드별 정보 가져오기
		List<Map<String, Object>> nextNodeList = null;
		Map<String, Object> nextNodeMap = null;

		//첨두시
		Map<String, Object> peakTmMap = null;

		//대표노선
		String repRoutId = "";

		//최소/최대 정차시간
		String minStopSec = null;
		String maxStopSec = null;


		Map<String, Object> resultMap = null;

		//insert용
		String rep_route_id = ""; //대표노선아이디
		String route_id;
		String day_div;
		String way_div;
		int alloc_no;
		int oper_sn;
		String node_id;
		String course_id;
		int node_sn;
		String node_type;
		String arrv_tm = "00:00:00";
		String dprt_tm = "00:00:00";
		String prev_dprt_tm = "00:00:00"; //이전 노드 출발 시각
		
		String arrv_tm_temp = "00:00:00"; //정류소 출도착시각 변경용
		int prev_diff_sec_temp = 0; //이전 노드에서부터 걸리는 시간(초) //정류소 출도착시각 변경용
		boolean bSuccess = false; //정류소 출도착시각 변경용

		String link_id;
		int link_len = 0;
		int prev_diff_sec = 0; //이전 노드에서부터 걸리는 시간(초)
		int next_diff_sec = 0; //다음 노드까지 걸리는 시간(초)
		int add_arrv_sec = 0; //다음 노드의 도착시간 연장 시간(초)

		String next_node_id;
		int next_node_sn;
		String next_node_type = "";
		String next_cross_id = "";
		int phase_remain_sec = 0;
		int phase_remain_sec_tmp = 0;
		int enter_phase_no1 = 0;
		int enter_phase_no2 = 0;
		int enter_phase_no3 = 0;
		String sig_ctr_type;


		int route_first_node_sn;
		int route_last_node_sn;
		String route_st_tm;
		String route_ed_tm;

		int min_stop_sec = 0;	 //최소 정차시간(sec)
		int max_stop_sec = 0;	 //최대 정차시간(sec)
		int stop_sec_peak = 0;	 //정류장 필요 정차시간(첨두시)
		int stop_sec_none_peak = 0;	 //정류장 필요 정차시간(비첨두시)
		float max_speed = MAX_SPEED_DEFAULT;	 //최고 속도(km/h)
		float max_speed_per_sec= 0;	 //최고 속도(m/s)
		int max_delay_sec = MAX_DELAY_SEC;	 //연장 최대 시간(초)

		float acc_avg;	 //출발 시 평균 가속도(m/s2)
		float dec_avg;	 //정차 시 평균 감속도(m/s2)
		int acc_avg_tm;	 //출발 시 평균 가속 시간(sec)
		int dec_avg_tm;	 //정차 시 평균 감속 시간(sec)
		int drv_avg_tm;	 //최고속도 주행 시간(sec)

		int acc_len;	 //출발 시 가속 거리(m)
		int dec_len;	 //도착 시 감속 거리(m)
		int drv_len;	 //최고속도 주행 거리(m)

		
		boolean bNeedChgEdTm = false; //도착예정시각변경 필요한 경우 //변경운행에 사용
		

		//리스트에 담아서 한 번에 insert
		List<Map<String, Object>> operNodeList = new ArrayList<>();


		//오전 첨두시 시작/종료
		String am_peak_st_tm = "00:00:00";
		String am_peak_ed_tm = "00:00:00";
		//오후 첨두시 시작/종료
		String pm_peak_st_tm = "00:00:00";
		String pm_peak_ed_tm = "00:00:00";


		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ROUT_ID", routId);
		paramMap.put("DAY_DIV", dayDiv);
		paramMap.put("OPER_SN", operSn);


		//지정된 범위의 노드간 운행계획 //운행계획을 수정하는 경우
		if(stNodeSn > 0) { //특정 순번부터의 노드 가져오기 위함
			paramMap.put("ST_NODE_SN", stNodeSn);
		}

		if(edNodeSn > 0) { //특정 순번까지의 노드 가져오기 위함
			paramMap.put("ED_NODE_SN", edNodeSn);
		}

		List<Map<String, Object>> nodeList = operPlanMapper.selectNodeList(paramMap);


		//운행순번에 따른 기점 출발시각, 종점 도착 시각
		Map<String, Object> routStEdTmInfo = operPlanMapper.selectRoutStEdTm(paramMap);
		route_first_node_sn = Integer.valueOf(routStEdTmInfo.get("FIRST_NODE_SN").toString());
		route_last_node_sn = Integer.valueOf(routStEdTmInfo.get("LAST_NODE_SN").toString());
		route_st_tm = String.valueOf(routStEdTmInfo.get("ROUT_ST_TM")) + ":00";
		route_ed_tm = String.valueOf(routStEdTmInfo.get("ROUT_ED_TM")) + ":00";;



		//대표노선 가져오기
		repRoutId = operPlanMapper.selectRepRout(routId);

		//최소 정차시간 가져오기
		minStopSec = operPlanMapper.selectMinStopTm();

		//최대 정차시간 가져오기
		maxStopSec = operPlanMapper.selectMaxStopTm();

		//노선의 첨두시 가져오기
		peakTmMap = operPlanMapper.selectPeakTm(paramMap);

		rep_route_id = repRoutId;

		min_stop_sec = Integer.valueOf(minStopSec);
		max_stop_sec = Integer.valueOf(maxStopSec);

		am_peak_st_tm = String.valueOf(peakTmMap.get("AM_PEAK_ST_TM")) + ":00";
		am_peak_ed_tm = String.valueOf(peakTmMap.get("AM_PEAK_ED_TM")) + ":00";

		pm_peak_st_tm = String.valueOf(peakTmMap.get("PM_PEAK_ST_TM")) + ":00";
		pm_peak_ed_tm = String.valueOf(peakTmMap.get("PM_PEAK_ED_TM")) + ":00";


		//변수 초기화
		acc_avg = (STD_AAC * 0.8f);//기준가속(1.67) 의 80%
		dec_avg = (STD_DEC * 0.8f); //기준감속(-2.5) 의 80%
		//acc_avg = 1.4f;  //기준가속(1.67) 의 80%
		//dec_avg = -2.1f; //기준감속(-2.5) 의 80%



		//정류장별 정차시간 가져오기
		sttnList = operPlanMapper.selectAllStopTm(routId);
		sttnMap = new HashMap<>();
		for (Map<String, Object> sttn : sttnList) {
			String sttnId = String.valueOf(sttn.get("STTN_ID"));

			sttnMap.put(sttnId, sttn);
		}

		//교차로 정보 가져오기
		crsList = operPlanMapper.selectAllCrsInfo(routId);
		crsMap = new HashMap<>();
		for (Map<String, Object> crs : crsList) {
			String crsId = String.valueOf(crs.get("CRS_ID"));

			crsMap.put(crsId, crs);
		}

		// 정보 가져오기
		nextNodeList = operPlanMapper.selectAllNextNodeInfo(routId);
		nextNodeMap = new HashMap<>();
		for (Map<String, Object> nextNode : nextNodeList) {
			String nodeSn = String.valueOf(nextNode.get("NODE_SN"));

			nextNodeMap.put(nodeSn, nextNode);
		}


		boolean isDone = false; //계획생성 완료여부
		int tryCount = 0;

		while(!isDone) {

			operNodeList.clear();

			max_speed_per_sec = (max_speed*1000/3600); //m/s 로 변경
			
			//운행순번에 따른 기점 출발시각, 종점 도착 시각
			route_first_node_sn = Integer.valueOf(routStEdTmInfo.get("FIRST_NODE_SN").toString());
			route_last_node_sn = Integer.valueOf(routStEdTmInfo.get("LAST_NODE_SN").toString());
			route_st_tm = String.valueOf(routStEdTmInfo.get("ROUT_ST_TM")) + ":00";
			route_ed_tm = String.valueOf(routStEdTmInfo.get("ROUT_ED_TM")) + ":00";;


			//노드별 도착출발시각 계산
			for(Map<String, Object> nodeInfo : nodeList) {

				rep_route_id = String.valueOf(nodeInfo.get("REP_ROUT_ID"));
				route_id = String.valueOf(nodeInfo.get("ROUT_ID"));
				day_div = String.valueOf(nodeInfo.get("DAY_DIV"));
				way_div = String.valueOf(nodeInfo.get("WAY_DIV"));
				course_id = String.valueOf(nodeInfo.get("COR_ID"));
				node_id  = String.valueOf(nodeInfo.get("NODE_ID"));
				node_sn = Integer.valueOf(nodeInfo.get("NODE_SN").toString());
				node_type = String.valueOf(nodeInfo.get("NODE_TYPE"));
				alloc_no = Integer.valueOf(nodeInfo.get("ALLOC_NO").toString());
				link_id = String.valueOf(nodeInfo.get("LINK_ID"));
				link_len = CommonUtil.bigDecimalToInt(nodeInfo.get("LEN"));

				acc_avg_tm = (int)((max_speed_per_sec-0) / acc_avg); //평균 가속 시간
				dec_avg_tm = (int)((0 - max_speed_per_sec) / dec_avg); //평균 감속 시간

				//다음노드정보 확인
				paramMap.put("ROUT_ID", route_id);
				paramMap.put("NODE_ID", node_id);
				paramMap.put("NODE_SN", node_sn);

				next_node_id = String.valueOf(nodeInfo.get("ED_NODE_ID"));

				//다음노드 순번 가져오기
				next_node_sn = node_sn + 1;
				for(int i = node_sn; i<nextNodeList.size(); i++) {

					Map<String, Object> nextNode = nextNodeList.get(i);
					String ndId = String.valueOf(nextNode.get("NODE_ID"));

					if(next_node_id.equals(ndId)) {
						next_node_sn = Integer.valueOf(nextNode.get("NODE_SN").toString());
					}

				}



				Map<String, Object> nextNodePhaseInfo = null;

				if(node_sn < route_last_node_sn) { //마지막 노드가 아닌경우 //다음노드까지 걸리는 시간 계산

					paramMap.put("ROUT_ID", route_id);
					paramMap.put("NODE_ID", next_node_id);
					paramMap.put("NODE_SN", next_node_sn);
					nextNodePhaseInfo = (Map<String, Object>) nextNodeMap.get(String.valueOf(next_node_sn));

					//다음노드 진입현시 정보 확인
					next_node_type = String.valueOf(nextNodePhaseInfo.get("NODE_TYPE"));
					next_cross_id = String.valueOf(nextNodePhaseInfo.get("CRS_ID"));

					acc_len = 0;
					dec_len = 0;

					if (node_type.equals(Constants.NODE_TYPE_BUSSTOP) || node_sn == route_first_node_sn) {  //정류장에서 출발하는 경우 or 첫번째 노드에서 출발

						//가속구간
						acc_len = (int) (0 + (acc_avg * Math.pow(acc_avg_tm, 2)) / 2);

						//가속구간이 링크길이보다 긴 경우
						if (acc_len >= link_len) {
							acc_len = link_len;
							acc_avg_tm = (int) Math.sqrt(2 * acc_len / acc_avg);
						}
					}


					if (next_node_type.equals(Constants.NODE_TYPE_BUSSTOP)) {  //다음노드가 정류장인 경우
						//감속구간
						dec_len = (int) ((max_speed_per_sec * dec_avg_tm) + (dec_avg * Math.pow(dec_avg_tm, 2)) / 2);

						//감속구간이 링크길이보다 긴 경우
						if (dec_len >= link_len) {
							dec_len = link_len;
							//ET dec_len = 0;

							//SET dec_avg = (0 - POW(max_speed_per_sec, 2)) / (2*dec_len);
							//SET dec_avg_tm = SQRT(2*dec_len/dec_avg);
							dec_avg_tm = dec_avg_tm / 2;

						}
					}


					//위 경우를 제외한 나머지 구간은 최고속도로 달린다고 가정
					//최고속도로 달리는 시간
					drv_avg_tm = (int) ((link_len - acc_len - dec_len) / max_speed_per_sec);

					if (acc_len == 0) {
						acc_avg_tm = 0;
					}

					if (dec_len == 0) {
						dec_avg_tm = 0;
					}


					//다음노드까지 걸리는 시간
					//가속시간 + 감속시간 + 등속주행시간
					next_diff_sec = acc_avg_tm + dec_avg_tm + drv_avg_tm;

				} //if(node_sn < route_last_node_sn)


				//도착시각/출발시각 계산
				if(node_sn == route_first_node_sn) {      //노선의 첫번째 노드이면
					
					dprt_tm = route_st_tm;
					arrv_tm = dprt_tm; //도착시각=출발시각
					
					//변경운행
					if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) {
						if (node_sn == stNodeSn) { //변경운행 시작노드

							//변경운행
							int diffSec1 = 0;
							
							if(offsetTm > 0) { //늦게 도착한 경우
								diffSec1 = DateUtil.diffSeconds(timeMax, arrv_tm, TIME_PATTERN);
							} else { //일찍 도착한 경우
								diffSec1 = DateUtil.diffSeconds(arrv_tm, timeMin, TIME_PATTERN);
							}
							
							logger.info("변경운행 전 도착예정시각1:{}, diffSec1:{}, offsetTm:{}", route_ed_tm, diffSec1, offsetTm);
							
							route_st_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, (diffSec1+offsetTm));
							

							//변경운행 생성시 종점 도착시각은 변경하지 않는 것을 기본으로 하자!!
							//도착시각을 맞출 수 없는 경우에는 도착시각 변경(속도를 최대로 올리거나 최소로 낮춰도 안 맞는 경우..)
							if(bNeedChgEdTm == true) {
								route_ed_tm = DateUtil.addSeconds2(route_ed_tm, TIME_PATTERN, (diffSec1+offsetTm)); //변경운행인 경우 기준도착시간도 변경
							}

							
							logger.info("변경운행 후 도착예정시각1:{}", route_ed_tm);
							

						}
						
					} //변경운행 종료

					
					//출/도착시각 변경
					dprt_tm = route_st_tm;
					arrv_tm = dprt_tm; //도착시각=출발시각
					
					//첫 번째 노드가 정류장이 아닌경우 첫번째 정류장의 출발시각 설정
					if(!node_type.equals(Constants.NODE_TYPE_BUSSTOP) && next_node_type.equals(Constants.NODE_TYPE_BUSSTOP)) {
						next_diff_sec = 0;
						prev_diff_sec = 0;
						prev_dprt_tm = dprt_tm;
					} else {
						prev_diff_sec = next_diff_sec;
						prev_dprt_tm = dprt_tm;
					}

				} else if(node_sn == route_last_node_sn && !node_type.equals(Constants.NODE_TYPE_BUSSTOP)) {  //노선의 마지막 노드이고 정류장아 아니면

					//마지막 노드가 정류장이 아니면 이전 노드(정류장)의 시간을 그대로 사용
					arrv_tm = prev_dprt_tm;
					dprt_tm = arrv_tm; //출발시각=도착시각

				} else {

					//변경운행
					if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) {
						if (node_sn == stNodeSn) { //변경운행 시작노드

							paramMap = new HashMap<>();
							paramMap.put("ROUT_ID", routId);
							paramMap.put("NODE_SN", node_sn);
							paramMap.put("OPER_SN", operSn);

							resultMap = operPlanMapper.selectArrvDprtTm(paramMap);

							String nodeArrvTm = String.valueOf(resultMap.get("ARRV_TM"));
							String nodeDprtTm = String.valueOf(resultMap.get("DPRT_TM"));

							
							//변경운행
							int diffSec1 = 0;
							
							if(offsetTm > 0) { //늦게 도착한 경우
								diffSec1 = DateUtil.diffSeconds(timeMax, nodeArrvTm, TIME_PATTERN);
							} else { //일찍 도착한 경우
								diffSec1 = DateUtil.diffSeconds(nodeArrvTm, timeMin, TIME_PATTERN);
							}
							
							logger.info("변경운행 전 도착예정시각2:{}, diffSec1:{}, offsetTm:{}", route_ed_tm, diffSec1, offsetTm);
							
							arrv_tm = DateUtil.addSeconds2(nodeArrvTm, TIME_PATTERN, (diffSec1+offsetTm));
							
							//변경운행 생성시 종점 도착시각은 변경하지 않는 것을 기본으로 하자!!
							//도착시각을 맞출 수 없는 경우에는 도착시각 변경(속도를 최대로 올리거나 최소로 낮춰도 안 맞는 경우..)
							if(bNeedChgEdTm == true) {
								route_ed_tm = DateUtil.addSeconds2(route_ed_tm, TIME_PATTERN, (diffSec1+offsetTm)); //변경운행인 경우 기준도착시간도 변경
							}
							
							logger.info("변경운행 후 도착예정시각2:{}", route_ed_tm);
							

						} else { //이후 노드들
							arrv_tm = DateUtil.addSeconds2(prev_dprt_tm, TIME_PATTERN, prev_diff_sec); //이전 노드 출발시각 + 현재노드까지 걸리는 시간
							arrv_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, add_arrv_sec); //신호가 걸려 추가된 시간
						}

					} else if(chgType == OperPlanCalc.CHG_TYPE_MODIFY) { //변경운행은 아니고, 수정인 경우

						if(node_sn == stNodeSn) {
							arrv_tm = stNodeArrvTm;
							dprt_tm = stNodeDprtTm;
						} else if(node_sn == edNodeSn) {
							
							arrv_tm = edNodeArrvTm;
							dprt_tm = edNodeDprtTm;
							
							arrv_tm_temp = DateUtil.addSeconds2(prev_dprt_tm, TIME_PATTERN, prev_diff_sec); //이전 노드 출발시각 + 현재노드까지 걸리는 시간
							//arrv_tm_temp = DateUtil.addSeconds2(arrv_tm_temp, TIME_PATTERN, add_arrv_sec); //신호가 걸려 추가된 시간
							
							prev_diff_sec_temp = prev_diff_sec;
							
							
						} else {
							arrv_tm = DateUtil.addSeconds2(prev_dprt_tm, TIME_PATTERN, prev_diff_sec); //이전 노드 출발시각 + 현재노드까지 걸리는 시간
							//arrv_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, add_arrv_sec); //신호가 걸려 추가된 시간
						}

					} else {
						arrv_tm = DateUtil.addSeconds2(prev_dprt_tm, TIME_PATTERN, prev_diff_sec); //이전 노드 출발시각 + 현재노드까지 걸리는 시간
						arrv_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, add_arrv_sec); //신호가 걸려 추가된 시간
					}



					add_arrv_sec = 0; //초기화
					stop_sec_peak = 0;
					stop_sec_none_peak = 0;

					//노드타입에 따라 최소정차시간 추가
					if(node_type.equals(Constants.NODE_TYPE_BUSSTOP)) { //정류장

						if(chgType != OperPlanCalc.CHG_TYPE_MODIFY) { //수정인 경우에는 정류장 출도착 시각을 전달받음

							//정류장별 필요정차시간 가져오기
							paramMap.put("NODE_ID", node_id);
							resultMap = (Map<String, Object>) sttnMap.get(node_id);


							stop_sec_peak = CommonUtil.bigDecimalToInt(resultMap.get("STOP_TM_PEAK"));
							stop_sec_none_peak =  CommonUtil.bigDecimalToInt(resultMap.get("STOP_TM_NONE_PEAK"));


							//20210927 최소정차시간 설정
							/*
							if(stop_sec_peak <= min_stop_sec) {
							    stop_sec_peak = min_stop_sec;
							}
							
							if(stop_sec_none_peak <= min_stop_sec) {
							    stop_sec_none_peak = min_stop_sec;
							}
							 */



							if(stop_sec_peak == 0 || stop_sec_none_peak == 0){ //데이터가 없는 경우
								dprt_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, min_stop_sec); //최소정차시간 추가

							} else { //정류장별 필요정차시간 확인

								//################정차시간 조정
								//#SET stop_sec_peak = stop_sec_peak * 1.2;
								//#SET stop_sec_none_peak = stop_sec_none_peak * 1.2;

								try {
									if ( DateUtil.diffTime(arrv_tm, am_peak_st_tm, "HH:mm:ss") >= 0
											&& DateUtil.diffTime(arrv_tm, am_peak_ed_tm, "HH:mm:ss") <=0 ) { //오전첨두시
	
										dprt_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, stop_sec_peak);
	
									} else if (DateUtil.diffTime(arrv_tm, pm_peak_st_tm, "HH:mm:ss") >= 0
											&& DateUtil.diffTime(arrv_tm, pm_peak_ed_tm, "HH:mm:ss") <=0 ) { //오후첨두시
	
										dprt_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, stop_sec_peak);
	
									} else { //비첨두시
										//dprt_tm = DATE_ADD(arrv_tm, INTERVAL stop_sec_none_peak SECOND);
										dprt_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, stop_sec_none_peak);
									}
									
								} catch (Exception e) {
									//필요정차시간이 없는 경우
									//e.printStackTrace();
									
									dprt_tm = DateUtil.addSeconds2(arrv_tm, TIME_PATTERN, stop_sec_none_peak); //비첨두시정차시간 추가
								}

							} /*else //필요정차시간이 없는 경우
							    dprt_tm = DATE_ADD(arrv_tm, INTERVAL min_stop_sec SECOND); --최소정차시간 추가
							}*/

						} //if(chgType != CHG_TYPE_MODIFY)		
						else   { //OperPlanCalc.CHG_TYPE_MODIFY
							
						}
						

					} else {
						dprt_tm = arrv_tm;
					}


					prev_diff_sec = next_diff_sec;
					prev_dprt_tm = dprt_tm;


					//마지막 정류장 처리
					if(next_node_sn == route_last_node_sn) { // 다음노드가 노선의 마지막 노드이면

						if(node_type.equals(Constants.NODE_TYPE_BUSSTOP) && !next_node_type.equals(Constants.NODE_TYPE_BUSSTOP)){ //다음 노드가 정류장이 아니면

							//출발시각 = 도착시각
							dprt_tm = arrv_tm; //출발시각=도착시각
							prev_dprt_tm = dprt_tm;
						}

					}

				}


				if(node_sn != route_last_node_sn) {


					if(next_node_type.equals(Constants.NODE_TYPE_CROSS) && !StringUtils.isEmpty(next_cross_id)){ //다음 노드가 교차로이면

						//현시정보 확인가능여부
						paramMap.put("NODE_ID", next_cross_id);

						resultMap = (Map<String, Object>) crsMap.get(next_cross_id);
						sig_ctr_type = String.valueOf(resultMap.get("SIG_CTR_TYPE"));


						//현시정보 확인이 가능한 경우 [[
						if(!StringUtils.isEmpty(sig_ctr_type)){

							enter_phase_no1 = CommonUtil.bigDecimalToInt(nextNodePhaseInfo.get("ENT_PHASE_NO_1"));
							enter_phase_no2 = CommonUtil.bigDecimalToInt(nextNodePhaseInfo.get("ENT_PHASE_NO_2"));
							enter_phase_no3 = CommonUtil.bigDecimalToInt(nextNodePhaseInfo.get("ENT_PHASE_NO_3"));

							if(enter_phase_no1 != 0) {
								//교차로 도착예정 시각의 진입현시 확인
								paramMap.put("NODE_ID", next_cross_id);
								paramMap.put("DPRT_TM", DateUtil.addSeconds2(dprt_tm, TIME_PATTERN, next_diff_sec));
								//paramMap.put("DIFF_TM", next_diff_sec);
								paramMap.put("PHASE_NUM", enter_phase_no1);
								paramMap.put("DAY_DIV", day_div);

								
								if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) { //변경운행 생성 시에는 신호 고려
									phase_remain_sec = operPlanMapper.selectPhaseRemainTm(paramMap);
								} else { //CHG_TYPE_NONE, CHG_TYPE_MODIFY 
									phase_remain_sec = 0; //운행계획 생성, 수정 시에는 모든신호 통과
								}
								

								if(enter_phase_no2 != 0) {

									// 다음 교차로 도착예정 시각에 enter_phase_no1 현시가 아닌경우
									// enter_phase_no2 가 있는지 확인
									if (phase_remain_sec <= 0) {
										phase_remain_sec_tmp = 0;

										paramMap.put("PHASE_NUM", enter_phase_no2);
										
										if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) { //변경운행 생성 시에는 신호 고려
											phase_remain_sec_tmp = operPlanMapper.selectPhaseRemainTm(paramMap);
										} else { //CHG_TYPE_NONE, CHG_TYPE_MODIFY 
											phase_remain_sec_tmp = 0; //운행계획 생성, 수정 시에는 모든신호 통과
										}
										

										if (phase_remain_sec_tmp > 0) {
											phase_remain_sec = phase_remain_sec_tmp;
										}
									}


									//다음 교차로 도착예정 시각에 enter_phase_no2 현시가 아닌경우
									//enter_phase_no3 가 있는지 확인
									if(enter_phase_no3 != 0) {
										if (phase_remain_sec <= 0) {
											phase_remain_sec_tmp = 0;

											paramMap.put("PHASE_NUM", enter_phase_no3);
											
											if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) { //변경운행 생성 시에는 신호 고려
												phase_remain_sec_tmp = operPlanMapper.selectPhaseRemainTm(paramMap);
											} else { //CHG_TYPE_NONE, CHG_TYPE_MODIFY 
												phase_remain_sec_tmp = 0; //운행계획 생성, 수정 시에는 모든신호 통과
											}

											if (phase_remain_sec_tmp > 0) {
												phase_remain_sec = phase_remain_sec_tmp;
											}

										}
									} //if(enter_phase_no3 != 0) {

								} //if(enter_phase_no2 != 0)

							} //if(enter_phase_no1 != 0) {

						}
						//현시정보 확인이 가능한 경우 ]]


						//다음 교차로 도착예정 시각에 원하는 현시가 아닌 경우(다음 초록불까지 남은 시간..)
						if(phase_remain_sec < 0) {

							//다음 초록불까지 남은 시간에 따라
							//현재 노드가 정류장이면 출발시각을 늦춘다(20초 정도까지..)
							if(node_type.equals(Constants.NODE_TYPE_BUSSTOP)) { //정류장
								if(Math.abs(phase_remain_sec) > max_delay_sec) {
									dprt_tm = DateUtil.addSeconds2(dprt_tm, TIME_PATTERN, max_delay_sec);
								} else {
									dprt_tm = DateUtil.addSeconds2(dprt_tm, TIME_PATTERN, Math.abs(phase_remain_sec));
								}
							} else {
								//현재 노드가 정류장이 아닌 경우 다음 노드의 도착시각을 늦춘다??
								if(Math.abs(phase_remain_sec) > max_delay_sec) {
									add_arrv_sec = max_delay_sec;
								} else {
									add_arrv_sec = Math.abs(phase_remain_sec);
								}
							}

						}


					} else { //다음노드가 교차로가 아니면
						phase_remain_sec = 0;
					}

					prev_diff_sec = next_diff_sec;
					prev_dprt_tm = dprt_tm;

				}

				/*
				if(chgType == CHG_TYPE_MODIFY) { //변경운행은 아니고, 수정인 경우
					if(node_sn == edNodeSn) {
						arrv_tm = edNodeArrvTm;
						dprt_tm = edNodeDprtTm;
					}
				}
				 */


				Map<String, Object> insertParamMap = new HashMap<>();

				insertParamMap.put("REP_ROUT_ID", rep_route_id);
				insertParamMap.put("DAY_DIV", day_div);
				insertParamMap.put("WAY_DIV", way_div);
				insertParamMap.put("ALLOC_NO", alloc_no);
				insertParamMap.put("OPER_SN", operSn);
				insertParamMap.put("ROUT_ID", route_id);
				insertParamMap.put("NODE_ID", node_id);
				insertParamMap.put("NODE_SN", node_sn);
				insertParamMap.put("COR_ID", course_id);
				insertParamMap.put("NODE_TYPE", node_type);
				insertParamMap.put("ARRV_TM", arrv_tm);
				insertParamMap.put("DPRT_TM", dprt_tm);
				//insertParamMap.put("TEST", link_len);
				//insertParamMap.put("TEST2", phase_remain_sec);
				//insertParamMap.put("TEST3", next_cross_id);

				operNodeList.add(insertParamMap);



				//logger.info("DAY_DIV:{}, WAY_DIV:{}, ALLOC_NO:{}, OPER_SN:{}, ROUT_ID:{}, NODE_ID:{}, NODE_SN:{}, NODE_TYPE:{}, ARRV_TM:{}, DPRT_TM:{}, TEST:{}, TEST2:{}, TEST3:{}"
				//        , day_div, way_div, alloc_no, operSn, route_id, node_id, node_sn, node_type, arrv_tm, dprt_tm, link_len, phase_remain_sec, next_cross_id);

			} //for(Map<String, Object> nodeInfo : nodeList)


			//#계산 해보니 최종 도착시간이 맞지 않다.
			//시간 비교

			int diffSec = DateUtil.diffSeconds(arrv_tm, route_ed_tm, "HH:mm:ss");
			
			
			if(chgType == OperPlanCalc.CHG_TYPE_MODIFY) {
				diffSec = DateUtil.diffSeconds(arrv_tm_temp, edNodeArrvTm, "HH:mm:ss");
				
				logger.info("도착시각:{}, 기준도착시각:{}, 차이:{}초, 이전노드부터차이:{}초", arrv_tm_temp, edNodeArrvTm, diffSec, prev_diff_sec_temp);
				
				
				String prevDprtTm =  DateUtil.addSeconds2(arrv_tm_temp, "HH:mm:ss", -prev_diff_sec_temp);
				if(DateUtil.diffTime(edNodeArrvTm, prevDprtTm, "HH:mm:ss") <= 0) {
					//fail
					bSuccess = false;
				} else {
					bSuccess = true;
				}
				
				
				
			} else {
				logger.info("도착시각:{}, 기준도착시각:{}, 차이:{}초", arrv_tm, route_ed_tm, diffSec);
			}
			

			/*if(chgType == OperPlanCalc.CHG_TYPE_MODIFY) {
				isDone = true;
				tryCount = 0;
			} else*/
			{
				

				if ( (chgType != OperPlanCalc.CHG_TYPE_MODIFY && (Math.abs(diffSec) > LIMIT_DIFF_SEC)) ||
						(chgType == OperPlanCalc.CHG_TYPE_MODIFY && (bSuccess == false)) 
				) {

					//#도착시간이 빠른 경우
					if (diffSec < 0) {

						if (max_speed > (MIN_SPEED_LIMIT-5)) {

							if (Math.abs(Math.abs(diffSec) - LIMIT_DIFF_SEC) <= 5) {
								max_speed -= 0.2;
								//max_speed--;
							} else if (Math.abs(Math.abs(diffSec) - LIMIT_DIFF_SEC) <= 10) {
								max_speed -= 0.5;
								//max_speed--;
							} else {
								//max_speed--;
								max_speed -= 1.0;
							}

							logger.info("최대속도 변경:{}", max_speed);
						} else {

						}
						
						isDone = false;
						tryCount++;
						

						if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) {
							
							if (max_speed <= (MIN_SPEED_LIMIT-5)) {
								
								logger.info("도착예정시각 변경 필요함!!!");
								
								max_speed = MAX_SPEED_DEFAULT; //최대속도 초기화			
								
								//변경운행에 도착예정시각 변경이 필요한경우
								bNeedChgEdTm = true;
								tryCount = 0;
							}
						} 
						
						

						if (tryCount >= OperPlanCalc.MAX_TRY_COUNT) {
							tryCount = 0;

							//최대속도변경으로 안되는 경우
							max_speed = MAX_SPEED_DEFAULT; //최대속도 초기화

							//가속도 변경
							
							return null; //bhmin tmp //변경운행 생성 실패

						}


					} else { //#도착시간이 늦는 경우

						if (max_speed < MAX_SPEED_LIMIT) {
							if (Math.abs(Math.abs(diffSec) - LIMIT_DIFF_SEC) <= 5) {
								max_speed += 0.2;
								//max_speed++;
							} else if (Math.abs(Math.abs(diffSec) - LIMIT_DIFF_SEC) <= 10) {
								max_speed += 0.5;
								//max_speed++;
							} else {
								//max_speed++;
								max_speed += 1.0;
							}

							logger.info("최대속도 변경:{}", max_speed);
						} else {

						}

						isDone = false;
						tryCount++;
						

						if(chgType == OperPlanCalc.CHG_TYPE_CHG_OPER) {
							
							if (max_speed >= MAX_SPEED_LIMIT) {
								
								logger.info("도착예정시각 변경 필요함!!!");
								
								max_speed = MAX_SPEED_DEFAULT; //최대속도 초기화			
								
								//변경운행에 도착예정시각 변경이 필요한경우
								bNeedChgEdTm = true;
								tryCount = 0;
							}
						} 						
						
						
						if (tryCount >= OperPlanCalc.MAX_TRY_COUNT) {
							tryCount = 0;

							//최대속도변경으로 안되는 경우
							max_speed = MAX_SPEED_DEFAULT; //최대속도 초기화

							//가속도 변경
							
							return null; //bhmin tmp //변경운행 생성 실패

						}						
						
					}

				} else {
					isDone = true;
					tryCount = 0;
				}

			}

		} //while(!isDone)


		if(bSave) {
			
			
			if(chgType == OperPlanCalc.CHG_TYPE_NONE) {
			
				//insert 전 delete
				paramMap.put("REP_ROUT_ID", rep_route_id);
				operPlanMapper.deleteOperPl(paramMap);
	
				//리스트 한 번에 insert
				Map<String, Object> insertMap = new HashMap<>();
				insertMap.put("ITEM_LIST", operNodeList);
				operPlanMapper.insertOperAllocPlNodeInfoList(insertMap);
				
			} else if(chgType == OperPlanCalc.CHG_TYPE_MODIFY) { //정류소출도착 시각 변경인 경우
				//update
				//리스트 한 번에 insert
				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("ITEM_LIST", operNodeList);
				operPlanMapper.updateOperAllocPlNodeInfoList(updateMap);
			}
		}


		return operNodeList;

	}

	
	
	/**
	 * 운행계획 생성 
	 */
	public List makeOperAllocPlNodeInfo(String routId, String dayDiv, int operSn, boolean bSave) {
		
		List operPlList = null;
		
		try {
			operPlList = makeOperAllocPlNodeInfo(routId, dayDiv, operSn, bSave, 0, 0, 0, null, null, null, null, null, null, OperPlanCalc.CHG_TYPE_NONE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return operPlList;		
		
	}
	
	
	
	/**
	 * 변경운행계획 생성
	 */
	public List makeChgOperAllocPlNodeInfo(String vhcId, String routId, String operDt, int operSn
			,String stNodeId
			,int stNodeSn //변경운행 //변경운행 시작 노드
			,int offsetTm //변경운행 //도착 예정시간과의 차이(초))
			,String timeMin //변경운행이 발생하지 않는 최소도착시각
			,String timeMax //변경운행이 발생하지 않는 최대도착시각			
			,boolean bSave
	) {
		List chgPlList = null;
		
		try {
			
			logger.info("변경운행 생성 시작 >> routId:{}, operDt:{}, operSn:{}, stNodeSn:{}, diffTm:{}, timeMin:{}, timeMax:{}"
					, routId, operDt, operSn, stNodeSn, offsetTm, timeMin, timeMax);
			
			String dayDiv = operPlanMapper.selectDayDiv(operDt);
			
			chgPlList = makeOperAllocPlNodeInfo(routId, dayDiv, operSn, false, stNodeSn, 0, offsetTm, timeMin, timeMax, null, null, null, null, OperPlanCalc.CHG_TYPE_CHG_OPER);
			
			if(bSave) {
				
				if(chgPlList != null && chgPlList.size() > 0) {
				
					Map<String, Object> chgInfo = (Map<String, Object>)chgPlList.get(0);
					
		            Map<String, Object> insertMap = new HashMap<>();
		            
		            insertMap.put("VHC_ID", vhcId);
		            insertMap.put("OCR_NODE_ID", stNodeId);
		            insertMap.put("OPER_DT", operDt);
		            insertMap.put("REP_ROUT_ID", chgInfo.get("REP_ROUT_ID"));
		            insertMap.put("ROUT_ID", routId);
		            insertMap.put("ALLOC_NO", chgInfo.get("ALLOC_NO"));
		            insertMap.put("OPER_SN", operSn);
		            insertMap.put("OCR_DTM", Instant.now(Clock.systemUTC()));
		            insertMap.put("VHC_ID", vhcId);
		            insertMap.put(Constants.UPD_DTM, Instant.now(Clock.systemUTC()));
		            operPlanMapper.insertChgOperInfo(insertMap);
		            
	
		            //리스트 한 번에 insert
		            insertMap.put("OPER_DT", operDt);
		            insertMap.put("ITEM_LIST", chgPlList);
					operPlanMapper.insertChgOperDtlInfo(insertMap);
		            
		            logger.info("변경운행 생성 완료!!");
		            
		            
				} else {
					logger.info("변경운행 생성 실패!!");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return chgPlList;
		
	}
	

	
	/**
	 * 특정 정류소 출도착 시각 변경
	 */	
	public List changeOperAllocPlNodeInfo(Map data) {	
		
		List operPlList = null;
		
		try {			
			boolean bSave = true;
			
			String routId = data.get("ROUT_ID").toString();
			String dayDiv = data.get("DAY_DIV").toString();
			int operSn = Integer.valueOf(data.get("OPER_SN").toString());
			int nodeSn = Integer.valueOf(data.get("NODE_SN").toString()); //변경할 정류소 노드 순번
			String chgArrvTm = data.get("ARRV_TM").toString(); //변경된 도착시각
			String chgDprtTm =  data.get("DPRT_TM").toString();	//변경된 출발시각 
			
			
			int stNodeSn = nodeSn;
			int edNodeSn= nodeSn;
			String stNodeArrvTm = chgArrvTm;
			String stNodeDprtTm = chgDprtTm;
			String edNodeArrvTm = chgArrvTm;
			String edNodeDprtTm = chgDprtTm;
			
			
			
			//원래 계획되어 있던 운행계획이랑 비교해서 도착시각이 변경되었는지 출발시각이 변경되었는지 확인
			Map<String, Object> params = new HashMap<>();
			params.put("ROUT_ID", routId);
			params.put("DAY_DIV", dayDiv);
			params.put("OPER_SN", operSn);
			params.put("NODE_SN", nodeSn);
			
			List<Map<String, Object>> plNodeList = operPlanMapper.selectOperAllocPlanNode(params);
			
			if(plNodeList == null) {
				return null;
			}
			
			params.put("NODE_SN", null);
			params.put("NODE_TYPE", Constants.NODE_TYPE_BUSSTOP);
			List<Map<String, Object>> plSttnList = operPlanMapper.selectOperAllocPlanNode(params);
			
			
			Map<String, Object> chgNodeInfo = plNodeList.get(0);
			
			boolean bFoundStNode = false;
			
			
			//도착시각이 변경된 경우와 출발시각이 변경된 경우 각각 처리	
			//도착시각이 변경된경우 : 이전 정류소의 다음 노드 도착시각부터 해당 정류소 도착시각까지 계획 수정
			String arrvTmOrigin = String.valueOf(chgNodeInfo.get("ARRV_TM"));
			if(DateUtil.diffTime(arrvTmOrigin, chgArrvTm, "HH:mm:ss") != 0) {
				
				stNodeSn = nodeSn;
				
				//변경시작노드 찾기
				for(int i = plSttnList.size()-1; i>=0; i--) { //노드순번 오름차순으로 리스트가 구성되어 있으므로 거꾸로 내려오면서 노드순번이 낮은걸 찾는다.
					Map<String, Object> sttn = plSttnList.get(i);
					
					int sttnNodeSn = Integer.valueOf(sttn.get("NODE_SN").toString());
					
					if(sttnNodeSn < stNodeSn) {
						stNodeSn = sttnNodeSn;
						stNodeArrvTm = sttn.get("ARRV_TM").toString();
						stNodeDprtTm = sttn.get("DPRT_TM").toString(); 
						
						bFoundStNode = true;
						break;
					}
				}
				
				//이전 정류소가 없는경우 //첫 정류소인 경우
				if(bFoundStNode == false) {
					//do nothing
				}
				
								
				edNodeSn= nodeSn;
				edNodeArrvTm = chgArrvTm;
				edNodeDprtTm = chgDprtTm;
				
				
				if(stNodeSn == edNodeSn) {
					return null;
				}
				
				
				operPlList = makeOperAllocPlNodeInfo(routId, dayDiv, operSn, bSave,
						stNodeSn, edNodeSn,
						0, null, null, 
						stNodeArrvTm, stNodeDprtTm, edNodeArrvTm, edNodeDprtTm, OperPlanCalc.CHG_TYPE_MODIFY);				
			} 
			
			
			//출발시각이 변경된경우 : 해당 정류소 출발부터 다음 정류소의 이전 노드 출발시각까지 계획 수정
			String dprtTmOrigin = String.valueOf(chgNodeInfo.get("DPRT_TM"));
			if(DateUtil.diffTime(dprtTmOrigin, chgDprtTm, "HH:mm:ss") != 0) {
				//변경종료노드 찾기
				edNodeSn = nodeSn;
				
				
				boolean bFoundEdNode = false;
				for(Map<String, Object> sttn : plSttnList) { //노드순번 오름차순으로 리스트가 구성되어 있으므로 차례로 올라가면서 노드순번이 높은걸 찾는다.
					
					int sttnNodeSn = Integer.valueOf(sttn.get("NODE_SN").toString());
					
					if(sttnNodeSn > edNodeSn) {
						edNodeSn = sttnNodeSn;
						edNodeArrvTm = sttn.get("ARRV_TM").toString();
						edNodeDprtTm = sttn.get("DPRT_TM").toString(); 
						
						bFoundEdNode = true;
						break;
					}
				}
				
				
				//다음 정류소가 없는경우 //마지막 정류소인 경우
				if(bFoundEdNode == false) {
					//do nothing
				}
				
				
				stNodeSn= nodeSn;
				stNodeArrvTm = chgArrvTm;
				stNodeDprtTm = chgDprtTm;
				
				
				if(stNodeSn == edNodeSn) {
					return null;
				}
				
				operPlList = makeOperAllocPlNodeInfo(routId, dayDiv, operSn, bSave,
						stNodeSn, edNodeSn,
						0, null, null, 
						stNodeArrvTm, stNodeDprtTm, edNodeArrvTm, edNodeDprtTm, OperPlanCalc.CHG_TYPE_MODIFY);
				
			} 		
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//logger.info("정류소출도착시각변경결과 : {}", operPlList);
		
		return operPlList;			
		
	}
	
	
	
	public List<Map<String, Object>> selectOperPlanRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return operPlanMapper.selectOperPlanRout(map);
	}
	
	public List<Map<String, Object>> selectOperPlanRoutAsc() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		map.put("WAY_DIV", "WD001");
		return operPlanMapper.selectOperPlanRout(map);
	}


	public List<Map<String, Object>> selectOperAllocPlanNode() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return operPlanMapper.selectOperAllocPlanNode(map);
	}
	
	public List<Map<String, Object>> selectOperAllocRealNode() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return operPlanMapper.selectOperAllocRealNode(map);
	}


	public List<Map<String, Object>> selectOperPlanRoutDesc() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		map.put("WAY_DIV", "WD002");
		return operPlanMapper.selectOperPlanRout(map);
	}
	
	public List<Map<String, Object>> selectCourseList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return operPlanMapper.selectCourseList(map);
	}
	
	public List<Map<String, Object>> selectOperAllocPlanCourseList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return operPlanMapper.selectOperAllocPlanCourseList(map);
	}
	
	public List<Map<String, Object>> selectCourseDtlList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("COR_IDS").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("COR_IDS", temp);
		return operPlanMapper.selectCourseDtlList(map);
	}

	public List<Map<String, Object>> selectTargetCourseDtlList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("COR_IDS").toString().replace("[","").replace("]","").replace(" ","").split(",");
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>>  list  =new ArrayList<>();
		
		for(int i=0; i<temp.length; i++ ) {
			param.put("COR_ID",temp[i]);
			list.addAll(operPlanMapper.selectCourseDtlList(param));
		}
		return list;
	}

}
