package com.example.back.service;

import com.example.back.dto.AirData;
import com.example.back.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataMapper mapper;

    // 환경변수(.env)에서 API 키 가져오기
    @Value("${API_KEY}")
    private String API_KEY;
    private final String API_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";

    // 데이터 가져오기 및 저장
    public List<AirData> fetchAndSave(String station) {
        try {
            URI url = makeUrl(station);
            String response = fetchApi(url);
            List<AirData> list = parse(response);

            // 데이터가 있으면 DB 갱신
            if (!list.isEmpty()) {
                mapper.clear();
                for (AirData data : list) {
                    mapper.save(data);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // API URL 생성
    private URI makeUrl(String station) {
        return UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("serviceKey", API_KEY)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", "24")
                .queryParam("pageNo", "1")
                .queryParam("dataTerm", "DAILY")
                .queryParam("ver", "1.3")
                .queryParam("stationName", station)
                .build().encode().toUri();
    }

    private String fetchApi(URI uri) {
        return new RestTemplate().getForObject(uri, String.class);
    }

    // JSON 파싱
    private List<AirData> parse(String jsonString) {
        List<AirData> list = new ArrayList<>();
        try {
            JSONObject body = new JSONObject(jsonString).getJSONObject("response").getJSONObject("body");
            JSONArray items = body.optJSONArray("items");

            if (items == null) return list;

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                AirData data = new AirData();
                
                data.setStation(item.optString("stationName"));
                data.setTime(item.optString("dataTime"));
                data.setPm10(toInt(item, "pm10Value"));
                data.setPm25(toInt(item, "pm25Value"));
                
                list.add(data);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 문자열을 정수로 변환, 비어있거나 "-"인 경우 0 반환
    private int toInt(JSONObject item, String key) {
        String val = item.optString(key, "0").trim();
        return (val.equals("-") || val.isEmpty()) ? 0 : Integer.parseInt(val);
    }
}