package kr.tracom.tims;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import kr.tracom.platform.attribute.manager.AttributeManager;

@Service
public class TimsService {

    @PostConstruct
    public void initialize() {
        bindAttribute();
    }

    public void bindAttribute() {
        AttributeManager.bind(AttributeManager.COMMON_ATTRIBUTE);
        AttributeManager.bind(AttributeManager.BMS_ATTRIBUTE);
        AttributeManager.bind(AttributeManager.BRT_ATTRIBUTE);
    }



}
