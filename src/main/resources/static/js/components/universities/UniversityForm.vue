<template>
  <v-layout>
    <v-text-field 
    label="New university"
    placeholder="Write something"
    v-model="name" 
    @keyup.enter="save"
    />
    <v-btn @click="save">Save</v-btn>
  </v-layout>
</template>

<script>
import { mapActions } from "vuex";

export default {
  props: ["universityAttr"],
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
    ...mapActions(["addUniversityAction", "updateUniversityAction"]),
    save() {
      const university = { id: this.id, name: this.name };

      if (this.id) {
        this.updateUniversityAction(university);
      } else {
        this.addUniversityAction(university);
      }

      this.name = "";
      this.id = "";
    }
  }
};
</script>

<style>
</style>