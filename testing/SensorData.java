/**
 * Created by Bhawley on 4/17/2018.
 */
import java.net.*;
import java.io.*;

public class SensorData{
    public SensorData() throws Exception {}

    String inputLine;
    double temp = 0.0;
    double press = 0.0;
    public double getTemp() throws IOException {
    URL input = new URL ("http://192.168.2.8/");
            BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));


            while ((inputLine = in.readLine()) != null){
                String[] parts =inputLine.split(" ");
                temp = Float.parseFloat(parts[0]);
                press = Float.parseFloat(parts[1]);
            }


            in.close();

        return temp;
    }
    public double getPress() throws IOException {
        URL input = new URL ("http://192.168.2.8/");
        BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));


        while ((inputLine = in.readLine()) != null){
            String[] parts =inputLine.split(" ");
            temp = Float.parseFloat(parts[0]);
            press = Float.parseFloat(parts[1]);
        }


        in.close();

        return press;
    }

}