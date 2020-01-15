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
        <universities-list :universities="universities" />
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import UniversitiesList from "components/universities/UniversitiesList.vue";
import { addHandler } from "util/ws";

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
      if (data.objectType === "MESSAGE") {
        let index = this.universities.findIndex(item => item.id === data.body.id);
        switch (data.eventType) {
          case "CREATE":
          case "UPDATE":
            if (index > -1) {
              this.universities.splice(index, 1, data.body);
            } else {
              this.universities.push(data.body);
            }
            break;
          case "REMOVE":
            this.universities.splice(index, 1);
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
