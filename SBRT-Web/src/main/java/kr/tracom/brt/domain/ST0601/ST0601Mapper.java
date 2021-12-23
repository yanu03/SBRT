package kr.tracom.brt.domain.ST0601;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0601Mapper {

	public List ST0601G0R0(Map param);
}
