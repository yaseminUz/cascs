package com.springapp.mvc.web.dao;/*
 * Created by lyp on 2016-02-29.
 * @author lyp
 * @date 2016-02-29
 * @Description: 设备数据获取
 * @Version: V1.0
 */

import com.springapp.mvc.web.model.NewDevice;
import com.springapp.mvc.web.util.RestClient;
import com.springapp.mvc.web.util.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DeviceDAO {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDAO.class);
    RestClient rc = new RestClient();

    /*
     * @function name: 
     * @param: {arg[0]:"描述",arg[1]:"描述",...}
     * @return: 返回值描述
     * @description: 方法描述
     * @author: lyp
     * @date: 2016-02-29
     */
    //返回用户查询的数据，用于前端以列表的形式显示设备信息（数据访问层）高级搜素
    public JSONObject getResult4DeviceSearch(String uri, Map<String, Object> criteria) {
        logger.debug("NewDAO ==>> getData4CommonSearch starts =================");
//        System.out.println("NewDAO ==>> getData4CommonSearch starts =======================");
        JSONObject result = JSONObject.fromObject(rc.get(uri, criteria));
//        System.out.println("before: " + result);

        if ("200".equals(result.getString("statuscode"))) {
            result = deviceDataConvert(result);
        }
        return result;
    }

    /*
     * @function name:
     * @param: {rawData:"需要转换的原始json数据，是从搜索平台获取到的原始设备数据"}
     * @return: 转换后的设备数据JSONObject。{}
     * @description: 将从搜索平台获取到的原始数据，转换为前端需要的格式，同时对数据做一些预处理，包括特殊字符和空值的处理
     * @author: lyp
     * @date: 2016-02-29
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private JSONObject deviceDataConvert(JSONObject rawData) {
        //aggregation中的country@%city的处理
        JSONObject agg = rawData.getJSONObject("aggregation");
        if (agg.containsKey("country@%city")) {
            JSONObject cc = agg.getJSONObject("country@%city");
            agg.put("country@%city", countryCityConvert(cc));
            rawData.put("aggregation", agg);
        }

        //data
        JSONArray data = rawData.getJSONArray("data");

        if (data.size() > 0) {
            List<NewDevice> devices = new ArrayList<NewDevice>();
            for (Object obj : data) {
                NewDevice device = new NewDevice();
                JSONObject d = JSONObject.fromObject(obj);
                JSONObject desc = d.getJSONObject("description");
                JSONObject device_location = desc.getJSONObject("device_location");
                JSONArray vul_info = desc.getJSONArray("vul_info");
                JSONArray port_info = desc.getJSONArray("port_info");
                JSONObject os_info = desc.getJSONObject("os_info");

                //(1.a)tags
                List<String> tags = new ArrayList<String>();
                //(2)ip
                device.setIp(desc.getString("ip"));
                //(3)lon
                device.setLon(device_location.getDouble("lon"));
                //(4)lat
                device.setLat(device_location.getDouble("lat"));
                //(5)location
                String location = "", country, city, province;

                country = !"".equals(device_location.getString("zh_CN")) ? device_location.getString("zh_CN") : device_location.getString("country");
                province = !"".equals(device_location.getString("zh_Pro")) ? device_location.getString("zh_Pro") : device_location.getString("province");
                city = !"".equals(device_location.getString("zh_City")) ? device_location.getString("zh_City") : device_location.getString("city");
                if (!"".equals(country)) {
                    location += country;
                }
                if (!"".equals(province)) {
                    location += ", " + province;
                }
                if (!"".equals(city)) {
                    location += ", " + city;
                }
                device.setLocation(location);
                //(6)ports
                if (port_info.size() > 0) {
                    List<Map<String, String>> ports = new ArrayList<Map<String, String>>();
                    for (int i = 0; i < port_info.size(); i++) {
                        Map<String, String> port = new HashMap<String, String>();
                        JSONObject item = port_info.getJSONObject(i);
                        String portKey, portValue;
                        portKey = item.getString("protocol") + ": " + item.getString("port");
                        portValue = item.getString("banner");
                        port.put(portKey, portValue);
                        ports.add(port);

                        String type = item.getString("device_type"),
                                brand = item.getString("device_brand"),
                                model = item.getString("device_model");
                        //(1.b)tags.type
                        if (!"".equals(type) && !contains(tags, type)) {
                            tags.add(type);
//                            System.out.println("Type---------" + type);
                        }
                        //(1.c)tags.brand
                        if (!"".equals(brand) && !contains(tags, brand)) {
                            tags.add(brand);
//                            System.out.println("Brand---------" + brand);
                        }
                        //(1.d)tags.model
                        if (!"".equals(model) && !contains(tags, model)) {
                            tags.add(model);
//                            System.out.println("Model---------" + model);
                        }
                    }
                    device.setPorts(ports);
                }
                //(1.e)tags.os
                if (os_info.containsKey("os")) {
                    String os = os_info.getString("os");
                    if (!"".equals(os) && !contains(tags, os)) {
                        tags.add(os_info.getString("os"));
                    }
                }
                //(7)vuls
                if (vul_info.size() > 0) {
                    List<Map<String, NewDevice.VulValueEntity>> vuls = new ArrayList<Map<String, NewDevice.VulValueEntity>>();
                    for (int i = 0; i < vul_info.size(); i++) {
                        Map<String, NewDevice.VulValueEntity> vul = new HashMap<String, NewDevice.VulValueEntity>();
                        JSONObject item = vul_info.getJSONObject(i), vul_ID = item.getJSONObject("vul_ID");
//                        System.out.println("Vul---------" + item);
//                        System.out.println("vul_ID---------" + vul_ID);

                        NewDevice.VulValueEntity vulValue = new NewDevice.VulValueEntity();
                        String vulKey;
                        vulKey = !"".equals(vul_ID.getString("CVE")) ? vul_ID.getString("CVE") : vul_ID.getString("CNVD");
//                        System.out.println("Vul Key--------------" + vulKey);
                        vulValue.setData(item.getJSONObject("data"));
                        //getString("data").replace("\"", "\\\"")
                        vulValue.setDesc(item.getString("description"));
                        vulValue.setPlatform(item.getString("platform"));
                        vulValue.setImgURL(item.getString("get_picture"));
                        vul.put(vulKey, vulValue);
                        vuls.add(vul);
                    }
                    device.setVuls(vuls);
                }

                //(8)lastModified (timestamp)
                device.setTimestamp(d.getString("lastModified"));
                //(1.f)
                device.setTags(tags);
                devices.add(device);
            }
            rawData.put("data", devices);
        }
        return rawData;
    }

    /*
     * @function name:
     * @param: {arg[0]:"描述",arg[1]:"描述",...}
     * @return: 返回值描述
     * @description: 方法描述
     * @author: lyp
     * @date: 2016-02-29
     */
    private JSONObject countryCityConvert(JSONObject rawData) {
        JSONObject zh2en = Tool.getCountryMapping();
        JSONObject countries = new JSONObject();    //用于存储处理后的countries
        JSONObject cc = rawData;
        Iterator<String> it = cc.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            String country, city;
            try {
                String[] keyArr = key.split("@%");
                if (keyArr.length == 2) {   //国家和城市都有值，例如"中国@%北京"
                    country = "".equals(keyArr[0]) ? "Others" : keyArr[0];
                    city = keyArr[1];
                } else if (keyArr.length == 1) {    //只有国家的，例如"中国@%"
                    country = keyArr[0];
                    city = "Unknown";
                } else {//既没有国家也没有城市的，例如"@%"
                    continue;
                }
                if (!countries.containsKey(country)) {
                    JSONObject countryObj = new JSONObject();
                    JSONObject cities = new JSONObject();
                    int initCount = cc.getInt(key);
                    cities.put(city, initCount);
                    countryObj.put("count", initCount);
                    countryObj.put("cities", cities);
                    if (zh2en.has(country)) {
                        countryObj.put("en", zh2en.getString(country));
                    }
                    countries.put(country, countryObj);
                } else {
                    JSONObject tmpCountry = countries.getJSONObject(country);
                    JSONObject tmpCities = tmpCountry.getJSONObject("cities");
                    tmpCities.put(city, cc.getInt(key));
                    tmpCountry.put("count", tmpCountry.getInt("count") + cc.getInt(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
     * @function name: contains
     * @param:{sArr:"字符串数组",s:"待查询的字符串"}
     * @return: {true:"sArr中包含s", false:"sArr中不包含s"}
     * @description: sArr字符串数组中是否包含s。如果包含则返回true，否则返回false
     * @author lyp
     * @date 2016-02-29
     */
    private boolean contains(List<String> sArr, String s) {
        boolean has = false;
        if (sArr != null) {
            for (int i = 0; i < sArr.size(); i++) {
                if (s.equals(sArr.get(i))) {
                    has = true;
                }
            }
        }
        return has;
    }
}
