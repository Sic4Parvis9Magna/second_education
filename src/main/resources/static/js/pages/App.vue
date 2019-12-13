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

export default {
  components: {
    UniversitiesList
  },
  data() {
    return {
      universities: frontendData.universities,
      profile: frontendData.profile
    };
  }
};
</script>


<style>
</style>
