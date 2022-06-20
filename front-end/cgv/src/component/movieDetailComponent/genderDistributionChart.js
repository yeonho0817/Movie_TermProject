import React from 'react';
import { Doughnut } from "react-chartjs-2";

import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(ArcElement, Tooltip, Legend, ChartDataLabels);

function GenderDistributionChart(props) {
  const {genderChartLabels, genderChartDatas} = props; 

    
    const data = {
        labels:genderChartLabels,
        datasets: [{
          label: 'My First Dataset',
          data: genderChartDatas,
          backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)'
          ],
          hoverOffset: 4
        }]
      };

    const options = {
        responsive: true,
        rotation: Math.PI * -28.9,
    
        layout: {
          padding: {
            bottom: 10,
          },
        },
    
        plugins: {
          legend: {
            display: true,
            position: "bottom",
    
            labels: {
              font: {
                size: 13,
              },
            },
          },
    
          datalabels: {
            
    
            color: "black",
            font: {
              size: 15,
              weight: 500,
            },
          },
        },
      };

    return (
        <div>
            <Doughnut data={data} options={options} />
        </div>
    );
}

export default GenderDistributionChart;