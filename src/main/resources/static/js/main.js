
function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var universityApi = Vue.resource('/university{/name}')

Vue.component('university-form', {
    props: ['universities', 'universityAttr'],
    data: function () {
        return {
            name: '',
            id: ''
        }
    },
    watch: {
        universityAttr: function (newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Write something" v-model="name"/>' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {
            var university = { name: this.name }

            if (this.id) {
                universityApi.update({ id: this.id }, university).then(
                    result => result.json().then(
                        data => {
                            var index = getIndex(this.universities, data.id);
                            this.universities.splice(index, 1, data);
                            this.name = ''
                            this.id = ''
                        }
                    )
                )

            } else {
                universityApi.save({}, university).then(
                    result => result.json().then(data => {
                        this.universities.push(data)
                        this.name = ''
                    })
                )
            }
        }
    }
})

Vue.component('university-row', {
    props: ['university', 'editMethod', 'universities'],
    template: '<div>' +
        '<i>({{ university.id }})</i> {{ university.name }}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.university)
        },
        del: function () {
            universityApi.remove({ id: this.university.id }).then(
                result => {
                    if (result.ok) {
                        this.universities.splice(this.universities.indexOf(this.university), 1)
                    }
                }
            )
        }
    }
})

Vue.component('universities-list', {
    props: ['universities'],
    data: function () {
        return {
            university: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
        '<university-form :universities="universities" :universityAttr="university" />' +
        '<university-row v-for="university in universities" :key="university.id" :university="university" :editMethod="editMethod" :universities="universities" />' +
        '</div>',
    created: function () {
        universityApi.get().then(
            result => result.json().then(
                data => data.forEach(
                    element => this.universities.push(element)
                )
            )
        )
    },
    methods: {
        editMethod: function (university) {
            this.university = university;
        }
    }
})

var app = new Vue({
    el: '#app',
    template: '<universities-list :universities="universities" />',
    data: {
        universities: []
    }
})