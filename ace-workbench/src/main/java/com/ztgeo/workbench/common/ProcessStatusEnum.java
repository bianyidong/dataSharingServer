package com.ztgeo.workbench.common;

/**
 * 模板状态枚举表
 */
public enum ProcessStatusEnum {

    DEFAULT_STATUS("未处理"),
    PROCESSING("处理中"),
    FINISH("已完成");

    private ProcessStatusEnum(String status){
        this.status = status;
    }

    private String status;

}
