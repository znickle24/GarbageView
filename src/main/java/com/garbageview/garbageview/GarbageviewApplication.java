package com.garbageview.garbageview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GarbageviewApplication
{

  public static void main(String[] args)
  {

    ApplicationContext serviceContext = SpringApplication.run(GarbageviewApplication.class, args);
  }
}
