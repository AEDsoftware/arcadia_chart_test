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
public class Chart_saver_jpeg extends Chart_saver {
    public void saveChart(JFreeChart chart, String filename){
        try{
            try (OutputStream output = new FileOutputStream(filename)) {
                int width = (int) Integer.parseInt((String)super.getSettings().get("jpeg_width"));
                int height = (int) Integer.parseInt((String)super.getSettings().get("jpeg_height"));
                ChartUtilities.writeChartAsJPEG(output, chart, width, height);
            }
        } catch(NumberFormatException | IOException e){
            boolean debug = true;
        }
    }
    
    public Chart_saver_jpeg(){
        super();
    }
    
    public Chart_saver_jpeg(HashMap settings){
        super(settings);
    }
}
