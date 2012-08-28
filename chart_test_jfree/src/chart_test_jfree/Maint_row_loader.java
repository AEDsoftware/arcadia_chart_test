package chart_test_jfree;

import java.util.HashMap;

// Maint_row_loader is a simple interface for Maint_row loading tools
public interface Maint_row_loader {
    public Maint_row[] loadData();
    public void settings(HashMap settings);
}
