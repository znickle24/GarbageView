package com.garbageview.garbageview;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.Map;

public class GCInformation {
  private static final String GC_BEAN_NAME = "java.lang:type=GarbageCollector,name=PS Scavenge";
  private static volatile GarbageCollectorMXBean gcMBean;
  /** Creates a new instance of GCInformation */
  public GCInformation() {
  }
  // initialize the GC MBean field
  private static void initGCMBean() {
    if (gcMBean == null) {
      synchronized (GCInformation.class) {
        if (gcMBean == null) {
          gcMBean = getGCMBean();
        }
      }
    }
  }
  // get the GarbageCollectorMXBean MBean from the
  // platform MBean server
  private static GarbageCollectorMXBean getGCMBean() {
    try {
      MBeanServer server = ManagementFactory.getPlatformMBeanServer();
      GarbageCollectorMXBean bean =
          ManagementFactory.newPlatformMXBeanProxy(server, GC_BEAN_NAME, GarbageCollectorMXBean.class);
      return bean;
    } catch (RuntimeException re) {
      throw re;
    } catch (Exception exp) {
      throw new RuntimeException(exp);
    }
  }

  public static void installGCMonitoring(){
    //get all the GarbageCollectorMXBeans - there's one for each heap generation
    //so probably two - the old generation and young generation
    List<java.lang.management.GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
    //Install a notifcation handler for each bean
    for (java.lang.management.GarbageCollectorMXBean gcbean : gcbeans) {
      System.out.println(gcbean);
      NotificationEmitter emitter = (NotificationEmitter) gcbean;
      //use an anonymously generated listener for this example
      // - proper code should really use a named class
      NotificationListener listener = new NotificationListener() {
        //keep a count of the total time spent in GCs
        long totalGcDuration = 0;

        //                @Override
        public void handleNotification(Notification n, Object o) {
          if (n.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            //get the information associated with this notification
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) n.getUserData());
            //get all the info and pretty print it
            long duration = info.getGcInfo().getDuration();
            String gctype = info.getGcAction();
            if ("end of minor GC".equals(gctype)) {
              gctype = "Young Gen GC";
            } else if ("end of major GC".equals(gctype)) {
              gctype = "Old Gen GC";
            }
            System.out.println();
            System.out.println(gctype + ": - " + info.getGcInfo().getId()+ " " + info.getGcName() + " (from " + info.getGcCause()+") "+duration + " milliseconds; start-end times " + info.getGcInfo().getStartTime()+ "-" + info.getGcInfo().getEndTime());
            //uncomment this line if you'd like to get the composite types of the objects from GcInfo
            //System.out.println("GcInfo CompositeType: " + info.getGcInfo().getCompositeType());
            System.out.println("GcInfo MemoryUsageAfterGc: " + info.getGcInfo().getMemoryUsageAfterGc());
            System.out.println("GcInfo MemoryUsageBeforeGc: " + info.getGcInfo().getMemoryUsageBeforeGc());

            //Get the information about each memory space, and pretty print it
            Map<String, MemoryUsage> membefore = info.getGcInfo().getMemoryUsageBeforeGc();
            Map<String, MemoryUsage> mem = info.getGcInfo().getMemoryUsageAfterGc();
            for (Map.Entry<String, MemoryUsage> entry : mem.entrySet()) {
              String name = entry.getKey();
              MemoryUsage memdetail = entry.getValue();
              long memInit = memdetail.getInit();
              long memCommitted = memdetail.getCommitted();
              long memMax = memdetail.getMax();
              long memUsed = memdetail.getUsed();
              MemoryUsage before = membefore.get(name);
              long beforepercent = ((before.getUsed()*1000L)/before.getCommitted());
              long percent = ((memUsed*1000L)/before.getCommitted()); //>100% when it gets expanded

              System.out.print(name + (memCommitted==memMax?"(fully expanded)":"(still expandable)") +"used: "+(beforepercent/10)+"."+(beforepercent%10)+"%->"+(percent/10)+"."+(percent%10)+"%("+((memUsed/1048576)+1)+"MB) / ");
            }
            System.out.println();
            totalGcDuration += info.getGcInfo().getDuration();
            long percent = totalGcDuration*1000L/info.getGcInfo().getEndTime();
            System.out.println("GC cumulated overhead "+(percent/10)+"."+(percent%10)+"%");
          }
        }
      };
      //Add the listener
      emitter.addNotificationListener(listener, null, null);
    }
  }
}

