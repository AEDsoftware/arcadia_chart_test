package chart_test_jfree;

// Maint_row_loader is a simple interface for Maint_row loading tools

import java.util.HashMap;

public interface Maint_row_loader {
    public Maint_row[] loadData();
    public void setSettings(HashMap settings);
}
