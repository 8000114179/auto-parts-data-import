package com;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2017/8/22.
 * Excel 解析文件
 */
public class ExcelUtils {
    private static final Logger logger = Logger.getLogger(ExcelUtils.class);
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet;
    private XSSFRow row;
    private int sheetIndex = 0 ;
    private int rowIndex = 0 ;
    private int cellIndex = 0 ;

    public static ExcelUtils me(InputStream s) throws IOException {
        return new ExcelUtils(s);
    }

    public ExcelUtils(InputStream s) throws IOException {
        workbook = new XSSFWorkbook(s);
        workbook.setMissingCellPolicy(Row.RETURN_NULL_AND_BLANK);
        setSheetIndex(0);
    }

    /**
     * 读取自定义读取方式数据
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<ArrayList<String>> readCoustom() throws FileNotFoundException, IOException {
        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
        for (int i = rowIndex ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);
            for (int j = cellIndex; j < row.getPhysicalNumberOfCells(); j++) {
                rowArr.add(getStringCellValue(row.getCell(j)));
            }
            excelData.add(rowArr);
        }
        return excelData;
    }
    /**
     * 以title列为标准读取自定义数据
     * @param titleIndex
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<ArrayList<String>> readCoustomByTitle(int titleIndex) throws FileNotFoundException, IOException {
        XSSFRow titleRow = sheet.getRow(titleIndex);
        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
        for (int i = rowIndex ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);
            for (int j = cellIndex; j < titleRow.getPhysicalNumberOfCells(); j++) {
                rowArr.add(getStringCellValue(row.getCell(j)));
            }
            excelData.add(rowArr);
        }
        return excelData;
    }

    /**
     * 读取自定义读取非空数据
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<ArrayList<String>> readCoustomExcludeNull() throws FileNotFoundException, IOException {
        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
        for (int i = rowIndex ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);
            for (int j = cellIndex; j < row.getPhysicalNumberOfCells(); j++) {
                String cellValue = getStringCellValue(row.getCell(j));
                if(!cellValue.equals(""))
                    rowArr.add(cellValue);
                }
            excelData.add(rowArr);
        }
        return excelData;
    }

    /**
     * 读取所有
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<List<String>> readAll() throws FileNotFoundException, IOException {
        List<List<String>> excelData = new ArrayList<List<String>>();
        Integer cellNum = sheet.getRow(0).getPhysicalNumberOfCells();

        for (int i = 0 ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);

            for (int j = 0; j < cellNum; j++) {
                rowArr.add(getStringCellValue(row.getCell(j)));
            }

//            Integer num = row.getPhysicalNumberOfCells();
            Integer tmpNum = 0;
            for (String str : rowArr){
                if(str.equals("")){
                    tmpNum++;
                }
            }
            if(tmpNum<cellNum){
                excelData.add(rowArr);
            }
        }
        return excelData;
    }

    /**
     * 以title行为标准来读其它所有取列值
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<List<String>> readAllByTitle(int titleIndex) throws FileNotFoundException, IOException {
        XSSFRow titleRow = sheet.getRow(titleIndex);
        List<List<String>> excelData = new ArrayList<List<String>>();
        for (int i = 0 ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);
            for (int j = 0; j < titleRow.getPhysicalNumberOfCells(); j++) {
                rowArr.add(getStringCellValue(row.getCell(j)));
            }
            excelData.add(rowArr);
        }
        return excelData;
    }

    /**
     * 读取title行下的所有数据
     * @param titleIndex
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<ArrayList<String>> readNextByTitle(int titleIndex) throws FileNotFoundException, IOException {
        XSSFRow titleRow = sheet.getRow(titleIndex);
        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
        for (int i = titleIndex+1 ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);
            for (int j = 0; j < titleRow.getPhysicalNumberOfCells(); j++) {
                rowArr.add(getStringCellValue(row.getCell(j)));
            }
            excelData.add(rowArr);
        }
        return excelData;
    }

    /**
     * 读取非空数据
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<ArrayList<String>> readAllExcludeNull() throws FileNotFoundException, IOException {
        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
        for (int i = 0 ; i < sheet.getPhysicalNumberOfRows(); i++) {
            ArrayList<String> rowArr = new ArrayList<String>();
            row = sheet.getRow(i);
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                String cellValue = getStringCellValue(row.getCell(j));
                if(!cellValue.equals(""))
                    rowArr.add(cellValue);
            }
            excelData.add(rowArr);
        }
        return excelData;
    }

    /**
     * 读取行的所有列的数据
     * @param rowIndex
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<String> readRowData(int rowIndex) throws FileNotFoundException, IOException {
        ArrayList<String> rowDataArr = new ArrayList<String>();
        row = sheet.getRow(rowIndex);
        for (int i = 0 ; i < row.getPhysicalNumberOfCells(); i++) {
            String cellStr = getStringCellValue(row.getCell(i));
            rowDataArr.add(cellStr);
        }
        return rowDataArr;
    }

    /**
     * 读取行的所有列的非空数据
     * @param rowIndex
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<String> readRowExcludeNullData(int rowIndex) throws FileNotFoundException, IOException {
        ArrayList<String> rowDataArr = new ArrayList<String>();
        row = sheet.getRow(rowIndex);
        for (int i = 0 ; i < row.getPhysicalNumberOfCells(); i++) {
            String cellValue = getStringCellValue(row.getCell(i));
            if(!cellValue.equals(""))
                rowDataArr.add(cellValue);
        }
        return rowDataArr;
    }

    /**
     * 读取读列的数据
     * 空为 ""
     * @param rowIndex
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String readCellData(int rowIndex , int cellIndex) throws FileNotFoundException, IOException {
        row = sheet.getRow(rowIndex);
        return getStringCellValue(row.getCell(cellIndex));
    }

    /**
     * 获取所有图片
     * @return
     */
    public List<XSSFPictureData> readPictures() {
        return workbook.getAllPictures();
    }

