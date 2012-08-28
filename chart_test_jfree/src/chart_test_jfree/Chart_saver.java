/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.util.HashMap;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Alex
 */
public class Chart_saver {
    private HashMap settings;
    
    public void settings(HashMap settings){
        this.settings = settings;
    }
    
    public HashMap getSettings(){
        return settings;
    }
    
    public Chart_saver(){
        
    }
    
    public Chart_saver(HashMap settingmap){
        settings = settingmap;
    }
    
    public void saveChart(JFreeChart chart, String filename){
    }
}
