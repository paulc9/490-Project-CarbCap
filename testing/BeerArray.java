import java.util.*;

public class BeerArray{
    ArrayList<Beer> beerArray = new ArrayList<Beer>();
    Beer lager, paleAle, amberAle, brownAle, stout, porter, belgianWhite;

    public BeerArray(){
    	lager = new Beer();
    	paleAle = new Beer();
    	amberAle = new Beer();
    	brownAle = new Beer();
    	stout = new Beer();
    	porter = new Beer();
    	belgianWhite = new Beer();

		lager.setDesiredPSI(17);
		lager.setDesiredTemp(45);
		lager.setType("Lager");
		lager.setBeerImage("lager");

		paleAle.setDesiredPSI(16);
		paleAle.setDesiredTemp(45);
		paleAle.setType("Pale Ale");
		paleAle.setBeerImage("pale ale");

		amberAle.setDesiredPSI(16);
		amberAle.setDesiredTemp(50);
		amberAle.setType("Amber Ale");
		amberAle.setBeerImage("amber ale");

		brownAle.setDesiredPSI(11);
		brownAle.setDesiredTemp(40);
		brownAle.setType("Brown Ale");
		brownAle.setBeerImage("brown ale");

		stout.setDesiredPSI(11);
		stout.setDesiredTemp(55);
		stout.setType("Stout");
		stout.setBeerImage("stout");

		porter.setDesiredPSI(6);
		porter.setDesiredTemp(50);
		porter.setType("Porter");
		porter.setBeerImage("porter");

		belgianWhite.setDesiredPSI(9);
		belgianWhite.setDesiredTemp(45);
		belgianWhite.setType("Belgian White");
		belgianWhite.setBeerImage("belgian white");

		beerArray.add(lager);
		beerArray.add(paleAle);
		beerArray.add(amberAle);
		beerArray.add(brownAle);
		beerArray.add(stout);
		beerArray.add(porter);
		beerArray.add(belgianWhite);
	}
}