
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konrad
 */
public class SaveToFile
{

    private double scale;
    
    public SaveToFile(double scale)
    {
        this.scale = scale;
    }

//    public void saveToXLS( ArrayList<String[]> data )
//    {
//        int rowNumber = 0;
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Simple sheet");
//        
//        ArrayList<String[]> temp = new ArrayList<String[]>();
//        temp = ManufactureWeightDataToDouble(data);
//
//        for ( String[] element : temp )
//        {
//            Row row = sheet.createRow(rowNumber++);
//            int cellNumber = 0;
//            for ( int i = 0; i <= 1; i++ )
//            {
//                Cell cell = row.createCell(cellNumber++);
//
//                if ( element != null )
//                {
//                    if ( i == 1 )
//                    {
//                        double d = Double.parseDouble(element[i]);
//                        d = d * scale; // przeskalowanie
//                        cell.setCellValue(d);
//                    } else
//                    {
//                        cell.setCellValue(element[i]);
//                    }
//                }
//            }
//        }
//
//        try
//        {
//			FileOutputStream plikXLSX = new FileOutputStream( new File(
//					"test.xls" ) );
//			workbook.write( plikXLSX );
//			plikXLSX.close();
//
//        } catch ( Exception e )
//        {
//
//        }

//    }
    
//    public ArrayList<String[]> ManufactureWeightDataToDouble( ArrayList<String[]> data )
//    {
//        ArrayList<byte[]> eachLineToByte = new ArrayList<byte[]>();
//        ArrayList<String[]> manufacturedData = new ArrayList<String[]>();
//        String tempix = "";
//        
//        for ( String[] element : data)
//        {
//            byte[] temp = {element[0].getBytes(),element[1].getBytes()};
//            eachLineToByte.add(temp);
//            
//        }
//
//        for ( String[] element : data )
//        {
//           
//            String[] toManufactureTab ={null, null};
//            tempix = "";
//            toManufactureTab[0] = element[0];
//            eachLineToByte.add(element[1].getBytes());
//            
//        }
//        
//        for ( byte[] elements : eachLineToByte )
//            {
//                for ( int i = 1; i < 7; i++ )
//                {
//                    tempix += (char) elements[i];
//                }
//                toManufactureTab[1] = tempix;
//                break;
//            }
//            
//            manufacturedData.add(toManufactureTab);
//
//        return manufacturedData;
//    }

}
