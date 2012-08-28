/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Alex
 */
public class Chart_test_jfree {

    /**
     * @param args the command line arguments
     */
    private static HashMap settings = new HashMap();
    private static Chart_output_base output;
    
    public static void main(String[] args) {
        loadSettings("C:/Users/Alex/Documents/GitHub/arcadia_chart_test/data/settings.txt");      
        Maint_row[] data = loadData();
        if(data == null){
            return;
        }
        String is_seperate = (String)settings.get("seperate_charts");
        String output_type = (String)settings.get("chart_type");
        switch (output_type){
            case "category_calls_time" : output = new Chart_output_category_calls_vs_time(settings);
                break;
        }
        if(is_seperate.equals("true")){
            Maint_row[][] new_data = splitData(data);
            for(Maint_row[] row : new_data){
                row = normalizeData(row);
            }
            output.outputMultipleCharts(new_data);
        } else{
            String output_file = (String)settings.get("output_file");
            data = normalizeData(data);
            output.outputSingleChart(data, output_file);
        }
        
        //String output = generateChart(time_array);
        String debug = "true";
    }
    
    public static void loadSettings(String setting_file){
        CSVReader reader;
        List<String[]> my_entries;
        
        try{
            reader = new CSVReader(new FileReader(setting_file));
            my_entries = reader.readAll();
            for(int i = 0; i < my_entries.size(); i++){
                if(my_entries.get(i)[0].equals("") || my_entries.get(i)[0].contains("!--")){
                    boolean debug1 = true;
                } else{
                    Object key = my_entries.get(i)[0];
                    Object value = my_entries.get(i)[1];
                    settings.put(key, value);
                }
            }
        } catch(Exception e){
            boolean debug = true;
        }
    }
    
    public static Maint_row[] loadData(){
        Maint_row_loader loader;
        Maint_row[] data = null;
        String source = (String)settings.get("source");
        switch (source){
            case "csv" : loader = new Maint_row_loader_csv();
                loader.settings(settings);
                data = loader.loadData();
                break;
        }
        return data;
    }
    
    public static Maint_row[][] splitData(Maint_row[] data){
        String services = (String)settings.get("service_list");
        Maint_row[][] new_data;
        List<String> chosen_services = new ArrayList<>();
        
        if(services != null){
            String[] services_temp = services.split("@");
            for(String service : services_temp){
                if(!service.equals("")){
                    chosen_services.add(service);
                }
            }
        }
        
        new_data = new Maint_row[chosen_services.size()][];
        int count = 0;
        List<Maint_row> temp_rows;
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
    
    /*public static Maint_row[] loadDataFromDatabase(){
        Maint_row[] data_array;
        
        try{
            // Open DB Connection
            // Create query to select rows
            // my_entries = run query
            data_array = new Maint_row[my_entries.size() - 1];
            for(int i = 1; i < my_entries.size(); i++){
                Maint_row temp_data = new Maint_row();
                temp_data.message_id = my_entries.get(i)[0];
                temp_data.start_time = my_entries.get(i)[1];
                temp_data.end_time = my_entries.get(i)[2];
                temp_data.error_time = my_entries.get(i)[3];
                temp_data.service_name = my_entries.get(i)[4];
                temp_data.source_name = my_entries.get(i)[5];
                temp_data.error_message = my_entries.get(i)[6];
                data_array[i - 1] = temp_data;
            }
        } catch(Exception e){
            data_array = null;
        }
        
        return data_array;
    }*/
    
    public static Maint_row[] normalizeData(Maint_row[] data){
        for(Maint_row item : data){
            if(item.error_message.equals("")){
                item.error_message = "(null)";
            }
            
            if(item.error_time.equals("")){
                item.error_time = "(null)";
            }
            
            Chart_time start = item.getStartTime();
            Chart_time end = item.getEndTime();
            Chart_time error = item.getErrorTime();
        }
        return data;
    }
}