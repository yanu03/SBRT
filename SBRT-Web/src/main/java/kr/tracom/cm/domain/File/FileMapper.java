package kr.tracom.cm.domain.File;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 작성자: 트라콤
 * 작성일: 2021.08.18.
 * 수정일: 2021.08.18.
 * 설명: 파일 클래스
 */
@Mapper
public interface FileMapper {
	List selectMultiFileList(String fileId);
	
	List selectMultiImageFileList(Map<String, Object> map);
	
	List selectMultiFileView(Map<String, Object> map);
	
	List selectMultiFileViewLast(Map<String, Object> map);

	void insertMultiFile(Map<String, Object> map);
	
	void updateMultiFileDelYN(Map<String, Object> map);

	void deleteMultiFile(Map<String, Object> map);

	void updateMultiFileCn(Map<String, Object> map);
	
	String selectNextMultiFileID();
	String selectNextMultiFileSN(String fileId);
	
}
