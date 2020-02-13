package com.woniu.miaosha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pid;
    private String pname;
    private int price;
    private int count;
    private String detial;
    private String img;

}
