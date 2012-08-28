/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Alex
 */
public class Chart_output_base {
    private HashMap settings;
    
    public Chart_output_base(){
        
    }
    
    public Chart_output_base(HashMap settings){
        this();
        this.settings = settings;
    }
    
    public void settings(HashMap settings){
        this.settings = settings;
    }
    
    public HashMap getSettings(){
        return settings;
    }
    
    public void outputSingleChart(Maint_row[] data, String output_file){
        
    }
    
    public void outputMultipleCharts(Maint_row[][] data){
        
    }
    
    public void saveChart(JFreeChart chart, String filename){
        try{
            try (OutputStream output = new FileOutputStream(filename)) {
                int width = (int) Integer.parseInt((String)settings.get("jpeg_width"));
                int height = (int) Integer.parseInt((String)settings.get("jpeg_height"));
                ChartUtilities.writeChartAsJPEG(output, chart, width, height);
            }
        } catch(NumberFormatException | IOException e){
            boolean debug = true;
        }
    }
}
