import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.lang.Math;

public class Beer implements Serializable{
    private int desiredPSI, beerID, currentPSI, desiredTemp, currentTemp;
    private double desiredVolume, currentVolume;
    private String beerType, beerName, beerImage, email;
    private Calendar bottleDate, trackingDate, readyDate;
    SimpleDateFormat sdf  =   new  SimpleDateFormat("MM-dd-yyyy");
    private ArrayList<TrackingObject>  trackingArray = new ArrayList<TrackingObject>();
    //color

    public Beer(String name, String bDate, String mail){
        this.beerName = name;
        setBottleDate(bDate);
        this.email = mail;
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

    //Converts psi and Fahrenheit temp to the CO2 carbonation in beer
    //Formula requires that temp be in Celsius and pressure be in bar
    //May not be the correct formula just yet???
    public void setCurrentVolume(int psi, int Ftemp){
        double Ctemp = fToC(Ftemp);
        double barPressure = psiToBar(psi);
        double carbInGperL = (barPressure + 1.013) * 10 * Math.pow(2.71828182845904, (-10.73797 + (2617.25 / (Ctemp + 273.15))));
        this.currentVolume = volConvert(carbInGperL, Ctemp);
    }
    public double getCurrentVolume(){return this.currentVolume;}

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




