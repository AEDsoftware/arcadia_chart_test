/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

/**
 *
 * @author Alex
 */
public class Chart_time {
    public String year;
    public String month;
    public String day;
    public String hour;
    public String minute;
    public String second;
    public boolean isAM = true;
    
    public static Chart_time fromString(String date_time){
        Chart_time result = new Chart_time();
        if(date_time.equals("")){
            return null;
        }
        String first_part = date_time.split(" ")[0];
        String second_part = date_time.split(" ")[1];
            
        result.month = first_part.split("/")[0];
        result.day = first_part.split("/")[1];
        result.year = first_part.split("/")[2];
            
        int hour = Integer.parseInt(second_part.split(":")[0]);
        if(hour > 12){
            hour = hour - 12;
            result.isAM = false;
        }
        
        result.hour = Integer.toString(hour);
        result.minute = second_part.split(":")[1];
        result.second = second_part.split(":")[2];
        
        return result;
    }
}
