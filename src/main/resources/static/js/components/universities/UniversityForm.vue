<template>
  <v-layout>
    <v-text-field label="New university" placeholder="Write something" v-model="name" />
    <v-btn @click="save">Save</v-btn>
  </v-layout>
</template>

<script>
import { sendUniversity } from "util/ws";

export default {
  props: ["universities", "universityAttr"],
  data() {
    return {
      name: "",
      id: ""
    };
  },
  watch: {
    universityAttr(newVal, oldVal) {
      this.name = newVal.name;
      this.id = newVal.id;
    }
  },
  methods: {
    save() {
      sendUniversity({ id: this.id, name: this.name });
      this.name = "";
      this.id = "";
    }
  }
};
</script>

<style>
</style>