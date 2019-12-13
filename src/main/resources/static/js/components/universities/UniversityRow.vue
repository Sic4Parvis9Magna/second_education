<template>
  <div>
    <i>({{ university.id }})</i>
    {{ university.name }}
    <span style="position: absolute; right: 0">
      <input type="button" value="Edit" @click="edit" />
      <input type="button" value="X" @click="del" />
    </span>
  </div>
</template>


<script>
export default {
  props: ["university", "editUniversity", "deleteUniversity", "universities"],
  methods: {
    edit() {
      this.editUniversity(this.university)
    },
    del() {
      this.deleteUniversity(this.university)
    }
  }
}
</script>



<style>
</style>