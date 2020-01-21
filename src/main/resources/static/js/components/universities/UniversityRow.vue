<template>
  <v-card class="my-2">
    <v-card-text primary-title>
    <i>({{ university.id }})</i>
    {{ university.name }}
    </v-card-text>
    <media v-if="university.link" :university="university"></media>
    <v-card-actions>
      <v-btn @click="edit" text small rounded>Edit</v-btn>
      <v-btn icon @click="del" small>
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>


<script>
import { mapActions } from 'vuex'
import Media from 'components/media/Media.vue'

export default {
  props: ["university", "editUniversity"],
  components:{ Media },
  methods: {
    ...mapActions(['removeUniversityAction']),
    edit() {
      this.editUniversity(this.university)
    },
    del() {
      this.removeUniversityAction(this.university)
    }
  }
}
</script>



<style>
</style>