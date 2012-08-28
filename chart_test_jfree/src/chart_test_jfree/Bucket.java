/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart_test_jfree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Alex
 */
public class Bucket {
    public int time_span;
    public int call_count;
    
    public Bucket(int span, int count){
        time_span = span;
        call_count = count;
    }
    
    public Bucket(){
        
    }
    
    public static Bucket[] getTrimmedBucket(Maint_row[] data, int trim, int threshold){
        return trimBuckets(getDataCallsPerTimeBucket(data), trim, threshold);
    }
    
    private static Bucket[] getDataCallsPerTimeBucket(Maint_row[] data){
        HashMap map = new HashMap();
        Integer map_result;
        int count = 0;
        for(Maint_row item : data){
            Chart_time start = item.getStartTime();
            Chart_time end = item.getEndTime();
            Chart_time error = item.getErrorTime();
            
            int time;
            int hour_diff;
            int minute_diff;
            float second_diff;
            
            if(error == null){
                hour_diff = Integer.parseInt(end.hour) - Integer.parseInt(start.hour);
                minute_diff = Integer.parseInt(end.minute) - Integer.parseInt(start.minute);
                second_diff = Float.parseFloat(end.second) - Float.parseFloat(start.second);
                
                hour_diff = hour_diff * 60 * 60 * 1000;
                minute_diff = minute_diff * 60 * 1000;
                second_diff = second_diff * 1000;
                
                time = hour_diff + minute_diff + (int)second_diff;
            } else{
                hour_diff = Integer.parseInt(error.hour) - Integer.parseInt(start.hour);
                minute_diff = Integer.parseInt(error.minute) - Integer.parseInt(start.minute);
                second_diff = Float.parseFloat(error.second) - Float.parseFloat(start.second);
                
                hour_diff = hour_diff * 60 * 60 * 1000;
                minute_diff = minute_diff * 60 * 1000;
                second_diff = second_diff * 1000;
                
                time = hour_diff + minute_diff + (int)second_diff;
            }
            time = (((time + 50) / 100) * 100);
            map_result = (Integer)map.get(time);
            if(map_result == null){
                map_result = 0;
            }
            map_result++;
            map.put(time, map_result);
            count++;
        }
        ArrayList key_list = new ArrayList(map.keySet());
        Collections.sort(key_list);
        Bucket[] bucket_array = new Bucket[key_list.size()];
        for(int i = 0; i < key_list.size(); i++){
            bucket_array[i] = new Bucket((int)key_list.get(i), (int)map.get(key_list.get(i)));
        }
        return bucket_array;
    }
    
    private static Bucket[] trimBuckets(Bucket[] data, int max, int threshold){
        int maxAt = 0;
        if(max == -1){
            return data;
        }
        if(max == 0){
            for(int i = data.length - 1; i > -1; i--){
                if(data[i].call_count > threshold){
                    max = data[i].time_span;
                    i = -1;
                }
            }
        }
        
        for(int i = 0; i < data.length; i++){
            if(data[i].time_span > max){
                maxAt = i - 1;
                i = data.length;
            }
        }
        Bucket[] new_data = new Bucket[maxAt + 1];
        for(int i = 0; i <= maxAt; i++){
            new_data[i] = data[i];
        }
        return new_data;
    }
}
