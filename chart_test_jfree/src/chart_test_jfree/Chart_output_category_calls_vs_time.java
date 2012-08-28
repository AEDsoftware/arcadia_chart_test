package chart_test_jfree;

import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

// Chart_output_category_calls_vs_time is a subclass of Chart_output_base
// this class creates a category chart (x-y line chart)
// it maps the time taken by calls, in 100 millisecond buckets, to the # of calls that took that amount of time
public class Chart_output_category_calls_vs_time extends Chart_output_base {
    
    public Chart_output_category_calls_vs_time(HashMap settings) {
        super(settings);
    }
    
    // outputMultipleCharts is used when the user requires seperate charts for each service
    @Override
    public void outputMultipleCharts(Maint_row[][] data){
        // we iterate through the sub-arrays of the input...
        for(Maint_row[] chart : data){
            // some string manipulation is done to get a unique output name reflecting the service
            String service_name = chart[0].service_name;
            HashMap settings = super.getSettings();
            String output_file = (String)settings.get("output_file");
            String output_replacement = "_" + service_name + ".";
            output_file = output_file.replace(".", output_replacement);
            
            // then, we just output a single chart as normal
            outputSingleChart(chart, output_file);
        }
    }
    
    // outputSingleChart takes an array of maint data and a file name and creates output
    @Override
    public void outputSingleChart(Maint_row[] data, String output_file){
        // transform the input data into a trimmed bucket array
        int threshold;
        threshold = Integer.parseInt((String)super.getSettings().get("trim_threshold"));
        int max;
        max = Integer.parseInt((String)super.getSettings().get("trim"));
        Bucket[] time_array = Bucket.getTrimmedBucket(data, max, threshold);
        
        // get a dataset
        CategoryDataset set = createDataset(time_array);
        
        // create the chart
        JFreeChart chart = createChart(set);
        
        // use the saver functions to save the chart to output
        super.getSaver().saveChart(chart, output_file);
    }
    
    // createDataset converts a Bucket array into a CategoryDataset
    private CategoryDataset createDataset(Bucket[] data) {
        // this type of chart uses only 1 "series" (or line)
        String series1 = "# of calls / time taken";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Bucket bucket : data){
            // for each Bucket, we add a value point to "series1"; this maps the call_count and time_span to the data set
            dataset.addValue((Number)bucket.call_count, series1, bucket.time_span);
        }
        return dataset;
    }
    
    // createChart turns a dataset into a chart
    private JFreeChart createChart(CategoryDataset data){
        // the basic chart constructer in ChartFactory takes a CategoryDataset and some other inputs and returns a line chart
        // "Line Chart Demo 1" should be changed to something better!
        JFreeChart chart = ChartFactory.createLineChart(
        "Line Chart Demo 1", // chart title
        "Time Taken", // domain axis label
        "Number of calls", // range axis label
        data, // data
        PlotOrientation.VERTICAL, // orientation
        true, // include legend
        true, // tooltips
        false // urls
        );
        // we do some manipulation to limit the number of ticks shown on the horizontal axis; this preserves readability in large data sets
        // we do this by using a special axis (SpareslyLabeledCategoryAxis) which supreses the extra ticks
        CategoryPlot plot = chart.getCategoryPlot();
        int num_ticks = Integer.parseInt((String)super.getSettings().get("max_ticks"));
        SpareslyLabeledCategoryAxis axis_new;
        axis_new = new SpareslyLabeledCategoryAxis(num_ticks);
        axis_new.setMaximumCategoryLabelWidthRatio(3f);
        plot.setDomainAxis(axis_new);
        return chart;
    }
}
