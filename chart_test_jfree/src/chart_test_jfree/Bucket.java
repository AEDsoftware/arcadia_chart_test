package chart_test_jfree;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.jfree.data.category.CategoryDataset;
import java.math.BigDecimal;
import java.text.DecimalFormat;

// Bucket is a class for holding a time span and a corresponding call count
public class Bucket {
    public int time_span;
    public int call_count;
    
    public Bucket(int span, int count){
        time_span = span;
        call_count = count;
    }
    
    public Bucket(){
        
    }
    
    // getDataCallsPerTimeBucket converts an array of maintenance data into an array of Buckets
    public static Bucket[] getDataCallsPerTimeBucket(Maint_row[] data){
        HashMap<Integer, Integer> map = new HashMap();
        Integer map_result;
        int count = 0;
        // loop through and process all maintenance rows
        for(Maint_row item : data){
            // load start, end, and error time from the rows
            Chart_time start = item.getStartTime();
            Chart_time end = item.getEndTime();
            Chart_time error = item.getErrorTime();
            
            int time;
            int hour_diff;
            int minute_diff;
            float second_diff;
            
            // if there's not an error, we calculate the difference between when the call started and ended
            if(error == null){
                hour_diff = Integer.parseInt(end.hour) - Integer.parseInt(start.hour);
                minute_diff = Integer.parseInt(end.minute) - Integer.parseInt(start.minute);
                second_diff = Float.parseFloat(end.second) - Float.parseFloat(start.second);
                
                hour_diff = hour_diff * 60 * 60 * 1000;
                minute_diff = minute_diff * 60 * 1000;
                second_diff = second_diff * 1000;
                
                time = hour_diff + minute_diff + (int)second_diff;
            } 
            
            // if there is an error, we calculate the difference between when the service started and when the error occured
            else{
                hour_diff = Integer.parseInt(error.hour) - Integer.parseInt(start.hour);
                minute_diff = Integer.parseInt(error.minute) - Integer.parseInt(start.minute);
                second_diff = Float.parseFloat(error.second) - Float.parseFloat(start.second);
                
                hour_diff = hour_diff * 60 * 60 * 1000;
                minute_diff = minute_diff * 60 * 1000;
                second_diff = second_diff * 1000;
                
                time = hour_diff + minute_diff + (int)second_diff;
            }
            
            // we round the time to the nearest hundred (may change to use a setting in later version)
            time = (((time + 50) / 100) * 100);
            
            // we ensure that we're using an int value if the given time hasn't been used yet
            map_result = map.get(time);
            if(map_result == null){
                map_result = 0;
            }
            
            // we increase the # of calls at the given time by 1
            map_result++;
            map.put(time, map_result);
            count++;
        }
        
        // we do some transformation to turn the map into a list
        ArrayList<Integer> key_list = new ArrayList(map.keySet());
        Collections.sort(key_list);
        Bucket[] bucket_array = new Bucket[key_list.size()];
        for(int i = 0; i < key_list.size(); i++){
            bucket_array[i] = new Bucket(key_list.get(i), map.get(key_list.get(i)));
        }
        return bucket_array;
    }
    
    // trimBuckets takes a Bucket array, a trim setting, and a threshold
    public static Bucket[] trimBuckets(Bucket[] data, int max, int threshold){
        int maxAt = 0;
        
        // if the trim setting (max) is -1, no trim is performed
        if(max == -1){
            return data;
        }
        
        // if the trim setting (max) is 0, auto-trim is used
        // we loop through the data backwards until we find a row with more calls than the threshold
        // this can prevent "long tails" of 20 entries with only 1-2 calls
        if(max == 0){
            for(int i = data.length - 1; i > -1; i--){
                if(data[i].call_count > threshold){
                    max = data[i].time_span;
                    i = -1;
                }
            }
        }
        
        // using the trim setting (max), either passed in or determined by auto-trim, we loop through the data
        // we find the maxAt; the index of an entry where the max value is found
        for(int i = 0; i < data.length; i++){
            if(data[i].time_span > max){
                maxAt = i - 1;
                i = data.length;
            }
        }
        
        // we then transfer all of the data up to the maxAt into a new array, which is returned
        Bucket[] new_data = new Bucket[maxAt + 1];
        for(int i = 0; i <= maxAt; i++){
            new_data[i] = data[i];
        }
        return new_data;
    }
    
    public static void writePercentiles(Bucket[] data, String output_folder){
        String filename;
        filename = output_folder + "percentiles.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename), ',')) {
            int total = 0;
            int running_total = 0;
            
            for(Bucket bucket : data){
                total = total + bucket.call_count;
            }
            
            String[] temp_array = new String[3];
            temp_array[0] = "Time Bucket";
            temp_array[1] = "Number of Calls";
            temp_array[2] = "Percentile";
            writer.writeNext(temp_array);
                
            for(Bucket bucket : data){
                temp_array = new String[3];
                running_total = running_total + bucket.call_count;
                double rt = running_total;
                double t = total;
                double percent = rt / t;
                percent = percent * 100;
                DecimalFormat twoDForm = new DecimalFormat("#.##");
                percent = Double.valueOf(twoDForm.format(percent));
                temp_array[0] = String.valueOf(bucket.time_span);
                temp_array[1] = String.valueOf(bucket.call_count);
                temp_array[2] = String.valueOf(percent);
                writer.writeNext(temp_array);
            }
        } catch(Exception e){
        }
    }
}
