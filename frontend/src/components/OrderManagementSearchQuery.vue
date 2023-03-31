<template>

    <v-card outlined>
        <v-card-title>
            OrderManagementSearch
        </v-card-title>

        <v-card-text>
            <Number label="Number" v-model="value.parameters.number" :editMode="editMode"/>
            <String label="DeliveryDestination" v-model="value.parameters.deliveryDestination" :editMode="editMode"/>
            <Date label="OrderDate" v-model="value.parameters.orderDate" :editMode="editMode"/>
            <Date label="DeliveryDate" v-model="value.parameters.deliveryDate" :editMode="editMode"/>
            <Date label="AccountDate" v-model="value.parameters.accountDate" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="search"
            >
                Search
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'OrderManagementSearchQuery',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {
                apiPath: '',
                parameters: {}
            },
        }),
        created() {
            this.value.parameters.number = 0;
            this.value.parameters.deliveryDestination = '';
            this.value.parameters.orderDate = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10);
            this.value.parameters.deliveryDate = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10);
            this.value.parameters.accountDate = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10);
        },
        watch: {
        },
        methods: {
            search() {
                this.$emit('search', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

