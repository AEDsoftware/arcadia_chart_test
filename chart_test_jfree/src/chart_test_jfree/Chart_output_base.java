package chart_test_jfree;

import java.util.HashMap;

// Chart_output_base is just a parent class for all of the output classes
// Each output class will represent a different type of chart (aka a way of displaying the data gathered)
public abstract class Chart_output_base extends Setting_manager {
    private Chart_saver_base saver;
    
    public Chart_output_base(){
    }
    
    public Chart_output_base(HashMap<String, String> settings){
        this();
        this.setSettings(settings);
    }
    
    @Override
    public final void setSettings(HashMap<String, String> settings){
        this.settings = settings;

        // when settings are loaded, we use the "output" variable to determine which type of Chart_saver to instantiate
        String output_type = settings.get("output");
        switch (output_type){
            case "jpeg" : saver = new Chart_saver_jpeg(settings);
                break;
            case "csv" : saver = new Chart_saver_csv(settings);
                break;
        }
    }
    
    // outputSingleChart and outputMultipleCharts are placeholders, to be implented by subclass
    public abstract void outputSingleChart(Maint_row[] data, String output_file);
    
    public abstract void outputMultipleCharts(Maint_row[][] data);
    
    public Chart_saver_base getSaver(){
        return saver;
    }
}
