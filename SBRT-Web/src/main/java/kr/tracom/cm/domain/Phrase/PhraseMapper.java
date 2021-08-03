package kr.tracom.cm.domain.Phrase;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhraseMapper {

	public List selectPhrase();
	
}
