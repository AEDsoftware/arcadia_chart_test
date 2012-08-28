/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.util.HashMap;
/**
 *
 * @author Alex
 */
public interface Maint_row_loader {
    public Maint_row[] loadData();
    public void settings(HashMap settings);
}
