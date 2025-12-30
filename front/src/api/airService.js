import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/api/air";

// 데이터 가져오기
export const fetchAirQualityData = async (stationName) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/${stationName}`);
    return response.data;
  } catch (error) {
    console.error("API Fetch Error:", error);
    throw error;
  }
};