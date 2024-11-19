import { createRouter, createWebHistory } from 'vue-router'
import ReportView from '../views/Report.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/report',
      name: 'report',
      component: ReportView
    }
  ],
})

export default router
