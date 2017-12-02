import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.lang.Math;

public class Beer implements Serializable{
    private int desiredPSI, beerID, currentPSI, desiredTemp, currentTemp;
    private double desiredVolume, currentVolume, avgVolRate;
    private String beerType, beerName, beerImage, email;
    private Calendar bottleDate, trackingDate, readyDate;
    private Boolean ready, warning, plateaued;
    SimpleDateFormat sdf  =   new  SimpleDateFormat("MM-dd-yyyy");
    private ArrayList<TrackingObject>  trackingArray = new ArrayList<TrackingObject>();
    //color

    public Beer(String name, String bDate, String mail){
        this.beerName = name;
        setBottleDate(bDate);
        this.email = mail;
        this.ready = false;
        this.warning = false;
        this.plateaued = false;
    }

    public Beer(){};

    public void setBeerImage(String i){this.beerImage=i;}
    public String getBeerImage(){return this.beerImage;}

    public void setDesiredPSI(int dPSI){this.desiredPSI = dPSI;}
    public int getDesiredPSI(){return this.desiredPSI;}

    public void setDesiredTemp(int temp){this.desiredTemp = temp;}
    public int getDesiredTemp(){return this.desiredTemp;}

    public void setBeerID(int id){this.beerID = id;}
    public int getBeerID(){return this.beerID;}

    public void setCurrentTracking(int psi){
        this.currentPSI = psi;
        this.currentTemp = 50;                                             //In Fahrenheit, assuming at 50 degrees for now
        setCurrentVolume(currentPSI, currentTemp);
        trackingArray.add(new TrackingObject(trackingDate));            //sets psi, then creats tracking object and adds it to the tracking array
        addToTrackingDate(1);                                              //this is used to simulate a day's passage after every manual psi input
    }
    public int getCurrentPSI(){return this.currentPSI;}

    public void setCurrentTemp(int temp){this.currentTemp = temp;}
    public int getCurrentTemp(){return this.currentTemp;}

    public void setType(String type){this.beerType = type;}
    public String getType(){return this.beerType;}

    public void setName(String name){this.beerName = name;}
    public String getName(){return this.beerName;}

    public void setEmail(String mail){this.email = mail;}
    public String getEmail(){return this.email;}

    public void setDesiredVolume(double vol){this.desiredVolume = vol;}
    public double getDesiredVolume(){return this.desiredVolume;}

    /*
        Converts psi and Fahrenheit temp to the CO2 carbonation in beer.
        Result is very close to numbers on beer chart from: 
        http://www.kegerators.com/carbonation-table.php
    */
    public void setCurrentVolume(int psi, int Ftemp){
        double c = (-16.6999 - 0.0101059 * Ftemp + 0.00116512 * Math.pow(Ftemp, 2)) - (double)psi;
        double b = 0.173354 * (double)Ftemp + 4.24267;
        double a = -0.0684226;
        double result = solveQuadEq(a, b, c);
        this.currentVolume = result;
    }
    public double getCurrentVolume(){return this.currentVolume;}

    public double solveQuadEq(double a, double b, double c){
        double result1, result2, root;
        root = Math.pow(b, 2) - 4 * a * c;
        result1 = ( -b + Math.sqrt(root) ) / ( 2 * a );
        result2 = ( -b + Math.sqrt(root) ) / ( 2 * a );
        if(result1 >= 0)
            return result1;
        else
            return result2;
    }

/* Could be used for future conversion features??

    public double fToC(int Ftemp){
        double result = ((double)Ftemp - 32) / 1.8;
        return result;
    }

    public double psiToBar(int psi){
        double result = (double)psi * 0.0689475729;
        return result;
    }

    public double volConvert(double unitVol, double Ctemp){
        double Ktemp = cToK(Ctemp);
        double result = unitVol * 0.0821 * Ktemp / 44;
        return result;
    }

    public double cToK(double Ctemp){
        double result = Ctemp + 273.15;
        return result;
    }
*/

    public ArrayList<TrackingObject> getTrackingArrayList(){return this.trackingArray;}

