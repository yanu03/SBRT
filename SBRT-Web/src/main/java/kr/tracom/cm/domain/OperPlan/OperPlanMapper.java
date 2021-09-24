package kr.tracom.cm.domain.OperPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperPlanMapper {
	public int insertSimpleOperPlan(Map param);
	public List<Map<String, Object>> selectSttnPeakInfoOfOperPlan(Map param);
	public Map<String, Object> selectOperPlanMst(Map param);
	
	/** �����ȹ ���� **/
	//��ǥ�뼱�� �����ȹ ����Ʈ
	public List<Map<String, Object>> selectPlList(Map<String, Object> params);
	
    //�뼱�� �����
	public List<Map<String, Object>> selectNodeList(Map<String, Object> params);

    //��������� ���� ���� ��߽ð�, ���� ���� �ð�
	public Map<String, Object> selectRoutStEdTm(Map<String, Object> params);

    //�ּ� �����ð� ��������
	public String selectMinStopTm();

    //�ִ� �����ð� ��������
	public String selectMaxStopTm();

    //�뼱�� ÷�ν� ��������
	public Map<String, Object> selectPeakTm(Map<String, Object> params);

    //��ǥ�뼱 ��������
	public String selectRepRout(String routId);

    //������� ���̵�
	public List<Map<String, Object>> selectAllNextNodeInfo(String routId);

    //�������� ����
	public List<Map<String, Object>> selectAllCrsInfo(String routId);

    //�����庰 �ʿ� �����ð� ��������
	public List<Map<String, Object>> selectAllStopTm(String routId);

    //���� ���� �ð� ��������
	public int selectPhaseRemainTm(Map<String, Object> params);

    //���������ȹ ����
	public int insertOperAllocPlNodeInfo(Map<String, Object> params);
	public int insertOperAllocPlNodeInfoList(Map<String, Object> params);
	public void deleteOperPl(Map<String, Object> params);
    
    

}
