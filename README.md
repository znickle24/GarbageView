# GarbageView

Repository for code of GarbageView Java application Garbage Collector listener and evaluation tool. Garbage Collection/Garbage Collector will be refered to as GC moving forward.

## Authors (in alphabetical order)

1. Stephen "Zander" Nickle
2. Mason E. West

## Description

This repository is made up of several repositories, listed below.

 * [GC Listener](src/main/java/com/garbageview) - GC listener, Spring Boot structure, and Socket files.
 * [React UI](src/main/app/garbageviewfrontend/src) - JavaScript React front end. Built using Create React App.

## Instructions

This project is created such that it can be easily setup in IntelliJ IDEA. If you want to use any other IDE, setup instructions might be different.

## Before you Start

You will need to create `GarbageView.jar`.
Make sure you include the dependencies while creating the jar.

## Step by Step Procedure

<!-- 1. After creation of the `GarbageView.jar` (with all the dependencies), copy the jar to `libraries/` folder in this project's repo.
2. Change the name of this dependency in `build.gradle` to match the name of jar which you created.
3. Run the gradle clean, build.
4. The project should build and compile fine.
5. Run the [DroneApplication.java](src/main/java/org/gids/robot/DroneApplication.java).
6. It should spin up a Spring Boot Application with an override UI accessible at localhost:8080/ui/index.html .
7. Now you have an application which follows speech commands as well as has an override control center in case you need to take immediate control.
8. You can find all the commands supported in [command.gram](src/main/resources/org.gids.robot/grammars/command.gram). Please see the [JSpeech Grammar Format](https://www.w3.org/TR/jsgf/) for more information on how to add your own commands. -->

This application follows the general Spring Boot Application paradigms.

## Using the Data Dashboard
## Starting the Dashboard:
- Make sure your application is running and the GarbageView.jar file has been properly added to you project.
- Connect to http://localhost:8080/ .
- The application should automatically recognize the connection and begin sending GC information to the dashboard.