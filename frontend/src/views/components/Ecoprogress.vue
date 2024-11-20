<template>
  <div class="tree-progress">
    <!-- 사용자 단계 정보 -->
    <div class="stage-info">
      <h2>{{ myMember.memberName }}님은 <span style="color: #0B243B;">"{{ userGrade }}"</span> 단계 입니다!</h2>
      <p style="color: dimgray;">함께 <strong>열대우림</strong>을 만들어 봐요 !</p>
      <!-- 나무 이미지 -->
      <div class="tree-image-container">
        <img :src="treeImage" alt="Tree Stage" class="tree-image" style="width: 85px; "/>
      </div>
      
    </div>

    
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import memberApi from '@/api/memberApi';

const memberObject = ref({});
const myMember = computed(() => memberObject.value);

// 사용자 데이터
// const userData = ref({
//   username: 'OO', // 사용자 이름
//   level: '', // 현재 단계
// });

// 이미지 경로 데이터
const treeImages = {
  0: require('@/assets/img/grade0.png'), // 새싹 이미지 경로
  1: require('@/assets/img/grade1.png'), // 어린 나무 이미지 경로
  2: require('@/assets/img/grade2.png'), // 작은 숲 이미지 경로
  3: require('@/assets/img/grade3.png'), // 울창한 숲 이미지 경로
  4: require('@/assets/img/grade4.png'), // 열대우림 이미지 경로
};
// 사용자 드급 데이터
const userGrades = {
  0: '씨앗',
  1: '새싹',
  2: '어린 잎',
  3: '어린 나무',
  4: '풍성한 나무',
};

// 현재 단계에 맞는 이미지 반환
const treeImage = computed(() => treeImages[myMember.value.memberGrade] || 'path/to/default-tree.png');
// 현재 단계에 맞는 등급 반환
const userGrade = computed(() => userGrades[myMember.value.memberGrade] || 0 );

// 사용자 데이터 가져오기
const load = async () => {
  try {
    const memberData = await memberApi.getMember();
    memberObject.value = memberData;

    console.log('memberObject: ', memberObject.value);
    console.log('memberObject.memberName: ', myMember.value.memberName);
    console.log('memberObject.memberGrade: ', myMember.value.memberGrade);
    console.log('treeImage src: ', treeImage);

    
  } catch (error) {
    console.error('Error finding account: ', error);
  }
};

// 컴포넌트 로드시 데이터 가져오기
onMounted(() => {
  load();
});
</script>


<style scoped>
.tree-progress {
  max-width: 600px;
  margin: 0 auto;
  font-family: 'Arial', sans-serif;
  text-align: center; /* 전체 텍스트 중앙 정렬 */
}

.stage-info {
  margin-bottom: 20px;
}

.stage-title {
  font-size: 2rem;
  font-weight: bold;
  color: #4caf50; /* 강조 색상 */
}

.tree-image-container {
  margin: 20px 0;
}

.tree-image {
  width: 200px;
  height: auto;
}

.progress-text {
  font-size: 1.2rem;
  margin-top: 10px;
}

.next-level-info {
  margin-top: 20px;
  font-size: 1rem;
  color: #555;
}
</style>
