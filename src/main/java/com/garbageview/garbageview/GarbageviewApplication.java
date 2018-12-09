package com.garbageview.garbageview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class GarbageviewApplication //extends SpringBootServletInitializer
{



  public static void main(String[] args)
  {
    ApplicationContext ctx = SpringApplication.run(GarbageviewApplication.class, args);
  }

//  @PostConstruct
  @Autowired
  @Bean
  CommandLineRunner runner (GarbageCollectionRepo gcr) {
   return args -> {
     //as soon as the beans are ready, install the monitor for each gc event
     GCInformation.installGCMonitoring(gcr);

     //the above start the monitoring - instead of printing this, save thi to the repo and broadcast via the socket out
     //for initial testing before running with the actual gcMonitor, testing to make sure that these are saving in the repo
     gcr.save( new GarbageCollection("PS1", "old", 21, "GCInfoName", "End of Loop",
         1000, "UsedMemoryAfter", "UsedMemoryBefore", 25));
     gcr.save( new GarbageCollection("PS1", "new", 21, "GCInfoName", "End of Loop",
         1000, "UsedMemoryAfter", "UsedMemoryBefore", 26));
     gcr.save( new GarbageCollection("Parallel", "new", 21, "GCInfoName", "End of Loop",
         1000, "UsedMemoryAfter", "UsedMemoryBefore", 27));
     //print out each of these to make sure they're saved
     gcr.findAll().forEach(System.out::println);


     ArrayList<Integer> numberList = new ArrayList<Integer>();
     ArrayList<Integer> numberList_2 = new ArrayList<Integer>();

     int bigNum = 50000000; //50000000

     long startTimeLoop = System.nanoTime();
     for(int i = 0; i < bigNum; i++) {
       numberList.add(i);
     }
     long endTimeLoop = System.nanoTime();
     System.out.println("numberList has been filled");

     // ----- While loop that runs for 2 minutes ----- //
     int time = 6; //seconds

     long startTimeWhile = System.nanoTime();
     while(time > 0) {
       ArrayList<Integer> numberList_3 = new ArrayList<Integer>();
       numberList_3 = numberList;
       String LoremIpsum = "Lorem ipsum dolor sit amet, ut sea liber conceptam quaerendum, labore ornatus quaestio in vis. Usu ad decore altera. Te autem maluisset dissentias pri, mel no pericula splendide. Omittantur consectetuer pri ut, est in idque movet urbanitas. Ei nam graecis tacimates quaerendum, aeterno numquam minimum vel ei. Eos minim iisque constituto in, per ea veri persius mentitum.";

       numberList_3.remove(1);

       System.out.println("here" + time);
       try {
         Thread.sleep(800);
       } catch (InterruptedException e) {
         e.printStackTrace();
       }
       time--;
     }

     System.out.println("done with stuff");
   };
  }
}


@RestController
class GarbageViewControllers {
  //creates the current endpoint
  @RequestMapping("/garbagecollections")
  Collection<GarbageCollection> garbageCollections() {
    return this.garbageCollectionsRepo.findAll();
  }

  @Autowired
  GarbageCollectionRepo garbageCollectionsRepo;


}

//if adding links to each of the gc events, could implement ResourceProcessor -- not necessary at this point

@RepositoryRestResource
interface GarbageCollectionRepo extends JpaRepository<GarbageCollection, Long>
{
  //allows you to grab all collections based on a particular type
  Collection<GarbageCollection> findByGcType (@Param("ct") String ct);
}




@Entity
class GarbageCollection
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
  @Column (length = 500000)
  private String memoryUsageAfterGC;
  @Column (length=500000)
  private String memoryUsageBeforeGC;
  private long gcOverhead;

  public GarbageCollection(String garbagecollector, String gcType, long gcInfoId, String gcInfoName, String gcInfoCause,
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

  GarbageCollection() { //JPA requires this separate constructor

  }

  @Override
  public String toString() {
    return "GarbageCollection {" + "id=" + id + ", garbagecollector=" + garbagecollector + ", gcType" + gcType +
        ", gcInfoId=" + gcInfoId + ", gcInfoName=" + gcInfoName + ", gcInfoCause=" + gcInfoCause + ", gcInfoDuration=" +
        gcInfoDuration + ", memoryUsageAfterGC=" + memoryUsageAfterGC + ", memoryUsageBeforeGC=" + memoryUsageBeforeGC +
        ", gcOverhead=" + gcOverhead + '\'' + "}";
  }

  //add a toJson method here that uses GSON

  public long getId () { return id; }

}
