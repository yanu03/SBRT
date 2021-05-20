package kr.tracom.cm.domain.Find;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class FindService extends ServiceSupport{

		@Autowired
		private FindMapper findMapper;
	
		public List findUser() throws Exception {
			Map<String, Object> map = getSimpleDataMap("dma_search");
			return findMapper.findUser(map);
		}
		
}
