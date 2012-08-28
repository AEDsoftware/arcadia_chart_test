/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.util.HashMap;

/**
 *
 * @author Alex
 */
public class Chart_output_base {
    private HashMap settings;
    private Chart_saver saver;
    
    public Chart_output_base(){
        
    }
    
    public Chart_output_base(HashMap settings){
        this();
        this.setSettings(settings);
    }
    
    public void setSettings(HashMap settings){
        this.settings = settings;
        String output_type = (String)settings.get("output");
        switch (output_type){
            case "jpeg" : saver = new Chart_saver_jpeg(settings);
                break;
        }
    }
    
    public HashMap getSettings(){
        return settings;
    }
    
    public void outputSingleChart(Maint_row[] data, String output_file){
        
    }
    
    public void outputMultipleCharts(Maint_row[][] data){
        
    }
    
    public Chart_saver getSaver(){
        return saver;
    }
}
