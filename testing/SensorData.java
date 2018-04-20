/**
 * Created by Bhawley on 4/17/2018.
 */
import java.net.*;
import java.io.*;

public class SensorData{
    public SensorData(){}
    private double[] temp = new double[3];
    private double[] press = new double[3];
    public void renewSensorData() throws Exception {
        int i = 0;
        URL input = new URL ("http://192.168.2.8/");
        BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            String[] parts = inputLine.split(" ");
            temp[i] = Double.parseDouble(parts[0]);
            press[i] = Double.parseDouble(parts[1]);
            i++;
        }
        in.close();
    }

    public double getTemp(int index) {
        return temp[index];
    }

    public double getPress(int index) {
        return press[index];
    }
}