package chart_test_jfree;
import java.util.HashMap;

// Maint_row_base implements settings... should probably be factored out into a single object for everything that uses settings
public class Maint_row_base {
    private HashMap settings;
    
    public void settings(HashMap settings){
        this.settings = settings;
    }
    
    public HashMap getSettings(){
        return settings;
    }
}
