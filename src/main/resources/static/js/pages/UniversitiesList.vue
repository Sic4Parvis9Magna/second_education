<template>
<v-container>
  <v-layout align-space-around justify-start column>
    <university-form :universityAttr="university" />
    <university-row
      v-for="university in sortedUniversities"
      :key="university.id"
      :university="university"
      :editUniversity="editUniversity"/>
    <lazy-loader></lazy-loader>
  </v-layout>
</v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import UniversityRow from 'components/universities/UniversityRow.vue'
import UniversityForm from 'components/universities/UniversityForm.vue'
import LazyLoader from 'components/LazyLoader.vue'

export default {
  components: {
    UniversityRow, UniversityForm, LazyLoader
  },
  data() {
    return {
      university: null
    }
  },
  computed: mapGetters(['sortedUniversities']),
  methods: {
    editUniversity(university) {
      this.university = university
    }
  }
}
</script>


<style>
</style>