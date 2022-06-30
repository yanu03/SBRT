package kr.tracom.brt.domain.MO0205;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.Intg.IntgMapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0205Service extends ServiceSupport{
	
	@Autowired
	private MO0205Mapper MO0205Mapper;
	
	@Autowired
	private IntgMapper intgMapper;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${api.gateway.url}")
	private String apiGatewayUrl;
	
	public List<Map> MO0205G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return MO0205Mapper.MO0205G0R0(param);
	}
	
	public List<Map> MO0205G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return MO0205Mapper.MO0205G1R0(param);
	}
	
	public List<Map> MO0205G2R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return MO0205Mapper.MO0205G2R0(param);
	}
	
	public List MO0205G0R1() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return MO0205Mapper.MO0205G0R1(param);
	}
	
	public List<Map> MO0205SHI0() throws Exception{
		return MO0205Mapper.MO0205SHI0();
	}
	
	public List MO0205SHI1() throws Exception {
		return MO0205Mapper.MO0205SHI1();
	}
	
	public List MO0205SHI2() throws Exception {
		return MO0205Mapper.MO0205SHI2();
	}
	
	public List MO0205SHI3() throws Exception {
		Map param = getSimpleDataMap("dma_search");		
		return MO0205Mapper.MO0205SHI3(param);
	}	
	
	public List MO0205P0R0() throws Exception {
		Map param = getSimpleDataMap("dma_search");		
		return MO0205Mapper.MO0205P0R0(param);
	}	
	
	//에어컨 제어
	public List airconControl() throws Exception {
		List<Map<String, Object>> param = getSimpleList("dlt_airconControl");
		
		for(int x = 0; x < param.size(); x++) {
			Map data = (Map) param.get(x);
			String intgFcltId = (String) data.get("INTG_FCLT_ID");
			String power = (String) data.get("PK002");
			String degree = (String) data.get("PK047");
			
			//전원제어
			if(power.isEmpty()==false) {
				data.put("INTG_TYPE", "PC");
				
				List<Map<String, Object>> token = intgMapper.selectIntgMstList(data);
				String key = (String) token.get(0).get("INTG_API_KEY");
				String intgUrl = (String) token.get(0).get("INTG_URL");
				
				String api = apiGatewayUrl + intgUrl + key + "&deviceId=" + intgFcltId + "&value=" + power;
				logger.debug("airconControl() api = "+ api);
				
				BufferedReader in = null;
				
				try {
					URL url = new URL(api);
					try {
						HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 접속 
						conn.setRequestMethod("GET"); // 전송 방식은 GET
						
						in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			//온도제어
			if(degree.isEmpty()==false) {
				data.put("INTG_TYPE", "DC");
				
				List<Map<String, Object>> token = intgMapper.selectIntgMstList(data);
				String key = (String) token.get(0).get("INTG_API_KEY");
				String intgUrl = (String) token.get(0).get("INTG_URL");
				
				String api = apiGatewayUrl + intgUrl + key + "&deviceId=" + intgFcltId + "&value=" + degree;
				logger.debug("airconControl() api = "+ api);
				
				
				BufferedReader in = null;
				
				try {
					URL url = new URL(api);
					try {
						HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 접속 
						conn.setRequestMethod("GET"); // 전송 방식은 GET
						
						in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		
		return null;
	}
}
