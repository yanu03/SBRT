package kr.tracom.bms.domain.FM0203;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;

@Service
public class FM0203Service extends ServiceSupport {

	@Autowired
	FTPHandler ftpHandler;
	
	@Autowired
	private FM0203Mapper FM0203Mapper;

	public List FM0203G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List returnList = FM0203Mapper.FM0203G0R0(map);
		
		for(Object obj:returnList) {
			Map<String, Object> temp = (Map<String, Object>)obj;
			temp.put("FILE_PATH", "/fileUpload/firmware/"+temp.get("FILE_NM"));			
		}
		
		return returnList;
		
	}
	
	public List FM0203G1R0() throws Exception {
		Map param = getSimpleDataMap("dma_subsearch");
		return FM0203Mapper.FM0203G1R0(param);
	}
	
	public Map FM0203G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BMS_FCLT_INFO");
		//Map<String, Object> fileInfo = getSimpleDataMap("dma_FILE_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map<String, Object> data = param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("FCLT_ID").equals(data.get("FILE_NM"))==false))
						{
							doMoveFile("up/", "firmware/", data.get("FILE_NM").toString(), data.get("FCLT_ID").toString()+ "."+ data.get("FILE_EXTENSION").toString());
							data.put("FILE_NM", data.get("FCLT_ID").toString()+ "."+ data.get("FILE_EXTENSION").toString());
						}
					
					iCnt += FM0203Mapper.FM0203G0I0(data);
					iCnt += FM0203Mapper.FM0203G0I1(data);
					uCnt += FM0203Mapper.FM0203G0U0(data);
					
					/*
					//장치 펌웨어 업데이트
					String mngId = String.valueOf(data.get("MNG_ID"));
					String fileExt = String.valueOf(fileInfo.get("FILE_EXTENSION"));
					String fileName = String.valueOf(fileInfo.get("FILE_NM"));
					
					//TODO: 이재혁
					//ftpHandler.uploadFM0203(mngId, "/up/", fileName, fileExt);
					*/
					

				}
				
				 // else if (rowStatus.equals("D")) { dCnt += pi0503Mapper.PI0503G1D0(data); }
				 
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

	public Map FM0203G0D0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BMS_FCLT_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt += FM0203Mapper.FM0203G0D0(data);
					iCnt += FM0203Mapper.FM0203G0D1(data);
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
	
	public List FM0203SHI0() throws Exception {
		return FM0203Mapper.FM0203SHI0();
	}
	
	
}
