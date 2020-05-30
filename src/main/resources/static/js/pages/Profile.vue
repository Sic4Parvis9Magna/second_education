<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
                <div class="title mb-3"> User Profile </div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex class="px-1">
                        <v-layout column>
                            <v-flex>Name: {{profile.name}}</v-flex>
                            <v-flex>Locale: {{profile.locale}}</v-flex>
                            <v-flex v-if="profile.gender">Gender: {{profile.gender}}</v-flex>
                            <v-flex>Last visit: {{profile.lastVisit}}</v-flex>
                            <v-flex>Subscriptions: {{ subscriptionsCount }} </v-flex>
                            <v-flex>Subscribers: {{ subscribersCount }} </v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-btn
                    v-if="!isMyProfile"
                    @click="changeSubscription"
                >
                    {{ isISubscribed ? 'Unsubscribe' : 'Subscribe' }}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import profileApi from 'api/profile'
export default {
    name: 'Profile',
    data() {
        return {
            profile: {}
        }
    },
    computed: {
        isMyProfile() {
            return !this.$route.params.id ||
                this.$route.params.id === this.$store.state.profile.id
        },
        isISubscribed() {
            return this.profile.subscribers &&
                this.profile.subscribers.find(subscription => {
                    return subscription === this.$store.state.profile.id
                }) 
        },
        subscriptionsCount() {
            return this.profile.subscriptions && this.profile.subscriptions.length > 0 ?
            this.profile.subscriptions.length : 0
        },
        subscribersCount() {
            return this.profile.subscribers && this.profile.subscribers.length > 0 ?
            this.profile.subscribers.length : 0
        }
    },
    watch: {
        '$route'() {
            this.updateProfile()
        }
    },
    methods: {
        async changeSubscription() {
            const data = await profileApi.changeSubscription(this.profile.id)
            this.profile = await data.json()
        },
        async updateProfile() {
            const id = this.$route.params.id || this.$store.state.profile.id
            
            const data = await profileApi.get(id)
            this.profile = await data.json()

            this.$forceUpdate()
        }
    },
    beforeMount() {
        this.updateProfile()
    }    
}
</script>

<style scoped>
    img {
        max-width: 100%;
        height: auto;
    }
</style>