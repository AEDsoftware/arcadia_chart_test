package chart_test_jfree;

// Maint_row is the core object for storing maintenance data
public class Maint_row {
    public String message_id = null;
    public String start_time = null;
    public String end_time = null;
    public String error_time = null;
    public String service_name = null;
    public String source_name = null;
    public String error_message = null;
    
    private Chart_time start_chart_time = null;
    private Chart_time end_chart_time = null;
    private Chart_time error_chart_time = null;
    
    // getStartTime, getEndTime, and getErrorTime translate the appropriate string into a Chart_time object
    // can probable be genericized into a single method
    public Chart_time getStartTime(){
        Chart_time result;
        if((start_chart_time == null) && (start_time != null) && (!start_time.equals("(null)")) && (!start_time.equals(""))){
            start_chart_time = Chart_time.fromString(start_time);
        }
        result = start_chart_time;
        return result;
    }
    
    public Chart_time getEndTime(){
        Chart_time result;
        if((end_chart_time == null) && (end_time != null) && (!end_time.equals("(null)")) && (!end_time.equals(""))){
            end_chart_time = Chart_time.fromString(end_time);
        }
        result = end_chart_time;
        return result;
    }
    
    public Chart_time getErrorTime(){
        Chart_time result;
        if((error_chart_time == null) && (error_time != null) && (!error_time.equals("(null)")) && (!error_time.equals(""))){
            error_chart_time = Chart_time.fromString(error_time);
        }
        result = error_chart_time;
        return result;
    }
}
