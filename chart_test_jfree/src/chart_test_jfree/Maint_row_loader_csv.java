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
public class Maint_row_loader_csv extends Maint_row_base implements Maint_row_loader {
    @Override
    public Maint_row[] loadData(){
        List<Maint_row> data_array = new ArrayList<>();
        CSVReader reader;
        List<String[]> my_entries;
        HashMap settings = super.getSettings();
        String filename = (String)settings.get("csv_input_file");
        String services = (String)settings.get("service_list");
        boolean any_service = true;
        List<String> chosen_services = new ArrayList<>();
        if(services != null){
            String[] services_temp = services.split("@");
            for(String service : services_temp){
                if(!service.equals("")){
                    chosen_services.add(service);
                    any_service = false;
                }
            }
        }
        
        
        try{
            reader = new CSVReader(new FileReader(filename));
            my_entries = reader.readAll();
            for(int i = 1; i < my_entries.size(); i++){
                Maint_row temp_data = new Maint_row();
                temp_data.message_id = my_entries.get(i)[0];
                temp_data.start_time = my_entries.get(i)[1];
                temp_data.end_time = my_entries.get(i)[2];
                temp_data.error_time = my_entries.get(i)[3];
                temp_data.service_name = my_entries.get(i)[4];
                temp_data.source_name = my_entries.get(i)[5];
                temp_data.error_message = my_entries.get(i)[6];
                if(any_service){
                    data_array.add(temp_data);
                } else{
                    for(String service : chosen_services){
                        if(service.equals(temp_data.service_name)){
                            data_array.add(temp_data);
                        }
                    }
                }
            }
        } catch(Exception e){
            data_array = null;
        }
        
        return data_array.toArray(new Maint_row[0]);
    }
}
