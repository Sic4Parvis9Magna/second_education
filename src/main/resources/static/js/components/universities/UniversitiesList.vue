<template>
  <v-layout align-space-around justify-start column>
    <university-form :universities="universities" :universityAttr="university" />
    <university-row
      v-for="university in sortedUniversities"
      :key="university.id"
      :university="university"
      :editUniversity="editUniversity"
      :deleteUniversity="deleteUniversity"
      :universities="universities"
    />
  </v-layout>
</template>

<script>
import UniversityRow from 'components/universities/UniversityRow.vue'
import UniversityForm from 'components/universities/UniversityForm.vue'
import universitiesApi from 'api/universities'

export default {
   components: {
    UniversityRow, UniversityForm
  },
  props: ["universities"],
  data() {
    return {
      university: null
    }
  },
  computed: {
    sortedUniversities() {
      return this.universities.sort((a,b) => -(a.id - b.id))
    }
  },
  methods: {
    editUniversity(university) {
      this.university = university
    },
    deleteUniversity(university) {
      universitiesApi.remove(university.id).then(result => {
        if (result.ok) {
          this.universities.splice(this.universities.indexOf(university),1)
        }
      })
    }
  }
}
</script>


<style>
</style>