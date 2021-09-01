package kr.tracom.bms.domain.PI0701;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0701.PI0701Mapper;
import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0701Service extends ServiceSupport {

	@Autowired
	private PI0701Mapper pi0701Mapper;
	
	@Autowired
	private FTPHandler ftpHandler;
	
	public List PI0701G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List returnList = pi0701Mapper.PI0701G0R0(map);
		
		/*for(Object obj:returnList) {
			Map<String, Object> temp = (Map<String, Object>) obj;
			temp.put("FILE_PATH", "/fileUpload/destination/"+temp.get("DVC_NM"));			
		}*/
		
		
		return returnList;
	}
	
	//스케쥴 파일 Read
	public List PI0701G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_ROUT_MST");
		String deviceCd = (String)map.get("SELECTED_DL");
		String schFileName = (String)map.get("SCH_FILE_NM");
		
		return ftpHandler.readSCH(deviceCd, schFileName);
	}
	
	public List PI0701SHI0() throws Exception {
		return pi0701Mapper.PI0701SHI0();
	}
	
	public List PI0701SHI1() throws Exception {
		return pi0701Mapper.PI0701SHI1();
	}
	
	/*public List PI0701P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0701Mapper.PI0701P0R0(map);
	}
		*/
	
	
	public Map PI0701G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_ROUT_MST");
		
		//선택한 노선의 정보, fileNM
		Map<String, Object> map_param = getSimpleDataMap("dma_ROUT_MST");
		String path = "temp/destination/"+map_param.get("SELECTED_DL").toString()+"/";
		String fileNM = map_param.get("FILE_NM").toString();
		String schFileNM = map_param.get("SCH_FILE_NM").toString();
		
		
		//동작설정 그리드 데이터 리스트
		List<Map<String, Object>> actionData = getSimpleList("dlt_actionSetting");
		
		try {
			
			//파일 변경시 저장부분
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {

					uCnt += 1;
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)) 
						{
							doMoveFile("up/",path,data.get("FILE_NM").toString(), fileNM);
						}					
				 } 
			}
			
			//동작설정(서브그리드) 변경 시 저장 부분
			for(int i=0; i<actionData.size(); i++) {
				Map data = (Map) actionData.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					ftpHandler.writeSCH(actionData, map_param.get("SELECTED_DL").toString(), schFileNM);
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
