package com.example.ryangrosscapstone;

public class BoxSizeHelper {
    public static Integer BoxSelector(Double fishSize) {
        if (fishSize >= 0 && fishSize < 10){
            return 1;
        }else if(fishSize >= 10 && fishSize < 20){
            return 2;
        }else if(fishSize >= 20 && fishSize < 30){
            return 3;
        }else if(fishSize >= 30 && fishSize < 40){
            return 4;
        }else if(fishSize >= 40 && fishSize < 50){
            return 5;
        }else if(fishSize >= 50 && fishSize < 60){
            return 6;
        }else {
            return 7;
        }
    }


}
