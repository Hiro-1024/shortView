import { createPinia } from "pinia";
import persist from 'pinia-plugin-persistedstate'

// pinia独立
const pinia = createPinia()
pinia.use(persist)

export default pinia

import { useUserStore } from './modules/user'
export { useUserStore }

// 接收所有user模块的按需导出
export * from './modules/user.js'