import React from 'react';
import {
  Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';

// 차트JS 등록
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

// AirChart 컴포넌트
const AirChart = ({ data }) => {
  const options = {
    responsive: true,
    plugins: {
      legend: { position: 'top' },
      title: { display: true, text: '지난 24시간 추이' },
    },
    interaction: {
      mode: 'index',
      intersect: false,
    },
  };

  return (
    <div style={{ width: '100%', height: '100%' }}>
      <Line options={options} data={data} />
    </div>
  );
};

export default AirChart;