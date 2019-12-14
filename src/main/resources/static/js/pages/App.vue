<template>
  <div>
    <div v-if="!profile">
      Please pass authorisation here
      <a href="/login">Google</a>
    </div>
    <div v-else>
      <div>
        {{profile.name}}&nbsp;
        <a href="/logout">Logout</a>
      </div>
      <universities-list :universities="universities" />
    </div>
  </div>
</template>

<script>
import UniversitiesList from "components/universities/UniversitiesList.vue";
import { addHandler } from 'util/ws'
import { getIndex } from 'util/collections'

export default {
  components: {
    UniversitiesList
  },
  data() {
    return {
      universities: frontendData.universities,
      profile: frontendData.profile
    };
  },
  created() {
    addHandler(data => {
      let index = getIndex(this.universities, data.id)
      if (index > -1) {
        this.universities.splice(index, 1, data)
      } else {
        this.universities.push(data)
      }
    })
  }
};
</script>


<style>
</style>
