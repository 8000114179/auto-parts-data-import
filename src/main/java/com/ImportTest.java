package com;

import com.entity.Brand;
import com.entity.Model;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.sinocontact.dbutils.DBControl;
import com.sinocontact.dbutils.operator.DbOperator;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 *
 * @author Max
 * @since 2018/4/23
 */
public class ImportTest {

    private static String reg = "[^a-zA-Z,]";

    public static List<Brand> getListBrand(){
        List<List<String>> tmpList = ExcelUtils.readExcel("https://shoutao-sh.oss-cn-hangzhou.aliyuncs.com/aaa.xlsx");
        Map<String,Map<String,Object>> brands = new HashMap<>();
        Map<String,Map<String,Long>> origins = new HashMap<>();
        Map<String,Map<String,Object>> carmakers = new HashMap<>();
        Map<String,Map<String,Object>> series = new HashMap<>();
        Map<String,Map<String,Object>> models = new HashMap<>();
        Map<String,Object> temp = new HashMap<>();
        if (tmpList!=null && tmpList.size()>0){
//====================================品牌=======================================
            for(List<String> list : tmpList){
                if(!brands.containsKey(list.get(3))){
                    temp = new HashMap<>();
                    temp.put("parent_id",null);
                    temp.put("name",list.get(3));
                    brands.put(list.get(3),temp);
                }
            }
            insert(brands,1,"BRAND");

//=====================================厂商======================================
            for(List<String> list : tmpList){
                if(StringUtils.isNotEmpty(list.get(3)) && !carmakers.containsKey(list.get(1)+list.get(3))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(1));
                    temp.put("parent_id",brands.get(list.get(3)).get("id"));
                    carmakers.put(list.get(1)+list.get(3),temp);
                }
            }
            insert(carmakers,2,"CARMAKER");

//======================================车系=====================================
            for(List<String> list : tmpList){
                if(StringUtils.isNotEmpty(list.get(4)) && !series.containsKey(list.get(1)+list.get(3)+list.get(4))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(4));
                    temp.put("parent_id",carmakers.get(list.get(1)+list.get(3)).get("id"));
                    series.put(list.get(1)+list.get(3)+list.get(4),temp);
                }
            }
            insert(series,3,"SERIES");

//=======================================车型====================================
            for(List<String> list : tmpList){
                if(StringUtils.isNotEmpty(list.get(6)) && !models.containsKey(list.get(1)+list.get(3)+list.get(4)+list.get(6))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(6));
                    temp.put("parent_id",series.get(list.get(1)+list.get(3)+list.get(4)).get("id"));
                    models.put(list.get(1)+list.get(3)+list.get(4)+list.get(6),temp);
                }
            }
            insert(models,4,"MODEL");

//========================================车身、驱动=================================
            Map<String,Map<String,Object>> bodyMap = new HashMap<>();
            Map<String,Map<String,Object>> driveMap = new HashMap<>();

            Map<String,Map<String,Object>> originMap = new HashMap<>();
            Map<String,Map<String,Object>> replacementMap = new HashMap<>();
            Map<String,Map<String,Object>> carTypeMap = new HashMap<>();
            Map<String,Map<String,Object>> frontTyre = new HashMap<>();
            Map<String,Map<String,Object>> rearTyre = new HashMap<>();

            for(List<String> list : tmpList){
                if(StringUtils.isNotEmpty(list.get(17)) && !bodyMap.containsKey(list.get(17))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(17));
                    temp.put("parent_id",null);
                    bodyMap.put(list.get(17),temp);
                }
                if(StringUtils.isNotEmpty(list.get(16)) && !driveMap.containsKey(list.get(16))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(16));
                    temp.put("parent_id",null);
                    driveMap.put(list.get(16),temp);
                }
                if(StringUtils.isNotEmpty(list.get(2)) && !originMap.containsKey(list.get(2))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(2));
                    temp.put("parent_id",null);
                    originMap.put(list.get(2),temp);
                }

                if(StringUtils.isNotEmpty(list.get(8)) && !replacementMap.containsKey(list.get(8))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(8));
                    temp.put("parent_id",null);
                    replacementMap.put(list.get(8),temp);
                }

                if(StringUtils.isNotEmpty(list.get(10)) && !carTypeMap.containsKey(list.get(10))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(10));
                    temp.put("parent_id",null);
                    carTypeMap.put(list.get(10),temp);
                }

                if(StringUtils.isNotEmpty(list.get(18)) && !frontTyre.containsKey(list.get(18))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(18));
                    temp.put("parent_id",null);
                    frontTyre.put(list.get(18),temp);
                }

                if(StringUtils.isNotEmpty(list.get(19)) && !rearTyre.containsKey(list.get(19))){
                    temp = new HashMap<>();
                    temp.put("name",list.get(19));
                    temp.put("parent_id",null);
                    rearTyre.put(list.get(19),temp);
                }
            }
            insert(bodyMap,0,"BODY");
            insert(driveMap,0,"DRIVE");
            insert(originMap,0,"ORIGIN");
            insert(replacementMap,0,"GENERATION");
            insert(carTypeMap,0,"CAR_TYPE");
            insert(frontTyre,0,"FRONT_TYRE");
            insert(rearTyre,0,"REAR_TYRE");
