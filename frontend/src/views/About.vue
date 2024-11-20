<template>
  <div class="green-card-app">
    <!-- 헤더 섹션 -->
    <header class="header">
      <h1 class="main-title">그린카드</h1>
      <p class="subtitle">지구도 살리고 혜택도 받는 그린카드 소개</p>
      <p>친환경 소비생활을 실천할 때마다 에코머니 포인트 제공합니다.</p>
    </header>

    <main>
      <!-- 카드 종류 섹션 -->
      <section class="card-types section">
        <h2>그린카드 종류</h2>
        <div class="cards-grid">
          <div v-for="card in cardTypes" :key="card.name" class="card">
            <h3>{{ card.name }}</h3>
            <ul>
              <li>국내 전가맹점 0.2%~0.8% 적립</li>
              <li>대중교통 최대 20% 적립</li>
              <li>쇼핑 2~3개월 무이자 할부</li>
            </ul>
            <img src="@/assets/img/그린카드v1.png" alt="그린카드" class="card-image"/>
          </div>
        </div>
      </section>

      <!-- 카드 혜택 섹션 -->
      <section class="benefits section">
        <h2>카드 혜택</h2>
        
        <!-- 기본 적립률 -->
        <div class="benefit-box">
          <h3>에코머니 포인트 적립</h3>
          <div class="rate-table">
            <table>
              <thead>
                <tr>
                  <th>전월이용금액</th>
                  <th>적립률</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="rate in basicRates" :key="rate.amount">
                  <td>{{ rate.amount }}</td>
                  <td>{{ rate.rate }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="benefit-notes">
            <h4>기본적립률 안내</h4>
            <ul>
              <li>국내 가맹점 이용금액 0.2% ~ 0.8% 에코머니 포인트 적립</li>
              <li>가장 많이 쓴 2개 업종 5배 (최대 4%) 추가 에코머니 적립 (대형할인점, 백화점, 교육, 병원, 주유소 등)</li>
              <li>업종별 한도 5천점, 최대 1만점 적립</li>
            </ul>
            <h4>적립 제외 대상</h4>
            <ul>
              <li>무이자할부</li>
              <li>제세공과금</li>
              <li>상품권</li>
              <li>대학등록금</li>
              <li>아파트관리비</li>
              <li>온라인업종</li>
              <li>후불교통 이용금액</li>
            </ul>
          </div>
        </div>

        <!-- 대중교통 혜택 -->
        <div class="benefit-box">
          <h3>대중교통 혜택</h3>
          <div class="transport-benefits">
            <div class="transport-table">
              <h4>버스, 지하철 적립률</h4>
              <table>
                <thead>
                  <tr>
                    <th>전월실적</th>
                    <th>적립률</th>
                    <th>적립한도</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="rate in transitRates" :key="rate.amount">
                    <td>{{ rate.amount }}</td>
                    <td>{{ rate.rate }}</td>
                    <td>{{ rate.limit }}</td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="transport-info">
              <h4>KTX, 고속버스</h4>
              <ul>
                <li>전월실적 20만원 이상 시 5% 적립</li>
                <li>일 1회, 월 2회 한정</li>
              </ul>
              
              <h4>이용 조건</h4>
              <ul>
                <li>버스, 지하철: 월 1만원 이상 이용 시 제공 (후불교통 사용처 기준)</li>
                <li>제외 대상: 공항리무진, 공항버스, 고속버스, 시외버스</li>
              </ul>
            </div>
          </div>
        </div>

        <!-- 무이자 할부 혜택 -->
        <div class="benefit-box">
          <h3>쇼핑 2~3개월 무이자 할부</h3>
          <div class="installment-benefits">
            <h4>대상 업종</h4>
            <ul>
              <li>대형할인점</li>
              <li>백화점</li>
              <li>학원</li>
              <li>병원, 의원</li>
            </ul>
            <p class="note">* 대형할인점 온라인몰 제외</p>
            
            <h4>이용 조건</h4>
            <ul>
              <li>전월실적 20만원 이상 시 제공</li>
              <li>월 2회, 월 적립한도 3,000 P</li>
            </ul>
          </div>
        </div>

        <!-- 그린서비스 혜택 -->
        <div class="benefit-box">
          <h3>그린서비스 혜택</h3>
          <ul class="green-benefits">
            <li>친환경제품 구매 시, 에코머니 포인트 적립</li>
            <li>에코머니 제휴 가맹점 이용 시 에코머니 포인트 적립</li>
            <li>전국 지자체 공공시설 할인</li>
            <li>에너지 절감 시 인센티브 제공</li>
          </ul>
        </div>
      </section>

      <!-- 녹색소비 섹션 -->
      <section class="green-store section">
        <h2>녹색소비</h2>
        <p class="section-desc">그린매장에서 친환경 제품 구매하고 에코머니 포인트로 돌려받아요!</p>
        
        <div class="store-categories">
          <div v-for="category in storeCategories" :key="category.title" class="store-category">
            <h3>{{ category.title }}</h3>
            <div class="store-list">
              <span v-for="store in category.stores" :key="store" class="store-tag">
                {{ store }}
              </span>
            </div>
          </div>
        </div>

        <div class="purchase-guide">
          <br>
          <h3>친환경제품 구매절차</h3>
          <div class="steps">
            <div v-for="(step, index) in purchaseSteps" :key="index" class="step">
              <div class="step-number">{{ index + 1 }}</div>
              <p>{{ step }}</p>
            </div>
          </div>
        </div>
      </section>

      <!-- 에너지 절약 섹션 -->
      <section class="energy-saving section">
    <h2>에너지 절약</h2>
    <p class="section-desc">생활 에너지 절약하고 에코머니 포인트로 돌려받아요!</p>

<div class="mt-6">
  <p class="intro-text">탄소포인트제/에코마일리지는 기후위기 대응을 위하여 온실가스를 줄일 수 있도록 가정, 상업, 아파트 단지 등에서 전기, 상수도, 도시가스의 사용량을 절감하고 감축률에 따라 환경부 및 지자체에서 인센티브를 제공하는 국민 참여형 온실가스 감축 실천 프로그램입니다.</p>
  
  <p class="highlight-text">그린카드 발급 후 탄소포인트제/에코마일리지에 가입하시면 에너지 절약 인센티브를 에코머니 포인트로 받을 수 있습니다.</p>

      <div class="tabs-container">
        <div class="flex border-b">
          <button
            class="tab-button"
            :class="{ 'active': activeTab === 'national' }"
            @click="activeTab = 'national'"
          >
            탄소포인트제(전국)
          </button>
          <button
            class="tab-button ml-4"
            :class="{ 'active': activeTab === 'seoul' }"
            @click="activeTab = 'seoul'"
          >
            에코마일리지(서울)
          </button>
        </div>

        <div class="tab-content mt-6" style="margin-top: 0px;">
          <!-- 전국 탭 내용 -->
          <div v-if="activeTab === 'national'" class="national-content">
            <h3 class="content-title">개요</h3>
            <p class="mb-4">환경부에서 운영하는 전국민대상 온실가스 감축 시민 참여 프로그램</p>

            <h3 class="content-title">가입대상</h3>
            <p class="mb-4">전 국민 (서울시민 제외)</p>

            <h3 class="content-title">인센티브 지급</h3>
            <p class="mb-4">가정 내 사용하는 에너지 항목(전기, 상수도, 도시가스)을 과거 1 ~ 2년간 월별 평균 사용량과 현재 사용량을 비교하여 절감비율에 따라 에코머니 포인트 지급 (연간 최대 10만점)</p>

            <h3 class="content-title">가입방법</h3>
            <div class="mb-4">
              <ul>
                <li>오프라인</li>
                <p>탄소포인트제 참여 신청서 작성 후 지방자치단체(전국 시청, 구청 주민센터)에 제출</p>
                <li>온라인</li>
                <p>한국환경공단 탄소포인트제 홈페이지에서 가입</p>
              </ul>
            </div>

            <h3 class="content-title">감축 인센티브</h3>
            <table class="w-full mb-4">
              <thead>
                <tr>
                  <th>감축률</th>
                  <th>전기</th>
                  <th>상수도</th>
                  <th>도시가스</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="point in nationalPoints" :key="point.reduction">
                  <td>{{ point.reduction }}</td>
                  <td>{{ point.electric }}</td>
                  <td>{{ point.water }}</td>
                  <td>{{ point.gas }}</td>
                </tr>
              </tbody>
            </table>

            <h3 class="content-title">유지 인센티브</h3>
            <p class="mb-2">2회 이상 연속으로 5% 이상 감축하여 인센티브를 받은 참여자가 이어서 0% 초과 ~ 5% 미만의 감축률을 유지할 경우 지급</p>
            <table class="w-full mb-4">
              <thead>
                <tr>
                  <th>감축률</th>
                  <th>전기</th>
                  <th>상수도</th>
                  <th>도시가스</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>0% 초과 ~ 5% 미만</td>
                  <td>3,000 P</td>
                  <td>450 P</td>
                  <td>1,800 P</td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- 서울 탭 내용 -->
          <div v-else class="seoul-content">
            <h3 class="content-title">개요</h3>
            <p class="mb-4">서울시에서 운영하는 온실가스 감축 시민 참여 프로그램</p>

            <h3 class="content-title">가입대상</h3>
            <p class="mb-4">서울시민</p>

            <h3 class="content-title">인센티브 지급</h3>
            <p class="mb-4">기준 사용량(최근 2년) 대비 6개월 간 월평균 에너지 항목(전기, 수도, 가스(지역난방 포함) 중 2개 항목 이상의 온실가스를 5% 이상 감축할 경우, 절감비율에 따라 에코머니 포인트 지급 (연간 최대 10만점)</p>

            <h3 class="content-title">가입방법</h3>
            <div class="mb-4">
              <ul>
                <li>오프라인</li>
                <p>'에코마일리지 가입 신청서' 작성 후 거주 지방자치단체(서울시내 시청, 구청, 주민센터)에 제출</p>
                <li>온라인</li>
                <p>서울시 에코마일리지 홈페이지에서 가입</p>
              </ul>
            </div>

            <h3 class="content-title">지급기준</h3>
            <table class="w-full mb-4">
              <thead>
                <tr>
                  <th>감축률</th>
                  <th>포인트</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="rate in seoulRates" :key="rate.reduction">
                  <td>{{ rate.reduction }}</td>
                  <td>{{ rate.points }}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- 공통 안내사항 -->
          <div class="notice-section mt-6 p-4 bg-gray-50 rounded-lg">
            <h3 class="font-bold mb-2">가입 및 제도안내</h3>
            <ul class="list-disc pl-5 space-y-2">
              <li class="font-bold">에너지사용량이 집계되지 않는 일부 지역의 경우, 서비스 이용에 제약이 발생할 수 있습니다.</li>
              <li class="font-bold">그린카드 발급 후, 탄소포인트(에코마일리지) 프로그램 회원가입 및 에너지 수집 정보를 입력하셔야 포인트를 적립 받으실 수 있습니다.</li>
              <li>거주 지역(주민등록상)에 따라 하나의 제도만 가입 가능하며, 가입 방법 및 인센티브 지급 기준 등 자세한 내용은 지역별로 다를 수 있으므로, 해당 홈페이지를 참고해 주시기 바랍니다.</li>
              <li>고객님의 주민등록상 주소지가 변경된 경우, 해당 탄소포인트제 홈페이지에서도 반드시 수정해주시기 바랍니다. 해당 홈페이지에서 수정하지 않으신 경우 에너지절감 인센티브가 지급되지 않을 수 있습니다.</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </section>
    </main>

    <footer class="footer">
    <div class="footer-content">
      <div class="contact-info">
        <span class="phone-icon">📞</span>
        <span class="contact-label">에코머니 전용상담</span>
        <span class="phone-number">1566-2929</span>
      </div>
    </div>
  </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 카드 종류 데이터
const cardTypes = [
{ name: '그린카드 V1', type: '신용/체크' }
]

// 기본 적립률 데이터
const basicRates = [
{ amount: '20만원 이상 ~ 50만원 미만', rate: '0.2%' },
{ amount: '50만원 이상 ~ 100만원 미만', rate: '0.4%' },
{ amount: '100만원 이상 ~ 150만원 미만', rate: '0.6%' },
{ amount: '150만원 이상', rate: '0.8%' }
]

// 대중교통 적립률 데이터
const transitRates = [
{ amount: '20만원 이상 ~ 100만원 미만', rate: '10%', limit: '5,000 P' },
{ amount: '100만원 이상', rate: '20%', limit: '10,000 P' }
]

// 제휴 매장 데이터
const storeCategories = [
{
  title: '편의점',
  stores: ['GS25', '세븐일레븐', 'CU']
},
{
  title: '백화점/아울렛',
  stores: ['갤러리아백화점', '롯데백화점', '롯데아울렛', 'NC백화점', '동아백화점']
},
{
  title: '친환경매장',
  stores: ['올가홀푸드', 'YES24 중고서점', '서울책보고', '굿윌스토어', '두레생협']
}
]

// 구매 절차 데이터
const purchaseSteps = [
'그린매장 방문',
'친환경제품 구매',
'그린카드로 결제',
'포인트 적립 확인',
'에코머니 포인트 적립 완료'
]

const activeTab = ref('national')

const nationalPoints = [
  { reduction: '5% 이상 ~ 10% 미만', electric: '5,000 P', water: '750 P', gas: '3,000P' },
  { reduction: '10% 이상 ~ 15% 미만', electric: '10,000 P', water: '1,500 P', gas: '6,000 P' },
  { reduction: '15% 이상', electric: '15,000 P', water: '2,000 P', gas: '8,000 P' }
]

const seoulRates = [
  { reduction: '5% 이상 ~ 10% 미만', points: '10,000 P' },
  { reduction: '10% 이상 ~ 15% 미만', points: '30,000 P' },
  { reduction: '15% 이상', points: '50,000 P' }
]
</script>

<style scoped>
/* 전체 레이아웃 스타일 */
.green-card-app {
max-width: 1200px;
margin: 0 auto;
padding: 2rem;
color: #333;
}

/* 공통 섹션 스타일 */
.section {
margin-bottom: 4rem;
background: #fff;
border-radius: 12px;
padding: 2rem;
box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

/* 헤더 스타일 */
.header {
text-align: center;
padding: 3rem 1rem;
background: linear-gradient(135deg, #c9f7e5, #06624a);
color: #ffffff;
border-radius: 12px;
margin-bottom: 3rem;
}

.main-title {
font-size: 2.5rem;
margin-bottom: 1rem;
color: white;
}

.subtitle {
font-size: 1.2rem;
opacity: 0.9;
}

/* 카드 그리드 스타일 */
.cards-grid {
display: flex;
flex-direction: column;
gap: 2rem;
margin-top: 2rem;
}

.card {
position: relative;
padding: 2rem;
border-radius: 8px;
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
transition: transform 0.3s ease;
min-height: 200px;
overflow: hidden;
background: #f5f5f5;
}

.card:hover {
transform: translateY(-5px);
}

.card-image {
position: absolute;
right: 2rem;
top: 50%;
transform: translateY(-50%);
width: 350px;
height: 100%;
}

.card h3 {
margin-bottom: 1rem;
padding-right: 200px;
}

ul {
list-style: none;
padding: 0;
padding-right: 200px;
}

li {
padding: 0.5rem 0;
padding-left: 1.5rem;
position: relative;
}

li::before {
  content: "•";
  position: absolute;
  left: 0;
  color: #40a578;
}

.tab-content {
  margin-top: 0px;
  padding: 1rem;
  background: #ffffff;
  border-radius: 8px;
}

.tab-button {
  position: relative;
  padding: 1rem 2rem;
  font-size: 1.1rem;
  font-weight: 500;
  color: #666;
  background: transparent;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-button::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 100%;
  height: 2px;
  background: transparent;
  transition: background-color 0.3s ease;
}

.tab-button.active {
  color: #344767;
  font-weight: 600;
}

.tab-button.active::after {
  background: #06624a;
}

.content-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin: 2rem 0 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #e5e7eb;
  color: #344767;
}

.content-title:first-child {
  margin-top: 0;
}

.notice-section {
  background: #f5f5f5;
  margin-top: 2rem;
  padding: 1.5rem;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}

.notice-section h3 {
  color: #344767;
  margin-bottom: 1rem;
}

.notice-section ul {
  padding-left: 1.5rem;
}

.notice-section li {
  margin-bottom: 0.5rem;
  line-height: 1.5;
}

.notice-section li.font-bold {
  color: #344767;
}

  /* 테이블 공통 스타일 */
  table {
    width: 100%;
    border-collapse: collapse;
    margin: 1rem 0;
  }
  
  th, td {
    padding: 1rem;
    border: 1px solid #ddd;
    text-align: left;
  }
  
  th {
    background-color: #f5f5f5;
    font-weight: 600;
  }

  h4 {
    margin-top: 20px;
  }
  
  /* 혜택 박스 스타일 */
  .benefit-box {
    background: #f9f9f9;
    padding: 2rem;
    border-radius: 8px;
    margin-bottom: 2rem;
  }
  
  /* 매장 카테고리 스타일 */
  .store-categories {
    display: grid;
    gap: 2rem;
    margin-top: 2rem;
  }
  
  .store-category {
    background: #f9f9f9;
    padding: 1.5rem;
    border-radius: 8px;
  }
  
  .store-tag {
    display: inline-block;
    padding: 0.5rem 1rem;
    background: white;
    border-radius: 20px;
    margin: 0.5rem;
    box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  }
  
  /* 구매 절차 스타일 */
  .steps {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 1rem;
    margin-top: 2rem;
  }
  
  .step {
    flex: 1;
    width: 50px;
    text-align: center;
    padding: 1.5rem;
    background: #f9f9f9;
    border-radius: 8px;
    position: relative;
  }
  
  .step-number {
    width: 30px;
    height: 30px;
    background: #40a578;
    color: white;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 1rem;
  }
  
  /* 에너지 절약 프로그램 스타일 */
  .eco-programs {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 2rem;
    margin-top: 2rem;
  }
  
  .program-card {
    background: #f9f9f9;
    padding: 2rem;
    border-radius: 8px;
  }
  
  .program-region {
    color: #666;
    font-size: 0.9rem;
    margin-bottom: 1rem;
  }

  .section-desc {
  font-size: 1.3rem;
  color: #06624a;
  font-weight: 600;
  text-align: center;
  margin-bottom: 2rem;
  position: relative;
  padding-bottom: 1rem;
}

.section-desc::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 50px;
  height: 2px;
  background: #06624a;
}

.intro-text {
  max-width: 900px;
  margin: 0 auto;
  line-height: 1.8;
  color: #666;
}

.highlight-text {
  background: #f0f9f6;
  border-left: 4px solid #06624a;
  padding: 1rem 1.5rem;
  margin: 1.5rem 0;
  font-weight: 600;
  color: #344767;
  border-radius: 0 8px 8px 0;
}

  
  /* 반응형 디자인을 위한 미디어 쿼리 */
  @media (max-width: 768px) {
    .green-card-app {
      padding: 1rem;
    }
    
    .steps {
      flex-direction: column;
    }
    
    .step {
      min-width: 100%;
    }
    
    .transport-benefits {
      flex-direction: column;
    }

    .card {
    padding-bottom: 160px; /* 모바일에서 이미지 공간 확보 */
  }

  .card-image {
    top: auto;
    bottom: 2rem;
    left: 50%;
    transform: translateX(-50%);
  }

  .card h3,
  .card ul {
    padding-right: 0;
  }

  .tab-button {
    padding: 0.75rem 1rem;
    font-size: 0.9rem;
  }
  
  table {
    font-size: 0.9rem;
  }
  
  th, td {
    padding: 0.75rem;
  }

  .content-title {
    font-size: 1.1rem;
  }

  .notice-section {
    padding: 1rem;
  }

  .contact-info {
    flex-direction: column;
    gap: 0.5rem;
    padding: 1rem;
  }
  
  .phone-icon {
    font-size: 1.5rem;
  }
}
  
  /* 푸터 스타일 */
  .footer {
  text-align: center;
  padding: 2rem;
  margin-top: 4rem;
  background: linear-gradient(135deg, #f8f9fa, #e9ecef);
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
}

.contact-info {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1.5rem;
  background: white;
  border-radius: 50px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.05);
}

.phone-icon {
  font-size: 1.25rem;
  animation: wiggle 1s ease-in-out infinite;
}

.contact-label {
  color: #666;
  font-weight: 500;
}

.phone-number {
  font-weight: 600;
  color: #06624a;
  letter-spacing: 0.5px;
}

@keyframes wiggle {
  0%, 100% {
    transform: rotate(-5deg);
  }
  50% {
    transform: rotate(5deg);
  }
}
  </style>