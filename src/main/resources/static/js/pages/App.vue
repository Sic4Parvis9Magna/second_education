<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Second Education</v-toolbar-title>
      <v-btn v-if="profile" :disabled="$route.path === '/'" @click="showUniversities">Universities</v-btn>
      <v-spacer />
      <v-btn
        v-if="profile"
        :disabled="$route.path === '/profile'"
        @click="showProfile"
      >{{profile.name}}</v-btn>
      <v-btn v-if="profile" icon href="/logout" color="green">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { addHandler } from "util/ws";

export default {
  computed: mapState(["profile"]),
  methods: {
    ...mapMutations([
      "addUniversityMutation",
      "updateUniversityMutation",
      "removeUniversityMutation"
    ]),
    showUniversities() {
      this.$router.push("/");
    },
    showProfile() {
      this.$router.push("/profile");
    }
  },
  created() {
    addHandler(data => {
      if (data.objectType === "MESSAGE") {
        switch (data.eventType) {
          case "CREATE":
            this.addUniversityMutation(data.body);
            break;
          case "UPDATE":
            this.updateUniversityMutation(data.body);
            break;
          case "REMOVE":
            this.removeUniversityMutation(data.body);
            break;
          default:
            console.error(`Unknown event type "${data.eventType}"`);
        }
      } else {
        console.error(`Unknown object type "${data.objectType}"`);
      }
    });
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace("/auth");
    }
  }
};
</script>


<style>
</style>
