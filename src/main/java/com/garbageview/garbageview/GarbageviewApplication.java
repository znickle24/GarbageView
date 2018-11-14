package com.garbageview.garbageview;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;

@SpringBootApplication
public class GarbageviewApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(GarbageviewApplication.class, args);
    //upon starting the program, make sure to add the listener for gc events
  }

  @Bean
  CommandLineRunner runner (GarbageCollectionsRepo gcr) {
   return args -> {
     //as soon as the beans are ready, install the monitor for each gc event
//     GCInformation.installGCMonitoring();

     //for initial testing before running with the actual gcMonitor, testing to make sure that these are saving in the repo
     gcr.save( new GarbageCollectionsEntity("PS1", "old", 21, "GCInfoName", "End of Loop",
         1000, "UsedMemoryAfter", "UsedMemoryBefore", 25));
     gcr.save( new GarbageCollectionsEntity("PS1", "new", 21, "GCInfoName", "End of Loop",
         1000, "UsedMemoryAfter", "UsedMemoryBefore", 26));
     gcr.save( new GarbageCollectionsEntity("Parallel", "new", 21, "GCInfoName", "End of Loop",
         1000, "UsedMemoryAfter", "UsedMemoryBefore", 27));
     //print out each of these to make sure they're saved
     gcr.findAll().forEach(System.out::println);

   };
  }
}

//@RestController
//class GarbageViewControllers {
//  @RequestMapping("/")
//
//
//}







interface GarbageCollectionsRepo extends JpaRepository<GarbageCollectionsEntity, Long>
{
  //allows you to grab all collections based on a particular type
  Collection<GarbageCollectionsEntity> findByGcType (String ct);
}


@Entity
class GarbageCollectionsEntity
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
