package com.springapp.mvc.web.service;

import com.alibaba.fastjson.JSONObject;
import com.springapp.mvc.web.daoLike.DeviceDAO;
import com.springapp.mvc.web.model.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyp on 2015/12/10.
 * 业务逻辑层
 */
@Service
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private static final String uri4AdvsSearch = "http://10.10.2.143:8083/se/search/advanced?q={q}";
    private final DeviceDAO deviceDAO;

    @Autowired
    public DeviceService(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    private boolean isValidSearchCriteriaOld(SearchCriteria search) {
        boolean valid = true;
        if (search == null) {
            valid = false;
        }
        if (StringUtils.isEmpty(search.getWd()) && StringUtils.isEmpty(search.getTypefilter())) {
            valid = false;
        }
        return valid;
    }

    //////
    //返回用户查询的数据，用于前端以列表的形式显示设备信息（业务逻辑层）
    public String getResponse4List(SearchCriteria search) {
        logger.debug("DeviceService.getResponse4List() ================");
//        System.out.println("DeviceService.getResponse4List() ================");
        JSONObject result;
        if (isValidSearchCriteriaOld(search)) {
            Map<String, Object> criteria = new HashMap<String, Object>();
            criteria.put("wd", search.getWd());
            criteria.put("page", search.getPage());
            result = deviceDAO.getDevices4List(criteria);
            if ("200".equals(result.getString("statuscode")) && result.getJSONArray("data").size() <= 0) {
                result.put("statuscode", "204");
                result.put("errmsg", "No related data!");
            }
        } else {
            result = new JSONObject();
            result.put("statuscode", "400");
            result.put("errmsg", "Search criteria is empty!");
        }
        return result.toString();
    }

    //返回用户查询的数据，用于前端3d地球显示设备信息（业务逻辑层）
    public String getResponse4Globe(SearchCriteria search) {
        logger.debug("Service ==>> getResponse4Globe starts ================");
//        System.out.println("Service ==>> getResponse4Globe starts-------");
        JSONObject result;
        if (isValidSearchCriteriaOld(search)) {
            result = deviceDAO.getDevices4Globe(JSONObject.toJSON(search).toString());
            if (result.getJSONObject("data").isEmpty()) {
                result.put("statuscode", "204");
                result.put("errmsg", "No related data!");
            }
        } else {
            result = new JSONObject();
            result.put("statuscode", "400");
            result.put("errmsg", "Search criteria is empty!");
        }
//        System.out.println("Service getDevices4Globe--> Result: " + result);
        return result.toString();
    }

    //返回用户查询的数据，用于前端地图显示设备信息（业务逻辑层）
    public String getResponse4Map(SearchCriteria search) {
        logger.debug("Service ==>> getResponse4Map starts ================");
//        System.out.println("Service ==>> getResponse4Map starts: " + JSONObject.fromObject(search));
        JSONObject result;
        if (isValidSearchCriteriaOld(search)) {
            result = deviceDAO.getDevices4Map(search.toMap());
//            System.out.println("Service getResponse4Map -> " + result);
            if (result.getJSONArray("data").size() <= 0) {
                result.put("statuscode", "204");
                result.put("errmsg", "No related data!");
            }
        } else {
            result = new JSONObject();
            result.put("statuscode", "400");
            result.put("errmsg", "Search criteria is empty!");
        }
        return result.toString();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //返回用户查询的数据，用于前端以列表的形式显示设备信息（高级搜索）
/*    public String getResponse4AdvanceSearch(NewAdvanceSearchCriteria search) {
        logger.debug("Service ==>> getResponse4AdvanceSearch starts ================");
        JSONObject result;
        if (isValidSearchCriteria(search)) {
            Map<String, Object> criteria = new HashMap<String, Object>();
            criteria.put("q", JSON.toJSON(search));
            result = deviceDAO.getData4CommonSearch(uri4AdvsSearch, criteria);
            if ("200".equals(result.getString("statuscode")) && result.getJSONArray("data").size() <= 0) {
                result.put("statuscode", "204");
                result.put("errmsg", "No related data!");
            }
        } else {
            result = new JSONObject();
            result.put("statuscode", "400");
            result.put("errmsg", "Search criteria is empty!");
        }
//        System.out.println("Service Advanced Search--> Result: " + result.toString());
        return result.toString();
    }

    private boolean isValidSearchCriteria(NewAdvanceSearchCriteria criteria) {
        boolean valid = true;
        if (criteria == null) {
            valid = false;
        } else {
            if ("".equals(criteria.toString())) {
                valid = false;
            }
        }
        return valid;
    }*/
}
