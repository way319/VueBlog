import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:'',
    // 反序列化
    userInfo:JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mutations: {
    // set
    SET_TOKEN: (state,token) =>{
      state.token = token
      localStorage.setItem("token",token)
    },
    SET_USERINFO: (state,userInfo) =>{
      state.userInfo = userInfo
      // 只能存字符串 不能存对象 可以序列化
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) =>{
      state.token = ''
      state.userInfo = {}
      localStorage.setItem("token","")
      sessionStorage.setItem("userInfo",JSON.stringify(''))
    }
  },
  getters:{
    // get
    getUser: state => {
      return state.userInfo
    }
  },
  actions: {
  },
  modules: {
  }
})
