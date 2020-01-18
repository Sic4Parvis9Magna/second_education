<template>
  <v-card class="my-2">
    <v-card-text primary-title>
    <i>({{ university.id }})</i>
    {{ university.name }}
    </v-card-text>
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

export default {
  props: ["university", "editUniversity"],
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