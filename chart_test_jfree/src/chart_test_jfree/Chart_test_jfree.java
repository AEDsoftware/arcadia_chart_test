package chart_test_jfree;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Chart_test_jfree is the core class for the charts project
public class Chart_test_jfree {
    private static HashMap settings = new HashMap();
    private static Chart_output_base output;
    
    // main is the core method that orchestrates the loading, processing, and output of the data
    public static void main(String[] args) {
        // ideally, we can get the setting file name from args so that this isn't platform dependant
        // we load the settings and data
        loadSettings("C:/Users/Alex/Documents/GitHub/arcadia_chart_test/data/settings.txt");      
        Maint_row[] data = loadData();
        if(data == null){
            return;
        }
        
        // we start by checking the chart_type and loading the appropriate Chart_output...
        switch ((String)settings.get("chart_type")){
            case "category_calls_time" : output = new Chart_output_category_calls_vs_time(settings);
                break;
        }
        
        String seperate = (String)settings.get("seperate_charts");
        if(seperate.equals("true")){
            // if the settings require seperate charts, we split the data by service name, then normalize each row and output all rows
            Maint_row[][] new_data = splitData(data);
            for(Maint_row[] row : new_data){
                row = normalizeData(row);
            }
            output.outputMultipleCharts(new_data);
        } else{
            // otherwise, we simply normalize and output the data
            String output_file = (String)settings.get("output_file");
            data = normalizeData(data);
            output.outputSingleChart(data, output_file);
        }
    }
    
    // loadSettings opens and maps the settings file
    public static void loadSettings(String setting_file){
        CSVReader reader;
        List<String[]> my_entries;
        
        try{
            reader = new CSVReader(new FileReader(setting_file));
            my_entries = reader.readAll();
            for(int i = 0; i < my_entries.size(); i++){
                // ignore blank lines and lines beginning with !--
                if(my_entries.get(i)[0].equals("") || my_entries.get(i)[0].contains("!--")){
                } else{
                    // Map the setting and value into the setting object
                    Object key = my_entries.get(i)[0];
                    Object value = my_entries.get(i)[1];
                    settings.put(key, value);
                }
            }
        } catch(Exception e){
        }
    }
    
    //loadData loads the data from the chosen source
    public static Maint_row[] loadData(){
        Maint_row_loader loader;
        Maint_row[] data = null;
        String source = (String)settings.get("source");
        switch (source){
            case "csv" : loader = new Maint_row_loader_csv();
                loader.setSettings(settings);
                data = loader.loadData();
                break;
        }
        return data;
    }
    
    //splitData takes a single array of Maint_rows and splits it by service name
    public static Maint_row[][] splitData(Maint_row[] data){
        String services = (String)settings.get("service_list");
        Maint_row[][] new_data;
        List<String> chosen_services = new ArrayList<>();
        
        if(services != null){
            // split services by @ and add to the list
            String[] services_temp = services.split("@");
            for(String service : services_temp){
                if(!service.equals("")){
                    chosen_services.add(service);
                }
            }
        }
        
        // initialize new data array
        new_data = new Maint_row[chosen_services.size()][];
        int count = 0;
        List<Maint_row> temp_rows;
        // loop through services
        // can probably be more efficient by only looping through data and mapping that way
        for(String service : chosen_services){
            temp_rows = new ArrayList<>();
            for(Maint_row row : data){
                if(row.service_name.equals(service)){
                    temp_rows.add(row);
                }
            }
            new_data[count] = temp_rows.toArray(new Maint_row[0]);
            count++;
        }
        
        return new_data;
    }
    
    // normalizeData takes an array of Maint_rows and performs some cleanup
    public static Maint_row[] normalizeData(Maint_row[] data){
        for(Maint_row item : data){
            // for each row, if error_message or _time are blank, set them to "(null)"
            if(item.error_message.equals("")){
                item.error_message = "(null)";
            }
            
            if(item.error_time.equals("")){
                item.error_time = "(null)";
            }
            
            // for each of start, end, and error time call the proper command
            // this converts the times from strings to objects if neccessary
            item.getStartTime();
            item.getEndTime();
            item.getErrorTime();
        }
        return data;
    }
}