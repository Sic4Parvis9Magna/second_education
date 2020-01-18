<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Second Education</v-toolbar-title>
      <v-spacer />
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
        <universities-list/>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import UniversitiesList from "components/universities/UniversitiesList.vue";
import { addHandler } from "util/ws";

export default {
  components: {
    UniversitiesList
  },
  computed: mapState(['profile']),
  methods: mapMutations(['addUniversityMutation','updateUniversityMutation','removeUniversityMutation']),
  created() {
    addHandler(data => {
      if (data.objectType === "MESSAGE") {
        switch (data.eventType) {
          case "CREATE":
            this.addUniversityMutation(data.body)
            break
          case "UPDATE":
            this.updateUniversityMutation(data.body)
            break;
          case "REMOVE":
            this.removeUniversityMutation(data.body)
            break;
          default:
            console.error(`Unknown event type "${data.eventType}"`);
        }
      } else {
        console.error(`Unknown object type "${data.objectType}"`);
      }
    });
  }
};
</script>


<style>
</style>
