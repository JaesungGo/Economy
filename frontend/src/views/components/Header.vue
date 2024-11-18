<template>
  <div class="tree-progress">
    <!-- 사용자 단계 정보 -->
    <div class="stage-info">
      <h2>{{ userData.username }}님은</h2>
      <h1 class="stage-title">“{{ userData.level }}” 단계 입니다!</h1>
      <p>함께 <strong>열대우림</strong>을 만들어 봐요</p>
    </div>

    <!-- 나무 이미지 -->
    <div class="tree-image-container">
      <img :src="treeImage" alt="Tree Stage" class="tree-image" />>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TreeProgress',
  data() {
    return {
      userData: {
        username: 'OO', // 사용자 이름
        level: '', // 현재 단계
      },
      treeImages: {
        새싹: 'path/to/sprout.png', // 새싹 이미지 경로
        '어린 나무': 'path/to/young-tree.png', // 어린 나무 이미지 경로
        '작은 숲': 'path/to/small-forest.png', // 작은 숲 이미지 경로
        '울창한 숲': 'path/to/dense-forest.png', // 울창한 숲 이미지 경로
        열대우림: 'path/to/rainforest.png', // 열대우림 이미지 경로
      },
    };
  },
  computed: {
    // 현재 단계에 맞는 이미지 반환
    treeImage() {
      return this.treeImages[this.userData.level] || 'path/to/default-tree.png';
    },
  },
  methods: {
    async fetchUserData() {
      try {
        // API 호출
        const response = await fetch('');
        const data = await response.json();

        // 데이터 업데이트
        this.userData = {
          username: data.username,
          level: data.level,
        };
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    },
  },
  mounted() {
    this.fetchUserData(); // 컴포넌트 로드시 데이터 가져오기
  },
};
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
