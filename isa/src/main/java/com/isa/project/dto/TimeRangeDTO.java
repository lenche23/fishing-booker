package com.isa.project.dto;

import java.util.Date;

public class TimeRangeDTO {

    private long id;
    private Date startDate;
    private Date endDate;
    private long serviceId;

    public TimeRangeDTO() {}

    public TimeRangeDTO(long id, Date startDate, Date endDate, long serviceId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.serviceId = serviceId;
    }

    public long getId() { return id; }

    public Date getStartDate() { return startDate; }

    public Date getEndDate() { return endDate; }

    public long getServiceId() { return serviceId; }
}
