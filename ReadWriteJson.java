import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class ReadWriteJson{
  public static void main(String[]args) throws org.json.simple.parser.ParseException
  {
    JSONParser jp= new JSONParser();
    //reading the provided json data.
    try(FileReader j=new FileReader("input.json"))
    {
      Object ob = jp.parse(j);
      JSONArray arr= (JSONArray) ob;
      System.out.println(arr);
      
      //traversing through each entry within JSON data
      
      for(int i=0;i<arr.size();i++)
      {
        JSONObject en = (JSONObject) arr.get(i);
        
        //Fetching both height and weight of patient 
        
        long ht=(long) en.get("HeightCm");
        long wt=(long) en.get("WeightKg");

        //converting height from cm to m

        double hf=ht*0.01;
        
        //calucating BMI index of patient
        DecimalFormat two = new DecimalFormat("####0.00");
        String BM=two.format(wt/hf);
        double BMI=Double.parseDouble(BM);
        
        //Method call to fetch BMI category and health risk of patient
        String fina=parsingObj(BMI);
        String BMIcat=fina.split(",")[1];
        String risk=fina.split(",")[1];

        //Inserting the entries of BMI index, BMI category and Health risk to JSON data
        en.put("BMI Value",BMI);
        en.put("BMI Category",BMIcat);
        en.put("Health risk",risk);
      }
      System.out.println(arr);
      File fi=new File("c:\test.json");
      fi.write(arr);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  public static String parsingObj(Double BMI)
  {
    String BMICategory="n",Healthrisk="m";
    
    //Determining category and health risk.
    
    if(BMI<=18.4)
    {
      BMICategory="Underweight";
      Healthrisk="Malnutrition risk";
    }
    else if(BMI>=40)
    {
      BMICategory="Very severely obese";
      Healthrisk="Very High risk";
    }
    else if(BMI<=24.9 && BMI>=18.5)
    {
      BMICategory="Normal";
      Healthrisk="Low risk";
    }
    else if(BMI>=25 && BMI<=29.9)
    {
      BMICategory="Over weight";
      Healthrisk="Enhanced risk";
    }
    else if(BMI>=30 && BMI<=34.9)
    {
      BMICategory="Moderetely obese";
      Healthrisk="Meadium risk";
    }
    else if(BMI>=35 && BMI<=39.9)
    {
      BMICategory="severely obese";
      Healthrisk="High risk";
    }
    return BMICategory+","+Healthrisk;
  }
}