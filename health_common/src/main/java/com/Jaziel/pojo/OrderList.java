package com.Jaziel.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.lang.String;

/**
 * 体检预约信息
 */
public class OrderList implements Serializable{
    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";
    private Integer id;
    private Date orderDate;//预约日期
    private String orderAddress; //预约地址

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String name;//姓名
    private String phoneNumber;//手机号
    private String orderType;//预约类型 电话预约/微信预约
    private String orderStatus;//预约状态（是否到诊）

    public OrderList(Date orderDate, String name, String phoneNumber, String orderType, String orderStatus) {
        this.orderDate = orderDate;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
    }

    public OrderList(Integer id, Date orderDate, String orderAddress, String name, String phoneNumber, String orderType, String orderStatus) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
