package com.garbageview.garbageview;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SpringBootApplication
public class GarbageviewApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(GarbageviewApplication.class, args);
    //upon starting the program, make sure to add the listener for gc events
  }

  @Bean
  CommandLineRunner runner () {
   return args -> {
     GCInformation.installGCMonitoring();
   };
  }
}

//@RestController
//class GarbageViewControllers {
//  @RequestMapping("/")
//
//
//}

