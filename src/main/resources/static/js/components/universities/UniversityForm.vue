<template>
  <v-layout>
    <v-text-field label="New university" placeholder="Write something" v-model="name" />
    <v-btn @click="save">Save</v-btn>
  </v-layout>
</template>

<script>
import universitiesApi from 'api/universities'

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
      const university = {id: this.id, name: this.name}

      if (this.id) {
          universitiesApi.update(university).then( result => 
          result.json().then(data => {
            const index = this.universities.findIndex(item => item.id === data.id)
            this.universities.splice(index, 1, data)
          })
          )
      } else {
          universitiesApi.add(university).then( result =>
            result.json().then(data => {
              const index = this.universities.findIndex(item => item.id === data.id)
              if (index > -1) {
                  this.universities.splice(index, 1 , data)
              } else {
                this.universities.push(data)
              }
            })
          )
      }

      this.name = "";
      this.id = "";
    }
  }
};
</script>

<style>
</style>