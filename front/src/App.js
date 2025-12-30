import React, { useState } from 'react';
import { useAirQuality } from './hooks/useAirQuality';
import AirChart from './components/AirChart';
import './App.css';

function App() {
  // 지역 상태 관리
  const [station, setStation] = useState("종로구");
  const [inputValue, setInputValue] = useState("종로구");

  // 커스텀 훅 사용
  const { chartData, loading, error } = useAirQuality(station);

  // 검색 처리
  const handleSearch = () => {
    if (!inputValue.trim()) {
      alert("지역명을 입력해주세요!");
      return;
    }
    setStation(inputValue);
  };

  // 엔터키 처리
  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div className="app-container">
      
      <header className="header">
        <h1 className="title">실시간 대기질 모니터링</h1>

        <div className="search-bar">
          <input 
            type="text" 
            className="search-input"
            placeholder="지역명 (예: 강남구, 종로구)"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <button className="search-button" onClick={handleSearch}>
            검색
          </button>
        </div>
        
        <div className="current-station">
          현재 조회 지역: <span className="badge">{station}</span>
        </div>
      </header>
      
      <main className="chart-card">
        {loading && <div className="status-message">데이터를 불러오는 중...</div>}
        {error && <div className="status-message">데이터를 가져올 수 없습니다.</div>}
        {!loading && !error && !chartData && <div className="status-message">데이터가 없습니다.</div>}
        
        {!loading && !error && chartData && (
          <AirChart data={chartData} />
        )}
      </main>
      
      <footer className="footer">
        Backend: Spring Boot | Frontend: React
      </footer>

    </div>
  );
}

export default App;