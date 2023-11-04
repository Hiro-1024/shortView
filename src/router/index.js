import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores'

// 用于创建路由实例
// createWebHistory：history模式   地址栏不带#
// createWebHashHistory：hash模式     地址栏带#
const router = createRouter({
  // vite中的环境变量  BASE_URL指代vite.config.js中的base属性
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 登录页
    {
      path: '/login',
      component: () => import('@/views/login/LoginPage.vue')
    },
    {
      path: '/',
      component: () => import('@/views/layout/LayoutContainer.vue'),
      redirect: '/content',
      children: [
        {
          path: '/content',
          component: () => import('@/views/content/ContentManage.vue')
        },
        {
          path: '/user/avatar',
          component: () => import('@/views/user/UserAvatar.vue')
        },
        {
          path: '/user/password',
          component: () => import('@/views/user/UserPassword.vue')
        },
        {
          path: '/user/profile',
          component: () => import('@/views/user/UserProfile.vue')
        }
      ]
    }
  ]
})

// 登录访问拦截:默认直接放行
// 根据返回值决定放行还是拦截
// 1.undefined || true 放行
// 2.false 拦回from的地址页面
// 3.具体的路径或路径对象 拦截到对应的地址
router.beforeEach((to) => {
  // 如果没有token且访问的是非登录页，拦截到login，其他情况正常放行
  const useStore = useUserStore()
  if (!useStore.token && to.path !== 'login') return '/login'

  return true
})

export default router
