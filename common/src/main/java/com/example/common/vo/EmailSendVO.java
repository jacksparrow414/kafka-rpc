package com.example.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmailSendVO implements Serializable {

    private String className;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] arguments;

    private String metadata;
}
