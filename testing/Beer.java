import java.util.Calendar;
import java.util.Date;
public class Beer{
    private int desiredPSI, beerID, currentPSI, desiredTemp; //currentPSI may be ArrayList
    private String beerType, beerName, bottleDate;
    //color
    //estimatedFinishDate

    public Beer(String name, String bDate){
        this.beerName = name;
        this.bottleDate = bDate;
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
    public void setBottleDate(String bDate){this.bottleDate = bDate;}
    public String getBottleDate(){return this.bottleDate;}
}
