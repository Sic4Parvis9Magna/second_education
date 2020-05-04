import Vue from 'vue'

const comments = Vue.resource('/comment')

export default {
    add:comment => comments.save({},comment)
}