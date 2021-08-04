package kr.tracom.cm.domain.Phrase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class PhraseService extends ServiceSupport{

	@Autowired
	private PhraseMapper phraseMapper;
	
	public List<Map> PhraseList() throws Exception {
		return phraseMapper.selectPhrase();
	}
}
