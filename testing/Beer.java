<<<<<<< HEAD
import java.util.Calendar;
import java.util.Date;
public class Beer{
    private int desiredPSI, beerID, currentPSI, desiredTemp; //currentPSI may be ArrayList
    private String beerType, beerName, bottleDate;
    //color
    //estimatedFinishDate

    public Beer(String name, String bDate, String type, int dPSI){
        this.beerName = name;
        this.bottleDate = bDate;
        this.beerType = type;
        this.desiredPSI = dPSI;
    }


    public void setDesiredPSI(int dPSI){this.desiredPSI = dPSI;}
    public int getDesiredPSI(){return desiredPSI;}
    public void setDesiredTemp(int temp){this.desiredTemp = temp;}
    public int getDesiredTemp(){return desiredTemp;}
    public void setBeerID(int id){this.beerID = id;}
    public int getBeerID(){return beerID;}
    public void setCurrentPSI(int psi){this.currentPSI = psi;}
    public int getCurrentPSI(){return currentPSI;}
    public void setType(String type){this.beerType = type;}
    public String getType(){return beerType;}
    public void setName(String name){this.beerName = name;}
    public String getName(){return beerName;}
=======
import java.util.Calendar;
import java.util.Date;
public class Beer{
    private int desiredPSI, beerID, currentPSI, desiredTemp; //currentPSI may be ArrayList
    private String beerType, beerName, bottleDate;
    //color
    //estimatedFinishDate

    public Beer(String name, String bDate, String type, int dPSI){
        this.beerName = name;
        this.bottleDate = bDate;
        this.beerType = type;
        this.desiredPSI = dPSI;
    }


    public void setDesiredPSI(int dPSI){this.desiredPSI = dPSI;}
    public int getDesiredPSI(){return desiredPSI;}
    public void setDesiredTemp(int temp){this.desiredTemp = temp;}
    public int getDesiredTemp(){return desiredTemp;}
    public void setBeerID(int id){this.beerID = id;}
    public int getBeerID(){return beerID;}
    public void setCurrentPSI(int psi){this.currentPSI = psi;}
    public int getCurrentPSI(){return currentPSI;}
    public void setType(String type){this.beerType = type;}
    public String getType(){return beerType;}
    public void setName(String name){this.beerName = name;}
    public String getName(){return beerName;}
>>>>>>> 5df23d0e8156b1d1551f25ea157c71e95d398186
}