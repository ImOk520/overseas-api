package com.iflytek.fpva.overseasapi.controller;

import com.iflytek.fpva.overseasapi.request.QueryRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 海外同传数据接口
 * Feng, Ge 2020-10-10 22:34
 */
@RestController
@Slf4j
@RequestMapping("/overseas")
public class OverseasController {

    @Value("${website}")
    private String website;

    @Value("${app_key}")
    private String app_key;

    @Value("${token}")
    private String token;

    private final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("海外同传分页列表")
    @GetMapping("/list")
    public String getOverseasInfoPageList(QueryRequest query) {
        if(StringUtils.isEmpty(query.getKeywords())){
            query.setKeywords(null);
        }
        String timestamp = df.format(new Date());
        String signStr = "app_key" + app_key
                + "country_type" + query.getCountry_type()
                + "keywords" + query.getKeywords()
                + "page" + query.getPage()
                + "page_size" + query.getPage_size()
                + "timestamp" + timestamp + token;
        String sign = DigestUtils.md5DigestAsHex(signStr.getBytes()).toUpperCase();
        String interface_name = "api/overseas_info/search_overseas_info";
        String url = website + interface_name + "?app_key=" + app_key
                + "&country_type=" + query.getCountry_type()
                + "&keywords=" + query.getKeywords()
                + "&page=" + query.getPage()
                + "&page_size=" + query.getPage_size()
                + "&timestamp=" + timestamp
                + "&sign=" + sign;

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return result.getBody();
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    public String getOverseasInfoDetail(String gdcode) {
        String timestamp = df.format(new Date());
        String interface_name = "api/overseas_info/overseas_info_details";
        String signStr = "app_key" + app_key + "gdcode" + gdcode + "timestamp" + timestamp + token;
        String sign = DigestUtils.md5DigestAsHex(signStr.getBytes()).toUpperCase();
        String url = website + interface_name + "?app_key=kedaxunfei&timestamp=" + timestamp+ "&gdcode=" + gdcode + "&sign=" + sign;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

}
