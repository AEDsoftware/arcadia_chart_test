package chart_test_jfree;

import java.util.HashMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// Chart_output_base is just a parent class for all of the output classes
// Each output class will represent a different type of chart (aka a way of displaying the data gathered)
public class Chart_output_base {
    private HashMap settings;
    private Chart_saver_base saver;
    
    public Chart_output_base(){
    }
    
    public Chart_output_base(HashMap settings){
        this();
        this.setSettings(settings);
    }
    
    private void setSettings(HashMap settings){
        this.settings = settings;
        
        // when settings are loaded, we use the "output" variable to determine which type of Chart_saver to instantiate
        String output_type = (String)settings.get("output");
        switch (output_type){
            case "jpeg" : saver = new Chart_saver_jpeg(settings);
                break;
        }
    }
    
    public HashMap getSettings(){
        return settings;
    }
    
    // outputSingleChart and outputMultipleCharts are placeholders, to be implented by subclass
    public void outputSingleChart(Maint_row[] data, String output_file){
        throw new NotImplementedException();
    }
    
    public void outputMultipleCharts(Maint_row[][] data){
        throw new NotImplementedException();
    }
    
    public Chart_saver_base getSaver(){
        return saver;
    }
}
