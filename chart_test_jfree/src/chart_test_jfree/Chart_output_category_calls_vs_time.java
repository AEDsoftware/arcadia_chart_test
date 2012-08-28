/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Alex
 */
public class Chart_output_category_calls_vs_time extends Chart_output_base {

    Chart_output_category_calls_vs_time(HashMap settings) {
        super(settings);
    }
    
    @Override
    public void outputMultipleCharts(Maint_row[][] data){
        for(Maint_row[] chart : data){
            String service_name = chart[0].service_name;
            HashMap settings = super.getSettings();
            String output_file = (String)settings.get("jpeg_output_file");
            String output_replacement = "_" + service_name + ".jpeg";
            output_file = output_file.replace(".jpeg", output_replacement);
            outputSingleChart(chart, output_file);
        }
    }
    
    @Override
    public void outputSingleChart(Maint_row[] data, String output_file){
        //double[] time_array = getDoubleData(data);
        int threshold;
        threshold = Integer.parseInt((String)super.getSettings().get("trim_threshold"));
        int max;
        max = Integer.parseInt((String)super.getSettings().get("trim"));
        Bucket[] time_array = Bucket.getTrimmedBucket(data, max, threshold);
        CategoryDataset set = createDataset(time_array);
        JFreeChart chart = createChart(set);
        saveChart(chart, output_file);
    }
    
    private CategoryDataset createDataset(Bucket[] data) {
        String series1 = "# of calls / time taken";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Bucket bucket : data){
            dataset.addValue((Number)bucket.call_count, series1, bucket.time_span);
        }
        return dataset;
    }
    
    private JFreeChart createChart(CategoryDataset data){
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
        CategoryPlot plot = chart.getCategoryPlot();
        //CategoryAxis axis = plot.getDomainAxis();
        int num_ticks = Integer.parseInt((String)super.getSettings().get("max_ticks"));
        SpareslyLabeledCategoryAxis axis_new;
        axis_new = new SpareslyLabeledCategoryAxis(num_ticks);
        axis_new.setMaximumCategoryLabelWidthRatio(3f);
        
        /*axis_new.setAxisLinePaint(axis.getAxisLinePaint());
        axis_new.setAxisLineStroke(axis.getAxisLineStroke());
        axis_new.setAxisLineVisible(axis.isAxisLineVisible());
        axis_new.setCategoryLabelPositionOffset(axis.getCategoryLabelPositionOffset());
        axis_new.setCategoryLabelPositions(axis.getCategoryLabelPositions());
        axis_new.setFixedDimension(axis.getFixedDimension());
        axis_new.setLabel(axis.getLabel());
        axis_new.setLabelAngle(axis.getLabelAngle());
        axis_new.setLabelFont(axis.getLabelFont());
        axis_new.setLabelInsets(axis.getLabelInsets());
        axis_new.setLabelPaint(axis.getLabelPaint());
        axis_new.setLowerMargin(axis.getLowerMargin());
        axis_new.setMaximumCategoryLabelLines(axis.getMaximumCategoryLabelLines());
        axis_new.setMaximumCategoryLabelWidthRatio(axis.getMaximumCategoryLabelWidthRatio());
        axis_new.setMinorTickMarkInsideLength(axis.getMinorTickMarkInsideLength());
        axis_new.setMinorTickMarkOutsideLength(axis.getMinorTickMarkOutsideLength());
        axis_new.setMinorTickMarksVisible(axis.isMinorTickMarksVisible());
        axis_new.setPlot(axis.getPlot());
        */
        plot.setDomainAxis(axis_new);
        return chart;
    }
}
