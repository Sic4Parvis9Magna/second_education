import Vue from 'vue'

const universities = Vue.resource('/university')

export default {
    add: university => universities.save({}, university),
    update: university => universities.update({id: university.id}, university),
    remove: id => universities.remove({id: id}),
    page: page => Vue.http.get('/university', {params: { page }})
}