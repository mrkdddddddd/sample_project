package com.example.back.mapper;

import com.example.back.dto.AirData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import java.util.List;

@Mapper
public interface DataMapper {
    // 데이터 저장
    @Insert("INSERT INTO AIR_DATA (station, time, pm10, pm25) VALUES (#{station}, #{time}, #{pm10}, #{pm25})")
    void save(AirData data);

    // 모든 데이터 조회
    @Select("SELECT * FROM AIR_DATA ORDER BY time ASC")
    List<AirData> findAll();

    // 모든 데이터 삭제
    @Delete("DELETE FROM AIR_DATA")
    void clear();
}