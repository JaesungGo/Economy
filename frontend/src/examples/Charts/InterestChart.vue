<script setup>
import { ref, onMounted } from 'vue';
import dailyInterestApi from '@/api/dailyInterestApi';
import Chart from 'chart.js/auto';

const chartData = ref(null);
const interestRateDifference = ref(null); // 이익률 차이를 저장할 변수
const interestRateClass = ref(''); // 스타일 클래스를 저장할 변수
let chart = null;

const formatMonth = (dateStr) => {
  const [year, month] = dateStr.split('-');
  return `${year.slice(2)}-${month}`; // YY-MM 형식
};

const initChart = (data) => {
  const ctx = document.getElementById('monthlyInterestChart').getContext('2d');
  
  // 기존 차트 제거
  if (chart) {
    chart.destroy();
  }

  // 그라데이션 설정
  const gradient = ctx.createLinearGradient(0, 230, 0, 50);
  gradient.addColorStop(1, 'rgba(203, 12, 159, 0.2)');
  gradient.addColorStop(0.2, 'rgba(72, 72, 176, 0.0)');
  gradient.addColorStop(0, 'rgba(203, 12, 159, 0)');

  chart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: data.map(item => formatMonth(item.todayDate.substring(0, 7))),
      datasets: [
        {
          label: '월 이자',
          data: data.map(item => item.monthlyInterest),
          borderColor: '#4CAF50',
          backgroundColor: gradient,
          tension: 0.4,
          borderWidth: 2, // 선 굵기 조정 (얇게)
          fill: true,
          pointRadius: 4,
          pointBackgroundColor: '#4CAF50',
          pointBorderColor: '#fff',
          pointBorderWidth: 2
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          callbacks: {
            label: (context) => `${context.parsed.y.toLocaleString()}원`
          }
        }
      },
      scales: {
        y: {
          grid: {
            drawBorder: false,
            display: true,
            drawOnChartArea: true,
            drawTicks: false,
            borderDash: [5, 5]
          },
          ticks: {
            callback: (value) => `${value.toLocaleString()}원`,
            padding: 10,
            color: '#9ca3af'
          }
        },
        x: {
          grid: {
            drawBorder: false,
            display: false
          },
          ticks: {
            color: '#9ca3af',
            padding: 10
          }
        }
      }
    }
  });
};

const loadChartData = async () => {
  try {
    const monthlyData = await dailyInterestApi.getMonthly();
    // 최근 날짜 기준 정렬 후 12개월 데이터만 유지
    const sortedData = monthlyData
      .sort((a, b) => new Date(b.todayDate) - new Date(a.todayDate))
      .slice(0, 12)
      .reverse(); // 최근 12개월 데이터만 사용 후 순서 반전 (과거 -> 최근)

    chartData.value = sortedData;
    initChart(chartData.value);

    // 최근 두 달의 이익률(%) 차이 계산
    if (chartData.value.length >= 2) {
      const latestMonth = chartData.value[chartData.value.length - 1];
      const previousMonth = chartData.value[chartData.value.length - 2];
      const percentageDifference =
        ((latestMonth.monthlyInterest - previousMonth.monthlyInterest) / previousMonth.monthlyInterest) * 100;

      interestRateDifference.value = percentageDifference.toFixed(1); // 소수점 첫째 자리

      // 수익률 차이에 따른 색상 클래스 설정
      interestRateClass.value = percentageDifference >= 0 ? 'text-red-500' : 'text-blue-500';
    }
  } catch (error) {
    console.error('월별 이자 데이터 로딩 실패:', error);
  }
};

onMounted(() => {
  loadChartData();
});
</script>

<template>
  <div class="card z-index-2 h-full">
    <div class="card-header pb-0">
      <h6 class="mb-2">월별 이자 추이</h6>
      <p class="text-sm text-gray-600" style="margin-bottom: 0px">
        전 달 대비 이자율 차 :&nbsp; 
        <span :class="interestRateClass" class="font-weight-bold">{{ interestRateDifference }}%</span>
      </p>
    </div>
    <div class="card-body p-3">
      <div class="chart h-[300px]">
        <canvas id="monthlyInterestChart"></canvas>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  background: white;
  border-radius: 1rem;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.card-header {
  padding: 1.5rem;
}

h6 {
  font-size: 1rem;
  font-weight: 600;
  color: #1f2937;
}

.card-body {
  padding: 1.5rem;
}

.chart {
  height: 300px;
}

.text-red-500 {
  color: red;
}

.text-blue-500 {
  color: blue;
}
</style>