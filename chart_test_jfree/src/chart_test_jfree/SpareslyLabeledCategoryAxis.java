package chart_test_jfree;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryTick;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.text.*;
import org.jfree.ui.RectangleEdge;

/**
* A category axis that only writes the tick labels on a few of the ticks.
*/
public class SpareslyLabeledCategoryAxis extends CategoryAxis {
    private static final long serialVersionUID = 478725789943763302L;
    
    /**
     * The number of ticks to label.
     */
    private int labeledTicks;

    /**
     * Construct an axis without a label.
     * @param labeledTicks show only this many labeled ticks
     */
    public SpareslyLabeledCategoryAxis(int labeledTicks) {
        this.labeledTicks = labeledTicks;
    }
    
    public void setLabeledTicks(int ticks){
        this.labeledTicks = ticks;
    }

    /**
     * Construct and axis with a label.
     * @param labeledTicks show only this many labeled ticks
     * @param label the axis label
     */
    public SpareslyLabeledCategoryAxis(int labeledTicks, String label) {
        super(label);
        this.labeledTicks = labeledTicks;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea,
            RectangleEdge edge) {
        List<CategoryTick> standardTicks = super.refreshTicks(g2, state, dataArea, edge);
        if (standardTicks.isEmpty()) {
            return standardTicks;
        }
        int tickEvery = standardTicks.size() / labeledTicks;
        if (tickEvery < 1) {
            return standardTicks;
        }
        
        //Replace a few labels with blank ones
        List<CategoryTick> fixedTicks = new ArrayList<>(standardTicks.size());
        //Skip the first tick so your 45degree labels don't fall of the edge
        CategoryTick tick;
        //tick = standardTicks.get(0);
        //fixedTicks.add(new CategoryTick(tick.getCategory(), new TextBlock(), tick.getLabelAnchor(), tick.getRotationAnchor(), tick.getAngle()));
        for (int i = 0; i < standardTicks.size(); i++) {
            tick = standardTicks.get(i); 
            if (i % tickEvery == 0) {
                TextBlock test = new TextBlock();
                TextLine line = new TextLine();
                CategoryPlot plot = (CategoryPlot)this.getPlot();
                CategoryDataset dataset = (CategoryDataset)plot.getDataset();
                int number = (int)dataset.getColumnKeys().get(i);
                TextFragment frag = new TextFragment(String.valueOf(number));
                line.addFragment(frag);
                test.addLine(line);
                fixedTicks.add(new CategoryTick(tick.getCategory(), test, tick.getLabelAnchor(), tick.getRotationAnchor(), tick.getAngle()));
            }
            else {
                fixedTicks.add(new CategoryTick(tick.getCategory(), new TextBlock(), tick.getLabelAnchor(), tick.getRotationAnchor(), tick.getAngle()));
            }
        }
        return fixedTicks;
    }
}