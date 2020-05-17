<template>
  <v-card class="my-2" v-if="university.name">
    <v-card-text primary-title>
      <user-link
        :user="university.author"
        size="36px"
      ></user-link>
      <div class="pt-3">
        {{ university.name }}
      </div>
    </v-card-text>
    <media v-if="university.link" :university="university"></media>
    <v-card-actions>
      <v-btn @click="edit" text small rounded>Edit</v-btn>
      <v-btn icon @click="del" small>
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
  <comment-list
  :comments="university.comments"
  :university-id="university.id"
  ></comment-list>
  </v-card>
</template>


<script>
import { mapActions } from 'vuex'
import Media from 'components/media/Media.vue'
import CommentList from 'components/comment/CommentList.vue'
import UserLink from 'components/UserLink.vue'


export default {
  props: ["university", "editUniversity"],
  components: { Media, CommentList, UserLink },
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