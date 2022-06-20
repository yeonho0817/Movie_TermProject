import React from 'react';

import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
  } from 'chart.js';
  import { Bar } from 'react-chartjs-2';
  
  ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
  );

function AgeDistributionChart(props) {
    const {ageChartLabels, ageChartDatas} = props;


    const data = {
      labels: ageChartLabels,
      datasets: [{
        label: '연령별',
        data: ageChartDatas,
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(255, 159, 64, 0.2)',
          'rgba(255, 205, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(201, 203, 207, 0.2)'
        ],
        borderWidth: 1
      }]
    };

    const options={
        // scales: {
        //     yAxes: [{
        //         ticks: { 
        //             min: 0, // 스케일에 대한 최솟갓 설정, 0 부터 시작
        //             stepSize: 1, // 스케일에 대한 사용자 고정 정의 값
        //         }
        //     }]
        // },
    }

    return (
        <>
            <Bar data={data} options={options} height={200} width={300} />
        </>
    );
}

export default AgeDistributionChart;