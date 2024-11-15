<template>
    <div class="min-h-screen bg-gray-100 p-8">
      <div class="max-w-4xl mx-auto bg-white rounded-lg shadow-lg p-6">
        <h1 class="text-3xl font-bold text-center mb-6">금융 레포트</h1>
        
        <!-- 기간 선택 탭 -->
        <div class="flex justify-center mb-6 space-x-2">
          <button 
            v-for="period in periods" 
            :key="period"
            @click="selectedPeriod = period"
            :class="[
              'px-4 py-2 font-medium rounded-md transition',
              selectedPeriod === period 
                ? 'bg-blue-500 text-white' 
                : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
            ]"
          >
            {{ periodLabels[period] }}
          </button>
        </div>
  
        <!-- 차트 -->
        <div class="mb-8 bg-white p-4 rounded-lg shadow">
          <canvas ref="chart"></canvas>
        </div>
  
        <!-- 이자 리스트 -->
        <div class="overflow-x-auto bg-white rounded-lg shadow">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-gray-100">
                <th class="p-3">날짜</th>
                <th class="p-3">이자 금액</th>
                <th class="p-3">계좌 총액</th>
                <th class="p-3">달성 퀘스트</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in filteredData" :key="index" class="border-b">
                <td class="p-3">{{ item.date }}</td>
                <td class="p-3">{{ formatCurrency(item.interest) }}</td>
                <td class="p-3">{{ formatCurrency(item.totalBalance) }}</td>
                <td class="p-3">{{ item.questsCompleted }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, watch, computed } from 'vue';
  import Chart from 'chart.js/auto';
  
  const periods = ['daily', 'weekly', 'monthly'];
  const periodLabels = {
    daily: '일간',
    weekly: '주간',
    monthly: '월간'
  };
  const selectedPeriod = ref('daily');
  const chart = ref(null);
  
  // 예시 데이터 (실제 구현 시 API 호출로 대체)
  const data = ref([
    { date: '2023-06-01', interest: 1000, totalBalance: 100000, questsCompleted: 2 },
    { date: '2023-06-02', interest: 1200, totalBalance: 101200, questsCompleted: 3 },
    { date: '2023-06-03', interest: 950, totalBalance: 102150, questsCompleted: 1 },
    { date: '2023-06-04', interest: 1100, totalBalance: 103250, questsCompleted: 2 },
    { date: '2023-06-05', interest: 1300, totalBalance: 104550, questsCompleted: 4 },
  ]);
  
  const filteredData = computed(() => {
    // 실제 구현 시 선택된 기간에 따라 데이터 필터링
    return data.value;
  });
  
  const formatCurrency = (value) => {
    return new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(value);
  };
  
  const updateChart = () => {
    if (chart.value) {
      chart.value.destroy();
    }
  
    const ctx = document.getElementById('financialChart');
    chart.value = new Chart(ctx, {
      type: 'line',
      data: {
        labels: filteredData.value.map(item => item.date),
        datasets: [
          {
            label: '얻은 이자',
            data: filteredData.value.map(item => item.interest),
            borderColor: 'rgb(255, 99, 132)',
            backgroundColor: 'rgba(255, 99, 132, 0.5)',
          },
          {
            label: '계좌 총액',
            data: filteredData.value.map(item => item.totalBalance),
            borderColor: 'rgb(54, 162, 235)',
            backgroundColor: 'rgba(54, 162, 235, 0.5)',
          },
          {
            label: '달성 퀘스트',
            data: filteredData.value.map(item => item.questsCompleted),
            borderColor: 'rgb(75, 192, 192)',
            backgroundColor: 'rgba(75, 192, 192, 0.5)',
          }
        ]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  };
  
  onMounted(() => {
    updateChart();
  });
  
  watch(selectedPeriod, () => {
    updateChart();
  });
  </script>