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
import java.util.Collection;

@SpringBootApplication
public class GarbageviewApplication //extends SpringBootServletInitializer
{



  public static void runGVApp(String[] args)
  {
    ApplicationContext ctx = SpringApplication.run(GarbageviewApplication.class, args);
  }

  @PostConstruct
  @Autowired
  @Bean
  CommandLineRunner runner (GarbageCollectionRepo gcr) {
   return args -> {
     //as soon as the beans are ready, install the monitor for each gc event
     GCInformation.installGCMonitoring(gcr);
     //the above start the monitoring - instead of printing this, save this to the repo and broadcast via the socket out
     //print out each of these to make sure they're saved
     gcr.findAll().forEach(System.out::println);
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

  public long getId () { return id; }

}
