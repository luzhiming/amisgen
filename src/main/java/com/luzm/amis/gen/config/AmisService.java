package com.luzm.amis.gen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@XStreamAlias("amisService")
@Data
public class AmisService {
    private String serviceName;
    private String controllerName;
    private String controllerMapping;
    private String daoClazzName;
    private String mapperClazzName;
    private String exampleClazzName;
}