    public void setBottleDate(String bDate){
        bottleDate = Calendar.getInstance();
        try{
            bottleDate.setTime(sdf.parse(bDate));
        } catch (Exception e){
        }
        setTrackingDate();
    }
    public String getBottleDateString(){return sdf.format(bottleDate.getTime());}

    public void setTrackingDate(){
        Date bDate = bottleDate.getTime();
        trackingDate = Calendar.getInstance();
        trackingDate.setTime(bDate);
    }
    public String getTrackingDateString(){return sdf.format(trackingDate.getTime());}

    public void addToTrackingDate(int days){
        trackingDate.add(Calendar.DATE, days);
    }

    public void setReadyDate(int days){
        Date bDate = bottleDate.getTime();
    	readyDate = Calendar.getInstance();
    	readyDate.setTime(bDate);
    	readyDate.add(Calendar.DATE, days);
    }
    public String getReadyDateString(){return sdf.format(readyDate.getTime());}

    /* 
        Adjusts estimated ready date based on average rate of last 4 CO2 volumes
        If there are less than 4 recorded volumes, then estimated date of 3 weeks from bottle date is used
    */
    public void adjustReadyDate(){
        if(ready == false){
            double remainingVol;
            double rate = avgVolRate;
            int daysRemaining = 0;
            remainingVol = desiredVolume - currentVolume;
            daysRemaining = (int)(Math.ceil(remainingVol / rate)) - 1;      // subtract 1 because the current day's PSI has not been recorded yet
            Date currentDate = trackingDate.getTime();
            readyDate = Calendar.getInstance();
            readyDate.setTime(currentDate);
            readyDate.add(Calendar.DATE, daysRemaining);
        }
    }

    public void adjustAvgVolRate(){
        if(trackingArray.size() >= 4){
            double rate = 0;
            int count = 3;
            for(int i = trackingArray.size() - 1; i > trackingArray.size() - 4; i--){
                double change = trackingArray.get(i).getVolume() - trackingArray.get(i-1).getVolume();
                rate += change;
            }
            rate /= count;
            this.avgVolRate = rate;
        }
    }
    public String getAvgVolRateString(){
        String result;
        if(trackingArray.size() >= 4)
            result = Double.toString(avgVolRate);
        else
            result = "Not enough data";
        return result;
    }
    public double getAvgVolRate(){return avgVolRate;}

    public void readyLogged(){this.ready = true;}
    public Boolean readyCheck(){return ready;}

    public void warningLogged(){this.warning = true;}
    public Boolean warningCheck(){return warning;}

    public void plateauedLogged(){this.plateaued = true;}
    public Boolean plateauedCheck(){return plateaued;}

    /*
        Checks the change in CO2 volume over the past 7 days
        If the change is less than the changeThreshold, the function returns true (meaning the beer has plateaued)
    */
    public Boolean weekPlateaued(){
        Double changeThreshold = 0.05;
        if(trackingArray.size() >= 7){
            int i = trackingArray.size() - 1;
            Double rate = trackingArray.get(i).getVolume() - trackingArray.get(i-6).getVolume();
            if(rate < changeThreshold)
                return true;
        }
        return false;
    }



    public class TrackingObject implements Serializable{
        private int trackedPSI, trackedTemp; //temp in F
        private double trackedVol;
        private Calendar trackedDate;

        public TrackingObject(){
            trackedPSI = getCurrentPSI();
            trackedDate = Calendar.getInstance();
	    	trackedTemp = getCurrentTemp();
        }

        public TrackingObject(Calendar date){
            Date trackDate = date.getTime();
            trackedPSI = getCurrentPSI();
            trackedDate = Calendar.getInstance();
            trackedDate.setTime(trackDate);
	    	trackedTemp = getCurrentTemp();
            trackedVol = getCurrentVolume();
        }

        public int getPSI(){return trackedPSI;}
        public String getDateString(){return sdf.format(trackedDate.getTime());}
		public int getTemp(){return trackedTemp;}
        public double getVolume(){return trackedVol;}
    }

    public void saveCurrentBeerStateToFile(){
        try{
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("savedCurrentBeer.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);

            out.close();
            file.close();

            System.out.println("Object has been serialized");
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught /n save error");
        }

    }

}




