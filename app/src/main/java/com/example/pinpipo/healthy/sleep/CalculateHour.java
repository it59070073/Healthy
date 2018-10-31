package com.example.pinpipo.healthy.sleep;

class CalculateHour {
    private String hour;
    private String minute;

    public CalculateHour(){

    }

    public String calculate(String sleepTime, String wakeTime) {
        String[] sleepArray = sleepTime.split(":");
        String[] wakeArray = wakeTime.split(":");

        String hour;
        String minute;

        int intSleep = (Integer.parseInt(sleepArray[0]) * 3600) + (Integer.parseInt(sleepArray[1]) * 60);
        int intWake = (Integer.parseInt(wakeArray[0]) * 3600) + (Integer.parseInt(wakeArray[1]) * 60);

        if ( intSleep > intWake ) {
            hour = String.valueOf(Math.round(86400 - (intSleep - intWake)) / 3600);
            minute = String.valueOf(Math.round((86400 - (intSleep - intWake)) % 3600) / 60);
            return hour + ":" + minute;
        }

        hour = String.valueOf(Math.round(86400 - (intWake - intSleep)) / 3600);
        minute = String.valueOf(Math.round((86400 - (intWake - intSleep)) % 3600) / 60);
        return hour + ":" + minute;
    }
}