////========================================列表=================================
            List<Model> modelList = new ArrayList<>();
            Model model = null;

            for(List<String> list : tmpList){
                model = new Model();
                model.setVehicle_type_number(list.get(0));
                model.setSales_name(list.get(7));
                model.setBrand_id(list.get(3)==null?null: (Long)brands.get(list.get(3)).get("id"));
                model.setCarmarker_id(StringUtils.isEmpty(list.get(1))?null:(Long)carmakers.get(list.get(1)+list.get(3)).get("id"));
                model.setSeries_id(StringUtils.isEmpty(list.get(4))?null:(Long)series.get(list.get(1)+list.get(3)+list.get(4)).get("id"));
                model.setTag_id(StringUtils.isEmpty(list.get(6))?null:(Long)models.get(list.get(1)+list.get(3)+list.get(4)+list.get(6)).get("id"));
                model.setDrive_type_id(StringUtils.isEmpty(list.get(16))?null:driveMap.get(list.get(16))==null?null:(Long)driveMap.get(list.get(16)).get("id"));
                model.setOrigin_type_id(StringUtils.isEmpty(list.get(2))?null:originMap.get(list.get(2))==null?null:(Long)originMap.get(list.get(2)).get("id"));
                model.setCar_type_id(StringUtils.isEmpty(list.get(10))?null:carTypeMap.get(list.get(10))==null?null:(Long)carTypeMap.get(list.get(10)).get("id"));
                model.setFront_tyre_type_id(StringUtils.isEmpty(list.get(18))?null:frontTyre.get(list.get(18))==null?null:(Long)frontTyre.get(list.get(18)).get("id"));
                model.setRear_tyre_type_id(StringUtils.isEmpty(list.get(19))?null:rearTyre.get(list.get(19))==null?null:(Long)rearTyre.get(list.get(19)).get("id"));
                model.setBody_type_id(StringUtils.isEmpty(list.get(17))?null:bodyMap.get(list.get(17))==null?null:(Long)bodyMap.get(list.get(17)).get("id"));
                model.setDisplacement(StringUtils.isEmpty(list.get(14))?null:list.get(14));
                model.setGeneration_number(StringUtils.isEmpty(list.get(8))?null:list.get(8));
                model.setEngine_number(StringUtils.isEmpty(list.get(13))?null:list.get(13));
                model.setChassis_number(StringUtils.isEmpty(list.get(9))?null:list.get(9));
                model.setMax_power(StringUtils.isEmpty(list.get(15))?null:list.get(15));
                model.setProductive_year(StringUtils.isEmpty(list.get(11))?null:list.get(11));
                model.setDiscontinued_year(list.get(12).equals("0")?null:list.get(12));
                model.setSales_year(list.get(5).equals("0")?null:list.get(5));
                model.setSearch_words(getSearchWords(list.get(3),list.get(1),list.get(4),list.get(6),list.get(7)));
                model.setStatus(1);
                modelList.add(model);
            }
            insertModel(modelList);
        }
        return null;
    }


    private static void insert(Map<String,Map<String,Object>> map,Integer level,String type){
        DbOperator dbOp = DBControl.getTransactionMainDbOperator();
        try{
            String sql = "insert into vehicle_type_tag(level,type,parent_id,name,`order`) values(?,?,?,?,?)";
            for(String key : map.keySet()){
                map.get(key).put("id",dbOp.insert(sql,new ScalarHandler<Long>(),level,type,map.get(key).get("parent_id")
                        ,map.get(key).get("name"),getFirstSpell(map.get(key).get("name").toString())));
            }
            dbOp.commitAndClose();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void insertModel(List<Model> modelList){
        DbOperator dbOperator = DBControl.getTransactionMainDbOperator();
        try{
            String sql = "insert into vehicle_type (vehicle_type_number,sales_name,brand_id,carmarker_id,series_id,tag_id,drive_type_id" +
                    ",origin_type_id,car_type_id,front_tyre_type_id,rear_tyre_type_id,body_type_id,displacement,generation_number,engine_number" +
                    ",chassis_number,max_power,productive_year,discontinued_year,sales_year,search_words,create_time) " +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now()) ";
            for(Model model : modelList){
                dbOperator.update(sql,model.getVehicle_type_number(),model.getSales_name(),model.getBrand_id(),model.getCarmarker_id(),model.getSeries_id()
                        ,model.getTag_id(),model.getDrive_type_id(),model.getOrigin_type_id(),model.getCar_type_id(),model.getFront_tyre_type_id(),model.getRear_tyre_type_id()
                        ,model.getBody_type_id(),model.getDisplacement(),model.getGeneration_number(),model.getEngine_number(),model.getChassis_number()
                        ,model.getMax_power(),model.getProductive_year(),model.getDiscontinued_year(),model.getSales_year(),model.getSearch_words());
            }
            dbOperator.commitAndClose();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    private static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char anArr : arr) {
            if (anArr > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(anArr, defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(anArr);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    private static String getWords(String str){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        StringBuilder stringBuilder = new StringBuilder();
        for(String word : segmenter.sentenceProcess(str)){
            stringBuilder.append(word).append(",");
        }
        return stringBuilder.toString();
    }

    public static String getSearchWords(String brand,String carmaker,String series,String model,String saleName){
        StringBuilder words = new StringBuilder();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        for(String word : segmenter.sentenceProcess(brand)){
            if(StringUtils.isNotEmpty(word)){
                words.append(word).append(",");
            }
        }

        for(String word : segmenter.sentenceProcess(carmaker)){
            if(StringUtils.isNotEmpty(word)){
                words.append(word).append(",");
            }
        }

        for(String word : segmenter.sentenceProcess(series)){
            if(StringUtils.isNotEmpty(word)){
                words.append(word).append(",");
            }
        }

        for(String word : segmenter.sentenceProcess(model)){
            if(StringUtils.isNotEmpty(word)){
                words.append(word).append(",");
            }
        }

        for(String word : segmenter.sentenceProcess(saleName)){
            if(StringUtils.isNotEmpty(word)){
                words.append(word).append(",");
            }
        }

        words.append(getFirstSpellSmall(brand)).append(getPinyin(brand));
        words.append(getFirstSpellSmall(carmaker)).append(getPinyin(carmaker));
        words.append(getFirstSpellSmall(series)).append(getPinyin(series));
        words.append(getFirstSpellSmall(model)).append(getPinyin(model));

        return words.toString();
    }

    private static String getPinyin(String input) {
        StringBuilder pinyin = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            char c = input.charAt(i);
            String[] pinyinArray = null;
            try {
                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (pinyinArray != null) {
                pinyin.append(pinyinArray[0]).append(",");
            } else if (c != ' ') {
                pinyin.append(input.charAt(i));
            }
        }
        return pinyin.toString().trim().toLowerCase().replaceAll(reg,"");
    }

    private static String getFirstSpellSmall(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char anArr : arr) {
            if (anArr > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(anArr, defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0)).append(",");
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(anArr);
            }
        }
        return pybf.append(pybf.toString().replaceAll("\\W", "").trim()).append(",").toString().replaceAll(reg,"");
    }


    public static void main(String[] args) {
        getListBrand();
//        String str = "3.6 手自一体 改款周年纪念导航版舒适型";
//        System.out.println(getPinyin("300(进口)"));
//        System.out.println(getFirstSpellSmall("大切诺基"));
    }
}
