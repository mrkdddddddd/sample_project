import { useState, useEffect } from 'react';
import { fetchAirQualityData } from '../api/airService';

export const useAirQuality = (stationName) => {
  const [chartData, setChartData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const init = async () => {
      try {
        // 데이터 로딩 시작
        setLoading(true);
        
        // API에서 데이터 가져오기
        const rawData = await fetchAirQualityData(stationName);
        
        // 시간순 정렬
        rawData.sort((a, b) => a.time.localeCompare(b.time));

        // 차트 데이터 가공
        const processedData = processChartData(rawData);
        
        // 차트 데이터 설정
        setChartData(processedData);

        // 알림 체크
        checkAlert(rawData);

      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    if (Notification.permission !== "granted") {
      Notification.requestPermission();
    }
    
    init();
  }, [stationName]);

  return { chartData, loading, error };
};

// 차트 데이터 가공
const processChartData = (list) => ({
  labels: list.map(d => d.time.slice(11, 16)), // HH:mm
  datasets: [
    {
      label: '미세먼지 (PM10)',
      data: list.map(d => d.pm10),
      borderColor: 'rgb(53, 162, 235)',
      backgroundColor: 'rgba(53, 162, 235, 0.5)',
      tension: 0.3, 
    },
    {
      label: '초미세먼지 (PM2.5)',
      data: list.map(d => d.pm25),
      borderColor: 'rgb(255, 99, 132)',
      backgroundColor: 'rgba(255, 99, 132, 0.5)',
      tension: 0.3,
    }
  ]
});

// 알림 체크
const checkAlert = (list) => {
  if (list.length === 0) return;
  const last = list[list.length - 1];

  if (last.pm10 > 80 || last.pm25 > 35) {
    const msg = `경고! 미세먼지 수치가 높습니다. (PM10: ${last.pm10}, PM2.5: ${last.pm25})`;
    
    if (Notification.permission === "granted") {
      new Notification("대기질 경고", { body: msg });
    } else {
      alert(msg);
    }
  }
};