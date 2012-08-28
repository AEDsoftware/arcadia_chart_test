package chart_test_jfree;

// Maint_row_loader_db will be used to load data from the maint database
// it is currently not implemented
public class Maint_row_loader_db extends Maint_row_base implements Maint_row_loader {
    @Override
    public Maint_row[] loadData(){
        Maint_row[] data_array = null;
        /*List<String[]> my_entries;
        
        try{
            reader = new CSVReader(new FileReader(filename));
            my_entries = reader.readAll();
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
        */
        
        return data_array;
    }
}
