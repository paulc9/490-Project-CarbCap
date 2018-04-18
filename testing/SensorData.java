/**
 * Created by Bhawley on 4/17/2018.
 */
import java.net.*;
import java.io.*;

public class SensorData{


    String inputLine;
    double temp = 0.0;
    double press = 0.0;
    public SensorData() throws Exception {
        URL input = new URL ("http://192.168.2.8/");
        BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));
        while ((inputLine = in.readLine()) != null){
            String[] parts =inputLine.split(" ");
            temp = Double.parseDouble(parts[0]);
            press = Double.parseDouble(parts[1]);
        }
        in.close();
    }

    public double getTemp() {
        return temp;
    }

    public double getPress() {
        return press;
    }

}