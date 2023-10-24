package stepdef;

import io.cucumber.java.en.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import utilities.Driver;
import utilities.ExcelUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class Test {
    List<Map<String,String>> datalist;


    @Given("navigate amazon web site sellers product")
    public void navigate_amazon_web_site_sellers_product() throws InterruptedException
        {

            //--------------------------------------------
            LocalDateTime now = LocalDateTime.now();
            int day = now.getDayOfMonth();
            int month = now.getMonthValue();
            int hour = now.getHour();
            int minutes = now.getMinute();
            String date = day+"/"+month+"--"+hour+":"+minutes;
            //--------------------------------------------
            String path = "./src/test/resources/testdata/deneme.xlsx";
            String sheetName = "deals-2-grab";
            ExcelUtils excelUtils = new ExcelUtils(path,sheetName);
            datalist = excelUtils.getDataList();
            String runtime = excelUtils.getCellData(0,1);
            double runTime = Double.valueOf(runtime);
            //System.out.println(runTime);
            //WebElement price1 = Driver.getDriver().findElement(By.xpath("//span[@class='a-price aok-align-center']//span[@class='a-offscreen']"));





        for (Map<String,String> eachdata : datalist)
            {

                int row = datalist.indexOf(eachdata);
                String amazonurl = "https://www.amazon.com/dp/";
                Driver.getDriver().get(amazonurl+eachdata.get("asin")+"/"+eachdata.get("marketid"));
                Thread.sleep(1000);
                try {
                WebElement price2 = Driver.getDriver().findElement(By.xpath("//span[@class='a-price aok-align-center']"));
                String pricestr2 = price2.getText();
                String pricel= pricestr2.replaceAll("[^0-9]","");
                int num = Integer.valueOf(pricel);
                double price = (double) num/100;
                //System.out.println(eachdata.get("asin")+"-->"+price+" "+row);

                excelUtils.setCellData(price,row+2, (int) runTime+2);
                String cellcondition = excelUtils.cellBlankorNot(row+2,(int) runTime+2);

                                if(runTime!=0.0 && cellcondition.equals("int")){

                                        double previous = excelUtils.getCellDataint(row+2,(int) runTime+1);
                                        double last = excelUtils.getCellDataint(row+2,(int) runTime+2);
                                        FileOutputStream file = new FileOutputStream(path);

                                            if(last>previous){
                                                              Cell cell;
                                                              CellStyle cellStyle = excelUtils.workBook.createCellStyle();
                                                              cell = excelUtils.workSheet.getRow(row+2).getCell((int) runTime+2);
                                                              cellStyle.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
                                                              cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                              cell.setCellStyle(cellStyle);
                                                              excelUtils.workBook.write(file);

                                                             }
                                            else if (last<previous) {
                                                              Cell cell1;
                                                              CellStyle cellStyle1 = excelUtils.workBook.createCellStyle();
                                                              cell1 = excelUtils.workSheet.getRow(row+2).getCell((int) runTime+2);
                                                              cellStyle1.setFillForegroundColor(IndexedColors.RED.getIndex());
                                                              cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                              cell1.setCellStyle(cellStyle1);
                                                              excelUtils.workBook.write(file);
                                                             }

                                                                              }

                   }
                catch (NoSuchElementException | IOException e){ }


            }
            excelUtils.setCellData((int) (runTime+1),0,1);
            excelUtils.setCellDataString(date,1,(int)runTime+2);

        }

    @Given("check if page exist")
    public void check_if_page_exist() {

//        String path = "./src/test/resources/testdata/deneme.xlsx";
//        String sheetName = "deals-2-grab";
//        ExcelUtils excelUtils = new ExcelUtils(path,sheetName);
//        //System.out.println(excelUtils.getCellData(0, 1));
//
//
//        if(!excelUtils.getCellData(0,1).equals("")){
//            int data = excelUtils.getCellDataint(0,1);
//
//            System.out.println(data);
//        }
//
//        excelUtils.cellBlankorNot(0,1);


    }
    @Given("if page exist get price and write on excel if not end test")
    public void if_page_exist_get_price_and_write_on_excel_if_not_end_test() {

    }
    @Then("close driver")
    public void close_driver() {

        Driver.closeDriver();

    }
}
