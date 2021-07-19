package kr.tracom.cm.support.file;

import websquare.http.WebSquareContext;
import websquare.upload.handl.*;

/**
 * websquare의 upload 컴포넌트 또는 multi-upload 컴포넌트에서 upload시 서버에 저장할 파일 패스, 파일명을 구현하는 Class 샘플.
 * websquare.upload.handl.AbstractUploadFileDefiner를 상속받아 getFileName, getFilePath를 구현한다.
 *
 */
public class UploadFileDefinerImpl extends AbstractUploadFileDefiner {
	 	
    /**
     * @param          String clientFileName : client에서 요청한 파일명
     * 				   String originalFileName : 현재 웹스퀘어가 변환한 파일명
     * @return         String : 변경된 파일명
     * @desc           변경 대상 실제 파일명을 받아 파일명 변경 한다. 
     *                 getFileName을 구현 하여야 한다.
     */
	public String getFileName( String clientFileName, String originalFileName ) throws Exception {
		System.out.println("[clientFileName]" + clientFileName);
		System.out.println("[originalFileName]" + originalFileName);
		String returnFileName = "";
		String ext = "";
		int index = clientFileName.lastIndexOf(".");
		
		WebSquareContext context = WebSquareContext.getContext();
		System.out.println("[context getRequest:]" + context.getRequest());
		String t1 = (context.getRequest()).getParameter("callbackFunction");
		String t2 = (context.getRequest()).getParameter("param1");
		System.out.println("[context t1:]" + t1);
		System.out.println("[context t2:]" + t2);
		
		if(index > -1) {
			ext = clientFileName.substring(index + 1);
			returnFileName = clientFileName.substring(0, index) + "_"+ t2 + "." + ext;
		} else {
			returnFileName = clientFileName.substring(0) + "_" + t2;
		}
		
		
		System.out.println("[returnFileName]" + returnFileName);
    	return returnFileName;
    }
    
    /**
     * @param          String filePath : 현재 웹스퀘어가 정의한 서버 파일 경로
     * @return         String : 변경된 파일 경로
     * @desc           현재 파일경로를 받아 파일경로를 재 설정 한다.
	 *                 getFilePath를 구현 하여야 한다.
     */
    public String getFilePath( String filePath ) throws Exception {
		System.out.println("[filePath]" + filePath);
		/*
		String returnFilePath = "d:\\test\\customUploadFile";
		System.out.println("[returnFilePath]" + returnFilePath);
        return returnFilePath;
        */
		return filePath;
    }
}