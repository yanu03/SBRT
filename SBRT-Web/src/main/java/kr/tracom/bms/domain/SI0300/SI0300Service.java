package kr.tracom.bms.domain.SI0300;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class SI0300Service extends ServiceSupport{

	@Autowired
	private SI0300Mapper si0300Mapper;
	
	@Autowired
	FTPHandler ftpHandler;
	
	public List SI0300G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		List returnList = si0300Mapper.SI0300G0R0(map);
		
		for(Object obj:returnList) {
			Map<String, Object> temp = (Map<String, Object>)obj;
			temp.put("FILE_PATH", "/fileUpload/common/employee/"+temp.get("DRV_ID")+".png");
		}
		
		return returnList;
	}
	
	public List SI0300P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0300Mapper.SI0300P0R0(map);
	}
	
	public List SI0300SHI0() throws Exception {
		return si0300Mapper.SI0300SHI0();
	}	

	public Map SI0300G0K0() throws Exception {
		return si0300Mapper.SI0300G0K0(); 
	}
	
	public Map SI0300G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_DRV_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0300Mapper.SI0300G0I0(data);
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("DRV_ID").equals(data.get("FILE_NM"))==false)) {
						
						
						doMoveFile("up/","common/employee/",data.get("FILE_NM").toString(),data.get("DRV_ID").toString()+".png");
						
						
			    		/*  2020-09-29 추가
			    		 *  설명: .jpg 이미지와 CERTI 이미지가 없을 경우 운전자 단말기에서 로그인이 되지 않음. 따라서 아래 코드 추가함
			    		 */
						String imgFileName = data.get("DRV_ID").toString();
						String pngExtName = ".png";
						doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+".jpg");
						doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+"_CERTI.jpg");
						
						//ftp sync
						ftpHandler.uploadSI0300();
						
					}
				} else if (rowStatus.equals("U")) {
					uCnt += si0300Mapper.SI0300G0U0(data);
					if((data.get("FILE_NM")!=null)&&(data.get("FILE_NM").toString().isEmpty()==false)
							&&(data.get("DRV_ID").equals(data.get("FILE_NM"))==false)) {
						doMoveFile("up/","common/employee/",data.get("FILE_NM").toString(),data.get("DRV_ID").toString()+".png");
						
						
						/*  2020-09-29 추가
			    		 *  설명: .jpg 이미지와 CERTI 이미지가 없을 경우 운전자 단말기에서 로그인이 되지 않음. 따라서 아래 코드 추가함
			    		 */
						String imgFileName = data.get("DRV_ID").toString();
						String pngExtName = ".png";
						doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+".jpg");
						doCopyFile("common/employee/", "common/employee/", imgFileName+pngExtName, imgFileName+"_CERTI.jpg");
						
						//ftp sync
						ftpHandler.uploadSI0300();
						
					}
				} else if (rowStatus.equals("D")) {
					dCnt += si0300Mapper.SI0300G0D0(data);
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
