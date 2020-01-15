import Vue from 'vue'
import Vuetify from 'vuetify'
import 'api/resource'
import App from 'pages/App.vue'
import { connect } from './util/ws'
import 'vuetify/dist/vuetify.min.css'

if (frontendData.profile) {
    connect()
}

Vue.use(Vuetify)

const vuetifyOptions = { icons: {
    iconfont: 'md', // 'mdi' || 'mdiSvg' || 'md' || 'fa' || 'fa4'
  },}

new Vue({
    el: '#app',
    render: a => a(App),
    vuetify: new Vuetify(vuetifyOptions)
})