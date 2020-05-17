import Vue from 'vue'
import Vuex from 'vuex'
import universitiesApi from 'api/universities'
import commentApi from 'api/comment'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        universities: universities || [],
        profile,
        ...frontendData
    },
    getters: {
        sortedUniversities: state => (state.universities || []).sort((a, b) => -(a.id - b.id))
    },
    mutations: {
        addUniversityMutation(state, university) {
            state.universities = [
                ...state.universities,
                university
            ]
        },
        updateUniversityMutation(state, university) {
            const updateIndex = state.universities.findIndex(item => item.id === university.id)
            state.universities = [
                ...state.universities.slice(0, updateIndex),
                university,
                ...state.universities.slice(updateIndex + 1)
            ]
        },
        removeUniversityMutation(state, university) {
            const deletionIndex = state.universities.findIndex(item => item.id === university.id)
            if (deletionIndex > -1) {
                state.universities = [
                    ...state.universities.slice(0, deletionIndex),
                    ...state.universities.slice(deletionIndex + 1)
                ]
            }
        },
        addCommentMutation(state, comment) {
            const updateIndex = state.universities.findIndex(item => item.id === comment.universityId)
            const university = state.universities[updateIndex]
            
            if (!university.comments.find(it => it.id === comment.id)) {
                state.universities = [
                    ...state.universities.slice(0, updateIndex),
                    {
                        ...university,
                        comments: [
                            ...university.comments,
                            comment
                        ]
                    },
                    ...state.universities.slice(updateIndex + 1)
                ]
            }
        },
        addUniversityPageMutation(state, universities) {
            const targetUniversities = state.universities
                .concat(universities)
                .reduce((res, val) => {
                    res[val.id] = val
                    return res
                }, {})
            state.universities = Object.values(targetUniversities)
        },
        updateTotalPagesMutation(state, totalPages) {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage) {
            state.currentPage = currentPage
        }
    },
    actions: {
        async addUniversityAction({ commit, state }, university) {
            const result = await universitiesApi.add(university)
            const data = await result.json()
            const index = state.universities.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateUniversityMutation', data)
            } else {
                commit('addUniversityMutation', data)
            }
        },
        async updateUniversityAction({ commit }, university) {
            const result = await universitiesApi.update(university)
            const data = await result.json()
            commit('updateUniversityMutation', data)
        },
        async removeUniversityAction({ commit }, university) {
            const result = await universitiesApi.remove(university.id)
            if (result.ok) {
                commit('removeUniversityMutation', university)
            }
        },
        async addCommentAction({ commit }, comment) {
            const response = await commentApi.add(comment)
            const data = await response.json()
            commit('addCommentMutation', data)
        },
        async loadPageAction({commit, state}) {
            const response = await universitiesApi.page(state.currentPage + 1)
            const data = await response.json()
            commit('addUniversityPageMutation', data.universities)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
        }
    }
})