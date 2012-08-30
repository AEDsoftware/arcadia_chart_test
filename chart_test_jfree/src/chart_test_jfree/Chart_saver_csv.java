/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author Alex
 */
public class Chart_saver_csv extends Chart_saver_base {
    @Override
    public void saveChart(JFreeChart chart, String filename){
        if(Boolean.parseBoolean(settings.get("with_percentiles"))){
            String percentile_file;
            percentile_file = settings.get("data_folder") + "percentiles.csv";
            File percentiles;
            percentiles = new File(percentile_file);
            File output = new File(filename);
            boolean success = percentiles.renameTo(output);
            return;
        }
        // we try to open a CSV writer on the output filename
        // we then use ChartUtilities to write the chart as a CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename), ',')) {
            CategoryDataset data = chart.getCategoryPlot().getDataset();
            String[] temp_array = new String[2];
            temp_array[0] = "Time Bucket";
            temp_array[1] = "Number of Calls";
            writer.writeNext(temp_array);
            
            for(int i = 0; i < data.getColumnKeys().size(); i++){
                temp_array = new String[2];
                temp_array[0] = data.getColumnKey(i).toString();
                temp_array[1] = data.getValue(0, i).toString();
                writer.writeNext(temp_array);
            }
        } catch(Exception e){
            
        }
    }
    
    public Chart_saver_csv(){
        super();
    }
    
    public Chart_saver_csv(HashMap<String, String> settings){
        super(settings);
    }
}
