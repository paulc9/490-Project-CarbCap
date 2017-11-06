import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Beer{
    private int desiredPSI, beerID, currentPSI, desiredTemp; //currentPSI may be ArrayList
    private String beerType, beerName;
    private Calendar bottleDate, readyDate;
    SimpleDateFormat sdf  =   new  SimpleDateFormat("MM-dd-yyyy"); 
    //color
    //estimatedFinishDate

    public Beer(String name, String bDate){
        this.beerName = name;
        setBottleDate(bDate);
    }

    public Beer(){};

    public void setDesiredPSI(int dPSI){this.desiredPSI = dPSI;}
    public int getDesiredPSI(){return this.desiredPSI;}
    public void setDesiredTemp(int temp){this.desiredTemp = temp;}
    public int getDesiredTemp(){return this.desiredTemp;}
    public void setBeerID(int id){this.beerID = id;}
    public int getBeerID(){return this.beerID;}
    public void setCurrentPSI(int psi){this.currentPSI = psi;}
    public int getCurrentPSI(){return this.currentPSI;}
    public void setType(String type){this.beerType = type;}
    public String getType(){return this.beerType;}
    public void setName(String name){this.beerName = name;}
    public String getName(){return this.beerName;}

    public void setBottleDate(String bDate){
        bottleDate = Calendar.getInstance();
        try{
            bottleDate.setTime(sdf.parse(bDate));
        } catch (Exception e){
        }
    }

    public String getBottleDateString(){return sdf.format(bottleDate.getTime());}

    public void setReadyDate(int days){
        Date bDate = bottleDate.getTime();
    	readyDate = Calendar.getInstance();
    	readyDate.setTime(bDate);
    	readyDate.add(Calendar.DATE, days);
    }

    public String getReadyDateString(){return sdf.format(readyDate.getTime());}
}
