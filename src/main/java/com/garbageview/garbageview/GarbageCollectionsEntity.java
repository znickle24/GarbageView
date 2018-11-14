package com.garbageview.garbageview;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GarbageCollectionsEntity
{
  @Id
  @GeneratedValue
  private long id;

  private String garbagecollector;
  private String gcType;
  private long gcInfoId;
  private String gcInfoName;
  private String gcInfoCause;
  private long gcInfoDuration;
  private String memoryUsageAfterGC;
  private String memoryUsageBeforeGC;
  private long gcOverhead;
  public GarbageCollectionsEntity(String garbagecollector, String gcType, long gcInfoId, String gcInfoName, String gcInfoCause,
                            long gcInfoDuration, String memoryUsageAfterGC, String memoryUsageBeforeGC, long gcOverhead) {
    this.garbagecollector = garbagecollector;
    this.gcType = gcType;
    this.gcInfoId = gcInfoId;
    this.gcInfoName = gcInfoName;
    this.gcInfoCause = gcInfoCause;
    this.gcInfoDuration = gcInfoDuration;
    this.memoryUsageAfterGC = memoryUsageAfterGC;
    this.memoryUsageBeforeGC = memoryUsageBeforeGC;
    this.gcOverhead = gcOverhead;
  }

  GarbageCollectionsEntity() { //JPA requires this separate constructor

  }

  @Override
  public String toString() {
    return "GarbageCollections {" + "id=" + id + ", garbagecollector=" + garbagecollector + ", gcType" + gcType +
        ", gcInfoId=" + gcInfoId + ", gcInfoName=" + gcInfoName + ", gcInfoCause=" + gcInfoCause + ", gcInfoDuration=" +
        gcInfoDuration + ", memoryUsageAfterGC=" + memoryUsageAfterGC + ", memoryUsageBeforeGC=" + memoryUsageBeforeGC +
        ", gcOverhead=" + gcOverhead + '\'' + "}";
  }

  public long getId () { return id; }

}
