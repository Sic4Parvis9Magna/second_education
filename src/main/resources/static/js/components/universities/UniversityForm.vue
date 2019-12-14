<template>
  <div>
    <input type="text" placeholder="Write something" v-model="name" />
    <input type="button" value="Save" @click="save" />
  </div>
</template>

<script>
import { sendUniversity } from 'util/ws'

/*function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}*/

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
      sendUniversity({id: this.id, name: this.name})
      this.name = ''
      this.id = ''
      /*const university = { name: this.name };

      if (this.id) {
        this.$resource('/university{/name}').update({ id: this.id }, university).then(result =>
          result.json().then(data => {
            const index = getIndex(this.universities, data.id);
            this.universities.splice(index, 1, data);
            this.name = '';
            this.id = '';
          })
        );
      } else {
        this.$resource('/university{/name}').save({}, university).then(result =>
          result.json().then(data => {
            this.universities.push(data);
            this.name = '';
          })
        );
      }*/
    }
  }
};
</script>

<style>
</style>