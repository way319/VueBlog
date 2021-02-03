import router from "./router"

// 路由判断登录 根据路由配置文件的参数
router.beforeEach((to, from, next) => {
    // 判断该路由是否需要登录权限
    if (to.matched.some(record => record.meta.requireAuth)) {
        const token = localStorage.getItem("token")
        console.log("--------" + token)

        if (token) {
            if (to.path === '/login') {

            } else {
                next()
            }
        } else {
            next({
                path: '/login'
            })
        }
    } else {
        next()
    }


})