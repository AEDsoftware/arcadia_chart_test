/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Alex
 */
public class Chart_saver_csv extends Chart_saver_base {
    @Override
    public void saveChart(JFreeChart chart, String filename){
        try{
            // we try to open a file stream on the output file name; if sucessful, we set the width and height
            // we then use ChartUtilities to write the chart as a JPEG
            try (OutputStream output = new FileOutputStream(filename)) {
                // Get buckets from data in chart
                // write to string
                // output
            }
        } catch(NumberFormatException | IOException e){
        }
    }
    
    public Chart_saver_csv(){
        super();
    }
    
    public Chart_saver_csv(HashMap settings){
        super(settings);
    }
}
