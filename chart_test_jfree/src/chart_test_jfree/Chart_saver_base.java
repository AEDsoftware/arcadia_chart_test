package chart_test_jfree;

import java.util.HashMap;
import org.jfree.chart.JFreeChart;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// Chart_saver is a simple base class for all chart savers
public class Chart_saver_base extends Setting_manager {
    public Chart_saver_base(){
        
    }
    
    public Chart_saver_base(HashMap settingmap){
        settings = settingmap;
    }
    
    // saveChart is a stub and should only be implemented by subclasses
    public void saveChart(JFreeChart chart, String filename){
        throw new NotImplementedException();
    }
}
