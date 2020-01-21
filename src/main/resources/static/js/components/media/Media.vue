<template>
  <v-card>
    <v-flex v-if="type === 'href'" xs12 sm6 offset-sm3>
      <v-img v-if="university.linkCover" :src="university.linkCover" aspect-ratio="2.75"></v-img>
      <v-card-title>
        <div>
          <h3>
            <a :href="university.link">{{university.linkTitle || university.link}}</a>
            <div v-if="university.linkDescription">{{university.linkDescription}}</div>
          </h3>
        </div>
      </v-card-title>
    </v-flex>
    <v-flex v-if="type === 'image'" xs12 sm6 offset-sm3>
      <a :href="university.link">
        <v-img v-if="university.linkCover" :src="university.linkCover" aspect-ratio="2.75"></v-img>
        {{message.link}}
      </a>
    </v-flex>
    <v-flex v-if="type === 'youtube'" xs12 sm6 offset-sm3>
        <you-tube :src="university.link"></you-tube>
    </v-flex>
  </v-card>
</template>

<script>
import YouTube from './YouTube.vue'
export default {
  name: "Media",
  components: { YouTube },
  props: ["university"],
  data() {
    return {
      type: "href"
    };
  },
  beforeMount() {
      if (this.university.link.indexOf('youtu') > -1) {
          this.type = 'youtube'
      } else if (this.university.link.match(/\.(jpeg|jpg|gif|png)$/) !== null) {
        this.type = 'image'
      } else {
        this.type = 'href'
      }
  }
};
</script>

<style>
</style>