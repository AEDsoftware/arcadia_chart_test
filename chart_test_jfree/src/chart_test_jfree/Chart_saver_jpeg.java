package chart_test_jfree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

// Chart_saver_jpeg is a chart saver for outputting to JPEGs
public class Chart_saver_jpeg extends Chart_saver_base {
    @Override
    public void saveChart(JFreeChart chart, String filename){
        try{
            // we try to open a file stream on the output file name; if sucessful, we set the width and height
            // we then use ChartUtilities to write the chart as a JPEG
            try (OutputStream output = new FileOutputStream(filename)) {
                int width = (int) Integer.parseInt(settings.get("jpeg_width"));
                int height = (int) Integer.parseInt(settings.get("jpeg_height"));
                ChartUtilities.writeChartAsJPEG(output, chart, width, height);
            }
        } catch(NumberFormatException | IOException e){
        }
    }
    
    public Chart_saver_jpeg(){
        super();
    }
    
    public Chart_saver_jpeg(HashMap<String, String> settings){
        super(settings);
    }
}
