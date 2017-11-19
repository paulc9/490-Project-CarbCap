import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Beer implements Serializable{
    private int desiredPSI, beerID, currentPSI, desiredTemp, currentTemp; //currentPSI may be ArrayList
    private String beerType, beerName, beerImage, email;
    private Calendar bottleDate, trackingDate, readyDate;
    SimpleDateFormat sdf  =   new  SimpleDateFormat("MM-dd-yyyy");
    private ArrayList<PSItrackingObject>  trackingArray = new ArrayList<PSItrackingObject>();
    //color
    //estimatedFinishDate

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

    public void setCurrentPSI(int psi){
        this.currentPSI = psi;
        trackingArray.add(new PSItrackingObject(trackingDate));            //sets psi, then creats tracking object and adds it to the tracking array
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

    public ArrayList<PSItrackingObject> getTrackingArrayList(){return this.trackingArray;}

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

    public void addToTrackingDate(int days){
        trackingDate.add(Calendar.DATE, days);
    }

    public String getTrackingDateString(){return sdf.format(trackingDate.getTime());}

    public void setReadyDate(int days){
        Date bDate = bottleDate.getTime();
    	readyDate = Calendar.getInstance();
    	readyDate.setTime(bDate);
    	readyDate.add(Calendar.DATE, days);
    }

    public String getReadyDateString(){return sdf.format(readyDate.getTime());}



    public class PSItrackingObject implements Serializable{
        private int trackedPSI, trackedTemp;
        private Calendar trackedDate;

        public PSItrackingObject(){
            trackedPSI = getCurrentPSI();
            trackedDate = Calendar.getInstance();
	    trackedTemp = getCurrentTemp();
        }

        public PSItrackingObject(Calendar date){
            Date trackDate = date.getTime();
            trackedPSI = getCurrentPSI();
            trackedDate = Calendar.getInstance();
            trackedDate.setTime(trackDate);
	    trackedTemp = getCurrentTemp();
        }

        public int getPSI(){return trackedPSI;}
        public String getDateString(){return sdf.format(trackedDate.getTime());}
	public int getTemp(){return trackedTemp;}
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




