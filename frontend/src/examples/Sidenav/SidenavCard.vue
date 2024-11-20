<script setup>
import { defineProps } from 'vue';

defineProps({
  card: {
    type: Object,
    required: true,
    title: String,
    description: String,
    links: {
      type: Array,
      label: String,
      route: String,
      color: String,
    },
  },
});
</script>

<template>
  <div
    v-show="layout !== 'landing'"
    class="card card-plain shadow-none"
    id="sidenavCard"
  >
    <div class="p-3 card-body text-center w-100 pt-0">
      <img
        class="w-50 mx-auto"
        src="@/assets/img/illustrations/icon-documentation.svg"
        alt="sidebar_illustration"
      />

      <h6 class="mb-0 text-dark up">Need Help ?</h6>
      <p class="text-xs font-weight-bold">Please check our guide</p>
    </div>

    <router-link
      to="/ourvision"
      target="_blank"
      class="mb-3 btn btn-dark btn-sm w-100"
      >Our Vision</router-link
    >
    <a
      href="https://www.green-card.co.kr/app/mainPage.do"
      target="_blank"
      class="mb-3 btn btn-success btn-sm w-100"
    >
      Go to Greencard Hompage
    </a>
  </div>
</template>
