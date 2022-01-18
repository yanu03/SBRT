package kr.tracom.cm.domain.Program;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramMapper {

	// 프로그램관리 조회
	public List<Map> selectProgram(Map param);
	
	public List<Map> searchUserAuthProgram(Map param);
	
	public List<Map> searchOrgAuthProgram(Map param);

	// 프로그램관리 C, U, D
	public int insertProgram(Map param);

	public int deleteProgram(Map param);

	public int updateProgram(Map param);

	// 프로그램별 접근프로그램 조회
	public List<Map> selectProgramAuthority(Map param);

	public List<Map> excludeSelectProgramAuthority(Map param);

	// 프로그램별 접근프로그램 C, D
	public int insertProgramAuthority(Map param);
	
	public int updateProgramAuthority(Map param);

	public int deleteProgramAuthority(Map param);
	
	//유저 권한 프로그램
	public int insertUserAuthProgram(Map param);
	
	public int updateUserAuthProgram(Map param);

	public int deleteUserAuthProgram(Map param);
	
	//조직 권한 프로그램
	public int insertOrgAuthProgram(Map param);
	
	public int updateOrgAuthProgram(Map param);

	public int deleteOrgAuthProgram(Map param);
}
