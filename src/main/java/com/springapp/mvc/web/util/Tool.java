package com.springapp.mvc.web.util;

import net.sf.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lyp on 2016-01-22.
 * 工具类
 */
public class Tool {
    private static final String ZH2EN = "{\"阿尔巴尼亚\":\"Albania\",\"阿尔及利亚\":\"Algeria\",\"阿富汗\":\"Afghanistan\",\"阿根廷\":\"Argentina\",\"阿联酋\":\"United Arab Emirates\",\"阿曼\":\"Oman\",\"阿塞拜疆\":\"Azerbaijan\",\"埃及\":\"Egypt\",\"埃塞俄比亚\":\"Ethiopia\",\"爱尔兰\":\"Ireland\",\"爱沙尼亚\":\"Estonia\",\"安道尔\":\"Andorra\",\"安哥拉\":\"Angola\",\"安提瓜和巴布达\":\"Antigua and Barbuda\",\"奥地利\":\"Austria\",\"澳大利亚\":\"Australia\",\"巴巴多斯\":\"Barbados\",\"巴布亚新几内亚\":\"Papua New Guinea\",\"巴哈马\":\"Bahamas, The\",\"巴基斯坦\":\"Pakistan\",\"巴拉圭\":\"Paraguay\",\"巴林\":\"Bahrain\",\"巴拿马\":\"Panama\",\"巴西\":\"Brazil\",\"百慕大\":\"Bermuda\",\"保加利亚\":\"Bulgaria\",\"贝宁\":\"Benin\",\"比利时\":\"Belgium\",\"冰岛\":\"Iceland\",\"波多黎各\":\"Puerto Rico\",\"波兰\":\"Poland\",\"玻利维亚\":\"Bolivia\",\"伯利兹\":\"Belize\",\"博茨瓦纳\":\"Botswana\",\"布隆迪\":\"Burundi\",\"丹麦\":\"Denmark\",\"德国\":\"Germany\",\"多哥\":\"Togo\",\"多米尼加\":\"Dominica\",\"多米尼克\":\"Dominican Republic\",\"俄罗斯\":\"Russia\",\"厄瓜多尔\":\"Ecuador\",\"法国\":\"France\",\"法罗群岛\":\"Faroe Islands\",\"法属波利尼西亚\":\"French Polynesia\",\"菲律宾\":\"Philippines\",\"斐济\":\"Fiji\",\"芬兰\":\"Finland\",\"刚果(金)\":\"Congo\",\"哥伦比亚\":\"Colombia\",\"哥斯达黎加\":\"Costa Rica\",\"格林纳达\":\"Grenada\",\"格陵兰\":\"Greenland\",\"格鲁吉亚\":\"Georgia\",\"古巴\":\"Cuba\",\"瓜德罗普\":\"Guadeloupe\",\"哈萨克斯坦\":\"Kazakhstan\",\"海地\":\"Haiti\",\"韩国\":\"Korea, Peoples Republic of\",\"荷兰\":\"Netherlands\",\"洪都拉斯\":\"Honduras\",\"吉布提\":\"Djibouti\",\"吉尔吉斯斯坦\":\"Kyrgyzstan\",\"加拿大\":\"Canada\",\"加纳\":\"Ghana\",\"加蓬\":\"Gabon\",\"柬埔寨\":\"Cambodia\",\"捷克\":\"Czech Republic\",\"喀麦隆\":\"Cameroon\",\"卡塔尔\":\"Qatar\",\"科特迪瓦\":\"Ivory Coast\",\"科威特\":\"Kuwait\",\"克罗地亚\":\"Croatia\",\"肯尼亚\":\"Kenya\",\"拉脱维亚\":\"Latvia\",\"莱索托\":\"Lesotho\",\"老挝\":\"Laos\",\"黎巴嫩\":\"Lebanon\",\"立陶宛\":\"Lithuania\",\"列支敦士登\":\"Liechtenstein\",\"留尼汪岛\":\"Reunion\",\"卢森堡\":\"Luxembourg\",\"卢旺达\":\"Rwanda\",\"罗马尼亚\":\"Romania\",\"马达加斯加\":\"Madagascar\",\"马恩岛\":\"Isle of Man\",\"马尔代夫\":\"Maldives\",\"马耳他\":\"Malta\",\"马来西亚\":\"Malaysia\",\"马里\":\"Mali\",\"马其顿\":\"Macedonia\",\"马提尼克岛\":\"Martinique\",\"毛里求斯\":\"Mauritius\",\"毛里塔尼亚\":\"Mauritania\",\"美国\":\"United States\",\"美属萨摩亚\":\"Western Samoa\",\"蒙古\":\"Mongolia\",\"孟加拉\":\"Bangladesh\",\"秘鲁\":\"Peru\",\"缅甸\":\"Myanmar (Burma)\",\"摩尔多瓦\":\"Moldova\",\"摩洛哥\":\"Morocco\",\"摩纳哥\":\"Monaco\",\"莫桑比克\":\"Mozambique\",\"墨西哥\":\"Mexico\",\"纳米比亚\":\"Namibia\",\"南非\":\"South Africa\",\"尼泊尔\":\"Nepal\",\"尼加拉瓜\":\"Nicaragua\",\"尼日尔\":\"Niger\",\"尼日利亚\":\"Nigeria\",\"挪威\":\"Norway\",\"葡萄牙\":\"Portugal\",\"日本\":\"Japan\",\"瑞典\":\"Sweden\",\"瑞士\":\"Switzerland\",\"萨尔瓦多\":\"El Salvador\",\"塞内加尔\":\"Senegal\",\"塞浦路斯\":\"Cyprus\",\"塞舌尔\":\"Seychelles\",\"沙特阿拉伯\":\"Saudi Arabia\",\"圣基茨和尼维斯\":\"St. Christopher-Nevis\",\"圣马力诺\":\"San Marino\",\"圣文森特和格林纳丁斯\":\"St. Vincent and the Grenadines\",\"斯里兰卡\":\"Sri Lanka\",\"斯洛伐克\":\"Slovakia\",\"斯洛文尼亚\":\"Slovenia\",\"斯威士兰\":\"Swaziland\",\"苏丹\":\"Sudan\",\"塔吉克斯坦\":\"Tajikistan\",\"泰国\":\"Thailand\",\"坦桑尼亚\":\"Tanzania, United Republic of\",\"特立尼达和多巴哥\":\"Trinidad and Tobago\",\"突尼斯\":\"Tunisia\",\"土耳其\":\"Turkey\",\"瓦努阿图\":\"Vanuatu\",\"危地马拉\":\"Guatemala\",\"委内瑞拉\":\"Venezuela\",\"文莱\":\"Brunei\",\"乌干达\":\"Uganda\",\"乌克兰\":\"Ukraine\",\"乌拉圭\":\"Uruguay\",\"乌兹别克斯坦\":\"Uzbekistan\",\"西班牙\":\"Spain\",\"希腊\":\"Greece\",\"新加坡\":\"Singapore\",\"新喀里多尼亚\":\"New Caledonia\",\"新西兰\":\"New Zealand\",\"匈牙利\":\"Hungary\",\"叙利亚\":\"Syria\",\"牙买加\":\"Jamaica\",\"亚美尼亚\":\"Armenia\",\"也门\":\"Yemen\",\"伊拉克\":\"Iraq\",\"伊朗\":\"Iran\",\"以色列\":\"Israel\",\"意大利\":\"Italy\",\"印度\":\"India\",\"印度尼西亚\":\"Indonesia\",\"英国\":\"United Kingdom\",\"约旦\":\"Jordan\",\"越南\":\"Vietnam\",\"赞比亚\":\"Zambia\",\"智利\":\"Chile\",\"中国\":\"China\"}";

    public static JSONObject getCountryMapping() {
        return JSONObject.fromObject(ZH2EN);
    }

    public static String getCountryMappingStr() {
        return ZH2EN;
    }

    //排序算法：
    public static List<JSONObject> solrBucketList(List<JSONObject> list) {//对list按照list.get(x).getDocCount()降序排列，用于处理搜索聚类
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject b1, JSONObject b2) {
                return (int) (b2.getInt("count") - b1.getInt("count"));//降序排列
            }
        });
        return list;
    }
}
