package restaurant.server.ui.form.info;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.text.NumberFormat;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ServerMemoryAndCPUUsage {
    
    public static String getMemoryUsage(){
        Runtime runtime = Runtime.getRuntime();

        // Get memory usage information
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        // Format the memory usage information
        NumberFormat format = NumberFormat.getInstance();
        String formattedMaxMemory = format.format(maxMemory / 1024);
        String formattedAllocatedMemory = format.format(allocatedMemory / 1024);
        String formattedFreeMemory = format.format(freeMemory / 1024);
        String formattedTotalFreeMemory = format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024);

//        // Display the memory usage information
//        System.out.println("Max Memory: " + formattedMaxMemory + " KB");
//        System.out.println("Allocated Memory: " + formattedAllocatedMemory + " KB");
//        System.out.println("Free Memory: " + formattedFreeMemory + " KB");
//        System.out.println("Total Free Memory: " + formattedTotalFreeMemory + " KB");
        
        String memoryInfo = "Max Memory: " + formattedMaxMemory+" KB\n"+"Allocated Memory: " +formattedAllocatedMemory+" KB\n" +"Free Memory: " + formattedFreeMemory+" KB\n" +"Total Free Memory: " + formattedTotalFreeMemory + " KB";
        return memoryInfo;
    }
    public static String getCPUUsage(){
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
       

        // Get the CPU usage as a percentage
        double cpuUsage = osMxBean.getProcessCpuLoad() * 100;

        // Display the CPU usage
        System.out.println("CPU Usage: " + cpuUsage + "%");
        String cpuUsageS = "CPU Usage: " + cpuUsage + "%";
        return cpuUsageS;
    }
}
