package com.zhang.backend2.websocket.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendMessage implements Serializable {

    private static final long serialVersionUID = 3608503806763579057L;

    private String name;

}
