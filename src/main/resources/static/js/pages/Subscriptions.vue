<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list>
                <v-list-item
                    v-for="item in subscriptions"
                    v-bind:key="item.subscriber.id"
                >
                    <user-link
                        :user="item.subscriber"
                    ></user-link>
                    <v-btn
                        @click="changeSubscriptionStatus(item.subscriber.id)"
                    >
                        {{item.active ? 'Dismiss' : 'Approve'}}
                    </v-btn>
                </v-list-item>
            </v-list>
        </v-layout>
    </v-container>
</template>

<script>
import UserLink from 'components/UserLink.vue'
import profileApi from 'api/profile'

export default {
    name: 'Subscriptions',
    components:{UserLink},
    data() {
        return {
            subscriptions: []
        }
    },
    methods: {
        async changeSubscriptionStatus(subscriberId) {
            await profileApi.changeSubscriptionStatus(subscriberId)

            const subscriptionIndex = this.subscriptions.findIndex(item => item.subscriber.id === subscriberId)
            const subscription = this.subscriptions[subscriptionIndex]
           

            this.subscriptions = [
                ...this.subscriptions.slice(0,subscriptionIndex),
                {
                    ...subscription,
                    active: !subscription.active
                },
                ...this.subscriptions.slice(subscriptionIndex+1)
            ]

        },
        async initSubscriptions() {
            const response = await profileApi.subscriberList(this.$store.state.profile.id)
            this.subscriptions = await response.json()
        }

    },

    beforeMount() {
        this.initSubscriptions()
    }

}
</script>

<style>

</style>