    /**
     * 读取单元格内容，支持获取函数内容
     * @param cell
     * @return
     */
    public static String getStringCellValue(Cell cell) {
        String strCell = "";
        if(cell==null) return strCell;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING: // get String data
                strCell = cell.getRichStringCellValue().getString().trim();
                break;
            case Cell.CELL_TYPE_NUMERIC:    // get date or number data
                if (DateUtil.isCellDateFormatted(cell)) {
                    strCell = DateUtils.dateToStr(cell.getDateCellValue(),"yyyy-MM-dd HH:mm:ss");
                } else {
                    BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                    strCell = bd.toString();
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:    // get boolean data
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:    // get expression data
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                evaluator.evaluateFormulaCell(cell);
                CellValue cellValue = evaluator.evaluate(cell);
                strCell = String.valueOf(cellValue.getNumberValue()) ;
                break;
            default:
                strCell = "";
        }
        return strCell;
    }

    /**
     * 设置读取sheet下标，默认为0
     * @param sheetIndex
     * @return
     */
    public ExcelUtils setSheetIndex(int sheetIndex){
        this.sheetIndex = sheetIndex ;
        sheet = workbook.getSheetAt(sheetIndex);
        return this;
    }
    /**
     * 设置读取sheet下标，默认为0
     * @param rowIndex
     * @return
     */
    public ExcelUtils setRowIndex(int rowIndex){
        this.rowIndex = rowIndex ;
        return this;
    }
    /**
     * 设置读取cell下标，默认为0
     * @return
     */
    public ExcelUtils setCellIndex(int cellIndex){
        this.cellIndex = cellIndex ;
        return this;
    }
    /**
     * 获取sheet总数
     * @return
     */
    public int getSheetCount(){
        return this.workbook.getNumberOfSheets();
    }
    /**
     * 根据sheet下标获取sheet名称
     * @param index
     * @return
     */
    public String getSheetNameByIndex(int index){
        return this.workbook.getSheetAt(index).getSheetName();
    }
    public String getCurrentSheetName(){
        return this.workbook.getSheetAt(sheetIndex).getSheetName();
    }
    /**
     * 获取Excel2007图片
     * @param sheetNum 当前sheet编号
     * @param sheet 当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
     */
    public static Map<String, PictureData> getSheetPictrues(int sheetNum,
                                                            XSSFSheet sheet, XSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();

        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = pic.getPreferredSize();
                    CTMarker ctMarker = anchor.getFrom();
                    String picIndex = String.valueOf(sheetNum) + "_"
                            + ctMarker.getRow() + "_" + ctMarker.getCol();
                    sheetIndexPicMap.put(picIndex, pic.getPictureData());
                }
            }
        }

        return sheetIndexPicMap;
    }


    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    /**
     * 读取网络文件
     * @param path
     * @return
     */
    public static List<List<String>> readExcel(String path){
        try{
            InputStream inputStream = getFileInputStream(path);
            List<List<String>> list = ExcelUtils.me(inputStream).setSheetIndex(0).readAll();
//            System.out.println(JSON.toJSONString(list));
            list.remove(0);
            return list;
        }catch (Exception e){
            logger.error("解析Excel文件异常：  Path："+path,e);
        }
        return null;
    }

    private static InputStream getFileInputStream(String path) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            return conn.getInputStream();
        } catch (Exception e) {
            logger.error("读取网络文件异常:"+path);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {

        System.out.println(readExcel("http://cocacola-sh.oss-cn-hangzhou.aliyuncs.com/h5_upload/images/8H4aicrPab.xlsx"));
        // list all demo

//        for(List<String> rowArray : list){
//            for(String string : rowArray){
//                System.out.print(string+"\t");
//            }
//            System.out.println();
//        }
        // read picture demo
        /*List<XSSFPictureData> readPictures = ReadExcel.me(inputStream).readPictures();
        for(XSSFPictureData pictureData:readPictures){
            int type = pictureData.getPictureType();
            if(type == Workbook.PICTURE_TYPE_JPEG){
                File file = new File("d:\\"+System.currentTimeMillis()+".jpg");
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(pictureData.getData());
                outputStream.flush();
                outputStream.close();
            }
        }*/
//        ExcelUtils readExcel = ExcelUtils.me(inputStream).setSheetIndex(0);
//        Map<String, PictureData> dataMap =  readExcel.getSheetPictrues(readExcel.getSheetIndex(),readExcel.getSheet(),readExcel.getWorkbook());
//        for(Map.Entry<String,PictureData> entry : dataMap.entrySet()){
//            System.out.println("key = "+entry.getKey()+" , value = "+entry.getValue());
//        }
    }
}
