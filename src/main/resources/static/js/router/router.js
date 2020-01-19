import Vue from 'vue'
import VueRouter from 'vue-router'
import UniversitiesList from "pages/UniversitiesList.vue";
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: UniversitiesList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '*', component: UniversitiesList }
]

export default new VueRouter({
    mode: 'history',
    routes
})