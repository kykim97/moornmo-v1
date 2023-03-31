<template>

    <v-card outlined>
        <v-card-title>
            EmployeeManagementSearch
        </v-card-title>

        <v-card-text>
            <String label="Username" v-model="value.parameters.username" :editMode="editMode"/>
            <String label="Department" v-model="value.parameters.department" :editMode="editMode"/>
            <Boolean label="EmploymentStatus" v-model="value.parameters.employmentStatus" :editMode="editMode"/>
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
        name: 'EmployeeManagementSearchQuery',
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
            this.value.parameters.username = '';
            this.value.parameters.department = '';
            this.value.parameters.employmentStatus = false;
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

