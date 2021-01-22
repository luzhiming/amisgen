package com.luzm.amis.gen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

@XStreamAlias("generationConfiguration")
@Data
public class GenerationConfiguration {

    private String apiHost = "";

    private String servicePackage;

    private String controllerPackage;

    private String amisPackage;

    private List<AmisService> amis;

}