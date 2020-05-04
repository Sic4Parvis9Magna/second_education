<template>
  <v-layout>
    <v-text-field
      label="Add comment"
      placeholder="Write something"
      v-model="text"
      @keyup.enter="save"
    />
    <v-btn @click="save">Add</v-btn>
  </v-layout>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'CommentForm',
  props: ['messageId', 'universityId'],
  data() {
    return {
      text: ''
    };
  },
  methods: {
    ...mapActions(['addCommentAction']),
    async save() {
      await this.addCommentAction({
        text: this.text,
        universityId: this.universityId
      });
      this.text = '';
    }
  }
};
</script>

<style>
</style>