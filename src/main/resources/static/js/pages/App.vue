<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Second Education</v-toolbar-title>
      <v-spacer/>
      <span v-if="profile">{{profile.name}}</span>
      <v-btn v-if="profile" icon href="/logout" color="green">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content>
        <v-container v-if="!profile">
          Please pass authorisation here
          <a href="/login">Google</a>
        </v-container>
        <v-container v-if="profile">
          <universities-list :universities="universities" />
        </v-container>
    </v-content>
  </v-app>
</template>

<script>
import UniversitiesList from "components/universities/UniversitiesList.vue";
import { addHandler } from "util/ws";
import { getIndex } from "util/collections";

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
      let index = getIndex(this.universities, data.id);
      if (index > -1) {
        this.universities.splice(index, 1, data);
      } else {
        this.universities.push(data);
      }
    });
  }
};
</script>


<style>
</style>
