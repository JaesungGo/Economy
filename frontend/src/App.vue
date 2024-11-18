<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { ref, onMounted } from 'vue';

// 카운트다운 초기 값 설정
const count = ref(100000);
const interest = ref(0);

// 매초마다 카운트다운을 감소시킴
const startCount = () => {
  const interval = setInterval(() => {
    if (count.value > 1) {
      //interest.value += Math.round((count.value * 4 / 100 / (24*60*60)) * 10000) / 10000;
    } else {
      clearInterval(interval);  // 카운트가 1에 도달하면 타이머 종료
    }
  }, 1000);  // 1000ms(1초)마다 실행
};

// 컴포넌트가 마운트될 때 카운트다운 시작
onMounted(() => {
  startCount();
});
</script>

<template>
    <div>
        <h1>{{ interest }}</h1>
    </div>
</template>

<style scoped>
h1 {
  font-size: 3rem;
  text-align: center;
  margin-top: 20%;
}
</style>