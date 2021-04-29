package com.bin.david.smarttable.bean;

public class DevicePosition {

    private String system;
    private String device;
    private String position;
    private String state;
    private String remark;

    public DevicePosition(String system, String device, String position, String state, String remark) {
        this.system = system;
        this.device = device;
        this.position = position;
        this.state = state;
        this.remark = remark;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
